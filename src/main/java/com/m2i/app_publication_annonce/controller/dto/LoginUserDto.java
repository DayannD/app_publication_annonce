package com.m2i.app_publication_annonce.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginUserDto(
       @NotNull @Email String email,
       @NotNull String password
) {

}
