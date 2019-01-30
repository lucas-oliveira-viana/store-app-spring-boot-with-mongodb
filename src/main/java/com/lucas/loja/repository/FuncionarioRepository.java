package com.lucas.loja.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lucas.loja.domain.Funcionario;

@Repository
public interface FuncionarioRepository extends MongoRepository<Funcionario, String>{

	Funcionario findByEmail(String email);

	Funcionario findByCpf(String cpf);

	List<Funcionario> findByRg(String rg);
}
