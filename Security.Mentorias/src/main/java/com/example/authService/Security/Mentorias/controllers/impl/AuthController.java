package com.example.authService.Security.Mentorias.controllers.impl;

import com.example.authService.Security.Mentorias.common.constants.ApiPathConstants;
import com.example.authService.Security.Mentorias.common.dtos.LoginRequest;
import com.example.authService.Security.Mentorias.common.dtos.TokenResponse;
import com.example.authService.Security.Mentorias.common.dtos.UserRequest;
import com.example.authService.Security.Mentorias.controllers.AuthApi;
import com.example.authService.Security.Mentorias.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPathConstants.V1_ROUTE + ApiPathConstants.AUTH_ROUTE)
public class AuthController implements AuthApi {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<TokenResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(authService.createUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        System.out.println("Login request received for email: " + loginRequest.getEmail());
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Override
    public ResponseEntity<String> getUser(String userId) {
        return ResponseEntity.ok(userId);
    }
}
