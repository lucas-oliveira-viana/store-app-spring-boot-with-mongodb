package com.lucas.loja.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.lucas.loja.domain.produto.ProdutoEmEstoque;
import com.lucas.loja.service.CompraService;


public class LojaTest {

	@Test
	public void testSalvarNoEstoque() {
		CompraService service = new CompraService();
		
		List<ProdutoEmEstoque> produtosEmEstoque = new ArrayList<>();
		produtosEmEstoque.add(new ProdutoEmEstoque("123as", "Arroz", 13.00, "1452758", 10));
		
		Assert.assertEquals("a", service.saveProdutoNoEstoque(produtosEmEstoque));
	}
	
//	@Test
//	public void testRetirarDoEstoque() {
//		CompraService service = new CompraService();
//		ProdutoComprado produtoComprado = new ProdutoComprado("a", 16.00, 123, 2);
//		List<ProdutoEmEstoque> produtosEmEstoque = new ArrayList<>();
//		produtosEmEstoque.add(new ProdutoEmEstoque("20", "aa", 16.00, 123, 6));
//		
//		Assert.assertEquals("aaaa", service.buscaProdutoNoEstoque(produtoComprado, produtosEmEstoque));
//	}
}
