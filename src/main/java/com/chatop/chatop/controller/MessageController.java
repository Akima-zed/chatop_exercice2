package com.chatop.chatop.controller;

import com.chatop.chatop.dto.MessageDTO;
import com.chatop.chatop.entity.Message;
import com.chatop.chatop.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller REST pour la gestion des messages.
 * - Création, mise à jour, suppression et récupération de messages
 */
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /** Récupère tous les messages */
    @GetMapping
    public List<MessageDTO> getAll() {
        return messageService.getAll();
    }

    /** Crée un nouveau message */
    @PostMapping
    public ResponseEntity<MessageDTO> create(@RequestBody Message message, Principal principal) {
        return ResponseEntity.ok(messageService.create(message, principal));
    }

    /** Met à jour un message existant */
    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> update(@PathVariable Long id, @RequestBody Message message) {
        MessageDTO updated = messageService.update(id, message);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /** Supprime un message */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return messageService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
