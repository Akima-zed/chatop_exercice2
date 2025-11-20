package com.chatop.chatop.repository;

import com.chatop.chatop.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour l’entité {@link Rental}.
 * Fournit automatiquement toutes les opérations CRUD standard.
 */
public interface RentalRepository extends JpaRepository<Rental, Long> {}
