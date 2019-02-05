package com.lucas.loja.service.validator;

import static com.lucas.loja.service.validator.TipoDocumento.CPF;
import static com.lucas.loja.service.validator.TipoDocumento.EMAIL;
import static com.lucas.loja.service.validator.TipoDocumento.RG;
import static com.lucas.loja.service.validator.TipoUsuario.CLIENTE;
import static com.lucas.loja.service.validator.TipoUsuario.FUNCIONARIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.exception.DocumentException;
import com.lucas.loja.exception.funcionario.FuncionarioAlreadyExistsException;
import com.lucas.loja.repository.ClienteRepository;
import com.lucas.loja.repository.FuncionarioRepository;

@Service
public class VerificationDocumento {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public void verificaSeCampoJaEstaCadastrado(String documento, String tipoDeUsuario) {

		switch (tipoDeUsuario) {
		case FUNCIONARIO:
			switch (documento) {
			case CPF:
				if (funcionarioRepository.findByCpf(documento) != null) {
					throw new DocumentException(mensagemErroAlreadyExists(FUNCIONARIO, CPF));
				}
			case RG:
				if (funcionarioRepository.findByRg(documento) != null) {
					throw new DocumentException(mensagemErroAlreadyExists(FUNCIONARIO, RG));
				}
			case EMAIL:
				if (funcionarioRepository.findByEmail(documento) != null) {
					throw new FuncionarioAlreadyExistsException(mensagemErroAlreadyExists(FUNCIONARIO, EMAIL));
				}
			}
		case CLIENTE:
			switch (documento) {
			case CPF:
				if (clienteRepository.findByCpf(documento) != null) {
					throw new DocumentException(mensagemErroAlreadyExists(CLIENTE, CPF));
				}
			case RG:
				if (clienteRepository.findByRg(documento) != null) {
					throw new DocumentException(mensagemErroAlreadyExists(CLIENTE, RG));
				}
			case EMAIL:
				if (clienteRepository.findByEmail(documento) != null) {
					throw new FuncionarioAlreadyExistsException(mensagemErroAlreadyExists(CLIENTE, EMAIL));
				}
			}
		}
	}

	private String mensagemErroAlreadyExists(String tipoUsuario, String documento) {
		return "Já existe um " + tipoUsuario + " com esse " + documento;
	}
}
