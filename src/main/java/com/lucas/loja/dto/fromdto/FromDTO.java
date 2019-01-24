package com.lucas.loja.dto.fromdto;

import com.lucas.loja.domain.Compra;
import com.lucas.loja.domain.Funcionario;
import com.lucas.loja.domain.produto.ProdutoEmEstoque;
import com.lucas.loja.dto.CompraDTO;
import com.lucas.loja.dto.ProdutoEmEstoqueDTO;
import com.lucas.loja.dto.FuncionarioDTO;

public class FromDTO {

	public Compra fromDTOCompra(CompraDTO compraDTO) {
		return new Compra(compraDTO.getId(), compraDTO.getProdutosComprados(), compraDTO.getCliente(),
				compraDTO.getFuncionario(), compraDTO.getFormaPagamento());
	}

	public Funcionario fromDTOFuncionario(FuncionarioDTO funcionarioDTO) {
		return new Funcionario(funcionarioDTO.getId(), funcionarioDTO.getNome(), funcionarioDTO.getDataNascimento(),
				funcionarioDTO.getCpf(), funcionarioDTO.getRg(), funcionarioDTO.getEmail(),
				funcionarioDTO.getTelefone(), funcionarioDTO.getEndereco(), funcionarioDTO.getCargo());
	}
	
	public ProdutoEmEstoque fromDTOProdutoEmEstoque(ProdutoEmEstoqueDTO ProdutoEmEstoqueDTO) {
		return new ProdutoEmEstoque(ProdutoEmEstoqueDTO.getId(), ProdutoEmEstoqueDTO.getNome(), ProdutoEmEstoqueDTO.getValor(),
				ProdutoEmEstoqueDTO.getCodigoBarras(), ProdutoEmEstoqueDTO.getEstoque());
	}
}
