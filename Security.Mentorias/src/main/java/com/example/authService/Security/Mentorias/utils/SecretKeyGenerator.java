package com.example.authService.Security.Mentorias.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static String generateSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void main(String[] args) {
        System.out.println(generateSecretKey());
    }
}
