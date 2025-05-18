package com.manfredsolutions.boilerplate.infraestructure.web;
import com.manfredsolutions.boilerplate.application.dto.AuthResponse;
import com.manfredsolutions.boilerplate.application.dto.LoginRequest;
import com.manfredsolutions.boilerplate.application.dto.RegisterRequest;
import com.manfredsolutions.boilerplate.application.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
