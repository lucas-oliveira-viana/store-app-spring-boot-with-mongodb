package com.lucas.loja.exception.cliente;

public class InsufficientAgeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InsufficientAgeException(String msg) {
		super(msg);
	}
}
