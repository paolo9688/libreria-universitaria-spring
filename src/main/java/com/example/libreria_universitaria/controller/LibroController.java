package com.example.libreria_universitaria.controller;

import com.example.libreria_universitaria.entity.Libro;
import com.example.libreria_universitaria.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Libro>> getLibriByDisponibile(@PathVariable String genere) {
        List<Libro> libriToFind = libroService.getLibriDisponibiliAndGenere(genere);

        if (libriToFind.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(libriToFind);
    }
}
