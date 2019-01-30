package com.lucas.loja.service;

import static com.lucas.loja.service.validator.Validator.CLIENTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.domain.Cliente;
import com.lucas.loja.exception.cliente.ClienteNotFoundException;
import com.lucas.loja.repository.ClienteRepository;
import com.lucas.loja.service.validator.Validator;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private Validator validator;
	
	public List<Cliente> findAllClientes(){
		return clienteRepository.findAll();
	}
	
	public Cliente findClienteById(String id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado"));
	}
	
	public Cliente findClienteByCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}
	
	public Cliente saveCliente(Cliente cliente) {
		passarPorValidacoes(cliente);
		return clienteRepository.save(cliente);
	}

	private void passarPorValidacoes(Cliente cliente) {
		validator.verificaSeUsuarioJaExiste(cliente.getEmail(), CLIENTE);
		validator.verificaSeTemIdadeMinima(cliente.getDataNascimento());
		validator.verificaSeCPFJaExiste(cliente.getCPF(), CLIENTE);
		validator.verificaSeRGJaExiste(cliente.getRG(), CLIENTE);
		Validator.validarCPF(cliente.getCPF());
		Validator.validarRG(cliente.getRG());
	}

	public void deleteCliente(String id) {
		clienteRepository.deleteById(id);
	}

	public void updateCliente(Cliente clienteAtualizado) {
		Cliente clienteAntigo = findClienteById(clienteAtualizado.getId());
		updateDataCliente(clienteAntigo, clienteAtualizado);
		saveCliente(clienteAntigo);
	}

	private void updateDataCliente(Cliente clienteAntigo, Cliente clienteAtualizado) {
		clienteAntigo.setNome(clienteAtualizado.getNome());
		clienteAntigo.setDataNascimento(clienteAtualizado.getDataNascimento());
		clienteAntigo.setCpf(clienteAtualizado.getCPF());
		clienteAntigo.setRg(clienteAtualizado.getRG());
		clienteAntigo.setEmail(clienteAtualizado.getEmail());
		clienteAntigo.setTelefone(clienteAtualizado.getTelefone());
		clienteAntigo.setEndereco(clienteAtualizado.getEndereco());
	}
}
