package com.lucas.loja.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="cliente")
public class Cliente extends Pessoa {

	@Id
	private String id;
	
	public Cliente(String id, String nome, Date dataNascimento, String cpf, String rg, String email, String telefone,
			Endereco endereco) {
		super(nome, dataNascimento, cpf, rg, email, telefone, endereco);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
