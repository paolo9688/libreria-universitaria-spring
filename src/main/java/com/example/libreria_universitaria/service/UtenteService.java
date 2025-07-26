package com.example.libreria_universitaria.service;

import com.example.libreria_universitaria.entity.Utente;
import com.example.libreria_universitaria.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    // Crea un nuovo utente:
    public Utente addUtente(Utente utente) {
        Utente nuovoUtente = utenteRepository.save(utente);

        return nuovoUtente;
    }
}
