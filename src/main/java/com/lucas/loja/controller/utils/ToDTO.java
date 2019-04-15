package com.lucas.loja.controller.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.lucas.loja.dto.ClienteDTO;
import com.lucas.loja.dto.CompraDTO;
import com.lucas.loja.dto.EnderecoDTO;
import com.lucas.loja.dto.FuncionarioDTO;
import com.lucas.loja.dto.ProdutoEmEstoqueDTO;
import com.lucas.loja.entities.Cliente;
import com.lucas.loja.entities.Compra;
import com.lucas.loja.entities.Endereco;
import com.lucas.loja.entities.Funcionario;
import com.lucas.loja.entities.produto.ProdutoEmEstoque;

public class ToDTO {

	public static List<FuncionarioDTO> passarFuncionarioParaDTO(List<Funcionario> listaFuncionarios) {
		return listaFuncionarios.stream().map(funcionario -> new FuncionarioDTO(funcionario)).collect(Collectors.toList());
	}
	
	public static List<ClienteDTO> passarClienteParaDTO(List<Cliente> clientes) {
		return clientes.stream().map(cliente -> new ClienteDTO(cliente)).collect(Collectors.toList());
	}
	
	public static List<CompraDTO> passarCompraParaDTO(List<Compra> comprasQueContemEsseProduto) {
		return comprasQueContemEsseProduto.stream().map(compra -> new CompraDTO(compra)).collect(Collectors.toList());
	}
	
	public static List<EnderecoDTO> passarEnderecoParaDTO(List<Endereco> enderecos) {
		return enderecos.stream().map(endereco -> new EnderecoDTO(endereco)).collect(Collectors.toList());
	}
	
	public static List<ProdutoEmEstoqueDTO> passarEstoqueParaDTO(List<ProdutoEmEstoque> produtoEmEstoque) {
		return produtoEmEstoque.stream().map(produto -> new ProdutoEmEstoqueDTO(produto))
				.collect(Collectors.toList());
	}
}
