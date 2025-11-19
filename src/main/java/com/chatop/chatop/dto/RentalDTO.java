package com.chatop.chatop.dto;

import lombok.Data;

@Data
public class RentalDTO {
    private Long id;
    private String title;
    private String description;
    private Double pricePerDay;
}