package com.chatop.chatop.repository;


import com.chatop.chatop.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {}