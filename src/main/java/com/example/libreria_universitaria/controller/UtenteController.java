package com.example.libreria_universitaria.controller;

import com.example.libreria_universitaria.dto.AuthRequest;
import com.example.libreria_universitaria.entity.Utente;
import com.example.libreria_universitaria.security.JwtUtil;
import com.example.libreria_universitaria.service.UserDetailsServiceImpl;
import com.example.libreria_universitaria.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthenticationManager authenticationManager; // Inietta AuthenticationManager

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // Inietta UserDetailsServiceImpl

    @Autowired
    private JwtUtil jwtUtil; // Inietta JwtUtil

    // Endpoint per la registrazione di un nuovo utente
    @PostMapping("/register-utente")
    public ResponseEntity<?> registerUtente(@Valid @RequestBody Utente utente) {
        try {
            Utente nuovoUtente = utenteService.addUtente(utente);
            // Non ritornare la password nel response
            nuovoUtente.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuovoUtente);
        } catch (Exception e) {
            // Gestisci il caso in cui l'email sia già registrata (es. Unique constraint violation)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore durante la registrazione: " + e.getMessage());
        }
    }

    // Endpoint per il login dell'utente e generazione del token JWT
    @PostMapping("/login-utente")
    public ResponseEntity<?> loginUtente(@Valid @RequestBody AuthRequest authRequest) {
        try {
            // Tenta di autenticare l'utente con email e password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Credenziali non valide
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o password non validi.");
        }

        // Se l'autenticazione ha successo, carica i dettagli dell'utente e genera il token JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Ritorna il token JWT nel corpo della risposta
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}

// DTO per la risposta del login (contiene il token JWT)
class JwtResponse {
    private String jwt;

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}