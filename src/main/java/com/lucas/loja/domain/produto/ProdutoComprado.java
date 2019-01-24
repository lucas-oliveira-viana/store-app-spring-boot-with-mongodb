package com.lucas.loja.domain.produto;

public class ProdutoComprado extends Produto{

	private Integer quantidade;
	private Double valorTotalProduto;
	
	public ProdutoComprado(String nome, Double valor, String codigoBarras, Integer quantidade) {
		super(nome, valor, codigoBarras);
		this.quantidade = quantidade;
	}

	public Double getValorTotalProduto() {
		return valorTotalProduto;
	}

	public void setValorTotalProduto(Double valorTotalProduto) {
		this.valorTotalProduto = valorTotalProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Double calcularValorTotalCadaProduto(Integer quantidade, Double valor) {
		return valorTotalProduto = quantidade * valor;
	}
}
