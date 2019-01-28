package com.lucas.loja.service;

import static com.lucas.loja.controller.utils.Validators.validarCPF;
import static com.lucas.loja.controller.utils.Validators.validarRG;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.domain.Funcionario;
import com.lucas.loja.exception.funcionario.FuncionarioAlreadyExistsException;
import com.lucas.loja.exception.funcionario.FuncionarioNotFoundException;
import com.lucas.loja.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public List<Funcionario> findAllFuncionarios() {
		return funcionarioRepository.findAll();
	}
	
	private Funcionario findFuncionarioById(String id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		return funcionario.orElseThrow(() -> new FuncionarioNotFoundException("Funcionario não encontrado"));
	}
	
	public Funcionario saveFuncionario(Funcionario funcionario) {
		verificaSeFuncionarioJaExiste(funcionario);
		validarCPF(funcionario.getCPF());
		validarRG(funcionario.getRG());
		return funcionarioRepository.save(funcionario);
	}

	public void updateFuncionario(Funcionario funcionarioAtualizado) {
		Funcionario funcionarioAntigo = findFuncionarioById(funcionarioAtualizado.getId());
		updateDataFuncionario(funcionarioAntigo, funcionarioAtualizado);
		funcionarioRepository.save(funcionarioAntigo);
	}

	private void updateDataFuncionario(Funcionario funcionarioAntigo, Funcionario funcionarioAtualizado) {
		funcionarioAntigo.setNome(funcionarioAtualizado.getNome());
		funcionarioAntigo.setDataNascimento(funcionarioAtualizado.getDataNascimento());
		funcionarioAntigo.setCpf(funcionarioAtualizado.getCPF());
		funcionarioAntigo.setRg(funcionarioAtualizado.getRG());
		funcionarioAntigo.setEmail(funcionarioAtualizado.getEmail());
		funcionarioAntigo.setTelefone(funcionarioAtualizado.getTelefone());
		funcionarioAntigo.setEndereco(funcionarioAtualizado.getEndereco());
		funcionarioAntigo.setCargo(funcionarioAtualizado.getCargo());
	}

	public void deleteFuncionario(String id) {
		funcionarioRepository.deleteById(id);
	}
	
	private void verificaSeFuncionarioJaExiste(Funcionario funcionario) {
		if (funcionarioRepository.findByEmail(funcionario.getEmail()) != null) {
			throw new FuncionarioAlreadyExistsException("Já existe um funcionário com esse e-mail");
		}
	}
}
