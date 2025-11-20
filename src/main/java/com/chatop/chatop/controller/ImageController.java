package com.chatop.chatop.controller;

import com.chatop.chatop.dto.ImageDTO;
import com.chatop.chatop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public List<ImageDTO> getAll() {
        return imageService.getAll();
    }

    @PostMapping
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam("file") MultipartFile file,
                                                @RequestParam Long rentalId) throws IOException {
        return ResponseEntity.ok(imageService.uploadImage(file, rentalId));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ImageDTO> update(@PathVariable Long id,
                                           @RequestParam(value = "file", required = false) MultipartFile file,
                                           @RequestParam(value = "rentalId", required = false) Long rentalId) {
        ImageDTO updated = imageService.updateImage(id, file, rentalId);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return imageService.deleteImage(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
