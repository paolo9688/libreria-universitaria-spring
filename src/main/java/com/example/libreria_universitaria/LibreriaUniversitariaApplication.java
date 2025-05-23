package com.example.libreria_universitaria;

import com.example.libreria_universitaria.entity.Libro;
import com.example.libreria_universitaria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LibreriaUniversitariaApplication implements ApplicationRunner {

	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LibreriaUniversitariaApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		populateBiblioteca();
	}

	private void populateBiblioteca() {
		List<Libro> listaLibri = new ArrayList<>();
		int libriMax = 20;

		for (int i = 0; i < libriMax; i++) {
			Integer annoPubblicazione = 2000 + i;
			Boolean disponibile = annoPubblicazione % 2 == 0;
			Double prezzo = 1.0 + i;

			Libro libro = new Libro(null, "titolo-" + i,"autore-" + i, annoPubblicazione, "genere-" + i, disponibile, prezzo);

			listaLibri.add(libro);
		}

		libroRepository.saveAll(listaLibri);
	}
}
