package com.gateway.service.filters;

import com.common.component.component.TokenManager;
import com.common.data.authentication.exception.ForbiddenException;
import com.common.data.authentication.exception.JWTParsingException;
import com.common.redis.content.RedisAuthenticationConstant;
import com.common.redis.service.RedisService;
import com.gateway.service.configuration.AuthenticationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@SuppressWarnings("all")
public class AuthenticationGlobalFilter implements GlobalFilter, Ordered {
    @Autowired
    private final TokenManager tokenManager;
    private final RedisService redisService;
    private final AuthenticationProperties authenticationProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (isExclude(path) || path.startsWith("/authentication/")) {
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("Authorization");
        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        try {
            String tokenBody = token.substring(7);
            tokenManager.validateToken(tokenBody);
            Map<String, String> tokenPayload = tokenManager.extractUserDetails(tokenBody);
            return exchange.getResponse().setComplete();
        } catch (Exception exception) {
            if (exception instanceof JWTParsingException) {
                throw new ForbiddenException(exception.getMessage());
            } else {
                throw new RuntimeException(exception.getMessage());
            }
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private boolean isExclude(String path) {
        for (String excludePath : authenticationProperties.getExcludePaths()) {
            if (antPathMatcher.match(excludePath, path)) {
                return true;
            }
        }
        return false;
    }
}
