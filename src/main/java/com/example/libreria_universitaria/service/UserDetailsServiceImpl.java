package com.example.libreria_universitaria.service;

import com.example.libreria_universitaria.entity.Utente;
import com.example.libreria_universitaria.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository; // Avrai bisogno di un UtenteRepository

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Cerca l'utente per email (che sarà il "username" per Spring Security)
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));

        // Costruisci un oggetto UserDetails da Spring Security
        // Per ora, non abbiamo ruoli, quindi passiamo una lista vuota di GrantedAuthority
        return new org.springframework.security.core.userdetails.User(
                utente.getEmail(),
                utente.getPassword(), // La password criptata dal database
                new ArrayList<>() // Lista vuota di GrantedAuthority (se non gestisci ruoli)
        );
    }
}