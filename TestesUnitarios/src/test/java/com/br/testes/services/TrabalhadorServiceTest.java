package com.br.testes.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
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
    class TesteServiceCadastro {
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
	
    @Nested
    class TesteServiceConsulta {
    	
    	@Test
    	void deve_Retornar_Todos() {
    		//DADO
    		List<Trabalhador> trabList = new ArrayList<>();
    		trabList.add(new Trabalhador(1L, "Rivaldo", 26, "07081244459", 25.0, 20));
    		trabList.add(new Trabalhador(2L, "Thomas", 28, "13474562444", 47.8, 18));
    		trabList.add(new Trabalhador(3L, "Ayrton", 27, "22527433448", 51.2, 15));
    		//QUANDO
    		given(repo.findAll()).willReturn(trabList);
    		List<Trabalhador> esperado = service.verTodos();
    		//ENTAO
    		assertEquals(esperado, trabList);   		
    		
    	}
    	
    	@Test
    	void deve_Retornar_Um() {
    		//DADO
    		final Long id = 1L;
    		final Trabalhador trabalhador = new Trabalhador(1L, "Rivaldo", 26, "07081244459", 25.0, 20);
    		//QUANDO
    		given(repo.findById(id)).willReturn(Optional.of(trabalhador));
    		final Trabalhador esperado = service.buscarUm(id);
    		//ENTAO
    		assertThat(esperado).isNotNull();    		
    	}
    }
    
    @Nested
    class testeServicoDeletar {
    	
    	/**
    	 * Nesse caso verifiquei se o metodo foi executado
    	 * a quantidade de vezes que foi chamado, 2x, ou seja 
    	 * o repositorio identificou 2 chamadas para deletar o mesmo id com sucesso.
    	 */
    	@Test
    	void deve_Deletar() {
    		//DADO id = 1
    		final Long id = 1L;
    		//QUANDO eu executar 2 vezes
    		service.deletar(id);
    		service.deletar(id);
    		//ENTAO verifique se o repositorio recebeu 2 requisições
    		verify(repo, times(2)).deleteById(id);
    	}
    }
}

	
	//@Test
	//CalcularSalario
	
	//@Test
	//CalcularHorasTrabalhadas
	

