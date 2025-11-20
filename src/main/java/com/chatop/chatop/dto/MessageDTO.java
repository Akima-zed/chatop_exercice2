package com.chatop.chatop.dto;

import lombok.Data;
import java.util.Date;

/**
 * DTO transportant un message échangé entre un utilisateur et un propriétaire.
 * Utilisé pour l'affichage ou l'historique des conversations.
 */
@Data
public class MessageDTO {
    private Long id;
    private String content;
    private Long rentalId;
    private Long userId;
    private Date createdAt;
}
