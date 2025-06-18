package com.example.libreria_universitaria.controller;

import com.example.libreria_universitaria.entity.Libro;
import com.example.libreria_universitaria.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libri")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping("/create-libro")
    public ResponseEntity<Libro> createLibro(@RequestBody Libro libro) {
        Libro heroToAdd = libroService.addLibro(libro);

        return ResponseEntity.ok(heroToAdd);
    }

    // Ritorna tutti i libri di uno specifico autore:
    @GetMapping("/autore/{autore}")
    public ResponseEntity<List<Libro>> getLibriByAutore(@PathVariable String autore) {
        List<Libro> libriToFind = libroService.getLibriByAutore(autore);

        /* N.B. : in questi casi, quando devo ritornare una lista di oggetti,
        *         non è necessario mettere ResponseEntity.noContent().build(),
        *         in quanto se la mia lista di ritorno è vuota non devo lanciare un errore lato server.
        *         Come ritorno avrò sempre ResponseEntity.ok(listaOggetti) ed avrò una lista vuota.*/

        return ResponseEntity.ok(libriToFind);
    }

    // Ritorna tutti i libri pubblicati dopo un certo anno:
    @GetMapping("/anno/{anno}")
    public ResponseEntity<List<Libro>> getLibriByAnnoGreaterThan(@PathVariable Integer anno) {
        List<Libro> libriToFind = libroService.getLibriByAnnoGreaterThan(anno);

        return ResponseEntity.ok(libriToFind);
    }

    // Ritorna tutti i libri disponibili di un certo genere:
    @GetMapping("/genere/{genere}")
    public ResponseEntity<List<Libro>> getLibriByDisponibile(@PathVariable String genere, @RequestParam Boolean disponibile) {
        List<Libro> libriToFind = libroService.getLibriDisponibiliAndGenere(disponibile, genere);

        return ResponseEntity.ok(libriToFind);
    }

    // Ritorna tutti i libri con prezzo inferiore ad un certo valore:
    @GetMapping("/prezzo-inferiore")
    public ResponseEntity<List<Libro>> getLibriByPrezzoLessThan(@RequestParam Double prezzo) {
        List<Libro> libriToFind = libroService.getLibriByPrezzoLessThan(prezzo);

        return ResponseEntity.ok(libriToFind);
    }

    // Ritorna il numero di libri per un determinato autore:
    @GetMapping("/libri-per-autore")
    public ResponseEntity<Integer> getNumeroLibriByAutore(@RequestParam String autore) {
        Integer numeroLibriByAutore = libroService.getNumeroLibriByAutore(autore);

        return ResponseEntity.ok(numeroLibriByAutore);
    }

    // Ritorna i primi tre libri più costosi:
    @GetMapping("/libri-più-costosi")
    public ResponseEntity<Page<Libro>> getPrimiTreLibriByPrezzo(@RequestParam int page,
                                                                @RequestParam int length) {
        Page<Libro> listaLibri = libroService.getThreeLibriByPrezzo(page, length);

        return ResponseEntity.ok(listaLibri);
    }

    // Ritorna tutti i libri il cui titolo contiene una parola chiave:
    @GetMapping("/parola-chiave")
    public ResponseEntity<List<Libro>> getLibriContainingParolaChiave(@RequestParam String parolaChiave) {
        List<Libro> libriToFind = libroService.getLibriContainingParolaChiave(parolaChiave);

        return ResponseEntity.ok(libriToFind);
    }

    // Ritorna i libri ordinati per genere e paginati:
    @GetMapping("/genere")
    public ResponseEntity<Page<Libro>> getLibriByGenerePageable(@RequestParam String genere,
                                                                @RequestParam int page,
                                                                @RequestParam int length) {
        Page<Libro> listaLibri = libroService.getLibriByGenerePageable(genere, page, length);

        return ResponseEntity.ok(listaLibri);
    }

    // Ritorna tutti i libri disponibili con la paginazione:
    @GetMapping("/disponibile")
    public ResponseEntity<Page<Libro>> getLibriByDisponibilePageable(@RequestParam Boolean disponibile,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int length) {
        Page<Libro> listaLibri = libroService.getLibriByDisponibilePageable(disponibile, page, length);

        return ResponseEntity.ok(listaLibri);
    }

    // Ritorna tutti i libri filtrati per prezzo oppure per anno e paginati:
    @GetMapping("/prezzo-anno")
    public ResponseEntity<Page<Libro>> getLibriByPrezzoOrAnnoPubblicazionePageable(@RequestParam(required = false) Double prezzo,
                                                                                   @RequestParam(required = false) Integer annoPubblicazione,
                                                                                   @RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "10") int length) {
        Page<Libro> listaLibri = libroService.getLibriByPrezzoOrAnnoPubblicazionePageable(prezzo, annoPubblicazione, page, length);

        return ResponseEntity.ok(listaLibri);
    }

    // Ritorna tutti i libri pubblicati in un determinato anno con una Native Query:
    @GetMapping("/anno-pubblicazione")
    public ResponseEntity<List<Libro>> getLibriByAnnoPubblicazione(@RequestParam Integer annoPubblicazione) {
        List<Libro> listaLibri = libroService.getLibriByAnnoPubblicazioneNative(annoPubblicazione);

        return ResponseEntity.ok(listaLibri);
    }

    // Ritorna il numero di libri per genere con una Native Query:
    @GetMapping("/numero-libri-per-genere")
    public ResponseEntity<Integer> getNumeroLibriByGenere(@RequestParam String genere) {
        Integer numeroLibri = libroService.getNumeroLibriByGenereNative(genere);

        return ResponseEntity.ok(numeroLibri);
    }

    // Ritorna tutti i libri con prezzo compreso fra 5.0 e 15.0 con una Native Query:
    @GetMapping("/prezzo-compreso")
    public ResponseEntity<List<Libro>> getLibriByPrezzoBetween(@RequestParam Double prezzoMin,
                                                               @RequestParam Double prezzoMax) {
        List<Libro> listaLibri = libroService.getLibriByPrezzoBetweenNative(prezzoMin, prezzoMax);

        return ResponseEntity.ok(listaLibri);
    }

    // Ritorna tutti i libri disponibili di un certo genere con paginazione:
    @GetMapping("/genere-pageable/{genere}")
    public ResponseEntity<Page<Libro>> getLibriByDisponibilePageable(@PathVariable String genere,
                                                                     @RequestParam boolean disponibile,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int length) {

        Page<Libro> libriToFind = libroService.getLibriDisponibiliAndGenerePageable(disponibile, genere, page, length);

        return ResponseEntity.ok(libriToFind);
    }
}