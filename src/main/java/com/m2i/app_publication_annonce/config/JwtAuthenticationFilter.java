package com.m2i.app_publication_annonce.config;

import com.m2i.app_publication_annonce.entities.UserEntity;
import com.m2i.app_publication_annonce.repository.UserRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException, java.io.IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println("TOKKKENN" + token);
            if (jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.extract(token);

                UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name());

                System.out.println("USER-IN-FILTER" + userEntity);
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        Collections.singletonList(authority));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
