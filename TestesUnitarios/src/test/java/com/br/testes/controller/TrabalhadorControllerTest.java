package com.br.testes.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.br.testes.entity.Trabalhador;
import com.br.testes.entity.DTO.TrabalhadorDTO;
import com.br.testes.repositories.TrabalhadorRepository;
import com.br.testes.services.TrabalhadorService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author : Rivaldo Oliveira Testes de Integração
 * @See https://github.com/Soturnus/testes-unitarios
 */

@WebMvcTest(controllers = TrabalhadorController.class)
class TrabalhadorControllerTest {

	@MockBean
	private TrabalhadorService service;

	@MockBean
	private TrabalhadorRepository repo;

	@Autowired
	private ObjectMapper objectMapper;

	private List<Trabalhador> trabList;

	@BeforeEach
	void setUp() {
		this.trabList = new ArrayList<>();
		this.trabList.add(new Trabalhador(1L, "Rivaldo", 26, "07081244459", 25.0, 20));
		this.trabList.add(new Trabalhador(2L, "Thomas", 28, "13474562444", 47.8, 18));
		this.trabList.add(new Trabalhador(3L, "Ayrton", 27, "22527433448", 51.2, 15));

		objectMapper.registerModule(new ProblemModule());
		objectMapper.registerModule(new ConstraintViolationProblemModule());
	}

	@Nested
	@ContextConfiguration(classes = { TrabalhadorController.class, TrabalhadorDTO.class })
	class TestesCadastroTrabalhadores {

		@Autowired
		private MockMvc mockMvc;

		@Test
		void deve_Cadastrar_Com_Sucesso() throws Exception {
			// DADO
			Trabalhador trabalhador = new Trabalhador(5L, "Rivaldo", 26, "07081244459", 25.0, 20);
			// QUANDO
			given(service.cadastrarTrabalhador(any(Trabalhador.class))).willReturn(trabalhador);
			// ENTAO
			this.mockMvc.perform(post("/trabalhador/cadastrar").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(trabalhador))).andExpect(status().isCreated());
		}

		@Test
		void deve_Retornar400_Quando_Nome_CPF_estiverem_Vazio() throws Exception {
			Trabalhador trabalhador = new Trabalhador(null, 26, null, 25.0, 20);

			given(service.cadastrarTrabalhador(any(Trabalhador.class))).willReturn(trabalhador);

			this.mockMvc.perform(post("/trabalhador/cadastrar").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(trabalhador))).andExpect(status().isBadRequest());
		}
	}

	@Nested
	class TesteConsultasTrabalhadores {

		@Autowired
		private MockMvc mockMvc;

		@Test
		void deve_Retornar_Todos_Trabalhadores() throws Exception {

			given(service.verTodos()).willReturn(trabList);

			this.mockMvc.perform(get("/trabalhador")).andExpect(status().isOk())
					.andExpect(jsonPath("$.size()", is(trabList.size())));
		}

		@Test
		void deve_Retornar_Trabalhador_porId() throws Exception {
			final Long id = 4L;
			final Trabalhador trabalhador = new Trabalhador(4L, "Rivaldo", 26, "07081244459", 25.0, 20);

			given(service.buscarUm(id)).willReturn(trabalhador);

			this.mockMvc.perform(get("/trabalhador/buscar/{id}", id)).andExpect(status().isOk())
					.andExpect(jsonPath("$.nome", is(trabalhador.getNome())))
					.andExpect(jsonPath("$.idade", is(trabalhador.getIdade())))
					.andExpect(jsonPath("$.rendaDiaria", is(trabalhador.getRendaDiaria())))
					.andExpect(jsonPath("$.diasTrabalhados", is(trabalhador.getDiasTrabalhados())));
		}

		@Test
		void deve_Retornar_404_Quando_Não_EncontrarTrabalhador() throws Exception {
			final Long id = 1L;

			given(service.buscarUm(id)).willReturn(null);

			this.mockMvc.perform(get("/trabalhador/buscar/{id}", id)).andExpect(status().isNotFound());
		}
	}

	@Nested
	class TestesRemocaoTrabalhadores {

		@Autowired
		private MockMvc mockMvc;

		@DisplayName("Deletar retorna NO_CONTENT")
		@Test
		void deve_Remover_Trabalhador_E_Retornar_NoContent() throws Exception {
			final Long id = 5L;
			final Trabalhador trabalhador = new Trabalhador(5L, "Rivaldo", 26, "07081244459", 25.0, 20);

			given(service.buscarUm(id)).willReturn(trabalhador);
			doNothing().when(service).deletar(trabalhador.getId());

			this.mockMvc.perform(delete("/trabalhador/deletar/{id}", id)).andExpect(status().isNoContent());
		}
	}
}
