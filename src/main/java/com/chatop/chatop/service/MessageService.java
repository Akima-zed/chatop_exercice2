package com.chatop.chatop.service;

import com.chatop.chatop.dto.MessageDTO;
import com.chatop.chatop.entity.Message;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.mapper.MessageMapper;
import com.chatop.chatop.repository.MessageRepository;
import com.chatop.chatop.repository.RentalRepository;
import com.chatop.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepo;
    private final UserRepository userRepo;
    private final RentalRepository rentalRepo;

    public List<MessageDTO> getAll() {
        return messageRepo.findAll()
                .stream()
                .map(MessageMapper::toDTO)
                .toList();
    }

    public MessageDTO create(Message message, Principal principal) {
        User user = userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (message.getRental() != null && message.getRental().getId() != null) {
            message.setRental(getRentalOrThrow(message.getRental().getId()));
        }

        message.setUser(user);
        message.setCreatedAt(new Date());

        Message saved = messageRepo.save(message);
        return MessageMapper.toDTO(saved);
    }

    public MessageDTO update(Long id, Message updatedMessage) {
        return messageRepo.findById(id)
                .map(msg -> {
                    msg.setContent(updatedMessage.getContent());

                    if (updatedMessage.getRental() != null && updatedMessage.getRental().getId() != null) {
                        msg.setRental(getRentalOrThrow(updatedMessage.getRental().getId()));
                    }

                    Message saved = messageRepo.save(msg);
                    return MessageMapper.toDTO(saved);
                })
                .orElse(null);
    }

    public boolean delete(Long id) {
        return messageRepo.findById(id)
                .map(msg -> {
                    messageRepo.delete(msg);
                    return true;
                })
                .orElse(false);
    }

    private Rental getRentalOrThrow(Long rentalId) {
        return rentalRepo.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental introuvable"));
    }
}
