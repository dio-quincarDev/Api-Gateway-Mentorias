package com.example.ApiGateway_mentorias.service;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouterValidator validator;
    private final JwtUtils jwtUtils;

    public AuthenticationFilter(RouterValidator validator, JwtUtils jwtUtils){
        super(Config.class);
        this.validator = validator;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public GatewayFilter apply(Config config){
        return ((exchange, chain) -> {
            ServerHttpRequest request =  exchange.getRequest();

            if(validator.isSecured.test(request)){
                // Detailed logging for secured routes
                System.out.println("Secured route accessed: " + request.getPath());

                if (authMissing(request)){
                    System.err.println("Authorization header missing");
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                try {
                    String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                    System.out.println("Full Authorization Header: " + authHeader);

                    if(!authHeader.startsWith("Bearer ")){
                        System.err.println("Invalid Authorization header format");
                        return onError(exchange, HttpStatus.UNAUTHORIZED);
                    }

                    String token = authHeader.substring(7);
                    System.out.println("Extracted Token: " + token);

                    // Validate token
                    if (jwtUtils.isExpired(token)){
                        System.err.println("Token has expired");
                        return onError(exchange, HttpStatus.UNAUTHORIZED);
                    }

                    // Extract and validate userId
                    Optional<String> userIdOptional = jwtUtils.extractUserId(token);
                    if(userIdOptional.isEmpty()){
                        System.err.println("Unable to extract userId from token");
                        return onError(exchange, HttpStatus.FORBIDDEN);
                    }

                    String userId = userIdOptional.get();
                    System.out.println("Extracted UserId: " + userId);
                    // Mutate request with userId
                    ServerHttpRequest mutatedRequest = request.mutate()
                            .header("X-User-Id", userId)
                            .build();

                    return chain.filter(exchange.mutate().request(mutatedRequest).build());

                } catch (Exception e) {
                    System.err.println("Authentication error: " + e.getMessage());
                    e.printStackTrace();
                    return onError(exchange, HttpStatus.FORBIDDEN);
                }
            }

            return chain.filter(exchange);
        });
    }

    private Mono <Void> onError(ServerWebExchange exchange, HttpStatus httpStatus){
        ServerHttpResponse response = (ServerHttpResponse) exchange.getResponse();
        response.setStatusCode(httpStatus);
        return  response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request){
        return !request.getHeaders().containsKey("Authorization");
    }

    public static class  Config{}
}
