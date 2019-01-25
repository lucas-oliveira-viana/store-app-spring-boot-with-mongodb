package com.lucas.loja.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.domain.Cliente;
import com.lucas.loja.domain.Compra;
import com.lucas.loja.domain.Endereco;
import com.lucas.loja.domain.Funcionario;
import com.lucas.loja.domain.produto.ProdutoComprado;
import com.lucas.loja.domain.produto.ProdutoEmEstoque;
import com.lucas.loja.exception.compra.CompraNotFoundException;
import com.lucas.loja.exception.compra.InsufficientEstoqueException;
import com.lucas.loja.exception.funcionario.FuncionarioAlreadyExistsException;
import com.lucas.loja.exception.produto.ProdutoAlreadyExistsInEstoqueException;
import com.lucas.loja.exception.produto.ProdutoNotFoundException;
import com.lucas.loja.repository.ClienteRepository;
import com.lucas.loja.repository.CompraProdutoRepository;
import com.lucas.loja.repository.CompraRepository;
import com.lucas.loja.repository.EnderecoRepository;
import com.lucas.loja.repository.EstoqueRepository;
import com.lucas.loja.repository.FuncionarioRepository;

@Service
public class CompraService {

	@Autowired
	public CompraRepository compraRepository;
	@Autowired
	public ClienteRepository clienteRepository;
	@Autowired
	public FuncionarioRepository funcionarioRepository;
	@Autowired
	public EstoqueRepository estoqueRepository;
	@Autowired
	public CompraProdutoRepository compraProdutoRepository;
	@Autowired
	public EnderecoRepository enderecoRepository;

	public List<Compra> findAllCompras() {
		return compraRepository.findAll();
	}

	public List<ProdutoEmEstoque> findAllProdutosEmEstoque() {
		return estoqueRepository.findAll();
	}

	public Compra findComprasById(String id) {
		Optional<Compra> compra = compraRepository.findById(id);
		return compra.orElseThrow(() -> new CompraNotFoundException("Cliente não encontrado"));
	}
	
	private ProdutoEmEstoque findProdutoEmEstoqueById(String id) {
		Optional<ProdutoEmEstoque> produtoEmEstoque = estoqueRepository.findById(id);
		return produtoEmEstoque.orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
	}
	
	public ProdutoEmEstoque findByCodigoBarras(String codigoBarras) {
		return estoqueRepository.findByCodigoBarras(codigoBarras);
	}
	
	public Compra insertCompra(Compra compra) {
		salvarEntidades(compra);
		verificarSeProdutoCompradoExisteNoEstoque(compra.getProdutosComprados());
		definirValorTotal(compra);
		return compraRepository.insert(compra);
	}

	public List<ProdutoComprado> saveCompraProduto(List<ProdutoComprado> produtosComprados) {
		return compraProdutoRepository.saveAll(produtosComprados);
	}

	public ProdutoEmEstoque saveProdutoNoEstoque(ProdutoEmEstoque produtoParaSerCadastrado) {
		verificaSeProdutoCadastradoExisteNoEstoque(produtoParaSerCadastrado);
		return estoqueRepository.save(produtoParaSerCadastrado);
	}

