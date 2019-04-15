package com.lucas.loja.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lucas.loja.entities.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String>{

	Cliente findByEmail(String email);

	Cliente findByCpf(String cpf);
	
	Cliente findByRg(String rg);
}
