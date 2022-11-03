package com.br.testes.controller;

import java.util.InputMismatchException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.testes.DTO.TrabalhadorDTO;
import com.br.testes.DTO.TrabalhadorResponseDTO;
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
	public ResponseEntity<?> cadastrar(@RequestBody TrabalhadorDTO dto) {

		try {
			if (ValidaCPF.isCPF(dto.getCpf())) {

				Trabalhador trabalhador = service.cadastrarTrabalhador(dto);
				
				logger.info("\n Usuario cadastrado com sucesso: "
						+ "\n nome: {}, \n cpf: {}", dto.getNome().toString(), ValidaCPF.imprimeCPF(dto.getCpf()));
				
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
	
	@GetMapping(value = "/calcular-salario/{id}")
	public ResponseEntity<?> calcularSalario(@PathVariable Long id){
		
		Optional<Trabalhador> trab = repo.findById(id);
		
		if (trab.isPresent()) {
			Double salario = service.calcularSalario(id);
			return new ResponseEntity<Double>(salario, HttpStatus.OK);
		}
		return new ResponseEntity<>("Usuario com id: " + id + " não encontrado" , HttpStatus.NOT_FOUND);
	}

}
