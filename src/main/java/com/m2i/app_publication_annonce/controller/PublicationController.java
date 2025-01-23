package com.m2i.app_publication_annonce.controller;

import com.m2i.app_publication_annonce.controller.dto.CreatePublicationDto;
import com.m2i.app_publication_annonce.controller.dto.PublicationDto;
import com.m2i.app_publication_annonce.entities.Publication;
import com.m2i.app_publication_annonce.mapper.PublicationMapper;
import com.m2i.app_publication_annonce.service.PublicationService;
import com.m2i.app_publication_annonce.utils.UrlUtils;
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

    @PostMapping(UrlUtils.Publication.CREATE)
    public Publication createPublication(@RequestBody CreatePublicationDto createPublicationDto) {
        return this.publicationService.createPublication(this.publicationMapper.toEntity(createPublicationDto));
    }

    @GetMapping(UrlUtils.Publication.BASE_URL)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PublicationDto> getPublications() {
        return this.publicationMapper.toDto(this.publicationService.getPublications());
    }

    @GetMapping(UrlUtils.Publication.ALL)
    @PreAuthorize("hasRole('ADMIN')")
    public List<PublicationDto> getAllPublications() {
        return this.publicationMapper.toDto(this.publicationService.getAllPublications());
    }
}
