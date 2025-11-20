package com.chatop.chatop.repository;

import com.chatop.chatop.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour la gestion des entités {@link Image}.
 * Fournit automatiquement toutes les opérations CRUD standard.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {}
