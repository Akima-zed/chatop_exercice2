package com.chatop.chatop.service;

import com.chatop.chatop.dto.UserDTO;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.mapper.UserMapper;
import com.chatop.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public List<UserDTO> getAll() {
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserDTO getOne(Long id) {
        return userRepo.findById(id)
                .map(UserMapper::toDTO)
                .orElse(null);
    }

    public UserDTO update(Long id, User user) {
        return userRepo.findById(id)
                .map(u -> {
                    u.setName(user.getName());
                    u.setEmail(user.getEmail());
                    User updated = userRepo.save(u);
                    return UserMapper.toDTO(updated);
                })
                .orElse(null);
    }

    public boolean delete(Long id) {
        if (!userRepo.existsById(id)) return false;
        userRepo.deleteById(id);
        return true;
    }
}
