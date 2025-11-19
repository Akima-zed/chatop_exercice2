package com.chatop.chatop.mapper;

import com.chatop.chatop.dto.RentalDTO;
import com.chatop.chatop.entity.Rental;

public class RentalMapper {

    /** Transforme une entité Rental en DTO */
    public static RentalDTO toDTO(Rental rental) {
        if (rental == null) return null;

        RentalDTO dto = new RentalDTO();
        dto.setId(rental.getId());
        dto.setTitle(rental.getTitle());
        dto.setDescription(rental.getDescription());
        dto.setPricePerDay(rental.getPricePerDay());
        return dto;
    }

    /** Transforme un DTO RentalDTO en entité Rental */
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