	public Endereco saveEndereco(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}

	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Funcionario saveFuncionario(Funcionario funcionario) {
		verificaSeFuncionarioJaExiste(funcionario);
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

	public Compra updateCompra(Compra compraAtualizada) {
		Compra compraAntiga = findComprasById(compraAtualizada.getId());
		updateDataCompra(compraAntiga, compraAtualizada);
		return compraRepository.save(compraAtualizada);
	}
	
	private void updateDataCompra(Compra compraAntiga, Compra compraAtualizada) {
		compraAntiga.setProdutosComprados(compraAtualizada.getProdutosComprados());
		
		compraAtualizada.getCliente().setId(compraAntiga.getCliente().getId());
		compraAntiga.setCliente(compraAtualizada.getCliente());
		saveCliente(compraAtualizada.getCliente());
		
		compraAtualizada.getFuncionario().setId(compraAntiga.getCliente().getId());
		compraAntiga.setFuncionario(compraAtualizada.getFuncionario());
		saveFuncionario(compraAtualizada.getFuncionario());
		
		compraAntiga.setFormaPagamento(compraAtualizada.getFormaPagamento());
		compraAntiga.setValorTotal(compraAtualizada.getValorTotal());

	}
	
	public void updateProdutoEmEstoque(ProdutoEmEstoque produtoEmEstoqueAtualizado) {
		ProdutoEmEstoque produtoEmEstoqueAntigo = findProdutoEmEstoqueById(produtoEmEstoqueAtualizado.getId());
		updateDataProdutoEmEstoque(produtoEmEstoqueAntigo, produtoEmEstoqueAtualizado);
		estoqueRepository.save(produtoEmEstoqueAtualizado);
	}

	private void updateDataProdutoEmEstoque(ProdutoEmEstoque produtoEmEstoqueAntigo,
			ProdutoEmEstoque produtoEmEstoqueAtualizado) {
		produtoEmEstoqueAtualizado.setId(produtoEmEstoqueAntigo.getId());
		produtoEmEstoqueAntigo.setNome(produtoEmEstoqueAtualizado.getNome());
		produtoEmEstoqueAntigo.setValor(produtoEmEstoqueAtualizado.getValor());
		produtoEmEstoqueAntigo.setCodigoBarras(produtoEmEstoqueAtualizado.getCodigoBarras());
		produtoEmEstoqueAntigo.setEstoque(produtoEmEstoqueAtualizado.getEstoque());
	}

	private void verificaSeFuncionarioJaExiste(Funcionario funcionario) {
		if (funcionarioRepository.findByEmail(funcionario.getEmail()) != null) {
			throw new FuncionarioAlreadyExistsException("Já existe um funcionário com esse e-mail");
		}
	}
	
	private void verificaSeProdutoCadastradoExisteNoEstoque(ProdutoEmEstoque produtoParaSerCadastrado) {
			if (findByCodigoBarras(produtoParaSerCadastrado.getCodigoBarras()) != null) {
				throw new ProdutoAlreadyExistsInEstoqueException("Produto já existe no estoque");
		}
	}
	
	private void definirValorTotal(Compra compra) {
		double somaValorDeTodosOsProdutos = listValorDeCadaProdutoComprado(compra).stream().mapToDouble(valorProduto -> valorProduto).sum();
		compra.setValorTotal(somaValorDeTodosOsProdutos);
	}

	private List<Double> listValorDeCadaProdutoComprado(Compra compra) {
		return compra.getProdutosComprados().stream().map(produto -> produto.getQuantidade() * produto.getValor()).collect(Collectors.toList());
	}

	private ProdutoComprado verificarSeProdutoCompradoExisteNoEstoque(List<ProdutoComprado> produtosComprados) {
		for (ProdutoComprado produtoComprado : produtosComprados) {
			boolean existeProdutoNoEstoque = findByCodigoBarras(produtoComprado.getCodigoBarras()) != null;
			if (existeProdutoNoEstoque) {
				retiraDoEstoque(produtoComprado);
				return produtoComprado;
			}
		}
		throw new ProdutoNotFoundException("Produto não encontrado no estoque");
	}
	
	private ProdutoEmEstoque retiraDoEstoque(ProdutoComprado produtoComprado) {
		ProdutoEmEstoque estoqueDoProdutoComprado = findByCodigoBarras(produtoComprado.getCodigoBarras());
		
		Integer quantidadeDeEstoqueDoProdutoComprado = estoqueDoProdutoComprado.getEstoque();
		Integer quantidadeProdutoComprada = produtoComprado.getQuantidade();
		
		if (quantidadeDeEstoqueDoProdutoComprado < quantidadeProdutoComprada) {
			throw new InsufficientEstoqueException("Existem apenas " + quantidadeDeEstoqueDoProdutoComprado + " unidades" + " de " + produtoComprado.getNome() + " no estoque!");
		}
		estoqueDoProdutoComprado.setEstoque(quantidadeDeEstoqueDoProdutoComprado -= quantidadeProdutoComprada);
		return estoqueDoProdutoComprado;
	}

	private void salvarEntidades(Compra compra) {
		saveEndereco(compra.getCliente().getEndereco());
		saveCliente(compra.getCliente());
		saveFuncionario(compra.getFuncionario());
		saveEndereco(compra.getFuncionario().getEndereco());
	}
}
