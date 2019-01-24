package com.lucas.loja.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.lucas.loja.domain.produto.ProdutoComprado;

@Document(collection="compra")
public class Compra {

	@Id
	private String id;
	
	private List<ProdutoComprado> produtosComprados;
	
	@DBRef
	private Cliente cliente;
	
	@DBRef
	private Funcionario funcionario;
	
	private String formaPagamento;
	
	private Double valorTotal;
	
	public Compra(String id, List<ProdutoComprado> produto, Cliente cliente,
			Funcionario funcionario, String formaPagamento) {
		this.id = id;
		this.produtosComprados = produto;
		this.cliente = cliente;
		this.funcionario = funcionario;
		this.formaPagamento = formaPagamento;
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
