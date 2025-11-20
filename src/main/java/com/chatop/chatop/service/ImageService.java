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

/**
 * Service de gestion des images liées aux locations.
 * <p>
 * Fournit les opérations CRUD pour les entités Image ainsi que la gestion des fichiers physiques.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepo;
    private final RentalRepository rentalRepo;

    /**
     * Récupère toutes les images.
     *
     * @return une liste de DTO d'images
     */
    public List<ImageDTO> getAll() {
        return imageRepo.findAll()
                .stream()
                .map(ImageMapper::toDTO)
                .toList();
    }

    /**
     * Upload une nouvelle image pour une location donnée.
     *
     * @param file     le fichier image à uploader
     * @param rentalId l'identifiant de la location associée
     * @return le DTO de l'image créée
     * @throws IOException si une erreur survient lors de l'écriture du fichier
     */
    public ImageDTO uploadImage(MultipartFile file, Long rentalId) throws IOException {
        Rental rental = getRentalOrThrow(rentalId);
        String filePath = handleFileUpload(null, file);
        Image image = Image.builder().url(filePath).rental(rental).build();
        imageRepo.save(image);
        return ImageMapper.toDTO(image);
    }

    /**
     * Met à jour une image existante.
     *
     * @param id       l'identifiant de l'image à mettre à jour
     * @param file     le nouveau fichier image (optionnel)
     * @param rentalId l'identifiant de la location associée (optionnel)
     * @return le DTO de l'image mise à jour ou null si l'image n'existe pas
     */
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

    /**
     * Supprime une image existante et son fichier associé.
     *
     * @param id l'identifiant de l'image à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteImage(Long id) {
        Image img = imageRepo.findById(id).orElse(null);
        if (img == null) return false;

        deleteFileIfExists(img.getUrl());
        imageRepo.delete(img);
        return true;
    }

    // ---------------- Méthodes privées ----------------

    /**
     * Gère l'upload d'un fichier et supprime l'ancien fichier si nécessaire.
     *
     * @param oldUrl ancien chemin du fichier (optionnel)
     * @param file   fichier à uploader
     * @return le chemin du fichier stocké
     */
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

    /**
     * Supprime un fichier si celui-ci existe.
     *
     * @param url chemin du fichier
     */
    private void deleteFileIfExists(String url) {
        if (url == null) return;
        try {
            Files.deleteIfExists(Path.of(url));
        } catch (IOException e) {
            System.err.println("Erreur suppression fichier : " + e.getMessage());
        }
    }

    /**
     * Récupère une location par son identifiant ou lève une exception si elle n'existe pas.
     *
     * @param rentalId identifiant de la location
     * @return la location
     * @throws RuntimeException si la location n'existe pas
     */
    private Rental getRentalOrThrow(Long rentalId) {
        return rentalRepo.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental introuvable"));
    }
}
