package com.lucas.loja.exception.cliente;

public class ClienteNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ClienteNotFoundException(String msg) {
		super(msg);
	}
}
