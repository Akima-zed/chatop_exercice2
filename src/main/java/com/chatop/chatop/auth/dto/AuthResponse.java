package com.chatop.chatop.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * DTO pour la réponse d'authentification.
 * Contient le token JWT renvoyé après login ou register.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}