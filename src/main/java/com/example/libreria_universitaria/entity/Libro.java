package com.example.libreria_universitaria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titolo")
    private String titolo;

    @NotNull
    @Column(name = "autore")
    private String autore;

    @NotNull
    @Column(name = "anno_pubblicazione")
    private Integer annoPubblicazione;

    @NotNull
    @Column(name = "genere")
    private String genere;

    @NotNull
    @Column(name = "disponibile")
    private Boolean disponibile;

    @NotNull
    @Column(name = "prezzo")
    private Double prezzo;

    public Libro() {}

    public Libro(String titolo, String autore, Integer annoPubblicazione, String genere, Boolean disponibile, Double prezzo) {
        this.titolo = titolo;
        this.autore = autore;
        this.annoPubblicazione = annoPubblicazione;
        this.genere = genere;
        this.disponibile = disponibile;
        this.prezzo = prezzo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public Integer getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(Integer annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Boolean getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(Boolean disponibile) {
        this.disponibile = disponibile;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(id, libro.id) && Objects.equals(titolo, libro.titolo) && Objects.equals(autore, libro.autore) && Objects.equals(annoPubblicazione, libro.annoPubblicazione) && Objects.equals(genere, libro.genere) && Objects.equals(disponibile, libro.disponibile) && Objects.equals(prezzo, libro.prezzo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titolo, autore, annoPubblicazione, genere, disponibile, prezzo);
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", genere='" + genere + '\'' +
                ", disponibile=" + disponibile +
                ", prezzo=" + prezzo +
                '}';
    }
}
