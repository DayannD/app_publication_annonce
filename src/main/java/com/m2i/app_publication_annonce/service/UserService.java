package com.m2i.app_publication_annonce.service;

import com.m2i.app_publication_annonce.config.JwtUtil;
import com.m2i.app_publication_annonce.controller.dto.LoginUserDto;
import com.m2i.app_publication_annonce.entities.UserEntity;
import com.m2i.app_publication_annonce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<String> registerUser(final UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<String> loginUser(final LoginUserDto user) {
        Optional<UserEntity> userEntityOpt = userRepository.findByEmail(user.email());
        if (userEntityOpt.isEmpty() || !passwordEncoder.matches(user.password(), userEntityOpt.get().getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.email());
        return ResponseEntity.ok(token);
    }
}
