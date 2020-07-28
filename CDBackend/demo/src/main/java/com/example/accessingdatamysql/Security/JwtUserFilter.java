package com.example.accessingdatamysql.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.accessingdatamysql.Security.SecurityConstants.HEADER_STRING;
import static com.example.accessingdatamysql.Security.SecurityConstants.TOKEN_PREFIX;

@Component
public class JwtUserFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsServiceImpl service;

    @Autowired
    private OnlineCounter onlineCounter;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        // 从request中提取出Authorization Header
        String authorizationHeader = httpServletRequest.getHeader(HEADER_STRING);

        String token = null;
        String userName = null;
        // 如果Authorization Header不为空且有Bearer作为开头
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            token = authorizationHeader.substring(7); // 获取token
            userName = jwtUtil.extractUsername(token); // 调用jwtUtil来从token中解析出用户名
        }
        // 如果解析成功
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 调用UserDetailsServiceImpl从repository中找出该用户
            UserDetails userDetails = service.loadUserByUsername(userName);
            // 核实token与该用户名再一次重新生成的token是否吻合
            if (jwtUtil.validateToken(token, userDetails)) {

                onlineCounter.insertToken(token);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
