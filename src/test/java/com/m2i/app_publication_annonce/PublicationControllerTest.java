package com.m2i.app_publication_annonce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2i.app_publication_annonce.controller.PublicationController;
import com.m2i.app_publication_annonce.controller.dto.CreatePublicationDto;
import com.m2i.app_publication_annonce.controller.dto.PublicationDto;
import com.m2i.app_publication_annonce.entities.Publication;
import com.m2i.app_publication_annonce.mapper.PublicationMapper;
import com.m2i.app_publication_annonce.service.PublicationService;
import com.m2i.app_publication_annonce.utils.UrlUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicationController.class)
public class PublicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublicationService publicationService;

    @MockitoBean
    private PublicationMapper publicationMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    void testCreatePublication() throws Exception {
        PublicationDto publicationDto = new PublicationDto( 2L,"Title", "Description", 100.0, "http://image.url");
        Publication publication = new Publication();
        publication.setTitle(publicationDto.title());
        publication.setDescription(publicationDto.description());
        publication.setPrice(publicationDto.price());

        Mockito.when(publicationMapper.toEntity(Mockito.any(PublicationDto.class))).thenReturn(publication);

        Mockito.when(publicationService.createPublication(Mockito.any(Publication.class))).thenReturn(publication);

        mockMvc.perform(post(UrlUtils.Publication.CREATE)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicationDto)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(publication)));

        verify(publicationMapper).toEntity(Mockito.any(PublicationDto.class));
        verify(publicationService).createPublication(Mockito.any(Publication.class));
    }
}
