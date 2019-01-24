package com.lucas.loja.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lucas.loja.domain.Funcionario;

@Repository
public interface FuncionarioRepository extends MongoRepository<Funcionario, String>{

}
