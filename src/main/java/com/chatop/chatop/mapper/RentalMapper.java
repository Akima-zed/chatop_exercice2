package com.chatop.chatop.mapper;

import com.chatop.chatop.dto.ImageDTO;
import com.chatop.chatop.dto.RentalDTO;
import com.chatop.chatop.entity.Rental;

import java.util.stream.Collectors;

/**
 * Mapper utilitaire pour transformer les objets {@link Rental}
 * vers {@link RentalDTO} et inversement. Ne mappe pas les relations complexes.
 */
public class RentalMapper {

    /**
     * Convertit une entité Rental vers un DTO incluant la liste des images.
     *
     * @param rental entité source
     * @return DTO rempli ou null
     */
    public static RentalDTO toDTO(Rental rental) {
        if (rental == null) return null;

        RentalDTO dto = new RentalDTO();
        dto.setId(rental.getId());
        dto.setTitle(rental.getTitle());
        dto.setDescription(rental.getDescription());
        dto.setPricePerDay(rental.getPricePerDay());

        if (rental.getImages() != null) {
            dto.setImages(rental.getImages()
                    .stream()
                    .map(image -> {
                        ImageDTO imgDto = new ImageDTO();
                        imgDto.setId(image.getId());
                        imgDto.setUrl(image.getUrl());
                        // Ne pas inclure rental dans ImageDTO pour éviter la boucle
                        return imgDto;
                    })
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    /**
     * Convertit un DTO RentalDTO vers une entité Rental (sans images).
     *
     * @param dto DTO source
     * @return entité construite ou null
     */
    public static Rental toEntity(RentalDTO dto) {
        if (dto == null) return null;

        Rental rental = new Rental();
        rental.setId(dto.getId());
        rental.setTitle(dto.getTitle());
        rental.setDescription(dto.getDescription());
        rental.setPricePerDay(dto.getPricePerDay());
        return rental;
    }
}
