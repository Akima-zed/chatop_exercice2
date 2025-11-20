package com.chatop.chatop.auth.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * DTO pour la requête d'enregistrement.
 * Contient le nom, l'email et le mot de passe de l'utilisateur.
 */
@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
