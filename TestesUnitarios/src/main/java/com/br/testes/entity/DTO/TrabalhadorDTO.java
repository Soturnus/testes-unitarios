package com.br.testes.entity.DTO;

import javax.validation.constraints.NotEmpty;

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
	
	@NotEmpty
	private String nome;
	
	private Integer idade;
	
	@NotEmpty
	private String cpf;
	
	private Double rendaDiaria;
	
	private Integer diasTrabalhados;
	
	public Trabalhador transformaParaObjeto(TrabalhadorDTO dto) {
		return new Trabalhador(this.nome, this.idade, this.cpf, this.rendaDiaria, this.diasTrabalhados);
	}
}
