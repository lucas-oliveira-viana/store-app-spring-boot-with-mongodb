package com.lucas.loja.exception.produto;

public class ProdutoAlreadyExistsInEstoqueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProdutoAlreadyExistsInEstoqueException(String msg) {
		super(msg);
	}
}
