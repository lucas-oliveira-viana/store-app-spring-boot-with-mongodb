package com.lucas.loja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.controller.utils.Validators;
import com.lucas.loja.domain.Endereco;
import com.lucas.loja.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<Endereco> findAllEnderecos() {
		return enderecoRepository.findAll();
	}

	public void saveEndereco(Endereco endereco) {
		Validators.validarCEP(endereco.getCep());
		enderecoRepository.save(endereco);
	}

	public void deleteEndereco(String id) {
		enderecoRepository.deleteById(id);
	}
}
