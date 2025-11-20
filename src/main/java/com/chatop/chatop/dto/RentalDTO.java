package com.chatop.chatop.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO représentant une location, incluant ses informations principales
 * ainsi que la liste de ses images associées.
 */
@Data
public class RentalDTO {
    private Long id;
    private String title;
    private String description;
    private Double pricePerDay;
    private List<ImageDTO> images;
}