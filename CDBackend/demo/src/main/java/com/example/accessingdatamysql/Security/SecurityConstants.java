package com.example.accessingdatamysql.Security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// This class contains all four constants referenced by the JWTAuthenticationFilter class.
public class SecurityConstants {
    public static final String SECRET = "cardgameproject"; // secret used in jwtUtil
//    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours expiration date for token (JwtUtil)
    public static final long EXPIRATION_TIME = 1000 * 10; // 10 hours expiration date for token (JwtUtil)
    public static final String TOKEN_PREFIX = "Bearer "; // Token header prefix
    public static final String HEADER_STRING = "Authorization"; // Header that contains token
    public static Map countMap = new ConcurrentHashMap<String,Object>();

}
