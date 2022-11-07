package com.br.testes.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.br.testes.entity.Trabalhador;
import com.br.testes.repositories.TrabalhadorRepository;

@Configuration
public class CargaBanco implements CommandLineRunner{
	
	@Autowired
	private TrabalhadorRepository repo;
	
	@Override
	public void run(String... args) throws Exception {
		
		Trabalhador t1 = new Trabalhador(null, "Rivaldo", 26, "07081244459", 25.0, 20);
		Trabalhador t2 = new Trabalhador(null, "Renan", 28, "13474562444", 47.8, 18);
		Trabalhador t3 = new Trabalhador(null, "Zi", 27, "22527433448", 51.2, 15);
		Trabalhador t4 = new Trabalhador(null, "Luana", 1, "84081116474", 10.7, 31);
	
		repo.saveAll(Arrays.asList(t1, t2, t3, t4));
	}

}
