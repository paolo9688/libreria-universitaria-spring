package com.example.libreria_universitaria.repository;

import com.example.libreria_universitaria.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByAutore(String autore);
    List<Libro> findByAnnoPubblicazioneGreaterThan(Integer anno);
    List<Libro> findByDisponibileAndGenere(Boolean disponibile, String genere);
}
