package com.chatop.chatop.controller;

import com.chatop.chatop.dto.RentalDTO;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller REST pour la gestion des locations (rentals).
 * - CRUD complet
 */
@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    /** Récupère toutes les locations */
    @GetMapping
    public List<RentalDTO> getAll() {
        return rentalService.getAll();
    }

    /** Récupère une location par son ID */
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getOne(@PathVariable Long id) {
        RentalDTO rental = rentalService.getOne(id);
        return rental != null ? ResponseEntity.ok(rental) : ResponseEntity.notFound().build();
    }

    /** Crée une nouvelle location */
    @PostMapping
    public ResponseEntity<RentalDTO> create(@RequestBody Rental rental) {
        return ResponseEntity.ok(rentalService.create(rental));
    }

    /** Met à jour une location existante */
    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> update(@PathVariable Long id, @RequestBody Rental rental) {
        RentalDTO updated = rentalService.update(id, rental);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /** Supprime une location */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return rentalService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
