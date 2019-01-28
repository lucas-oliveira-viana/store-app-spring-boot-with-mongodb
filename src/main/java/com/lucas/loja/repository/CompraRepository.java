package com.lucas.loja.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lucas.loja.domain.Compra;

@Repository
public interface CompraRepository extends MongoRepository<Compra, String>{
	
	List<Compra> findByProdutosCompradosNomeContainingIgnoreCase (String produto);
}
