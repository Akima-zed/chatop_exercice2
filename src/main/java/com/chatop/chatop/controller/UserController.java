package com.chatop.chatop.controller;

import com.chatop.chatop.dto.UserDTO;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.mapper.UserMapper;
import com.chatop.chatop.repository.UserRepository;
import com.chatop.chatop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des utilisateurs.
 * - CRUD complet
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** Récupère tous les utilisateurs */
    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    /** Récupère un utilisateur par ID */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
        UserDTO user = userService.getOne(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    /** Met à jour un utilisateur existant */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User user) {
        UserDTO updated = userService.update(id, user);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /** Supprime un utilisateur */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
