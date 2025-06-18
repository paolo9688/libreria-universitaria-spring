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
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
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

		/*mockMvc.perform(post("/api/libri")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(libro)));*/
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
}
