package com.m2i.app_publication_annonce.controller;

import com.m2i.app_publication_annonce.controller.dto.CreateUserDto;
import com.m2i.app_publication_annonce.controller.dto.LoginUserDto;
import com.m2i.app_publication_annonce.mapper.UserMapper;
import com.m2i.app_publication_annonce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/users/register")
    public ResponseEntity<String> registerUser(@RequestBody CreateUserDto user) {
        return userService.registerUser(this.userMapper.toEntity(user));
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDto user) {
        return userService.loginUser(user);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }
}
