package com.br.testes.DTO;

import com.br.testes.entity.Trabalhador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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