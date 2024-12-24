package com.example.ApiGateway_mentorias.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtUtils {

    @Value("${jwt.secret}")
    private  String secretKey;

    private SecretKey getSigninKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims getClaims(String token){
        try {
            return Jwts.parser()
                    .verifyWith(getSigninKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            System.err.println("Error validando el token JWT: " + e.getMessage());
            throw new IllegalArgumentException("Error validando token JWT: " + e.getMessage(), e);
        }
    }
    public Optional<String> extractUserId(String token) {
        try {
            Claims claims = getClaims(token);
            // Primero intenta obtener userId del claim
            Object userIdClaim = claims.get("userId");
            if (userIdClaim != null) {
                return Optional.of(userIdClaim.toString());
            }// Si no, intenta obtener del subject
            return Optional.ofNullable(claims.getSubject());
        } catch (Exception e) {
            System.err.println("Error extrayendo userId: " + e.getMessage());
            return Optional.empty();
        }
    }

    public boolean isExpired(String token){
        try{
            return getClaims(token).getExpiration().before(new Date());
        }catch(Exception e){
            return true;
        }
    }

}


