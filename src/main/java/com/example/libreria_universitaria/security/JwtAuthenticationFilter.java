package com.example.libreria_universitaria.security;

import com.example.libreria_universitaria.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Log iniziale per ogni richiesta che passa dal filtro
        System.out.println("JWT Filter: Processing request for URI: " + request.getRequestURI());

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Estrae il token JWT dall'header Authorization (formato: "Bearer TOKEN")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Rimuove "Bearer "
            username = jwtUtil.extractUsername(jwt);
        }

        // Se il nome utente è stato estratto e non c'è già un'autenticazione nel contesto di sicurezza
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("JWT Filter: Attempting to load UserDetails for: " + username);
            UserDetails userDetails = null;
            try {
                // Carica i dettagli dell'utente usando il nostro UserDetailsService
                userDetails = this.userDetailsService.loadUserByUsername(username);
                System.out.println("JWT Filter: UserDetails loaded successfully.");
            } catch (Exception e) {
                System.err.println("JWT Filter: Error loading UserDetails: " + e.getMessage());
            }

            if (userDetails != null) {
                System.out.println("JWT Filter: Attempting to validate token...");
                // Valida il token
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    System.out.println("JWT Filter: Token validated successfully. Setting SecurityContext.");
                    // Se il token è valido, crea un oggetto di autenticazione
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Imposta l'autenticazione nel contesto di sicurezza di Spring
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    System.out.println("JWT Filter: Token validation FAILED.");
                }
            }
        } else {
            System.out.println("JWT Filter: Username is null or SecurityContext already has authentication.");
        }
        // Continua la catena dei filtri
        filterChain.doFilter(request, response);
    }
}