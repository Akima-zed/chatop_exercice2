package com.chatop.chatop.mapper;

import com.chatop.chatop.dto.UserDTO;
import com.chatop.chatop.entity.User;

/**
 * Mapper utilitaire pour les conversions entre {@link User}
 * et {@link UserDTO}. Le mot de passe n’est jamais exposé côté DTO.
 */
public class UserMapper {

    /**
     * Convertit une entité User vers un DTO.
     *
     * @param user entité source
     * @return DTO ou null
     */
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        // Ne jamais exposer le mot de passe
        return dto;
    }

    /**
     * Convertit un DTO UserDTO vers une entité User.
     * Le mot de passe est géré au niveau du service.
     *
     * @param dto DTO source
     * @return entité construite
     */
    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        // Mot de passe à gérer séparément
        return user;
    }
}

