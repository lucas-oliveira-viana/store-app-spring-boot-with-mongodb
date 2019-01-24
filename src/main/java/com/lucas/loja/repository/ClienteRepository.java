package com.lucas.loja.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lucas.loja.domain.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String>{

}
