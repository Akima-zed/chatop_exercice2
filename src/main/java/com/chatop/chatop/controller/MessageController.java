package com.chatop.chatop.controller;

import com.chatop.chatop.dto.MessageDTO;
import com.chatop.chatop.entity.Message;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.entity.User;
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
        return messageRepo.findAll().stream()
                .map(m -> new MessageDTO(
                        m.getId(),
                        m.getContent(),
                        m.getRental() != null ? m.getRental().getId() : null,
                        m.getUser() != null ? m.getUser().getId() : null,
                        m.getCreatedAt()
                ))
                .toList();
    }

    // ============================
    //            CREATE
    // ============================
    @PostMapping
    public MessageDTO create(@RequestBody Message message, Principal principal) {

        User user = userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (message.getRental() != null && message.getRental().getId() != null) {
            Rental rental = rentalRepo.findById(message.getRental().getId())
                    .orElseThrow(() -> new RuntimeException("Rental introuvable"));
            message.setRental(rental);
        }

        message.setUser(user);
        message.setCreatedAt(new Date());
        Message saved = messageRepo.save(message);

        return new MessageDTO(
                saved.getId(),
                saved.getContent(),
                saved.getRental() != null ? saved.getRental().getId() : null,
                saved.getUser().getId(),
                saved.getCreatedAt()
        );
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
                        Rental rental = rentalRepo.findById(updatedMessage.getRental().getId())
                                .orElseThrow(() -> new RuntimeException("Rental introuvable"));
                        msg.setRental(rental);
                    }

                    Message saved = messageRepo.save(msg);

                    return ResponseEntity.ok(
                            new MessageDTO(
                                    saved.getId(),
                                    saved.getContent(),
                                    saved.getRental() != null ? saved.getRental().getId() : null,
                                    saved.getUser().getId(),
                                    saved.getCreatedAt()
                            )
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================
    //            DELETE
    // ============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!messageRepo.existsById(id)) return ResponseEntity.notFound().build();
        messageRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
