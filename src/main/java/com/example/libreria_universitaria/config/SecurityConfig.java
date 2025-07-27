package com.example.libreria_universitaria.config;

import com.example.libreria_universitaria.security.JwtAuthenticationFilter;
import com.example.libreria_universitaria.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // Importa CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource; // Importa CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Importa UrlBasedCorsConfigurationSource

import java.util.Arrays; // Importa Arrays
import java.util.List; // Importa List

@Configuration
@EnableWebSecurity // Abilita la sicurezza web di Spring
@EnableMethodSecurity // Abilita la sicurezza a livello di metodo (es. @PreAuthorize)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Definisce il PasswordEncoder da usare (BCrypt è raccomandato)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura l'AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configura il DaoAuthenticationProvider che userà il nostro UserDetailsService e PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Definisce la configurazione CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Consenti l'origine del tuo frontend Vue/Vite
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        // Consenti i metodi HTTP necessari
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Consenti tutti gli header
        configuration.setAllowedHeaders(List.of("*"));
        // Consenti l'invio di credenziali (es. Authorization header)
        configuration.setAllowCredentials(true);
        // Imposta il tempo massimo per il caching delle risposte preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Applica questa configurazione CORS a tutti i percorsi sotto /api/**
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    // Definisce la catena dei filtri di sicurezza HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabilita CSRF (tipico per API REST stateless)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Abilita e configura CORS
                .authorizeHttpRequests(auth -> auth
                        // Permetti le richieste OPTIONS per tutti gli endpoint /api/** (per le richieste preflight CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        // Permetti l'accesso a questi endpoint senza autenticazione
                        .requestMatchers("/api/utenti/register-utente", "/api/utenti/login-utente").permitAll()
                        // Tutte le altre richieste devono essere autenticate
                        .anyRequest().authenticated()
                )
                // Configura la gestione delle sessioni come stateless (senza stato)
                // Essenziale per JWT, poiché ogni richiesta autenticata porta il token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Aggiunge il nostro filtro JWT prima del filtro di autenticazione utente/password
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Aggiunge il nostro AuthenticationProvider
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}