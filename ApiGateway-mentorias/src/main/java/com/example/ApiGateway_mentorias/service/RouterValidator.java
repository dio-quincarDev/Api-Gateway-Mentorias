package com.example.ApiGateway_mentorias.service;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {
    public static final List<String> openEndPoints = List.of("/v1/auth/login",
            "/v1/auth",
            "/v1/games");

    public Predicate<ServerHttpRequest> isSecured = serverHttpRequest->
            openEndPoints.stream().noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
}
