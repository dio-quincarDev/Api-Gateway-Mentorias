package com.example.authService.Security.Mentorias.services;

import com.example.authService.Security.Mentorias.common.dtos.LoginRequest;
import com.example.authService.Security.Mentorias.common.dtos.TokenResponse;
import com.example.authService.Security.Mentorias.common.dtos.UserRequest;

public interface AuthService {
    TokenResponse createUser(UserRequest userRequest);

    TokenResponse login(LoginRequest loginRequest);
}
