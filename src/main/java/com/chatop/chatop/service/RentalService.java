package com.chatop.chatop.service;

import com.chatop.chatop.dto.RentalDTO;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.mapper.RentalMapper;
import com.chatop.chatop.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de gestion des locations.
 * <p>
 * Fournit les opérations CRUD pour les entités Rental.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepo;

    /**
     * Récupère toutes les locations.
     *
     * @return liste de DTO de locations
     */
    public List<RentalDTO> getAll() {
        return rentalRepo.findAll()
                .stream()
                .map(RentalMapper::toDTO)
                .toList();
    }

    /**
     * Récupère une location par son identifiant.
     *
     * @param id identifiant de la location
     * @return DTO de la location ou null si elle n'existe pas
     */
    public RentalDTO getOne(Long id) {
        return rentalRepo.findById(id)
                .map(RentalMapper::toDTO)
                .orElse(null);
    }

    /**
     * Crée une nouvelle location.
     *
     * @param rental location à créer
     * @return DTO de la location créée
     */
    public RentalDTO create(Rental rental) {
        Rental saved = rentalRepo.save(rental);
        return RentalMapper.toDTO(saved);
    }

    /**
     * Met à jour une location existante.
     *
     * @param id     identifiant de la location
     * @param rental données mises à jour
     * @return DTO de la location mise à jour ou null si elle n'existe pas
     */
    public RentalDTO update(Long id, Rental rental) {
        return rentalRepo.findById(id)
                .map(r -> {
                    r.setTitle(rental.getTitle());
                    r.setDescription(rental.getDescription());
                    r.setPricePerDay(rental.getPricePerDay());
                    Rental updated = rentalRepo.save(r);
                    return RentalMapper.toDTO(updated);
                })
                .orElse(null);
    }

    /**
     * Supprime une location existante.
     *
     * @param id identifiant de la location
     * @return true si la suppression a réussi, false sinon
     */
    public boolean delete(Long id) {
        if (!rentalRepo.existsById(id)) return false;
        rentalRepo.deleteById(id);
        return true;
    }
}
