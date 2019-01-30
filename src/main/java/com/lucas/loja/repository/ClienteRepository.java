package com.lucas.loja.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lucas.loja.domain.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String>{

	Cliente findByEmail(String email);

	Cliente findByCpf(String cpf);
	
	List<Cliente> findByRg(String rg);
}
