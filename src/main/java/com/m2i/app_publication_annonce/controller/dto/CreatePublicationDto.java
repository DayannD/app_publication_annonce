package com.m2i.app_publication_annonce.controller.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePublicationDto(
       @NotNull String title,
        @NotNull String description,
        @NotNull Double price,
       String imageUrl
) {

}
