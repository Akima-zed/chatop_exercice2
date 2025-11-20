package com.chatop.chatop.auth;


import com.chatop.chatop.auth.dto.LoginRequest;
import com.chatop.chatop.auth.dto.RegisterRequest;
import com.chatop.chatop.auth.dto.AuthResponse;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.repository.UserRepository;
import com.chatop.chatop.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service pour gérer la logique d'authentification et d'enregistrement.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * Enregistre un nouvel utilisateur et retourne un token JWT.
     *
     * @param req Requête contenant email, nom et mot de passe
     * @return AuthResponse contenant le JWT
     */
    public AuthResponse register(RegisterRequest req) {
        User user = new User();
        user.setEmail(req.getEmail());
        user.setName(req.getName());
        // Encodage sécurisé du mot de passe
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    /**
     * Authentifie un utilisateur existant et retourne un token JWT.
     *
     * @param req Requête contenant email et mot de passe
     * @return AuthResponse contenant le JWT
     * @throws RuntimeException si les identifiants sont invalides
     */
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
