package com.example.libreria_universitaria;

import com.example.libreria_universitaria.controller.LibroController;
import com.example.libreria_universitaria.entity.Libro;
import com.example.libreria_universitaria.service.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles(value = "dev")
@AutoConfigureMockMvc
class LibroTests {

	@MockitoBean
	private LibroService libroService;

	@Autowired
	private LibroController libroController;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Libro libro;

	@BeforeEach
	public void setUp() throws Exception {
		libro = new Libro(1L, "Il signore degli anelli", "Tolkien", 1954, "Fantasy", true, 52.25);
	}

	@Test
	void contextLoads() {
		assertThat(libroController).isNotNull();
	}

	@Test
	public void findByAutoreTest() throws Exception {
		when(libroService.getLibriByAutore(libro.getAutore())).thenReturn(List.of(libro));

		mockMvc.perform(get("/api/libri/autore/" + libro.getAutore())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].autore").value(libro.getAutore()))
				.andDo(print());
	}

	@Test
	public void countByAutoreTest() throws Exception {
		when(libroService.getNumeroLibriByAutore(libro.getAutore())).thenReturn(List.of(libro).size());

		mockMvc.perform(get("/api/libri/libri-per-autore")
				.param("autore", libro.getAutore())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("1"))
				.andDo(print());
	}

	@Test
	public void findByAnnoPubblicazioneGreaterThanTest() throws Exception {
		Integer annoLimite = 1900;
		when(libroService.getLibriByAnnoGreaterThan(annoLimite)).thenReturn(List.of(libro));

		mockMvc.perform(get("/api/libri/anno/" + annoLimite)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].annoPubblicazione").value(libro.getAnnoPubblicazione()))
				.andDo(print());
	}

	@Test
	public void findByDisponibileAndGenereTest() throws Exception {
		boolean disponibile = true;
		String genere = "Fantasy";
		when(libroService.getLibriDisponibiliAndGenere(disponibile, genere)).thenReturn(List.of(libro));

		mockMvc.perform(get("/api/libri/genere/" + genere)
				.param("disponibile", String.valueOf(disponibile))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].disponibile").value(libro.getDisponibile()))
				.andExpect(jsonPath("$[0].genere").value(libro.getGenere()))
				.andDo(print());
	}

	@Test
	public void findByPrezzoLessThanTest() throws Exception {
		Double prezzo = 59.99;
		when(libroService.getLibriByPrezzoLessThan(prezzo)).thenReturn(List.of(libro));

		mockMvc.perform(get("/api/libri/prezzo-inferiore")
				.param("prezzo", String.valueOf(prezzo))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].prezzo").value(libro.getPrezzo()))
				.andDo(print());
	}

	@Test
	public void findByTitoloContainingTest() throws Exception {
		String parolaChiave = "signore";
		when(libroService.getLibriContainingParolaChiave(parolaChiave)).thenReturn(List.of(libro));

		mockMvc.perform(get("/api/libri/parola-chiave")
				.param("parolaChiave", parolaChiave)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].titolo").value(libro.getTitolo()))
				.andDo(print());
	}

	/*
	@Test
	void testGetPaginato() throws Exception {
		mockMvc.perform(get("/prodotti?page=0&size=10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(10)))
				.andExpect(jsonPath("$.totalElements").value(25))
				.andExpect(jsonPath("$.totalPages").value(3));
	}
	*/

