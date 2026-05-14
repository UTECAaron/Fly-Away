package com.flyaway.travel.controller;

import com.flyaway.travel.dto.request.LoginRequestDTO;
import com.flyaway.travel.dto.request.RegisterRequestDTO;
import com.flyaway.travel.dto.response.AuthResponseDTO;
import com.flyaway.travel.dto.response.UserResponseDTO;
import com.flyaway.travel.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authService.getById(id));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
