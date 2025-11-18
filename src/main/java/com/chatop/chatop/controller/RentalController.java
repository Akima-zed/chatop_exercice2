package com.chatop.chatop.controller;

import com.chatop.chatop.entity.Rental;
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

    @GetMapping
    public List<Rental> getAll() {
        return rentalRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getOne(@PathVariable Long id) {
        return rentalRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Rental create(@RequestBody Rental rental) {
        return rentalRepo.save(rental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> update(@PathVariable Long id, @RequestBody Rental rental) {
        return rentalRepo.findById(id)
                .map(r -> {
                    r.setTitle(rental.getTitle());
                    r.setDescription(rental.getDescription());
                    r.setPricePerDay(rental.getPricePerDay());
                    return ResponseEntity.ok(rentalRepo.save(r));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!rentalRepo.existsById(id)) return ResponseEntity.notFound().build();
        rentalRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
