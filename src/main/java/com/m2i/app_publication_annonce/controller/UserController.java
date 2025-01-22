package com.m2i.app_publication_annonce.controller;

import com.m2i.app_publication_annonce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userSerivce;

}
