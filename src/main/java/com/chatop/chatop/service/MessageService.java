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

/**
 * Service de gestion des messages liés aux locations.
 * <p>
 * Fournit les opérations CRUD pour les entités Message et associe chaque message à un utilisateur et éventuellement à une location.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepo;
    private final UserRepository userRepo;
    private final RentalRepository rentalRepo;

    /**
     * Récupère tous les messages.
     *
     * @return liste de DTO de messages
     */
    public List<MessageDTO> getAll() {
        return messageRepo.findAll()
                .stream()
                .map(MessageMapper::toDTO)
                .toList();
    }

    /**
     * Crée un nouveau message.
     *
     * @param message   message à créer
     * @param principal principal représentant l'utilisateur courant
     * @return DTO du message créé
     * @throws RuntimeException si l'utilisateur ou la location n'existe pas
     */
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

    /**
     * Met à jour un message existant.
     *
     * @param id             identifiant du message
     * @param updatedMessage données mises à jour
     * @return DTO du message mis à jour ou null si le message n'existe pas
     */
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

    /**
     * Supprime un message existant.
     *
     * @param id identifiant du message
     * @return true si la suppression a réussi, false sinon
     */
    public boolean delete(Long id) {
        return messageRepo.findById(id)
                .map(msg -> {
                    messageRepo.delete(msg);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Récupère une location par son identifiant ou lève une exception si elle n'existe pas.
     *
     * @param rentalId identifiant de la location
     * @return la location
     * @throws RuntimeException si la location n'existe pas
     */
    private Rental getRentalOrThrow(Long rentalId) {
        return rentalRepo.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental introuvable"));
    }
}
