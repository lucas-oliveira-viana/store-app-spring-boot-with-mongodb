package com.lucas.loja.service;

import static com.lucas.loja.controller.utils.Validators.validarCPF;
import static com.lucas.loja.controller.utils.Validators.validarRG;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.domain.Cliente;
import com.lucas.loja.exception.cliente.ClienteAlreadyExistsException;
import com.lucas.loja.exception.cliente.ClienteNotFoundException;
import com.lucas.loja.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> findAllClientes(){
		return clienteRepository.findAll();
	}
	
	public Cliente findClienteById(String id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ClienteNotFoundException("Funcionario não encontrado"));
	}
	
	public Cliente saveCliente(Cliente cliente) {
		verificaSeClienteJaExiste(cliente);
		validarCPF(cliente.getCPF());
		validarRG(cliente.getRG());
		return clienteRepository.save(cliente);
	}

	public void deleteCliente(String id) {
		clienteRepository.deleteById(id);
	}

	public void updateCliente(Cliente clienteAtualizado) {
		Cliente clienteAntigo = findClienteById(clienteAtualizado.getId());
		updateDataCliente(clienteAntigo, clienteAtualizado);
		clienteRepository.save(clienteAntigo);
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
	
	private void verificaSeClienteJaExiste(Cliente cliente) {
		if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
			throw new ClienteAlreadyExistsException("Já existe um funcionário com esse e-mail");
		}
	}
}
