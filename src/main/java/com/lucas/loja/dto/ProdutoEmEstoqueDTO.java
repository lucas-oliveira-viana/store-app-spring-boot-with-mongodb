package com.lucas.loja.dto;

import com.lucas.loja.entities.produto.ProdutoEmEstoque;

public class ProdutoEmEstoqueDTO{
	
	private String id;
	private String nome;
	private Double valor;
	private String codigoBarras;
	private Integer estoque;
	
	public ProdutoEmEstoqueDTO() {
	}
	
	public ProdutoEmEstoqueDTO(ProdutoEmEstoque estoqueProduto) {
		this.id = estoqueProduto.getId();
		this.nome = estoqueProduto.getNome();
		this.valor = estoqueProduto.getValor();
		this.codigoBarras = estoqueProduto.getCodigoBarras();
		this.estoque = estoqueProduto.getEstoque();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
}
