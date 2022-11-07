package com.br.testes.services.exeptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Object id) {
		super("Trabalhador não encontrado. Id: " + id);
	}
}