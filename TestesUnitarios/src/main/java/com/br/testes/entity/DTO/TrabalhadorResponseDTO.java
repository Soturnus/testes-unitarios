package com.br.testes.entity.DTO;

import com.br.testes.entity.Trabalhador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrabalhadorResponseDTO {
	
	private Long id;
	private String nome;
	private Integer idade;
	private Double rendaDiaria;
	private Integer diasTrabalhados;
	
	public static TrabalhadorResponseDTO transformaEmDTO(Trabalhador trabalhador) {
	    return new TrabalhadorResponseDTO(
	    		trabalhador.getId(), 
	    		trabalhador.getNome(), 
	    		trabalhador.getIdade(),
	    		trabalhador.getRendaDiaria(),
	    		trabalhador.getDiasTrabalhados()
	    	);
	}

}
