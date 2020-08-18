package com.example.accessingdatamysql.Security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.accessingdatamysql.Security.SecurityConstants.EXPIRATION_TIME;
import static com.example.accessingdatamysql.Security.SecurityConstants.SECRET;

//Utilization's regarding the jwt dependency
@Service
public class JwtUtil {

    private static final JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET);

    private Claims extractClaim(String token) throws ExpiredJwtException {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String extractUserIdStr(String token) throws ExpiredJwtException {
        return extractClaim(token).getSubject();
    }

//    public Date extractExpiration(String token) {
//        return extractExpiration(extractClaim(token));
//    }
//
//    public Date extractExpiration(Claims claims) {
//        return claims.getExpiration();
//    }
//
//    private Boolean isTokenExpired(Claims claims) {
//        return extractExpiration(claims).before(new Date());
//    }

    public String generateToken(String username) {
        // setClaim, setIssuedAt is useless, deleted. by izumi
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }
//
//    public Boolean validateToken(String token, String userIdStr) {
//        final Claims claims = extractClaim(token);
//        return extractUserIdStr(claims).equals(userIdStr) && !isTokenExpired(claims);
//    }
}
