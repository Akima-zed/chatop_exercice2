package com.chatop.chatop.service;

import com.chatop.chatop.dto.UserDTO;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.mapper.UserMapper;
import com.chatop.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de gestion des utilisateurs.
 * <p>
 * Fournit les opérations CRUD pour les entités User.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    /**
     * Récupère tous les utilisateurs.
     *
     * @return liste de DTO d'utilisateurs
     */
    public List<UserDTO> getAll() {
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id identifiant de l'utilisateur
     * @return DTO de l'utilisateur ou null si inexistant
     */
    public UserDTO getOne(Long id) {
        return userRepo.findById(id)
                .map(UserMapper::toDTO)
                .orElse(null);
    }

    /**
     * Met à jour un utilisateur existant.
     *
     * @param id   identifiant de l'utilisateur
     * @param user données mises à jour
     * @return DTO de l'utilisateur mis à jour ou null si inexistant
     */
    public UserDTO update(Long id, User user) {
        return userRepo.findById(id)
                .map(u -> {
                    u.setName(user.getName());
                    u.setEmail(user.getEmail());
                    User updated = userRepo.save(u);
                    return UserMapper.toDTO(updated);
                })
                .orElse(null);
    }

    /**
     * Supprime un utilisateur existant.
     *
     * @param id identifiant de l'utilisateur
     * @return true si la suppression a réussi, false sinon
     */
    public boolean delete(Long id) {
        if (!userRepo.existsById(id)) return false;
        userRepo.deleteById(id);
        return true;
    }
}
