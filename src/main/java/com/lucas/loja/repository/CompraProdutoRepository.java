package com.lucas.loja.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lucas.loja.domain.produto.ProdutoComprado;

@Repository
public interface CompraProdutoRepository extends MongoRepository<ProdutoComprado, String>{

}
