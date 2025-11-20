package com.chatop.chatop.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

/**
 * Classe utilitaire pour la gestion des JWT.
 * - Génération de token
 * - Validation
 * - Extraction des informations (email / username)
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    /** Durée de validité : 24h */
    private final long EXPIRATION = 24 * 60 * 60 * 1000; // 24h

    //** Génère la clé de signature à partir du secret */
    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    //** Génère un JWT pour un email donné */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /** Extrait le username/email depuis le JWT */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /** Vérifie la validité d'un token pour un UserDetails */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /** Vérifie si le token est expiré */
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /** Extrait tous les claims du JWT */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
