package com.hosiky.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    /**
     * 生成JWT令牌
     */
    public static String createJwt(String secretKey, long ttlMillis, Map<String, Object> claims) {
        try {
            // 使用SHA-256哈希处理密钥
            byte[] keyBytes = getHashedKeyBytes(secretKey);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);

            long expMillis = System.currentTimeMillis() + ttlMillis;
            Date exp = new Date(expMillis);

            return Jwts.builder()
                    .signWith(key)
                    .claims(claims)
                    .expiration(exp)
                    .compact();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate JWT key", e);
        }
    }

    /**
     * 解析JWT令牌
     */
    public static Claims parseJWT(String secretKey, String token) {
        try {
            byte[] keyBytes = getHashedKeyBytes(secretKey);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);

            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to parse JWT", e);
        }
    }

    /**
     * 使用SHA-256哈希处理密钥
     */
    private static byte[] getHashedKeyBytes(String secretKey) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}