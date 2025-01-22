package com.m2i.app_publication_annonce.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {

    @NotNull
    private String lastName;

    @NotNull
    private String username;

    @Min(8)
    @Max(20)
    private String password;

    @Email
    @NotNull
    private String email;
}
