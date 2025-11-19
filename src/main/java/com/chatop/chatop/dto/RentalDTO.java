package com.chatop.chatop.dto;

import lombok.Data;

import java.util.List;

@Data
public class RentalDTO {
    private Long id;
    private String title;
    private String description;
    private Double pricePerDay;
    private List<ImageDTO> images;
}