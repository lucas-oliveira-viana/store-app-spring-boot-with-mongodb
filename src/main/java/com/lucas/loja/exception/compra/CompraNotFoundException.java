package com.lucas.loja.exception.compra;

public class CompraNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CompraNotFoundException(String msg) {
		super(msg);
	}
}
