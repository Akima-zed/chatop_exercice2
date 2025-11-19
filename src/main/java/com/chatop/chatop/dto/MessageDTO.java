package com.chatop.chatop.dto;

import lombok.Data;
import java.util.Date;


@Data
public class MessageDTO {
    private Long id;
    private String content;
    private Long rentalId;
    private Long userId;
    private Date createdAt;
}
