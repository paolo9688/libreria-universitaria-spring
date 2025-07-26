package com.example.libreria_universitaria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(max = 100, message = "Il nome non può superare i 100 caratteri")
    private String nome;

    @NotBlank(message = "Il cognome non può essere vuoto")
    @Size(max = 100, message = "Il cognome non può superare i 100 caratteri")
    private String cognome;

    @Size(max = 255, message = "L'indirizzo non può superare i 255 caratteri")
    private String indirizzo;

    @Email(message = "Formato email non valido")
    @NotBlank(message = "L'email non può essere vuota")
    @Column(unique = true, nullable = false)
    @Size(max = 255, message = "L'email non può superare i 255 caratteri")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "La password non può essere vuota")
    @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
    private String password;

    public Utente() {}

    public Utente(String nome, String cognome, String indirizzo, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
