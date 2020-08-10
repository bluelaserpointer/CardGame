package com.example.accessingdatamysql.Security;

import io.jsonwebtoken.ExpiredJwtException;
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
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private OnlineCounter onlineCounter;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        // 从request中提取出Authorization Header
        String authorizationHeader = httpServletRequest.getHeader(HEADER_STRING);
//        System.out.println("In doFilter");
        String token = null;
        String userName = null;
        // 如果Authorization Header不为空且有Bearer作为开头
//        System.out.println(authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            token = authorizationHeader.substring(7); // 获取token
//            System.out.println("In parse branch1");
            try {
                userName = jwtUtil.extractUsername(token); // 调用jwtUtil来从token中解析出用户名
            } catch(ExpiredJwtException e) {
                System.out.println("JwtUserFilter: received a expired token: " + e.getMessage());
            }
//            System.out.println("In parse branch2");
        }
//        else{
//            System.out.println("In parse branch3");
//        }
//        System.out.println("After parse");
        // 如果解析成功
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 调用UserDetailsServiceImpl从repository中找出该用户
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            // 核实token与该用户名再一次重新生成的token是否吻合
            if (jwtUtil.validateToken(token, userDetails)) {
//                System.out.println("Logged in");
                onlineCounter.insertToken(token);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
//                System.out.println("Logged out");
//                userLoginRecordService.userLogout(userService.getOneUserByUserName(userName).getUserId(),2);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
