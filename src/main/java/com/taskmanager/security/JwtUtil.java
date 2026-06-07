package com.taskmanager.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        // 0.12.x uses Keys.hmacShaKeyFor with byte array
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)                          // 0.12.x: .subject() not .setSubject()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())               // 0.12.x: no algorithm param needed
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()                             // 0.12.x: .parser() not .parserBuilder()
                .verifyWith(getSigningKey())             // 0.12.x: .verifyWith() not .setSigningKey()
                .build()
                .parseSignedClaims(token)               // 0.12.x: .parseSignedClaims() not .parseClaimsJws()
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
