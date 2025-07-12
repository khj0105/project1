package com.example.project1.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final int jwtExpirationMs;
    private final long jwtEmailExpirationMs;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") int jwtExpirationMs,
            @Value("${jwt.email-expiration-ms}") long jwtEmailExpirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtEmailExpirationMs = jwtEmailExpirationMs;
    }

    // 로그인용 토큰 생성
    public String generateJwtToken(String username, String role) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 이메일 인증용 토큰 생성
    public String generateEmailToken(String email) {
        return Jwts.builder()
                .claim("username", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtEmailExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Bearer 제거
    public String removeBearer(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid JWT token format");
        }
        return bearerToken.substring("Bearer ".length());
    }

    // 토큰 유효성 검사
    public boolean isValidToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Claims 추출
    public Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    // username(email) 추출
    public String getUsernameFromJwt(String token) {
        return getClaims(token).get("username", String.class);
    }

    // role 추출
    public String getRoleFromJwt(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Request에서 이메일 추출
    public String getEmailFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = removeBearer(bearerToken);
            if (isValidToken(token)) {
                return getUsernameFromJwt(token);
            }
        }
        throw new RuntimeException("유효하지 않은 토큰입니다.");
    }

    public int getExpiration() {
        return jwtExpirationMs;
    }
}
