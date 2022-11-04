package com.br.testes.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_trabalhador")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trabalhador implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 

	private String nome;
	
	private Integer idade;
	
	@Column(length = 11)
	private String cpf;
	
	private Double rendaDiaria;
	
	private Integer diasTrabalhados;
	
	public Double calcularSalario() {
		return diasTrabalhados * rendaDiaria;
	}
	
	public Integer horasTrabalhadas() {
		return diasTrabalhados * 8;
	}

	public Trabalhador(String nome, Integer idade, String cpf, Double rendaDiaria, Integer diasTrabalhados) {
		this.nome = nome;
		this.idade = idade;
		this.cpf = cpf;
		this.rendaDiaria = rendaDiaria;
		this.diasTrabalhados = diasTrabalhados;
	}

	public Trabalhador(String nome, Integer idade, Double rendaDiaria, Integer diasTrabalhados) {
		this.nome = nome;
		this.idade = idade;
		this.rendaDiaria = rendaDiaria;
		this.diasTrabalhados = diasTrabalhados;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trabalhador other = (Trabalhador) obj;
		return Objects.equals(id, other.id);
	}
}
