package com.br.testes.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.testes.entity.Trabalhador;
import com.br.testes.repositories.TrabalhadorRepository;
import com.br.testes.services.exeptions.DatabaseException;
import com.br.testes.services.exeptions.ResourceNotFoundException;

@Service
public class TrabalhadorService {

	private static Logger logger = LoggerFactory.getLogger(TrabalhadorService.class);

	@Autowired
	private TrabalhadorRepository repo;

	public Trabalhador cadastrarTrabalhador(Trabalhador dto) {
		repo.save(dto);
		logger.info("Novo trabalhador salvo com sucesso!");

		return dto;
	}

	public List<Trabalhador> verTodos() {
		logger.info("listando todos os trabalhadores...");
		List<Trabalhador> trabs = repo.findAll();
		
		return trabs;
	}

	public Trabalhador buscarUm(Long id) {
		Optional<Trabalhador> trab = repo.findById(id);
		logger.info("Trabalhador com id {} encontrado!", id);
		return trab.orElseThrow(() -> new ResourceNotFoundException(id));

	}

	@SuppressWarnings("deprecation")
	public Trabalhador editar(long id, Trabalhador dto) {
		try {
			Trabalhador trab = repo.getOne(id);
			atualizarUsuario(trab, dto);
			
			logger.info("Trabalhador id {} editado com sucesso!", trab.getId());
			return repo.save(trab);
		} catch (EntityNotFoundException e) {
			logger.info("Usuario com id {} não encontrado", id);
			throw new ResourceNotFoundException(id);
		}

	}
	
	public void deletar(Long id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		
	}

	public Double calcularSalario(Long id) {
		try {
			Optional<Trabalhador> trab = repo.findById(id);
			
			if(trab.isPresent()) {
				logger.info("Trabalhador encontrado com id: {}", id);
				return trab.get().calcularSalario();
			} 
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
		return null;
	}
	
	public Integer horasTrabalhadas(Long id) {
		try {
			Optional<Trabalhador> trab = repo.findById(id);
			
			if(trab.isPresent()) {
				logger.info("Trabalhador encontrado com id: {}", id);
				return trab.get().horasTrabalhadas();
			} 
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
		return null;
		
	}
	
	private void atualizarUsuario(Trabalhador trab, Trabalhador dto) {

		trab.setIdade(dto.getIdade());
		trab.setNome(dto.getNome());
		trab.setCpf(dto.getCpf());
		trab.setRendaDiaria(dto.getRendaDiaria());
		trab.setDiasTrabalhados(dto.getDiasTrabalhados());
	}
}



