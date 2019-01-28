package com.lucas.loja.exception.funcionario;

public class FuncionarioNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public FuncionarioNotFoundException(String msg) {
		super(msg);
	}
}
