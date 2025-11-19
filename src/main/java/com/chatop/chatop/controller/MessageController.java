package com.chatop.chatop.controller;

import com.chatop.chatop.dto.MessageDTO;
import com.chatop.chatop.entity.Message;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.mapper.MessageMapper;
import com.chatop.chatop.repository.MessageRepository;
import com.chatop.chatop.repository.RentalRepository;
import com.chatop.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepo;
    private final UserRepository userRepo;
    private final RentalRepository rentalRepo;

    // ============================
    //          GET ALL
    // ============================
    @GetMapping
    public List<MessageDTO> getAll() {
        return messageRepo.findAll()
                .stream()
                .map(MessageMapper::toDTO)
                .toList();
    }

    // ============================
    //            CREATE
    // ============================
    @PostMapping
    public ResponseEntity<MessageDTO> create(@RequestBody Message message, Principal principal) {
        User user = userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (message.getRental() != null && message.getRental().getId() != null) {
            message.setRental(getRentalOrThrow(message.getRental().getId()));
        }

        message.setUser(user);
        message.setCreatedAt(new Date());
        Message saved = messageRepo.save(message);

        return ResponseEntity.ok(MessageMapper.toDTO(saved));
    }

    // ============================
    //            UPDATE
    // ============================
    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> update(@PathVariable Long id, @RequestBody Message updatedMessage) {
        return messageRepo.findById(id)
                .map(msg -> {
                    msg.setContent(updatedMessage.getContent());

                    if (updatedMessage.getRental() != null && updatedMessage.getRental().getId() != null) {
                        msg.setRental(getRentalOrThrow(updatedMessage.getRental().getId()));
                    }

                    Message saved = messageRepo.save(msg);
                    return ResponseEntity.ok(MessageMapper.toDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================
    //            DELETE
    // ============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var messageOpt = messageRepo.findById(id);
        if (messageOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        messageRepo.delete(messageOpt.get());
        return ResponseEntity.noContent().build();
    }

    // ---------------- Méthodes utilitaires ----------------
    private Rental getRentalOrThrow(Long rentalId) {
        return rentalRepo.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental introuvable"));
    }
}
