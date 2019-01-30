package com.lucas.loja.test;

import static com.lucas.loja.service.validator.Validator.validarRG;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.lucas.loja.exception.DocumentException;
import com.lucas.loja.service.validator.Validator;


public class ValidatorTestRG {
	
	private static final String RG_PADRAO = "12.345.678-9";

	@Test
	public void verificarFormatacaoRG() {
		Assert.assertEquals(RG_PADRAO, validarRG("123456789"));
	}
	
	@Test
	public void verificarRGComPontoEHifen() {
		Assertions.assertDoesNotThrow(() -> validarRG("12.345.678-9"));
	}
	
	@Test
	public void verificarQuantidadeDeDigitosInsuficiente() {
		Assertions.assertThrows(DocumentException.class, () -> Validator.validarRG("12345678"));
	}
	
	@Test
	public void verificarLetraDigitadaNoRG() {
		Assertions.assertThrows(DocumentException.class, () -> Validator.validarRG("12345678w"));
	}
}