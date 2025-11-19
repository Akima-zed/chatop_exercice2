package com.chatop.chatop.mapper;

import com.chatop.chatop.dto.UserDTO;
import com.chatop.chatop.entity.User;

public class UserMapper {

    /** Transforme une entité User en DTO */
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        // Ne jamais exposer le mot de passe
        return dto;
    }

    /** Transforme un DTO UserDTO en entité User (utile pour PUT/POST) */
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

