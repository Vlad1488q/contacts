package com.example.contacts.service;

import com.example.contacts.dto.AuthRequest;
import com.example.contacts.dto.AuthResponse;
import com.example.contacts.entity.User;
import com.example.contacts.repository.UserRepository;
import com.example.contacts.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Користувач вже існує");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        String token = jwtService.generateToken(request.getUsername());
        return new AuthResponse(token, request.getUsername());
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Невірний пароль");
        }

        String token = jwtService.generateToken(request.getUsername());
        return new AuthResponse(token, request.getUsername());
    }
}