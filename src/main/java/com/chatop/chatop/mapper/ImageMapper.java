package com.chatop.chatop.mapper;

import com.chatop.chatop.dto.ImageDTO;
import com.chatop.chatop.entity.Image;

public class ImageMapper {

    public static ImageDTO toDTO(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        dto.setRentalId(image.getRental() != null ? image.getRental().getId() : null);
        return dto;
    }
}

