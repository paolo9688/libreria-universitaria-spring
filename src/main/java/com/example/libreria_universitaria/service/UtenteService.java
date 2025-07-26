package com.example.libreria_universitaria.service;

import com.example.libreria_universitaria.entity.Utente;
import com.example.libreria_universitaria.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importa PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inietta PasswordEncoder

    // Crea un nuovo utente:
    public Utente addUtente(Utente utente) {
        utente.setPassword(passwordEncoder.encode(utente.getPassword())); // cripta la password prima di salvarla
        Utente nuovoUtente = utenteRepository.save(utente);

        return nuovoUtente;
    }

    // Trova un utente per email (usato da UserDetailsServiceImpl)
    public Optional<Utente> findUtenteByEmail(String email) {
        Optional<Utente> utenteOptional = utenteRepository.findByEmail(email);

        return utenteOptional;
    }
}
