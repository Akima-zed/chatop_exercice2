package com.chatop.chatop.controller;

import com.chatop.chatop.dto.MessageDTO;
import com.chatop.chatop.entity.Message;
import com.chatop.chatop.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public List<MessageDTO> getAll() {
        return messageService.getAll();
    }

    @PostMapping
    public ResponseEntity<MessageDTO> create(@RequestBody Message message, Principal principal) {
        return ResponseEntity.ok(messageService.create(message, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> update(@PathVariable Long id, @RequestBody Message message) {
        MessageDTO updated = messageService.update(id, message);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return messageService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
