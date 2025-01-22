package com.m2i.app_publication_annonce.repository;

import com.m2i.app_publication_annonce.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByUserId(Long userId);
}
