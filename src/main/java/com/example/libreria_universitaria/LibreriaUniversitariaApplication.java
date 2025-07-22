package com.example.libreria_universitaria;

import com.example.libreria_universitaria.entity.Libro;
import com.example.libreria_universitaria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class LibreriaUniversitariaApplication implements ApplicationRunner {

	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LibreriaUniversitariaApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//populateBiblioteca();
	}

	private void populateBiblioteca() {
		Set<Libro> listaLibri = new HashSet<>(Set.of(
				new Libro("Il Signore degli Anelli", "J.R.R. Tolkien", 1954, "Fantasy", true, 39.99),
				new Libro("1984", "George Orwell", 1949, "Distopico", true, 19.99),
				new Libro("Il Nome della Rosa", "Umberto Eco", 1980, "Storico", false, 25.50),
				new Libro("La coscienza di Zeno", "Italo Svevo", 1923, "Romanzo Psicologico", true, 18.75),
				new Libro("Harry Potter e la Pietra Filosofale", "J.K. Rowling", 1997, "Fantasy", true, 24.99),
				new Libro("Il Gattopardo", "Giuseppe Tomasi di Lampedusa", 1958, "Storico", true, 22.00),
				new Libro("Orgoglio e Pregiudizio", "Jane Austen", 1813, "Romanzo", false, 17.00),
				new Libro("Il Codice da Vinci", "Dan Brown", 2003, "Thriller", true, 21.90),
				new Libro("Siddhartha", "Hermann Hesse", 1922, "Spirituale", true, 16.50),
				new Libro("I Promessi Sposi", "Alessandro Manzoni", 1840, "Classico", true, 14.99),
				new Libro("Delitto e Castigo", "Fëdor Dostoevskij", 1866, "Classico", true, 18.99),
				new Libro("Il Processo", "Franz Kafka", 1925, "Esistenziale", true, 15.49),
				new Libro("Don Chisciotte della Mancia", "Miguel de Cervantes", 1605, "Romanzo Cavalleresco", true, 19.99),
				new Libro("Il Piccolo Principe", "Antoine de Saint-Exupéry", 1943, "Fiaba", true, 12.99),
				new Libro("Il Maestro e Margherita", "Michail Bulgakov", 1967, "Satirico", true, 17.50),
				new Libro("Cent'anni di solitudine", "Gabriel García Márquez", 1967, "Realismo Magico", true, 20.00),
				new Libro("Moby Dick", "Herman Melville", 1851, "Avventura", false, 18.00),
				new Libro("Il vecchio e il mare", "Ernest Hemingway", 1952, "Narrativa", true, 13.99),
				new Libro("Dracula", "Bram Stoker", 1897, "Horror", true, 16.80),
				new Libro("Frankenstein", "Mary Shelley", 1818, "Horror", true, 14.50),
				new Libro("Lo Hobbit", "J.R.R. Tolkien", 1937, "Fantasy", true, 21.99),
				new Libro("Le avventure di Sherlock Holmes", "Arthur Conan Doyle", 1892, "Giallo", true, 15.00),
				new Libro("Il ritratto di Dorian Gray", "Oscar Wilde", 1890, "Classico", true, 16.75),
				new Libro("Il barone rampante", "Italo Calvino", 1957, "Narrativa", false, 19.50),
				new Libro("Il deserto dei Tartari", "Dino Buzzati", 1940, "Esistenziale", true, 17.90),
				new Libro("Fahrenheit 451", "Ray Bradbury", 1953, "Distopico", true, 14.99),
				new Libro("Il giovane Holden", "J.D. Salinger", 1951, "Narrativa", false, 15.99),
				new Libro("La Metamorfosi", "Franz Kafka", 1915, "Surrealismo", true, 11.50),
				new Libro("Il Grande Gatsby", "F. Scott Fitzgerald", 1925, "Classico", true, 14.20),
				new Libro("Jane Eyre", "Charlotte Brontë", 1847, "Romanzo", true, 18.40),
				new Libro("Cime tempestose", "Emily Brontë", 1847, "Romanzo Gotico", true, 17.30),
				new Libro("Oliver Twist", "Charles Dickens", 1837, "Classico", false, 16.10),
				new Libro("David Copperfield", "Charles Dickens", 1850, "Classico", true, 19.00),
				new Libro("Anna Karenina", "Lev Tolstoj", 1877, "Romanzo", true, 21.50),
				new Libro("Guerra e pace", "Lev Tolstoj", 1869, "Storico", true, 24.00),
				new Libro("Zanna Bianca", "Jack London", 1906, "Avventura", true, 12.80),
				new Libro("Il richiamo della foresta", "Jack London", 1903, "Avventura", true, 13.60),
				new Libro("Cuore", "Edmondo De Amicis", 1886, "Narrativa per ragazzi", true, 11.90),
				new Libro("Il diario di Anna Frank", "Anne Frank", 1947, "Biografia", true, 15.99),
				new Libro("Se questo è un uomo", "Primo Levi", 1947, "Memoriale", true, 14.80),
				new Libro("La tregua", "Primo Levi", 1963, "Memoriale", true, 15.20),
				new Libro("Il mondo nuovo", "Aldous Huxley", 1932, "Distopico", true, 17.40),
				new Libro("Shining", "Stephen King", 1977, "Horror", false, 22.10),
				new Libro("It", "Stephen King", 1986, "Horror", true, 25.60),
				new Libro("Carrie", "Stephen King", 1974, "Horror", true, 18.30),
				new Libro("Misery", "Stephen King", 1987, "Thriller", true, 20.00),
				new Libro("Norwegian Wood", "Haruki Murakami", 1987, "Romanzo", true, 16.90),
				new Libro("Kafka sulla spiaggia", "Haruki Murakami", 2002, "Narrativa", true, 18.70),
				new Libro("1Q84", "Haruki Murakami", 2009, "Fantastico", true, 22.30)
		));

		libroRepository.saveAll(listaLibri);
	}
}
