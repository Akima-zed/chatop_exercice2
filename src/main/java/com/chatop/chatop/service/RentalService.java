package com.chatop.chatop.service;

import com.chatop.chatop.dto.RentalDTO;
import com.chatop.chatop.entity.Rental;
import com.chatop.chatop.mapper.RentalMapper;
import com.chatop.chatop.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepo;

    public List<RentalDTO> getAll() {
        return rentalRepo.findAll()
                .stream()
                .map(RentalMapper::toDTO)
                .toList();
    }

    public RentalDTO getOne(Long id) {
        return rentalRepo.findById(id)
                .map(RentalMapper::toDTO)
                .orElse(null);
    }

    public RentalDTO create(Rental rental) {
        Rental saved = rentalRepo.save(rental);
        return RentalMapper.toDTO(saved);
    }

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

    public boolean delete(Long id) {
        if (!rentalRepo.existsById(id)) return false;
        rentalRepo.deleteById(id);
        return true;
    }
}
