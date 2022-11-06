package com.br.testes.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.testes.entity.Trabalhador;
import com.br.testes.repositories.TrabalhadorRepository;

/**
 * @author : Rivaldo Oliveira
 * Testes Unitários
 * @See https://github.com/Soturnus/testes-unitarios 
 */

@ExtendWith(MockitoExtension.class)
class TrabalhadorServiceTest {
	
  	@Mock
    private TrabalhadorRepository repo;

    @InjectMocks
    private TrabalhadorService service;
	
    @Nested
    class TesteServiceCadastro{
    	@Test
    	void deve_Cadastrar_Novo_Trabalhador() throws Exception {
    		//DADO
    		final Trabalhador trab = new Trabalhador(1L, "Rivaldo", 26, "07081244459", 25.0, 20);	
    		given(repo.findByCpf(trab.getCpf())).willReturn(Optional.empty());
    		given(repo.save(trab)).willAnswer(invocation -> invocation.getArgument(0));
    		//QUANDO
    		Trabalhador trabSalvo = service.cadastrarTrabalhador(trab);
    		//ENTAO
    		assertThat(trabSalvo).isNotNull();
    		verify(repo).save(any(Trabalhador.class));
    	}
    	
    	@Test
    	void deve_Lancar_Exception_Quando_CPF_Existir() throws Exception {
    		//DADO
    		final Trabalhador trab = new Trabalhador(1L, "Rivaldo", 26, "07081244459", 25.0, 20);
    		given(repo.findByCpf(trab.getCpf())).willReturn(Optional.of(trab));
    		//QUANTO
    		assertThrows(Exception.class, () -> {
    			service.cadastrarTrabalhador(trab);
    		});
    		//ENTAO
    		verify(repo, never()).save(any(Trabalhador.class));		
    	}
    }
	
	
	//@Test
	//BuscarTodos
	
	//@Test
	//BuscarUm
	
	//@Test
	//BuscarDeletar
	
	//@Test
	//CalcularSalario
	
	//@Test
	//CalcularHorasTrabalhadas
	
	

}
