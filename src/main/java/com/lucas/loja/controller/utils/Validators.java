package com.lucas.loja.controller.utils;

import com.lucas.loja.exception.DocumentException;

public class Validators {

	public static String validarRG(String rg) {
		if (!rg.contains("-")) {
			rg = rg.substring(0, 2) + "." + rg.substring(2, 5) + "." + rg.substring(5, 8) + "-" + rg.substring(8);
		}
		if (quantidadeDeDigitosDiferenteDe(12, rg)) {
			throw new DocumentException("O RG deve conter 9 digitos!");
		}
		if (contemLetra(rg)) {
			throw new DocumentException("O RG não deve conter letras!");
		}
		return rg;
	}

	public static String validarCPF(String cpf) {
		if (!cpf.contains(".") && !cpf.contains("-")){
			cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
		}
		if (quantidadeDeDigitosDiferenteDe(14, cpf)) {
			throw new DocumentException("O CPF deve conter 11 digitos!");
		}
		if (contemLetra(cpf)) {
			throw new DocumentException("O CPF não deve conter letras!");
		}
		return cpf;
	}
	
	public static String validarCEP(String cep) {
		if (!cep.contains("-")) {
			cep = cep.substring(0, 5) + "-" + cep.substring(5);
		}
		if(quantidadeDeDigitosDiferenteDe(9, cep)) {
			throw new DocumentException("O CEP deve conter 8 digitos!");
		}
		if (contemLetra(cep)) {
			throw new DocumentException("O CPF não deve conter letras!");
		}
		return cep;
	}
	
	private static boolean quantidadeDeDigitosDiferenteDe(Integer valor, String documento) {
		return documento.length() != valor;
	}
	
	private static boolean contemLetra(String rg) {
		return rg.matches(".*[a-zA-Z]+.*");
	}
}
