package com.chatop.chatop.controller;

import com.chatop.chatop.dto.UserDTO;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.mapper.UserMapper;
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

    // Lister tous les utilisateurs
    @GetMapping
    public List<UserDTO> getAll() {
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    // Obtenir un utilisateur par id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
        return userRepo.findById(id)
                .map(UserMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User user) {
        return userRepo.findById(id)
                .map(u -> {
                    u.setName(user.getName());
                    u.setEmail(user.getEmail());
                    User updated = userRepo.save(u);
                    return ResponseEntity.ok(UserMapper.toDTO(updated));
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
