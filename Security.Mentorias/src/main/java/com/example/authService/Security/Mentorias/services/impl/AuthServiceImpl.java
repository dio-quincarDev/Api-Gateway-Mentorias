package com.example.authService.Security.Mentorias.services.impl;

import com.example.authService.Security.Mentorias.common.dtos.LoginRequest;
import com.example.authService.Security.Mentorias.common.dtos.TokenResponse;
import com.example.authService.Security.Mentorias.common.dtos.UserRequest;
import com.example.authService.Security.Mentorias.common.entities.UserModel;
import com.example.authService.Security.Mentorias.repositories.UserRepository;
import com.example.authService.Security.Mentorias.services.AuthService;
import com.example.authService.Security.Mentorias.services.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TokenResponse createUser(UserRequest userRequest) {
        return Optional.of(userRequest)
                .map(this::mapToEntity)
                .map(userRepository::save)
                .map(userCreated-> jwtService.generateToken(userCreated.getId()))
                .orElseThrow(() -> new RuntimeException("Error creating user"));
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        UserModel user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("user not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }
        return jwtService.generateToken(user.getId());
    }

    private UserModel mapToEntity (UserRequest userRequest) {
        return UserModel.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role("USER")
                .build();
    }

}
