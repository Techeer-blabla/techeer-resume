package com.techeer.backend.api.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    //@Value(value="${jwt.secretKey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        secretKey = "BIFqTtgjiZcSss1fL2LB9lmWF1chdiMO";
        System.out.println("Loaded secretKey: " + secretKey);
    }

    private final Key key = Keys.hmacShaKeyFor( "BIFqTtgjiZcSss1fL2LB9lmWF1chdiMO".getBytes());
    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 minutes
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key) // 키 설정
                .build() // 빌드 호출
                .parseClaimsJws(token) // 여기서 토큰 파싱
                .getBody(); // Claims 객체 가져오기

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser() // parserBuilder 사용
                    .setSigningKey(key) // 키 설정
                    .build() // 빌드 호출
                    .parseClaimsJws(token); // 여기서 토큰 파싱
            return true;
        } catch (Exception e) {
            return false; // Return false if the token is invalid
        }
    }
}
