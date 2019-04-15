package com.lucas.loja.service;

import static com.lucas.loja.service.validator.TipoDocumento.CEP;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.entities.Endereco;
import com.lucas.loja.repository.EnderecoRepository;
import com.lucas.loja.service.validator.ValidatorDocumento;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Endereco> findAllEnderecos() {
		return enderecoRepository.findAll();
	}

	public void saveEndereco(Endereco endereco) {
		ValidatorDocumento.validarDocumento(endereco.getCep(), CEP);
		enderecoRepository.save(endereco);
	}

	public void deleteEndereco(String id) {
		enderecoRepository.deleteById(id);
	}
}
