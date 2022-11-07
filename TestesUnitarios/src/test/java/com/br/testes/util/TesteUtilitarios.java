package com.br.testes.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author : Rivaldo Oliveira
 * Testes Unitários
 * @See https://github.com/Soturnus/testes-unitarios 
 */
class TesteUtilitarios {

	@Nested
	class TesteValidaçõesCPF {
		
		@Test
		void deve_retornarTrue_Quando_CPFforValido() {
			//DADO 
			String cpf = "13474562444";
			//QUANDO
			boolean validado = ValidaCPF.isCPF(cpf);
			//ENTAO
			assertTrue(validado);
		}
		
		@Test
		void deve_retornarFalse_Quando_TodosOsNumerosForemIguais() {
			//DADO 
			String cpf = "77777777777";
			//QUANDO
			boolean validado = ValidaCPF.isCPF(cpf);
			//ENTAO
			assertFalse(validado);
		}
		
		@Test
		void deve_retornarFalse_Quando_possuirMaisde11Caracteres() {
			//DADO  
			String cpf = "134745624441";
			//QUANDO
			boolean validado = ValidaCPF.isCPF(cpf);
			//ENTAO
			assertFalse(validado);
		}
		
		@Test
		void deve_retornarFalse_Quando_possuirMenosde11Caracteres() {
			//DADO 
			String cpf = "1347456244";
			//QUANDO
			boolean validado = ValidaCPF.isCPF(cpf);
			//ENTAO
			assertFalse(validado);
		}
	}
}
