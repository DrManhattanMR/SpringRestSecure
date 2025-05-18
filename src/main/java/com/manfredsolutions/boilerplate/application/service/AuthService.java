package com.manfredsolutions.boilerplate.application.service;
import com.manfredsolutions.boilerplate.application.dto.AuthResponse;
import com.manfredsolutions.boilerplate.application.dto.LoginRequest;
import com.manfredsolutions.boilerplate.application.dto.RegisterRequest;
import com.manfredsolutions.boilerplate.domain.model.User;
import com.manfredsolutions.boilerplate.domain.port.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        Optional<User>existsUser=userRepository.findByUsername(request.username);
        if(existsUser.isPresent()) {
            System.out.println("Ya existe un usuario con ese nombre");
            throw new IllegalArgumentException("El usuario ya existe");
        }
        User newUser = new User(
                null,
                request.username,
                passwordEncoder.encode(request.password),
                request.roles
        );

        User savedUser = userRepository.save(newUser);

        return new AuthResponse(
                jwtService.generateToken(savedUser.getId(), savedUser.getUsername(), savedUser.getRoles())
        );
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        return new AuthResponse(
                jwtService.generateToken(user.getId(), user.getUsername(), user.getRoles())
        );
    }
}
