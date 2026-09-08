package com.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // jjwt 0.12 要求 HS256 密钥至少 32 字节，旧的 7 字节密钥不再合法
    private static final String signKey = "ITHEIMA_DEMO_KEY_FOR_TLIAS_LEARNING_2026";
    private static final Long expire = 43200000L;

    private static final SecretKey KEY = Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));

    /**
     * 生成JWT令牌
     * @param claims 自定义声明（如用户id、用户名）
     * @return JWT令牌字符串
     */
    public static String generateJwt(Map<String,Object> claims){
        return Jwts.builder()
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + expire))
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
