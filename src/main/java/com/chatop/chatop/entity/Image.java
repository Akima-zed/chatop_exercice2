package com.chatop.chatop.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité représentant une image associée à une location.
 * Chaque image est stockée avec une URL et rattachée à une entité Rental.
 */
@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;



}
