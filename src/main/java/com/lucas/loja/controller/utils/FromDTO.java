package com.lucas.loja.controller.utils;

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

public class FromDTO {

	public static Compra fromDTOCompra(CompraDTO compraDTO) {
		return new Compra(compraDTO.getId(), compraDTO.getProdutosComprados(), compraDTO.getCliente(),
				compraDTO.getFuncionario(), compraDTO.getFormaPagamento());
	}

	public static Cliente fromDTOCliente(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getDataNascimento(),
				clienteDTO.getCpf(), clienteDTO.getRg(), clienteDTO.getEmail(), clienteDTO.getTelefone(),
				clienteDTO.getEndereco());
	}

	public static Funcionario fromDTOFuncionario(FuncionarioDTO funcionarioDTO) {
		return new Funcionario(funcionarioDTO.getId(), funcionarioDTO.getNome(), funcionarioDTO.getDataNascimento(),
				funcionarioDTO.getCpf(), funcionarioDTO.getRg(), funcionarioDTO.getEmail(),
				funcionarioDTO.getTelefone(), funcionarioDTO.getEndereco(), funcionarioDTO.getCargo());
	}

	public static ProdutoEmEstoque fromDTOProdutoEmEstoque(ProdutoEmEstoqueDTO ProdutoEmEstoqueDTO) {
		return new ProdutoEmEstoque(ProdutoEmEstoqueDTO.getId(), ProdutoEmEstoqueDTO.getNome(),
				ProdutoEmEstoqueDTO.getValor(), ProdutoEmEstoqueDTO.getCodigoBarras(),
				ProdutoEmEstoqueDTO.getEstoque());
	}

	public static Endereco fromDTOEndereco(EnderecoDTO enderecoDTO) {
		return new Endereco(enderecoDTO.getCep(), enderecoDTO.getPais(), enderecoDTO.getEstado(), enderecoDTO.getCidade(),
				enderecoDTO.getBairro(), enderecoDTO.getRua(), enderecoDTO.getNumero());
	}
}