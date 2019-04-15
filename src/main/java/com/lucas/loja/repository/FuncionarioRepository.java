package com.lucas.loja.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lucas.loja.entities.Funcionario;

@Repository
public interface FuncionarioRepository extends MongoRepository<Funcionario, String>{

	Funcionario findByEmail(String email);

	Funcionario findByCpf(String cpf);

	Funcionario findByRg(String rg);
}
