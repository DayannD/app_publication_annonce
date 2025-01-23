package com.m2i.app_publication_annonce;

import com.m2i.app_publication_annonce.config.JwtUtil;
import com.m2i.app_publication_annonce.controller.dto.CreatePublicationDto;
import com.m2i.app_publication_annonce.controller.dto.CreateUserDto;
import com.m2i.app_publication_annonce.controller.dto.LoginUserDto;
import com.m2i.app_publication_annonce.entities.Publication;
import com.m2i.app_publication_annonce.entities.UserEntity;
import com.m2i.app_publication_annonce.entities.enums.Role;
import com.m2i.app_publication_annonce.repository.PublicationRepository;
import com.m2i.app_publication_annonce.repository.UserRepository;
import com.m2i.app_publication_annonce.service.PublicationService;
import com.m2i.app_publication_annonce.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplicationTests {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PublicationService publicationService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PublicationRepository publicationRepository;

    @InjectMocks
    private UserService testedUserService;

    @InjectMocks
    private PublicationService testedPublicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    /**
     * Test User Registration
     */
    @Test
    void testUserRegistrationSuccess() {
        CreateUserDto userDto = new CreateUserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("strongpassword");
        userDto.setUsername("testuser");
        userDto.setLastName("Test");

        UserEntity userEntity = this.getUserEntity();

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedpassword");

        ResponseEntity<String> response = testedUserService.registerUser(userEntity);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully", response.getBody());
    }

    /**
     * Test Login User
     */
    @Test
    void testUserLoginSuccess() {
        LoginUserDto loginUserDto = new LoginUserDto("test@example.com", "encodedpassword");

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(this.getUserEntity()));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtUtil.generateToken(anyLong(), anyString())).thenReturn("valid.jwt.token");

        ResponseEntity<String> response = testedUserService.loginUser(loginUserDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("valid.jwt.token", response.getBody());
    }

    /**
     * Test Create Publication
     */
    @Test
    void testCreatePublicationSuccess() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("test@example.com");
        SecurityContextHolder.setContext(securityContext);

        CreatePublicationDto publicationDto = new CreatePublicationDto("Title", "Description", 100.0, "http://image.url");

        Publication publication = new Publication();
        publication.setTitle(publicationDto.title());
        publication.setDescription(publicationDto.description());
        publication.setPrice(publicationDto.price());

        when(publicationRepository.save(any(Publication.class))).thenReturn(publication);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(this.getUserEntity()));

        Publication createdPublication = testedPublicationService.createPublication(publication);

        assertNotNull(createdPublication);
        assertEquals(publicationDto.title(), createdPublication.getTitle());
    }

    /**
     * Test Get Publications
     */
    @Test
    void testGetPublications() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("test@example.com");
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(this.getUserEntity()));

        Publication publication = new Publication();
        publication.setTitle("Title");
        publication.setUserEntity(this.getUserEntity());
        when(publicationRepository.findByUserEntityId(1L)).thenReturn(List.of(publication));

        List<Publication> publications = testedPublicationService.getPublications();

        assertNotNull(publications);
        assertEquals(1, publications.size());
        assertEquals("Title", publications.get(0).getTitle());
        assertEquals("test@example.com", publications.get(0).getUserEntity().getEmail());

        SecurityContextHolder.clearContext();
    }

    /**
     * Test Invalid Login
     */
    @Test
    void testInvalidLogin() {
        LoginUserDto loginUserDto = new LoginUserDto("wrong@example.com", "wrongpassword");

        when(userService.loginUser(loginUserDto)).thenReturn(ResponseEntity.badRequest().body("Invalid email or password"));

        ResponseEntity<String> response = testedUserService.loginUser(loginUserDto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid email or password", response.getBody());
    }

    private UserEntity getUserEntity() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedpassword");
        user.setUsername("testuser Test");
        user.setRole(Role.USER);

        return user;
    }
}