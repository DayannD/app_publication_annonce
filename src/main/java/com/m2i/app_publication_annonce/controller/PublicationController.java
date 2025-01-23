package com.m2i.app_publication_annonce.controller;

import com.m2i.app_publication_annonce.controller.dto.CreatePublicationDto;
import com.m2i.app_publication_annonce.entities.Publication;
import com.m2i.app_publication_annonce.mapper.PublicationMapper;
import com.m2i.app_publication_annonce.service.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;
    private final PublicationMapper publicationMapper;

    @PostMapping("/publication")
    public Publication createPublication(@RequestBody CreatePublicationDto createPublicationDto) {
        return this.publicationService.createPublication(this.publicationMapper.toEntity(createPublicationDto));
    }

    @GetMapping("/publications")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Publication> getPublications() {
        return this.publicationService.getPublications();
    }

    @GetMapping("/publications/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Publication> getAllPublications() {
        return this.publicationService.getAllPublications();
    }

}
