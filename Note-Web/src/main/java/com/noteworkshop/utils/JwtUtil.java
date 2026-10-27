package com.noteworkshop.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类：生成 token、解析 token
 *
 * token 结构：header.payload.signature
 *   - payload 里放 userId、username、过期时间
 *   - signature 用密钥签名，防篡改
 *
 * ⚠️ 密钥生产环境必须从环境变量读，不能写死。这里为了 demo 写在代码里。
 */
@Component
public class JwtUtil {

    // 密钥：HS256 要求至少 32 字节（这里 44 字节）
    private static final String SECRET = "note-workshop-jwt-secret-key-2026-change-me!";

    // 有效期：24 小时
    private static final long EXPIRE_MS = 24 * 60 * 60 * 1000L;

    // 把字符串密钥变成 SecretKey 对象（只初始化一次）
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 生成 token
     *
     * @param userId   用户 id，放进 payload 的 sub 里
     * @param username 用户名，放进自定义字段
     */
    public String generate(Integer userId, String username) {
        return Jwts.builder()
                .subject(String.valueOf(userId))                          // sub = userId
                .claim("username", username)                              // 自定义字段
                .issuedAt(new Date())                                     // 签发时间
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_MS))  // 过期时间
                .signWith(key)                                            // 签名
                .compact();                                               // 生成字符串
    }

    /**
     * 解析 token，返回 userId
     *
     * @return 有效则返回 userId；签名错、过期、格式错 → 返回 null
     */
    public Integer parseUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)            // 用同一个密钥验签
                    .build()
                    .parseSignedClaims(token)   // 解析（失败会抛异常）
                    .getPayload();              // 取出 payload

            return Integer.valueOf(claims.getSubject());

        } catch (Exception e) {
            // 签名不对 / 已过期 / 格式错误 —— 都当无效处理
            return null;
        }
    }
}