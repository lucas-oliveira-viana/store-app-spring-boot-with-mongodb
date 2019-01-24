package com.lucas.loja.domain.produto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="estoqueprodutos")
public class ProdutoEmEstoque extends Produto{

	@Id
	private String id;
	private Integer estoque;

	public ProdutoEmEstoque(String id, String nome, Double valor, String codigoBarras, Integer estoque) {
		super(nome, valor, codigoBarras);
		this.id = id;
		this.estoque = estoque;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
}
