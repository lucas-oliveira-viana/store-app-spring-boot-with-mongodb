package com.lucas.loja.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.domain.Cliente;
import com.lucas.loja.domain.Compra;
import com.lucas.loja.domain.Endereco;
import com.lucas.loja.domain.Funcionario;
import com.lucas.loja.domain.produto.ProdutoComprado;
import com.lucas.loja.domain.produto.ProdutoEmEstoque;
import com.lucas.loja.exception.ProdutoNotFoundException;
import com.lucas.loja.exception.compra.CompraNotFoundException;
import com.lucas.loja.exception.compra.InsufficientEstoqueException;
import com.lucas.loja.repository.ClienteRepository;
import com.lucas.loja.repository.CompraProdutoRepository;
import com.lucas.loja.repository.CompraRepository;
import com.lucas.loja.repository.EnderecoRepository;
import com.lucas.loja.repository.EstoqueRepository;
import com.lucas.loja.repository.FuncionarioRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EstoqueRepository estoqueRepository;

	@Autowired
	private CompraProdutoRepository compraProdutoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<Compra> findAllCompras() {
		return compraRepository.findAll();
	}

	public List<ProdutoEmEstoque> findAllEstoqueProdutos() {
		return estoqueRepository.findAll();
	}

	public Compra findComprasById(String id) {
		Optional<Compra> compra = compraRepository.findById(id);
		return compra.orElseThrow(() -> new CompraNotFoundException("Cliente não encontrado!"));
	}
	
	public ProdutoEmEstoque findByCodigoBarras(String codigoBarras) {
		return estoqueRepository.findByCodigoBarras(codigoBarras);
	}

	public Compra insertCompras(Compra compra) {
		salvarEntidades(compra);
		verificarSeProdutoCompradoExisteNoEstoque(compra.getProdutosComprados());
		definirValorTotal(compra);
		return compraRepository.insert(compra);
	}

	public ProdutoComprado verificarSeProdutoCompradoExisteNoEstoque(List<ProdutoComprado> produtosComprados) {
		for (ProdutoComprado produtoComprado : produtosComprados) {
			boolean existeProdutoNoEstoque = findByCodigoBarras(produtoComprado.getCodigoBarras()) != null;
			if (existeProdutoNoEstoque) {
				retiraDoEstoque(produtoComprado);
				return produtoComprado;
			}
		}
		throw new ProdutoNotFoundException("Produto não encontrado no estoque");
	}
	
	private void retiraDoEstoque(ProdutoComprado produtoComprado) {
		ProdutoEmEstoque produtoCompradoEmEstoque = findByCodigoBarras(produtoComprado.getCodigoBarras());
		Integer quantidadeDoProdutoEmEstoque = produtoCompradoEmEstoque.getEstoque();
		Integer quantidadeProdutoComprada = produtoComprado.getQuantidade();
		if (quantidadeDoProdutoEmEstoque < quantidadeProdutoComprada) {
			throw new InsufficientEstoqueException("Existem apenas " + quantidadeDoProdutoEmEstoque + " unidades" + " de " + produtoComprado.getNome() + " no estoque!");
		}
		produtoCompradoEmEstoque.setEstoque(quantidadeDoProdutoEmEstoque -= quantidadeProdutoComprada);
		estoqueRepository.save(produtoCompradoEmEstoque);
	}

	private void definirValorTotal(Compra compra) {
		double somaValorDeTodosOsProdutos = listValorDeCadaProdutoComprado(compra).stream()
				.mapToDouble(valorProduto -> valorProduto).sum();
		compra.setValorTotal(somaValorDeTodosOsProdutos);
	}

	private List<Double> listValorDeCadaProdutoComprado(Compra compra) {

		List<Double> listaValoresTotais = new ArrayList<>();

		List<ProdutoComprado> produtosComprados = compra.getProdutosComprados();
		for (ProdutoComprado produto : produtosComprados) {
			listaValoresTotais.add(produto.calcularValorTotalCadaProduto(produto.getQuantidade(), produto.getValor()));
		}

		return listaValoresTotais;
	}

	public List<ProdutoComprado> saveCompraProduto(List<ProdutoComprado> produtosComprados) {
		return compraProdutoRepository.saveAll(produtosComprados);
	}

	public List<ProdutoEmEstoque> saveProdutoNoEstoque(List<ProdutoEmEstoque> produtosEmEstoque) {
		return estoqueRepository.saveAll(produtosEmEstoque);
	}

	public Endereco saveEndereco(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}

	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Funcionario saveFuncionario(Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}

	public Compra saveCompra(Compra compra) {
		return compraRepository.save(compra);
	}

	public void deleteCompra(String id) {
		compraRepository.deleteById(id);
	}

	public void deleteAll() {
		compraRepository.deleteAll();
	}

	public Compra updateCompra(Compra compra) {
		Compra compraAtualizada = findComprasById(compra.getId());
		updateData(compraAtualizada, compra);
		return compraRepository.save(compraAtualizada);
	}

	private void updateData(Compra compraAtualizada, Compra compra) {
		compraAtualizada.setProdutosComprados(compra.getProdutosComprados());
		compraAtualizada.setCliente(compra.getCliente());
		compraAtualizada.setFuncionario(compra.getFuncionario());
		compraAtualizada.setFormaPagamento(compra.getFormaPagamento());
		compraAtualizada.setValorTotal(compra.getValorTotal());

	}

	private void salvarEntidades(Compra compra) {
		saveEndereco(compra.getCliente().getEndereco());
		saveCliente(compra.getCliente());
		saveFuncionario(compra.getFuncionario());
		saveEndereco(compra.getFuncionario().getEndereco());
	}
}
