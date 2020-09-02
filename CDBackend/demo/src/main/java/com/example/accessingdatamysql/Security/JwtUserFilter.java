package com.example.accessingdatamysql.Security;

import com.example.accessingdatamysql.GlobalConstants;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
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
        final String authorizationHeader = httpServletRequest.getHeader(HEADER_STRING);
//        GlobalConstants.printIfDoDebug("In doFilter");
        String userIdStr = null;
        // 如果Authorization Header不为空且有Bearer作为开头
//        GlobalConstants.printIfDoDebug(authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            final String token = authorizationHeader.substring(7); // 获取token
//            GlobalConstants.printIfDoDebug("In parse branch1");
            try {
                userIdStr = jwtUtil.extractUserIdStr(token); // 调用jwtUtil来从token中解析出用户名
            } catch(ExpiredJwtException e) { //这里检测了token是否已过期
                GlobalConstants.printIfDoDebug("JwtUserFilter: received a expired token: " + e.getMessage());
            }
//            GlobalConstants.printIfDoDebug("In parse branch2");
        }
//        else{
//            GlobalConstants.printIfDoDebug("In parse branch3");
//        }
//        GlobalConstants.printIfDoDebug("After parse");
        // 如果解析成功
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (userIdStr != null && securityContext.getAuthentication() == null) {
            // 更新用户最后活跃时间
            onlineCounter.updateUserLastActiveTime(userIdStr);
            // 调用UserDetailsServiceImpl从repository中找出该用户
            final UserDetails userDetails = userDetailsService.loadUserByUsername(userIdStr);
            // 核实token与该用户名再一次重新生成的token是否吻合 <- 应该不用了，以前在这里同时做的过期检测也不用了 by izumi
//            GlobalConstants.printIfDoDebug(userIdStr + "at A vs " + userDetails.getUsername());

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            securityContext.setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
//        GlobalConstants.printIfDoDebug("at D");
    }
}
