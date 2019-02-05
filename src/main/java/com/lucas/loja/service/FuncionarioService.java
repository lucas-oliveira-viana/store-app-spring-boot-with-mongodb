package com.lucas.loja.service;

import static com.lucas.loja.service.validator.TipoDocumento.CEP;
import static com.lucas.loja.service.validator.TipoDocumento.CPF;
import static com.lucas.loja.service.validator.TipoDocumento.EMAIL;
import static com.lucas.loja.service.validator.TipoDocumento.RG;
import static com.lucas.loja.service.validator.TipoUsuario.FUNCIONARIO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.domain.Funcionario;
import com.lucas.loja.exception.funcionario.FuncionarioNotFoundException;
import com.lucas.loja.repository.FuncionarioRepository;
import com.lucas.loja.service.validator.ValidatorCalendar;
import com.lucas.loja.service.validator.ValidatorDocumento;
import com.lucas.loja.service.validator.VerificationDocumento;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private VerificationDocumento verificationDocumento;

	public List<Funcionario> findAllFuncionarios() {
		return funcionarioRepository.findAll();
	}
	
	public Funcionario findFuncionarioById(String id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		return funcionario.orElseThrow(() -> new FuncionarioNotFoundException("Funcionario não encontrado"));
	}
	
	public Funcionario findByCpf(String cpf) {
		return funcionarioRepository.findByCpf(cpf);
	}
	
	public Funcionario saveFuncionario(Funcionario funcionario) {
		passarPorValidacoes(funcionario);
		return funcionarioRepository.save(funcionario);
	}

	private void passarPorValidacoes(Funcionario funcionario) {
		ValidatorCalendar.verificaSeTemIdadeMinima(funcionario.getDataNascimento());
		
		verificationDocumento.verificaSeCampoJaEstaCadastrado(RG, FUNCIONARIO);
		verificationDocumento.verificaSeCampoJaEstaCadastrado(CPF, FUNCIONARIO);
		verificationDocumento.verificaSeCampoJaEstaCadastrado(EMAIL, FUNCIONARIO);
		
		ValidatorDocumento.validarDocumento(funcionario.getCPF(), CPF);
		ValidatorDocumento.validarDocumento(funcionario.getRG(), RG);
		ValidatorDocumento.validarDocumento(funcionario.getEndereco().getCep(), CEP);
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
