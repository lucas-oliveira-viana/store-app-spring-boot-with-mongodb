package com.lucas.loja.service;

import static com.lucas.loja.service.validators.Validator.FUNCIONARIO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.domain.Funcionario;
import com.lucas.loja.exception.funcionario.FuncionarioNotFoundException;
import com.lucas.loja.repository.FuncionarioRepository;
import com.lucas.loja.service.validators.Validator;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private Validator validator;

	public List<Funcionario> findAllFuncionarios() {
		return funcionarioRepository.findAll();
	}
	
	private Funcionario findFuncionarioById(String id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		return funcionario.orElseThrow(() -> new FuncionarioNotFoundException("Funcionario não encontrado"));
	}
	
	public Funcionario saveFuncionario(Funcionario funcionario) {
		passarPorValidacoes(funcionario);
		return funcionarioRepository.save(funcionario);
	}

	private void passarPorValidacoes(Funcionario funcionario) {
		validator.verificaSeUsuarioJaExiste(funcionario.getEmail(), FUNCIONARIO);
		validator.verificaSeCPFJaExiste(funcionario.getCPF(), FUNCIONARIO);
		validator.verificaSeRGJaExiste(funcionario.getRG(), FUNCIONARIO);
		validator.validarCPF(funcionario.getCPF());
		validator.validarRG(funcionario.getRG());
	}
	
	public void deleteFuncionario(String id) {
		funcionarioRepository.deleteById(id);
	}

	public void updateFuncionario(Funcionario funcionarioAtualizado) {
		Funcionario funcionarioAntigo = findFuncionarioById(funcionarioAtualizado.getId());
		updateDataFuncionario(funcionarioAntigo, funcionarioAtualizado);
		saveFuncionario(funcionarioAntigo);
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
}
