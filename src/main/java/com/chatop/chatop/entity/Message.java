package com.chatop.chatop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Entité représentant un message échangé dans le cadre d’une location.
 * Un message est toujours lié à un utilisateur et à une location.
 */
@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    /**
     * Utilisateur ayant envoyé le message.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Location concernée par le message.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private Rental rental;

    /**
     * Date de création enregistrée automatiquement au moment de l’envoi.
     */
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
