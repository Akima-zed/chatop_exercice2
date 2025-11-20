package com.chatop.chatop.repository;

import com.chatop.chatop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Recherche un utilisateur via son email.
 *
 * @param email email unique de l’utilisateur
 * @return Optional<User> vide si aucun résultat
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
