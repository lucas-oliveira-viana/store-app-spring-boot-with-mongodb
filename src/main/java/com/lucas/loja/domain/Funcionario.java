package com.lucas.loja.domain;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="funcionario")
public class Funcionario extends Pessoa {

	@Id
	private String id;
	private String cargo;

	public Funcionario(String id, String nome, Calendar dataNascimento, String cpf, String rg, String email, String telefone,
			Endereco endereco, String cargo) {
		super(nome, dataNascimento, cpf, rg, email, telefone, endereco);
		this.id = id;
		this.cargo = cargo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}
