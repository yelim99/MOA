package com.MOA.backend.global.auth.jwt.service;

import com.MOA.backend.global.auth.jwt.dto.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes());
    }

    public String generateAccessToken(String email, Long userId) {
        long tokenPerioid = 1000L * 60L * 30L;
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPerioid))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String remove(String token) {
        return token.replace("Bearer", "");
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Long extractUserId(String token) {

        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return claims.get("userId", Long.class);

    }
}
