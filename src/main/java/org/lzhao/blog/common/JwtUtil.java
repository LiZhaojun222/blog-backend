package org.lzhao.blog.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // 密钥（生产环境要从配置文件读取）
    private static final String SECRET = "your-secret-key-must-be-at-least-32-characters-long";

    // 过期时间：7 天
    private static final long EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ 手动创建 Logger
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 生成 Token
     */
    public String generateToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        log.info("生成 Token，userId={}", userId);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    /**
     * 解析 Token
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 验证 Token 是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
