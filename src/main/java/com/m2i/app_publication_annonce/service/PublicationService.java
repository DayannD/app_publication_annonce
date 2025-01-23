package com.m2i.app_publication_annonce.service;

import com.m2i.app_publication_annonce.entities.Publication;
import com.m2i.app_publication_annonce.entities.UserEntity;
import com.m2i.app_publication_annonce.repository.PublicationRepository;
import com.m2i.app_publication_annonce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationService {

    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    public Publication createPublication(final Publication publication) {
        UserEntity user= this.getAuthenticatedUser();

        publication.setUserEntity(user);
        publication.setCreatedAt(LocalDateTime.now());

        return publicationRepository.save(publication);
    }

    public List<Publication> getPublications() {
        UserEntity user = this.getAuthenticatedUser();

        return publicationRepository.findByUserEntityId(user.getId());
    }

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    private UserEntity getAuthenticatedUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
