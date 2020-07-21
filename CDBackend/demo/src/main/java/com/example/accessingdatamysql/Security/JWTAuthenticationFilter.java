package com.example.accessingdatamysql.Security;

import com.auth0.jwt.JWT;
// import com.auth0.samples.authapi.springbootauthupdated.user.ApplicationUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.accessingdatamysql.Security.SecurityConstants.EXPIRATION_TIME;
import static com.example.accessingdatamysql.Security.SecurityConstants.HEADER_STRING;
import static com.example.accessingdatamysql.Security.SecurityConstants.SECRET;
import static com.example.accessingdatamysql.Security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // Where we parse the user's credentials and issue them to the Authentication
    // Manager.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            com.example.accessingdatamysql.entity.User creds = new ObjectMapper().readValue(req.getInputStream(),
                    com.example.accessingdatamysql.entity.User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUserName(),
                    creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // The method called when a user successfully logs in. We use this method to
    // generate a JWT for this user.
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        // 这里的User是org.springframework.security.core.userdetails.User，不是User实体集
        String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}