package com.br.testes.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.testes.entity.DTO.TrabalhadorDTO;
import com.br.testes.entity.DTO.TrabalhadorResponseDTO;
import com.br.testes.entity.Trabalhador;
import com.br.testes.repositories.TrabalhadorRepository;
import com.br.testes.services.TrabalhadorService;
import com.br.testes.util.ValidaCPF;

@RestController
@RequestMapping(value = "/trabalhador")
public class TrabalhadorController {

	private static Logger logger = LoggerFactory.getLogger(TrabalhadorController.class);

	@Autowired
	private TrabalhadorService service;

	@Autowired
	private TrabalhadorRepository repo;

	@PostMapping(value = "/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody TrabalhadorDTO dto) throws Exception {

		try {
			Optional<Trabalhador> trab = repo.findByCpf(dto.getCpf());
			if (trab.isPresent()){		
				return new ResponseEntity<>("CPF já cadastrado", HttpStatus.BAD_REQUEST);
			}	
			if(dto.getNome() == null || dto.getCpf() == null) {
				return new ResponseEntity<>("Usuario ou CPF não podem estar vazios", HttpStatus.BAD_REQUEST);
			}
			if (ValidaCPF.isCPF(dto.getCpf())) {
				Trabalhador trabalhador = new Trabalhador(dto.getNome(), dto.getIdade(), dto.getCpf(), dto.getRendaDiaria(),
						dto.getDiasTrabalhados());
				service.cadastrarTrabalhador(trabalhador);

				logger.info("\n Usuario cadastrado com sucesso: " + "\n nome: {}, \n cpf: {}", dto.getNome().toString(),
						ValidaCPF.imprimeCPF(dto.getCpf()));

				return new ResponseEntity<>(TrabalhadorResponseDTO.transformaEmDTO(trabalhador), HttpStatus.CREATED);
			} else {
				logger.error("Erro, CPF invalido !!!");
				return new ResponseEntity<>("\"Erro, CPF invalido !!!\\n\"", HttpStatus.BAD_REQUEST);
			}
		} catch (InputMismatchException e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>("Debuga ai irmão", HttpStatus.BAD_REQUEST);
	}

	@GetMapping
	public ResponseEntity<List<Trabalhador>> exibirTodos() {
		List<Trabalhador> trabs = service.verTodos();

		return new ResponseEntity<>(trabs, HttpStatus.OK);
	}

	@GetMapping(value = "/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Trabalhador trab = service.buscarUm(id);

		if (trab != null) {
			return new ResponseEntity<>(TrabalhadorResponseDTO.transformaEmDTO(trab), HttpStatus.OK);
		}

		return new ResponseEntity<>("Trabalhador não encontrado!", HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/editar/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Trabalhador trab){		
		Optional<Trabalhador> trabs = repo.findById(id);
		
		if(trabs.isPresent()) {
			if(ValidaCPF.isCPF(trab.getCpf())) {
				service.editar(id, trab);
				return new ResponseEntity<>(TrabalhadorResponseDTO.transformaEmDTO(trabs.get()), HttpStatus.OK);
			}
			logger.error("Erro, CPF invalido !!!");
			return new ResponseEntity<>("\"Erro, CPF invalido !!!\\n\"", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Trabalhador não encontrado!", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		try {
			service.deletar(id);
			return new ResponseEntity<>("Trabalhador deletado dos arquivos!", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>("Trabalhador não encontrado!", HttpStatus.NOT_FOUND);
		}		
	}
	
	@GetMapping(value = "/calcular-salario/{id}")
	public ResponseEntity<?> calcularSalario(@PathVariable Long id) {

		Optional<Trabalhador> trab = repo.findById(id);

		if (trab.isPresent()) {
			Double salario = service.calcularSalario(id);
			return new ResponseEntity<Double>(salario, HttpStatus.OK);
		}
		return new ResponseEntity<>("Trabalhador com id: " + id + " não encontrado!", HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/horas-trabalhadas/{id}")
	public ResponseEntity<?> horasTrabalhadas(@PathVariable Long id) {

		Optional<Trabalhador> trab = repo.findById(id);

		if (trab.isPresent()) {
			Integer horasTrabalhadas = service.horasTrabalhadas(id);
			StringBuilder trabalho = new StringBuilder();
			trabalho.append("Trabalhador: ").append(trab.get().getNome()).append(" possui ").append(horasTrabalhadas).append(" Horas Trabalhadas.");
			
			return new ResponseEntity<>(trabalho, HttpStatus.OK);
		}
		return new ResponseEntity<>("Usuario com id: " + id + " não encontrado", HttpStatus.NOT_FOUND);
	}

}
