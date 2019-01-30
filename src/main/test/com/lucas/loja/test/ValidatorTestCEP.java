package com.lucas.loja.test;

import static com.lucas.loja.service.validator.Validator.validarCEP;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.lucas.loja.exception.DocumentException;
import com.lucas.loja.service.validator.Validator;


public class ValidatorTestCEP {
	
	private static final String CEP_PADRAO = "12345-678";

	@Test
	public void verificarFormatacaoCPF() {
		Assert.assertEquals(CEP_PADRAO, validarCEP("12345678"));
	}
	
	@Test
	public void verificarCPFComPontoEHifen() {
		Assertions.assertDoesNotThrow(() -> validarCEP("12345-678"));
	}
	
	@Test
	public void verificarQuantidadeDeDigitosInsuficiente() {
		Assertions.assertThrows(DocumentException.class, () -> Validator.validarRG("12345-67"));
	}
	
	@Test
	public void verificarLetraDigitadaNoCPF() {
		Assertions.assertThrows(DocumentException.class, () -> Validator.validarRG("89414724123w"));
	}
}