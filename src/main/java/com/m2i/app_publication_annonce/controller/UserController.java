package com.m2i.app_publication_annonce.controller;

import com.m2i.app_publication_annonce.controller.dto.CreateUserDto;
import com.m2i.app_publication_annonce.mapper.UserMapper;
import com.m2i.app_publication_annonce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody CreateUserDto user) {
        userService.registerUser(this.userMapper.toEntity(user));
        return ResponseEntity.ok("User registered successfully");
    }
}
