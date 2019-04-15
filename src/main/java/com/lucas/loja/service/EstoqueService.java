package com.lucas.loja.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.entities.produto.Cesta;
import com.lucas.loja.entities.produto.ProdutoEmEstoque;
import com.lucas.loja.exception.compra.InsufficientEstoqueException;
import com.lucas.loja.exception.produto.ProdutoAlreadyExistsInEstoqueException;
import com.lucas.loja.exception.produto.ProdutoNotFoundException;
import com.lucas.loja.repository.EstoqueRepository;

@Service
public class EstoqueService {

	@Autowired
	public EstoqueRepository estoqueRepository;
	
	public List<ProdutoEmEstoque> findAllProdutosEmEstoque() {
		return estoqueRepository.findAll();
	}
	
	public ProdutoEmEstoque findProdutoEmEstoqueById(String id) {
		Optional<ProdutoEmEstoque> produtoEmEstoque = estoqueRepository.findById(id);
		return produtoEmEstoque.orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
	}

	public ProdutoEmEstoque findByCodigoBarras(String codigoBarras) {
		return estoqueRepository.findByCodigoBarras(codigoBarras);
	}
	
	public ProdutoEmEstoque saveProdutoNoEstoque(ProdutoEmEstoque produtoParaSerCadastrado) {
		verificaSeProdutoCadastradoExisteNoEstoque(produtoParaSerCadastrado);
		return estoqueRepository.save(produtoParaSerCadastrado);
	}
	
	public void updateProdutoEmEstoque(ProdutoEmEstoque produtoEmEstoqueAtualizado) {
		ProdutoEmEstoque produtoEmEstoqueAntigo = findProdutoEmEstoqueById(produtoEmEstoqueAtualizado.getId());
		updateDataProdutoEmEstoque(produtoEmEstoqueAntigo, produtoEmEstoqueAtualizado);
		estoqueRepository.save(produtoEmEstoqueAtualizado);
	}
	
	public void deleteProduto(String id) {
		estoqueRepository.deleteById(id);
	}
	
	private void updateDataProdutoEmEstoque(ProdutoEmEstoque produtoEmEstoqueAntigo,
			ProdutoEmEstoque produtoEmEstoqueAtualizado) {

		produtoEmEstoqueAtualizado.setId(produtoEmEstoqueAntigo.getId());
		produtoEmEstoqueAntigo.setNome(produtoEmEstoqueAtualizado.getNome());
		produtoEmEstoqueAntigo.setValor(produtoEmEstoqueAtualizado.getValor());
		produtoEmEstoqueAntigo.setCodigoBarras(produtoEmEstoqueAtualizado.getCodigoBarras());
		produtoEmEstoqueAntigo.setEstoque(produtoEmEstoqueAtualizado.getEstoque());
	}
	
	private void verificaSeProdutoCadastradoExisteNoEstoque(ProdutoEmEstoque produtoParaSerCadastrado) {
		if (findByCodigoBarras(produtoParaSerCadastrado.getCodigoBarras()) != null) {
			throw new ProdutoAlreadyExistsInEstoqueException("Produto já existe no estoque");
		}
	}
	
	public Cesta verificarSeProdutoCompradoExisteNoEstoque(List<Cesta> produtosComprados) {
		for (Cesta produtoComprado : produtosComprados) {
			boolean existeProdutoNoEstoque = findByCodigoBarras(produtoComprado.getCodigoBarras()) != null;
			if (existeProdutoNoEstoque) {
				retiraDoEstoque(produtoComprado);
				return produtoComprado;
			}
		}
		throw new ProdutoNotFoundException("Produto não encontrado no estoque");
	}
	
	public ProdutoEmEstoque retiraDoEstoque(Cesta produtoComprado) {
		ProdutoEmEstoque estoqueDoProdutoComprado = findByCodigoBarras(produtoComprado.getCodigoBarras());

		Integer quantidadeDeEstoqueDoProdutoComprado = estoqueDoProdutoComprado.getEstoque();
		Integer quantidadeProdutoComprada = produtoComprado.getQuantidade();

		if (quantidadeDeEstoqueDoProdutoComprado < quantidadeProdutoComprada) {
			throw new InsufficientEstoqueException("Existem apenas " + quantidadeDeEstoqueDoProdutoComprado
					+ " unidades" + " de " + produtoComprado.getNome() + " no estoque!");
		}
		estoqueDoProdutoComprado.setEstoque(quantidadeDeEstoqueDoProdutoComprado -= quantidadeProdutoComprada);
		return estoqueDoProdutoComprado;
	}
}
