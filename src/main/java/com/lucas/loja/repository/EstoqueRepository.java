package com.lucas.loja.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lucas.loja.domain.produto.ProdutoEmEstoque;

@Repository
public interface EstoqueRepository extends MongoRepository<ProdutoEmEstoque, String>{

	ProdutoEmEstoque findByCodigoBarras(String codigoBarras);
}
