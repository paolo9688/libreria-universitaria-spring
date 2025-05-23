package com.example.libreria_universitaria.service;

import com.example.libreria_universitaria.entity.Libro;
import com.example.libreria_universitaria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
