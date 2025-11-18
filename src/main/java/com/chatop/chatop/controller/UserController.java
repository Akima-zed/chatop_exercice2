package com.chatop.chatop.controller;

import com.chatop.chatop.entity.User;
import com.chatop.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;

    // Lister tous les utilisateurs (protected route)
    @GetMapping
    public List<User> getAll() {
        return userRepo.findAll();
    }

    // Obtenir un utilisateur par id
    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable Long id) {
        return userRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return userRepo.findById(id)
                .map(u -> {
                    u.setName(user.getName());
                    u.setEmail(user.getEmail());
                    // Ne pas mettre à jour le mot de passe ici, gérer via endpoint spécifique
                    return ResponseEntity.ok(userRepo.save(u));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepo.existsById(id)) return ResponseEntity.notFound().build();
        userRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
