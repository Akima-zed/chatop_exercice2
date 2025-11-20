package com.chatop.chatop.auth;


import com.chatop.chatop.auth.dto.LoginRequest;
import com.chatop.chatop.auth.dto.RegisterRequest;
import com.chatop.chatop.auth.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller pour la gestion de l'authentification.
 * Gère l'enregistrement et la connexion des utilisateurs.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint pour enregistrer un nouvel utilisateur.
     *
     * @param request Données d'enregistrement (email, nom, mot de passe)
     * @return AuthResponse contenant le token JWT
     */
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * Endpoint pour la connexion d'un utilisateur existant.
     *
     * @param request Données de connexion (email, mot de passe)
     * @return AuthResponse contenant le token JWT
     */
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}