package com.chatop.chatop.service;

import com.chatop.chatop.dto.ImageDTO;
import com.chatop.chatop.entity.Image;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.mapper.ImageMapper;
import com.chatop.chatop.repository.ImageRepository;
import com.chatop.chatop.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepo;
    private final RentalRepository rentalRepo;

    public List<ImageDTO> getAll() {
        return imageRepo.findAll()
                .stream()
                .map(ImageMapper::toDTO)
                .toList();
    }

    public ImageDTO uploadImage(MultipartFile file, Long rentalId) throws IOException {
        Rental rental = getRentalOrThrow(rentalId);
        String filePath = handleFileUpload(null, file);
        Image image = Image.builder().url(filePath).rental(rental).build();
        imageRepo.save(image);
        return ImageMapper.toDTO(image);
    }

    public ImageDTO updateImage(Long id, MultipartFile file, Long rentalId) {
        return imageRepo.findById(id)
                .map(img -> {
                    if (file != null && !file.isEmpty()) {
                        img.setUrl(handleFileUpload(img.getUrl(), file));
                    }
                    if (rentalId != null) {
                        img.setRental(getRentalOrThrow(rentalId));
                    }
                    imageRepo.save(img);
                    return ImageMapper.toDTO(img);
                })
                .orElse(null);
    }

    public boolean deleteImage(Long id) {
        Image img = imageRepo.findById(id).orElse(null);
        if (img == null) return false;

        deleteFileIfExists(img.getUrl());
        imageRepo.delete(img);
        return true;
    }

    // ---------------- Méthodes privées ----------------
    private String handleFileUpload(String oldUrl, MultipartFile file) {
        deleteFileIfExists(oldUrl);
        try {
            String folder = "uploads/";
            Files.createDirectories(Path.of(folder));
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Path.of(folder, filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload du fichier", e);
        }
    }

    private void deleteFileIfExists(String url) {
        if (url == null) return;
        try {
            Files.deleteIfExists(Path.of(url));
        } catch (IOException e) {
            System.err.println("Erreur suppression fichier : " + e.getMessage());
        }
    }

    private Rental getRentalOrThrow(Long rentalId) {
        return rentalRepo.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental introuvable"));
    }
}
