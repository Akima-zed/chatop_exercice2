package com.chatop.chatop.mapper;

import com.chatop.chatop.dto.MessageDTO;
import com.chatop.chatop.entity.Message;

/**
 * Mapper utilitaire responsable des conversions entre {@link Message}
 * et {@link MessageDTO}. Gère uniquement les champs simples afin de ne pas
 * résoudre les relations ici (elles doivent être traitées dans le service).
 */
public class MessageMapper {

    /**
     * Convertit une entité Message en DTO.
     *
     * @param message entité source
     * @return DTO construit ou null si input null
     */
    public static MessageDTO toDTO(Message message) {
        if (message == null) return null;

        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setCreatedAt(message.getCreatedAt());
        dto.setRentalId(message.getRental() != null ? message.getRental().getId() : null);
        dto.setUserId(message.getUser() != null ? message.getUser().getId() : null);
        return dto;
    }

    /**
     * Convertit un DTO MessageDTO vers une entité Message.
     * Les associations rental/user doivent être injectées dans le service.
     *
     * @param dto DTO source
     * @return entité construite ou null si input null
     */
    public static Message toEntity(MessageDTO dto) {
        if (dto == null) return null;

        Message message = new Message();
        message.setId(dto.getId());
        message.setContent(dto.getContent());
        message.setCreatedAt(dto.getCreatedAt());
        // Les relations rental et user doivent être gérées séparément (service/controller)
        return message;
    }
}
