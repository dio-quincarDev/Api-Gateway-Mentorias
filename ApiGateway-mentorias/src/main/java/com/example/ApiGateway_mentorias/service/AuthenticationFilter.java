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
                if (authMissing(request)){
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                System.out.println("Header Authorization: " + authHeader);
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }else{
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }
                System.out.println("Token JWT: " + authHeader);
                if (jwtUtils.isExpired(authHeader)){
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                String userId = jwtUtils.extractUserId(authHeader).toString();

                System.out.println("UserId extraído: " + userId);
                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("userIdRequest", userId)
                        .build();

                exchange = exchange.mutate().request(mutatedRequest).build();

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
