package com.chatop.chatop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * Entité représentant une location (bien immobilier).
 * Contient les informations principales + la liste des images associées.
 */
@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private double pricePerDay;

    /**
     * Liste des images associées au bien.
     * Relation OneToMany : une location possède plusieurs images.
     * Le cascade ALL permet d’ajouter/supprimer les images automatiquement.
     */
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
}
