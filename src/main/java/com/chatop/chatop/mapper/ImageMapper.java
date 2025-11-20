package com.chatop.chatop.mapper;

import com.chatop.chatop.dto.ImageDTO;
import com.chatop.chatop.entity.Image;

/**
 * Mapper utilitaire responsable de la conversion entre l’entité {@link Image}
 * et son DTO associé {@link ImageDTO}. Garantit l’isolation des couches
 * API / Persistence.
 */
public class ImageMapper {

    /**
     * Convertit une entité Image vers un DTO.
     *
     * @param image entité source
     * @return DTO construit ou null si input null
     */
    public static ImageDTO toDTO(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        dto.setRentalId(image.getRental() != null ? image.getRental().getId() : null);
        return dto;
    }
}

