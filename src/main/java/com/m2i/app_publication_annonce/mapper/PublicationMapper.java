package com.m2i.app_publication_annonce.mapper;

import com.m2i.app_publication_annonce.controller.dto.CreatePublicationDto;
import com.m2i.app_publication_annonce.entities.Publication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    Publication toEntity(CreatePublicationDto createPublicationDto);
}
