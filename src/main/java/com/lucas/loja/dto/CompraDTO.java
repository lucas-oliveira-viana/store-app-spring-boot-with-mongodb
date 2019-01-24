package com.lucas.loja.dto;

import java.util.List;

import com.lucas.loja.domain.Cliente;
import com.lucas.loja.domain.Compra;
import com.lucas.loja.domain.Funcionario;
import com.lucas.loja.domain.produto.ProdutoComprado;

public class CompraDTO {
	
	private String id;
	private List<ProdutoComprado> produtosComprados;
	private Integer quantidade;
	private Cliente cliente;
	private Funcionario funcionario;
	private String formaPagamento;
	private Double valorTotal;
	
	public CompraDTO() {
	}
	
	public CompraDTO(Compra compra) {
		id = compra.getId();
		produtosComprados = compra.getProdutosComprados();
		cliente = compra.getCliente();
		funcionario = compra.getFuncionario();
		valorTotal = compra.getValorTotal();
		formaPagamento = compra.getFormaPagamento();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<ProdutoComprado> getProdutosComprados() {
		return produtosComprados;
	}

	public void setProdutosComprados(List<ProdutoComprado> produto) {
		this.produtosComprados = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
