package com.lucas.loja.entities;

import java.util.Calendar;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class Pessoa {

	private String nome;
	private Calendar dataNascimento;
	private String cpf;
	private String rg;
	private String email;
	private String telefone;
	private Endereco endereco;
	
	public Pessoa(String nome, Calendar dataNascimento, String cpf, String rg, String email, String telefone, Endereco endereco) {
		super();
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
		this.rg = rg;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCPF() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getRG() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}
}
