package com.chatop.chatop.controller;

import com.chatop.chatop.dto.ImageDTO;
import com.chatop.chatop.entity.Image;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.mapper.ImageMapper;
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
    public List<ImageDTO> getAll() {
        return imageRepo.findAll()
                .stream()
                .map(ImageMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam("file") MultipartFile file,
                                                @RequestParam Long rentalId) throws IOException {

        Rental rental = rentalRepo.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental introuvable"));

        String folder = "uploads/";
        Files.createDirectories(Paths.get(folder));

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(folder, filename);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Image image = Image.builder()
                .url(filePath.toString())
                .rental(rental)
                .build();

        imageRepo.save(image);

        return ResponseEntity.ok(ImageMapper.toDTO(image));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> update(@PathVariable Long id, @RequestBody ImageDTO dto) {
        return imageRepo.findById(id)
                .map(img -> {

                    img.setUrl(dto.getUrl());

                    if (dto.getRentalId() != null) {
                        Rental rental = rentalRepo.findById(dto.getRentalId())
                                .orElseThrow(() -> new RuntimeException("Rental introuvable"));
                        img.setRental(rental);
                    }

                    imageRepo.save(img);

                    return ResponseEntity.ok(ImageMapper.toDTO(img));
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
