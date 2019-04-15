package com.lucas.loja.service;

import static com.lucas.loja.entities.Compra.definirValorTotal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.entities.Compra;
import com.lucas.loja.exception.compra.CompraNotFoundException;
import com.lucas.loja.repository.CompraRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Autowired
	private EstoqueService estoqueService;

	public List<Compra> findAllCompras() {
		return compraRepository.findAll();
	}

	public Compra findComprasById(String id) {
		Optional<Compra> compra = compraRepository.findById(id);
		return compra.orElseThrow(() -> new CompraNotFoundException("Cliente não encontrado"));
	}

	public Compra saveCompra(Compra compra) {
		estoqueService.verificarSeProdutoCompradoExisteNoEstoque(compra.getProdutosComprados());
		definirValorTotal(compra);
		salvarEntidades(compra);
		return compraRepository.save(compra);
	}
	
	public List<Compra> findByProdutosComprados(String produto){
		return compraRepository.findByProdutosCompradosNomeContainingIgnoreCase(produto);
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
		return saveCompra(compraAtualizada);
	}

	private void updateDataCompra(Compra compraAntiga, Compra compraAtualizada) {
		compraAntiga.setProdutosComprados(compraAtualizada.getProdutosComprados());

		salvarEntidadeCliente(compraAntiga, compraAtualizada);

		salvarEntidadeFuncionario(compraAntiga, compraAtualizada);

		compraAntiga.setFormaPagamento(compraAtualizada.getFormaPagamento());
		compraAntiga.setValorTotal(compraAtualizada.getValorTotal());

	}

	private void salvarEntidadeFuncionario(Compra compraAntiga, Compra compraAtualizada) {
		compraAtualizada.getFuncionario().setId(compraAntiga.getCliente().getId());
		compraAntiga.setFuncionario(compraAtualizada.getFuncionario());
		funcionarioService.saveFuncionario(compraAtualizada.getFuncionario());
	}

	private void salvarEntidadeCliente(Compra compraAntiga, Compra compraAtualizada) {
		compraAtualizada.getCliente().setId(compraAntiga.getCliente().getId());
		compraAntiga.setCliente(compraAtualizada.getCliente());
		clienteService.saveCliente(compraAtualizada.getCliente());
	}

	private void salvarEntidades(Compra compra) {
		enderecoService.saveEndereco(compra.getCliente().getEndereco());
		clienteService.saveCliente(compra.getCliente());
		funcionarioService.saveFuncionario(compra.getFuncionario());
		enderecoService.saveEndereco(compra.getFuncionario().getEndereco());
	}
}
