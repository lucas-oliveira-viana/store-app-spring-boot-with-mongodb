package com.lucas.loja.exception.endereco;

public class EnderecoNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EnderecoNotFoundException(String msg) {
		super(msg);
	}
}
