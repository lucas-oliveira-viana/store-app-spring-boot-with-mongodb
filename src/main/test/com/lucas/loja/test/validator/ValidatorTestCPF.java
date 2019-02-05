package com.lucas.loja.test.validator;

import static com.lucas.loja.service.validator.ValidatorDocumento.validarDocumento;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.lucas.loja.exception.DocumentException;
import com.lucas.loja.service.validator.TipoDocumento;


public class ValidatorTestCPF {
	
	private static final String CPF_PADRAO = "894.147.241-23";

	@Test
	public void verificarFormatacaoCPF() {
		Assert.assertEquals(CPF_PADRAO, validarDocumento("89414724123", TipoDocumento.CPF));
	}
	
	@Test
	public void verificarCPFComPontoEHifen() {
		Assertions.assertDoesNotThrow(() -> validarDocumento("894.147.241-23", TipoDocumento.CPF));
	}
	
	@Test
	public void verificarQuantidadeDeDigitosInsuficiente() {
		Assertions.assertThrows(DocumentException.class, () -> validarDocumento("8941472412", TipoDocumento.CPF));
	}
	
	@Test
	public void verificarLetraDigitadaNoCPF() {
		Assertions.assertThrows(DocumentException.class, () -> validarDocumento("89414724123w", TipoDocumento.CPF));
	}
}