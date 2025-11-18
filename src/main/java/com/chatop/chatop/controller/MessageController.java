package com.chatop.chatop.controller;

import com.chatop.chatop.entity.Message;
import com.chatop.chatop.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepo;

    @GetMapping
    public List<Message> getAll() {
        return messageRepo.findAll();
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreatedAt(new Date());
        return messageRepo.save(message);
    }
}
