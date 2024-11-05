package com.MOA.backend.global.auth.jwt.service;

import com.MOA.backend.global.auth.jwt.dto.JwtProperties;
import io.jsonwebtoken.*;
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
        long tokenPeriod = 1000L * 60L * 30L;
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String remove(String token) {
        return token.replace("Bearer ", "");
    }

    public boolean verifyToken(String token) {
        try {
            String pureToken = remove(token);
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(pureToken);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (ExpiredJwtException e) {
            log.error("Token has expired");
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
            return false;
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT token");
            return false;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature");
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid");
            return false;
        }
    }


    public Long extractUserId(String token) {
        // 'Bearer ' 접두사 제거
        String pureToken = remove(token);


        if (!verifyToken(pureToken)) {
            throw new MalformedJwtException("JWT Token is not valid.");
        }

        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(pureToken).getBody();
        return claims.get("userId", Long.class);
    }
}
