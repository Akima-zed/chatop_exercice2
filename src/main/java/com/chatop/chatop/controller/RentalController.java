package com.chatop.chatop.controller;

import com.chatop.chatop.dto.RentalDTO;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.mapper.RentalMapper;
import com.chatop.chatop.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalRepository rentalRepo;

    // Lister tous les rentals
    @GetMapping
    public List<RentalDTO> getAll() {
        return rentalRepo.findAll()
                .stream()
                .map(RentalMapper::toDTO)
                .toList();
    }

    // Obtenir un rental par id
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getOne(@PathVariable Long id) {
        return rentalRepo.findById(id)
                .map(RentalMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un rental
    @PostMapping
    public ResponseEntity<RentalDTO> create(@RequestBody Rental rental) {
        Rental saved = rentalRepo.save(rental);
        return ResponseEntity.ok(RentalMapper.toDTO(saved));
    }

    // Mettre à jour un rental
    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> update(@PathVariable Long id, @RequestBody Rental rental) {
        return rentalRepo.findById(id)
                .map(r -> {
                    r.setTitle(rental.getTitle());
                    r.setDescription(rental.getDescription());
                    r.setPricePerDay(rental.getPricePerDay());
                    Rental updated = rentalRepo.save(r);
                    return ResponseEntity.ok(RentalMapper.toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un rental
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!rentalRepo.existsById(id)) return ResponseEntity.notFound().build();
        rentalRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
