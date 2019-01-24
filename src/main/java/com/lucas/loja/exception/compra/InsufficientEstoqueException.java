package com.lucas.loja.exception.compra;

public class InsufficientEstoqueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InsufficientEstoqueException(String msg) {
		super(msg);
	}
}