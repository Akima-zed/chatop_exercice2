package com.chatop.chatop.dto;

import lombok.Data;

/**
 * DTO exposant les informations essentielles d’un utilisateur.
 * Ne contient jamais le mot de passe pour des raisons de sécurité.
 */
@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
}