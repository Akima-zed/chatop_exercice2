package com.chatop.chatop.dto;

import lombok.Data;

/**
 * DTO représentant une image associée à une location.
 * Permet d'exposer une URL publique au front-end sans retourner l'entité complète.
 */
@Data
public class ImageDTO {
    private Long id;
    private String url;
    private Long rentalId;
}