	@Test
	public void getThreeLibriByPrezzoTest() throws Exception {
		int pagina = 0;
		int dimensione = 10;

		// 1. Crea una lista di libri per la pagina
		List<Libro> libriPerPagina = new ArrayList<>();
		for (int i = 0; i < dimensione; i++) {
			Libro libro = new Libro();
			libro.setId((long) (i + 1));
			libro.setTitolo("Libro " + (i + 1));
			libro.setPrezzo(10.0 + i); // Prezzi crescenti per simulare i più costosi
			libriPerPagina.add(libro);
		}

		// 2. Definisci il Pageable che il tuo servizio si aspetterà
		// Spring automaticamente crea un Pageable dalla richiesta /libri-più-costosi?pagina=0&dimensione=10
		Pageable pageable = PageRequest.of(pagina, dimensione);

		// 3. Crea un mock di Page usando PageImpl
		// totalElements e totalPages sono importanti per le tue asserzioni jsonPath
		long totalElements = 25; // Esempio: hai 25 libri totali
		int totalPages = (int) Math.ceil((double) totalElements / dimensione); // Calcola il numero totale di pagine

		Page<Libro> mockPage = new PageImpl<>(libriPerPagina, pageable, totalElements);

		// 4. Mokka il comportamento del servizio
		// Nota: il metodo del servizio probabilmente prende un Pageable, non int, int
		// Se il tuo servizio prende int, int, assicurati che internamente crei un Pageable.
		// L'esempio seguente assume che il servizio prenda un Pageable.
		when(libroService.getThreeLibriByPrezzo(pagina, dimensione)).thenReturn(mockPage);

		mockMvc.perform(get("/api/libri/libri-più-costosi") // Ho rimosso i parametri dalla stringa URL
				.param("pagina", String.valueOf(pagina))     // e li ho aggiunti con .param()
				.param("dimensione", String.valueOf(dimensione)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(dimensione))) // Verifica la dimensione della pagina
				.andExpect(jsonPath("$.totalElements").value(totalElements)) // Verifica il numero totale di elementi
				.andExpect(jsonPath("$.totalPages").value(totalPages))       // Verifica il numero totale di pagine
				.andDo(print());
	}

	@Test
	public void findByGenereTest() throws Exception {
		String genere = "Fantasy";
		int pagina = 0;
		int dimensione = 10;

		List<Libro> libriPerPagina = new ArrayList<>();
		for (int i = 0; i < dimensione; i++) {
			Libro libro = new Libro();
			libro.setId((long) (i + 1));
			libro.setTitolo("Libro " + (i + 1));
			libro.setPrezzo(10.0 + i);
			libro.setGenere(genere);
			libriPerPagina.add(libro);
		}

		long totalElements = 25;

		Page<Libro> mockPage = new PageImpl<>(libriPerPagina, PageRequest.of(pagina, dimensione), totalElements);

		when(libroService.getLibriByGenerePageable(genere, pagina, dimensione)).thenReturn(mockPage);

		mockMvc.perform(get("/api/libri/genere")
				.param("genere", genere)
				.param("pagina", String.valueOf(pagina))
				.param("dimensione", String.valueOf(dimensione)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(dimensione)))
				.andExpect(jsonPath("$.totalElements").value(totalElements))
				.andExpect(jsonPath("$.totalPages").value((int) Math.ceil((double) totalElements / dimensione)))
				.andDo(print());
	}

	@Test
	public void findByDisponibilePageableTest() throws Exception {
		boolean disponibile = true;
		int pagina = 0;
		int dimensione = 10;

		List<Libro> libriPerPagina = new ArrayList<>();
		for (int i = 0; i < dimensione; i++) {
			Libro libro = new Libro();
			libro.setId((long) (i + 1));
			libro.setTitolo("Libro " + (i + 1));
			libro.setPrezzo(10.0 + i);
			libro.setDisponibile(disponibile);
			libriPerPagina.add(libro);
		}

		long totalElements = 25;

		Page<Libro> mockPage = new PageImpl<>(libriPerPagina, PageRequest.of(pagina, dimensione), totalElements);

		when(libroService.getLibriByDisponibilePageable(disponibile, pagina, dimensione)).thenReturn(mockPage);

		mockMvc.perform(get("/api/libri/genere")
				.param("disponibile", String.valueOf(disponibile))
				.param("pagina", String.valueOf(pagina))
				.param("dimensione", String.valueOf(dimensione)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(dimensione)))
				.andExpect(jsonPath("$.totalElements").value(totalElements))
				.andExpect(jsonPath("$.totalPages").value((int) Math.ceil((double) totalElements / dimensione)))
				.andDo(print());
	}
}
