package com.lucas.loja.exception.cliente;

public class ClienteAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ClienteAlreadyExistsException(String msg) {
		super(msg);
	}
}
