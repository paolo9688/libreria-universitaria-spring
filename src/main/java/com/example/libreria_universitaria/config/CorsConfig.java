package com.example.libreria_universitaria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Applica a tutte le API sotto /api
                        .allowedOrigins("http://localhost:5173") // Consenti richieste da questo dominio/porta (il tuo frontend Next.js)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metodi HTTP consentiti
                        .allowedHeaders("*") // Tutte le intestazioni consentite
                        .allowCredentials(true); // Consenti l'invio di credenziali (es. cookie, authorization headers)
            }
        };
    }
}