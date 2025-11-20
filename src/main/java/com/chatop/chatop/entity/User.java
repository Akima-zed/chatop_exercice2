package com.chatop.chatop.entity;


import jakarta.persistence.*;
import lombok.*;

/**
 * Entité représentant un utilisateur de la plateforme.
 * Les informations d’authentification sont stockées de manière sécurisée.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}
