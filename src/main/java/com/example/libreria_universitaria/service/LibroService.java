package com.example.libreria_universitaria.service;

import com.example.libreria_universitaria.entity.Libro;
import com.example.libreria_universitaria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    // Crea un nuovo libro:
    public Libro addLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    // Ritorna tutti i libri di uno specifico autore:
    public List<Libro> getLibriByAutore(String autore) {
        return libroRepository.findByAutore(autore);
    }

    // Ritorna tutti i libri pubblicati dopo un certo anno:
    public List<Libro> getLibriByAnnoGreaterThan(Integer anno) {
        return libroRepository.findByAnnoPubblicazioneGreaterThan(anno);
    }

    // Ritorna tutti i libri disponibili di un certo genere:
    public List<Libro> getLibriDisponibiliAndGenere(Boolean disponibile, String genere) {
        return libroRepository.findByDisponibileAndGenere(disponibile, genere);
    }

    // Ritorna tutti i libri con prezzo inferiore ad un certo valore:
    public List<Libro> getLibriByPrezzoLessThan(Double prezzo) {
        return libroRepository.findByPrezzoLessThan(prezzo);
    }

    // Ritorna il numero di libri per un determinato autore:
    public Integer getNumeroLibriByAutore(String autore) {
        Integer numeroLibriByAutore = libroRepository.countByAutore(autore);
        return numeroLibriByAutore;
    }

    // Ritorna i primi tre libri più costosi:
    public List<Libro> getThreeLibriByPrezzo(Pageable pageable) {
        return libroRepository.findAllByOrderByPrezzoDesc(pageable);
    }

    // Ritorna tutti i libri il cui titolo contiene una parola chiave:
    public List<Libro> getLibriContainingParolaChiave(String parolaChiave) {
        return libroRepository.findByTitoloContaining(parolaChiave);
    }

    // Ritorna i libri ordinati per genere e paginati:
    public List<Libro> getLibriByGenerePageable(String genere, Pageable pageable) {
        return libroRepository.findByGenere(genere, pageable);
    }

    // Ritorna tutti i libri disponibili con la paginazione:
    public List<Libro> getLibriByDisponibilePageable(Boolean disponibile, Pageable pageable) {
        return libroRepository.findByDisponibile(disponibile, pageable);
    }

    // Ritorna tutti i libri filtrati per prezzo oppure per anno e paginati:
    public List<Libro> getLibriByPrezzoOrAnnoPubblicazionePageable(Double prezzo, Integer annoPubblicazione, Pageable pageable) {
        return libroRepository.findByPrezzoOrAnnoPubblicazione(prezzo, annoPubblicazione, pageable);
    }

    // Ritorna tutti i libri pubblicati in un determinato anno con una Native Query:

}
