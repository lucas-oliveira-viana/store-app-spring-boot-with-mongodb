package com.lucas.loja.service;

import static com.lucas.loja.service.validator.TipoDocumento.CEP;
import static com.lucas.loja.service.validator.TipoDocumento.CPF;
import static com.lucas.loja.service.validator.TipoDocumento.EMAIL;
import static com.lucas.loja.service.validator.TipoDocumento.RG;
import static com.lucas.loja.service.validator.TipoUsuario.CLIENTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.domain.Cliente;
import com.lucas.loja.exception.cliente.ClienteNotFoundException;
import com.lucas.loja.repository.ClienteRepository;
import com.lucas.loja.service.validator.ValidatorCalendar;
import com.lucas.loja.service.validator.ValidatorDocumento;
import com.lucas.loja.service.validator.VerificationDocumento;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private VerificationDocumento verificationDocumento;
	
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
		ValidatorCalendar.verificaSeTemIdadeMinima(cliente.getDataNascimento());
		
		verificationDocumento.verificaSeCampoJaEstaCadastrado(RG, CLIENTE);
		verificationDocumento.verificaSeCampoJaEstaCadastrado(CPF, CLIENTE);
		verificationDocumento.verificaSeCampoJaEstaCadastrado(EMAIL, CLIENTE);
		
		ValidatorDocumento.validarDocumento(cliente.getCPF(), CPF);
		ValidatorDocumento.validarDocumento(cliente.getRG(), RG);
		ValidatorDocumento.validarDocumento(cliente.getEndereco().getCep(), CEP);
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
