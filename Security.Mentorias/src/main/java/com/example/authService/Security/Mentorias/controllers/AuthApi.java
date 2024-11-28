package com.example.authService.Security.Mentorias.controllers;

import com.example.authService.Security.Mentorias.common.constants.ApiPathConstants;
import com.example.authService.Security.Mentorias.common.dtos.TokenResponse;
import com.example.authService.Security.Mentorias.common.dtos.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(ApiPathConstants.V1_ROUTE + ApiPathConstants.AUTH_ROUTE)
public interface  AuthApi {
    @PostMapping("/register")
    ResponseEntity<TokenResponse> createUser (@RequestBody @Valid UserRequest userRequest);

    @GetMapping
    ResponseEntity<String> getUser(@RequestAttribute(name="X-User-Id") @Valid String userId);
}
