package com.lucas.loja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.domain.Endereco;
import com.lucas.loja.repository.EnderecoRepository;
import com.lucas.loja.service.validator.Validator;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Endereco> findAllEnderecos() {
		return enderecoRepository.findAll();
	}

	public void saveEndereco(Endereco endereco) {
		Validator.validarCEP(endereco.getCep());
		enderecoRepository.save(endereco);
	}

	public void deleteEndereco(String id) {
		enderecoRepository.deleteById(id);
	}
}
