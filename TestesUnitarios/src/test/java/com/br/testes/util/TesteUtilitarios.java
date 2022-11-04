package com.br.testes.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TesteUtilitarios {

	@Nested
	class TesteValidaçõesCPF {
		
		@Test
		void deve_retornarTrue_Quando_CPFforValido() {
			//given 
			String cpf = "13474562444";
			//when
			boolean validado = ValidaCPF.isCPF(cpf);
			//then
			assertTrue(validado);
		}
		
		@Test
		void deve_retornarFalse_Quando_TodosOsNumerosForemIguais() {
			//given
			String cpf = "77777777777";
			//when
			boolean validado = ValidaCPF.isCPF(cpf);
			//then
			assertFalse(validado);
		}
		
		@Test
		void deve_retornarFalse_Quando_possuirMaisde11Caracteres() {
			//given 
			String cpf = "134745624441";
			//when
			boolean validado = ValidaCPF.isCPF(cpf);
			//then
			assertFalse(validado);
		}
		
		@Test
		void deve_retornarFalse_Quando_possuirMenosde11Caracteres() {
			//given 
			String cpf = "1347456244";
			//when
			boolean validado = ValidaCPF.isCPF(cpf);
			//then
			assertFalse(validado);
		}
	}
}
