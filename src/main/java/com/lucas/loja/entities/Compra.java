package com.lucas.loja.entities;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.lucas.loja.entities.produto.Cesta;

@Document(collection="compra")
public class Compra {

	@Id
	private String id;
	
	private List<Cesta> produtosComprados;
	
	@DBRef
	private Cliente cliente;
	
	@DBRef
	private Funcionario funcionario;
	
	private String formaPagamento;
	
	private Double valorTotal;
	
	public Compra(String id, List<Cesta> produtosComprados, Cliente cliente,
			Funcionario funcionario, String formaPagamento) {
		this.id = id;
		this.produtosComprados = produtosComprados;
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

	public List<Cesta> getProdutosComprados() {
		return produtosComprados;
	}

	public void setProdutosComprados(List<Cesta> produto) {
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
	
	public static void definirValorTotal(Compra compra) {
		double somaValorDeTodosOsProdutos = listValorDeCadaProdutoComprado(compra)
																				.stream()
																				.mapToDouble(valorProduto -> valorProduto)
																				.sum();
		compra.setValorTotal(somaValorDeTodosOsProdutos);
	}

	public static List<Double> listValorDeCadaProdutoComprado(Compra compra) {
		return compra
				.getProdutosComprados()
				.stream()
				.map(produto -> produto.getQuantidade() * produto.getValor())
				.collect(Collectors.toList());
	}
}
