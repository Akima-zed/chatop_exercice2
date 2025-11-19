package com.chatop.chatop.dto;

import java.util.Date;

public record MessageDTO(
        Long id,
        String content,
        Long rentalId,
        Long userId,
        Date createdAt
) {}
