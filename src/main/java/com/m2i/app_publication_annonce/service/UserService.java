package com.m2i.app_publication_annonce.service;

import com.m2i.app_publication_annonce.entities.UserEntity;
import com.m2i.app_publication_annonce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity registerUser(final UserEntity user) {
//        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().body("Email already in use");
//        }
//
//        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
//            return ResponseEntity.badRequest().body("Username already taken");
//        }
//
//        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//        userRepository.save(userRequest);
        return user;
    }
}
