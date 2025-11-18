package com.chatop.chatop.controller;

import com.chatop.chatop.entity.Image;
import com.chatop.chatop.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepo;

    @GetMapping
    public List<Image> getAll() {
        return imageRepo.findAll();
    }

    @PostMapping
    public Image create(@RequestBody Image image) {
        return imageRepo.save(image);
    }
}
