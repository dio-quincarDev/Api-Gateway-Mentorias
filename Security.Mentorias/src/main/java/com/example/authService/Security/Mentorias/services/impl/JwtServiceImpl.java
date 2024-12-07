package com.example.authService.Security.Mentorias.services.impl;

import com.example.authService.Security.Mentorias.common.dtos.TokenResponse;
import com.example.authService.Security.Mentorias.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private final SecretKey secretKey;
    private static final long EXPIRATION_TIME = 864000000;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
        if(secret.getBytes().length <32){
            throw new IllegalArgumentException("Secret key must be at least 32 characters long.");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public TokenResponse generateToken(Long userId) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);


        String token = Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();


        return TokenResponse.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.err.println("Error parsing JWT: " + e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }

    @Override
    public boolean isExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch(Exception e){
            return true;
        }

    }

    @Override public Integer extractUserId(String token) {
        try {
            return Integer.parseInt(getClaims(token).getSubject());
        } catch (IllegalArgumentException e) {
            System.err.println("Error validando token JWT: " + e.getMessage());
            System.err.println("Error extrayendo token" + e.getMessage());
            return null;
        }
    }
}
