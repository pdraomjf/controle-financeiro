package com.augustodev.api_controle_financeiro.security;

import com.augustodev.api_controle_financeiro.models.TipoUsuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtUtil {

    private final SecretKey SECRET;
    private final long EXPIRATION;

    public JwtUtil(
            @Value("${JWT_SECRET}") String secret,
            @Value("${EXPIRATION_TIME}") long expiration) {
        this.SECRET = Keys.hmacShaKeyFor(secret.getBytes());
        this.EXPIRATION = expiration;
    }

    // Gera token com ID do usuário e a role
    public String generateToken(UUID userId, String role) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET)
                .compact();
    }

    // Extrai ID do usuário do token
    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(SECRET)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Extrai a role do token
    public String getRole(String token) {
        return (String) Jwts.parser()
                .verifyWith(SECRET)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role");
    }

    // Valida o token (assinatura + expiração)
    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
