package com.lucas.loja.test.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.lucas.loja.exception.DocumentException;
import com.lucas.loja.service.validator.TipoDocumento;
import com.lucas.loja.service.validator.ValidatorDocumento;


public class ValidatorTestRG {
	
	private static final String RG_PADRAO = "12.345.678-9";

	@Test
	public void verificarFormatacaoRG() {
		Assert.assertEquals(RG_PADRAO, ValidatorDocumento.validarDocumento("123456789", TipoDocumento.CPF));
	}
	
	@Test
	public void verificarRGComPontoEHifen() {
		Assertions.assertDoesNotThrow(() -> ValidatorDocumento.validarDocumento("12.345.678-9", TipoDocumento.CPF));
	}
	
	@Test
	public void verificarQuantidadeDeDigitosInsuficiente() {
		Assertions.assertThrows(DocumentException.class, () -> ValidatorDocumento.validarDocumento("12345678", TipoDocumento.CPF));
	}
	
	@Test
	public void verificarLetraDigitadaNoRG() {
		Assertions.assertThrows(DocumentException.class, () -> ValidatorDocumento.validarDocumento("12345678w", TipoDocumento.CPF));
	}
}