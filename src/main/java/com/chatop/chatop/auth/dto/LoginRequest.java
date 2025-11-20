package com.chatop.chatop.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO pour la requête de connexion.
 * Contient l'email et le mot de passe de l'utilisateur.
 */
@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}