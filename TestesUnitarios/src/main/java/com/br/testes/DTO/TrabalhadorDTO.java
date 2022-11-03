package com.br.testes.DTO;

import com.br.testes.entity.Trabalhador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabalhadorDTO {
	
	private String nome;
	
	private Integer idade;

	private String cpf;
	
	private Double rendaDiaria;
	
	private Integer diasTrabalhados;
	
	public Trabalhador transformaParaObjeto() {
		return new Trabalhador(this.nome, this.idade, this.cpf, this.rendaDiaria, this.diasTrabalhados);
	}
}
