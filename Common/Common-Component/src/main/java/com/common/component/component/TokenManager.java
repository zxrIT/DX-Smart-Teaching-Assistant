package com.common.component.component;

import com.common.data.base.entity.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
@SuppressWarnings("all")
@RequiredArgsConstructor
public class TokenManager {
    private final static Logger logger = LoggerFactory.getLogger(TokenManager.class);
    private final static String secretKey = "DX-Smart-Teaching-Assistant-Secret";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(String id, String username, Integer roleId, List<UserRole> userRoles) {
        long expirationTime = 1000 * 60 * 60 * 24;
        logger.info("Token created for user: {} (ID: {}), Role: {}", username, id, roleId);
        return Jwts.builder()
                .subject("DX-Smart-Teaching-Assistant")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("id", id)
                .claim("username", username)
                .claim("roleId", roleId)
                .claim("userRoles", userRoles)
                .signWith(getSigningKey())
                .compact();
    }
}