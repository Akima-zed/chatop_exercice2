package com.chatop.chatop.controller;

import com.chatop.chatop.entity.Image;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.repository.ImageRepository;
import com.chatop.chatop.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepo;
    private final RentalRepository rentalRepo;

    @GetMapping
    public List<Image> getAll() {
        return imageRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file,
                                             @RequestParam Long rentalId) throws IOException {
        // Vérifie le rental
        Rental rental = rentalRepo.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental introuvable"));

        // Crée le dossier si nécessaire
        String folder = "uploads/";
        Files.createDirectories(Paths.get(folder));

        // Nom unique pour éviter collisions
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(folder, filename);

        // Sauvegarde physique
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Création de l'entité Image
        Image image = Image.builder()
                .url(filePath.toString())  // ou juste filename si tu veux
                .rental(rental)
                .build();

        imageRepo.save(image);
        return ResponseEntity.ok(image);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Image> update(@PathVariable Long id, @RequestBody Image updatedImage) {
        return imageRepo.findById(id)
                .map(img -> {
                    img.setUrl(updatedImage.getUrl());

                    if (updatedImage.getRental() != null && updatedImage.getRental().getId() != null) {
                        Rental rental = rentalRepo.findById(updatedImage.getRental().getId())
                                .orElseThrow(() -> new RuntimeException("Rental introuvable"));
                        img.setRental(rental);
                    }

                    return ResponseEntity.ok(imageRepo.save(img));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!imageRepo.existsById(id)) return ResponseEntity.notFound().build();
        imageRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
