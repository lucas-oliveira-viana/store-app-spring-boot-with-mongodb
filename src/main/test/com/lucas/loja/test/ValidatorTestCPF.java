package com.lucas.loja.test;

import static com.lucas.loja.service.validator.Validator.validarRG;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.lucas.loja.exception.DocumentException;
import com.lucas.loja.service.validator.Validator;


public class ValidatorTestCPF {
	
	private static final String CPF_PADRAO = "894.147.241-23";

	@Test
	public void verificarFormatacaoCPF() {
		Assert.assertEquals(CPF_PADRAO, validarRG("89414724123"));
	}
	
	@Test
	public void verificarCPFComPontoEHifen() {
		Assertions.assertDoesNotThrow(() -> validarRG("894.147.241-23"));
	}
	
	@Test
	public void verificarQuantidadeDeDigitosInsuficiente() {
		Assertions.assertThrows(DocumentException.class, () -> Validator.validarRG("8941472412"));
	}
	
	@Test
	public void verificarLetraDigitadaNoCPF() {
		Assertions.assertThrows(DocumentException.class, () -> Validator.validarRG("89414724123w"));
	}
}