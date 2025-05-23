package com.example.libreria_universitaria.controller;

import com.example.libreria_universitaria.entity.Libro;
import com.example.libreria_universitaria.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        if (libriToFind.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(libriToFind);
    }

    // Ritorna tutti i libri pubblicati dopo un certo anno:
    @GetMapping("/anno/{anno}")
    public ResponseEntity<List<Libro>> getLibriByAnnoGreaterThan(@PathVariable Integer anno) {
        List<Libro> libriToFind = libroService.getLibriByAnnoGreaterThan(anno);

        if (libriToFind.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(libriToFind);
    }

    // Ritorna tutti i libri disponibili di un certo genere:
    @GetMapping("/genere/{genere}")
    public ResponseEntity<List<Libro>> getLibriByDisponibile(@PathVariable String genere, @RequestParam Boolean disponibile) {
        List<Libro> libriToFind = libroService.getLibriDisponibiliAndGenere(disponibile, genere);

        if (libriToFind.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(libriToFind);
    }

    // Ritorna tutti i libri con prezzo inferiore ad un certo valore:
    @GetMapping("/prezzo-inferiore")
    public ResponseEntity<List<Libro>> getLibriByPrezzoLessThan(@RequestParam Double prezzo) {
        List<Libro> libriToFind = libroService.getLibriByPrezzoLessThan(prezzo);

        if (libriToFind.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(libriToFind);
    }

    // Ritorna il numero di libri per un determinato autore:
    @GetMapping("/libri-per-autore")
    public ResponseEntity<Integer> getNumeroLibriByAutore(@RequestParam String autore) {
        Integer numeroLibriByAutore = libroService.getNumeroLibriByAutore(autore);

        if (numeroLibriByAutore == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(numeroLibriByAutore);
    }

    // Ritorna i primi tre libri più costosi:
    @GetMapping("/libri-più-costosi")
    public ResponseEntity<List<Libro>> getPrimiTreLibriByPrezzo(@RequestParam int page, @RequestParam int length) {
        Pageable pageable = PageRequest.of(page, length);
        List<Libro> listaLibri = libroService.getThreeLibriByPrezzo(pageable);

        if (listaLibri.isEmpty() || page < 0 || length <= 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaLibri);
    }

    // Ritorna tutti i libri il cui titolo contiene una parola chiave:
    @GetMapping("/parola-chiave")
    public ResponseEntity<List<Libro>> getLibriContainingParolaChiave(@RequestParam String parolaChiave) {
        List<Libro> libriToFind = libroService.getLibriContainingParolaChiave(parolaChiave);

        if (libriToFind.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(libriToFind);
    }
}
