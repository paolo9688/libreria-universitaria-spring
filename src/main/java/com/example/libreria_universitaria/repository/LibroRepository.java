package com.example.libreria_universitaria.repository;

import com.example.libreria_universitaria.entity.Libro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByAutore(String autore);
    List<Libro> findByAnnoPubblicazioneGreaterThan(Integer anno);
    List<Libro> findByDisponibileAndGenere(Boolean disponibile, String genere);
    List<Libro> findByPrezzoLessThan(Double prezzo);
    List<Libro> findAllByOrderByPrezzoDesc(Pageable pageable);
    List<Libro> findByTitoloContaining(String parolaChiave);
    List<Libro> findByGenere(String genere, Pageable pageable);
    List<Libro> findByDisponibile(Boolean disponibile, Pageable pageable);
    List<Libro> findByPrezzoOrAnnoPubblicazione(Double prezzo, Integer annoPubblicazione, Pageable pageable);
}
