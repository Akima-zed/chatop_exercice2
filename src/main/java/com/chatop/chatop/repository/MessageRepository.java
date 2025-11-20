package com.chatop.chatop.repository;


import com.chatop.chatop.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour l’entité Message.
 * Fournit automatiquement toutes les opérations CRUD standard.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {}