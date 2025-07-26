package com.example.libreria_universitaria.repository;

import com.example.libreria_universitaria.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    // Metodo per trovare un utente tramite la sua email
    Optional<Utente> findByEmail(String email);
}
