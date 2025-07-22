package com.example.libreria_universitaria.service;

import com.example.libreria_universitaria.entity.Libro;
import com.example.libreria_universitaria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    // Crea un nuovo libro:
    public Libro addLibro(Libro libro) {
        Libro nuovoLibro = libroRepository.save(libro);

        return nuovoLibro;
    }

    // Ritorna tutti i libri:
    public List<Libro> findLibri() {
        List<Libro> libri = libroRepository.findAll();

        return libri;
    }

    // Cancella un libro:
    public Optional<Libro> deleteLibro(Long id) {
        Optional<Libro> libroToFind = libroRepository.findById(id);

        if(libroToFind.isPresent()) {
            libroRepository.deleteById(id);
            return libroToFind;
        }
        return Optional.empty();
    }

    // Ritorna tutti i libri di uno specifico autore:
    public List<Libro> getLibriByAutore(String autore) {
        List<Libro> libriToFind = libroRepository.findByAutore(autore);

        return libriToFind;
    }

    // Ritorna tutti i libri pubblicati dopo un certo anno:
    public List<Libro> getLibriByAnnoGreaterThan(Integer anno) {
        List<Libro> libriToFind = libroRepository.findByAnnoPubblicazioneGreaterThan(anno);

        return libriToFind;
    }

    // Ritorna tutti i libri disponibili di un certo genere:
    public List<Libro> getLibriDisponibiliAndGenere(Boolean disponibile, String genere) {
        List<Libro> libriToFind = libroRepository.findByDisponibileAndGenere(disponibile, genere);

        return libriToFind;
    }

    // Ritorna tutti i libri con prezzo inferiore ad un certo valore:
    public List<Libro> getLibriByPrezzoLessThan(Double prezzo) {
        List<Libro> libriToFind = libroRepository.findByPrezzoLessThan(prezzo);

        return libriToFind;
    }

    // Ritorna il numero di libri per un determinato autore:
    public Integer getNumeroLibriByAutore(String autore) {
        Integer numeroLibriByAutore = libroRepository.countByAutore(autore);

        return numeroLibriByAutore;
    }

    // Ritorna i primi tre libri più costosi:
    public Page<Libro> getThreeLibriByPrezzo(int pagina, int dimensione) {
        Pageable paginazione = PageRequest.of(pagina, dimensione);
        Page<Libro> libriToFind = libroRepository.findAllByOrderByPrezzoDesc(paginazione);

        return libriToFind;
    }

    // Ritorna tutti i libri il cui titolo contiene una parola chiave:
    public List<Libro> getLibriContainingParolaChiave(String parolaChiave) {
        List<Libro> libriToFind = libroRepository.findByTitoloContaining(parolaChiave);

        return libriToFind;
    }

    // Ritorna i libri ordinati per genere e paginati:
    public Page<Libro> getLibriByGenerePageable(String genere, int pagina, int dimensione) {
        Pageable paginazione = PageRequest.of(pagina, dimensione);
        Page<Libro> libriToFind = libroRepository.findByGenere(genere, paginazione);

        return libriToFind;
    }

    // Ritorna tutti i libri disponibili con la paginazione:
    public Page<Libro> getLibriByDisponibilePageable(Boolean disponibile, int pagina, int dimensione) {
        Pageable paginazione = PageRequest.of(pagina, dimensione);
        Page<Libro> libriToFind = libroRepository.findByDisponibile(disponibile, paginazione);

        return libriToFind;
    }

    // Ritorna tutti i libri filtrati per prezzo oppure per anno e paginati:
    public Page<Libro> getLibriByPrezzoOrAnnoPubblicazionePageable(Double prezzo, Integer annoPubblicazione, int pagina, int dimensione) {
        Pageable paginazione = PageRequest.of(pagina, dimensione);
        Page<Libro> libriToFind = libroRepository.findByPrezzoOrAnnoPubblicazione(prezzo, annoPubblicazione, paginazione);

        return libriToFind;
    }

    // Ritorna tutti i libri pubblicati in un determinato anno con una Native Query:
    public List<Libro> getLibriByAnnoPubblicazioneNative(Integer annoPubblicazione) {
        List<Libro> libriToFind = libroRepository.findByAnnoPubblicazioneNative(annoPubblicazione);

        return libriToFind;
    }

    // Ritorna il numero di libri per genere con una Native Query:
    public Integer getNumeroLibriByGenereNative(String genere) {
        Integer numeroLibri = libroRepository.countByGenereNative(genere);

        return numeroLibri;
    }

    // Ritorna tutti i libri con prezzo compreso fra prezzoMin e prezzoMax con una Native Query:
    public List<Libro> getLibriByPrezzoBetweenNative(Double prezzoMin, Double prezzoMax) {
        List<Libro> libriToFind = libroRepository.findByPrezzoBetweenNative(prezzoMin, prezzoMax);

        return libriToFind;
    }

    // Ritorna tutti i libri disponibili di un certo genere con paginazione:
    public Page<Libro> getLibriDisponibiliAndGenerePageable(boolean disponibile, String genere, int pagina, int dimensione) {
        Pageable paginazione = PageRequest.of(pagina, dimensione);
        Page<Libro> libriToFind = libroRepository.findByDisponibileAndGenere(disponibile, genere, paginazione);

        return libriToFind;
    }
}
