package com.lucas.loja.test.validator;

import static com.lucas.loja.service.validator.ValidatorDocumento.validarDocumento;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.lucas.loja.exception.DocumentException;
import com.lucas.loja.service.validator.TipoDocumento;


public class ValidatorTestCEP {
	
	private static final String CEP_PADRAO = "12345-678";

	@Test
	public void verificarFormatacaoCPF() {
		Assert.assertEquals(CEP_PADRAO, validarDocumento("12345678", TipoDocumento.CPF));
	}
	
	@Test
	public void verificarCPFComPontoEHifen() {
		Assertions.assertDoesNotThrow(() -> validarDocumento("12345-678", TipoDocumento.CPF));
	}
	
	@Test
	public void verificarQuantidadeDeDigitosInsuficiente() {
		Assertions.assertThrows(DocumentException.class, () -> validarDocumento("12345-67", TipoDocumento.CPF));
	}
	
	@Test
	public void verificarLetraDigitadaNoCPF() {
		Assertions.assertThrows(DocumentException.class, () -> validarDocumento("89414724123w", TipoDocumento.CPF));
	}
}