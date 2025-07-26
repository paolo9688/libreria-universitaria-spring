package com.example.libreria_universitaria.controller;

import com.example.libreria_universitaria.entity.Utente;
import com.example.libreria_universitaria.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    // Crea un nuovo utente:
    @PostMapping("/create-utente")
    public ResponseEntity<Utente> createUtente(@RequestBody Utente utente) {
        Utente utenteToAdd = utenteService.addUtente(utente);

        return ResponseEntity.ok(utenteToAdd);
    }
}
