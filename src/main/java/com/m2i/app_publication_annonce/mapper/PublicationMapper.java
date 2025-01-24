package com.m2i.app_publication_annonce.mapper;

import com.m2i.app_publication_annonce.controller.dto.CreatePublicationDto;
import com.m2i.app_publication_annonce.controller.dto.PublicationDto;
import com.m2i.app_publication_annonce.entities.Publication;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    Publication toEntity(CreatePublicationDto createPublicationDto);

    Publication toEntity(PublicationDto publicationDto);

    PublicationDto toDto(Publication publication);

    List<PublicationDto> toDto(List<Publication> publications);
}
