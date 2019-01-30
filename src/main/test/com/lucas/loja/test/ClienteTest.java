package com.lucas.loja.test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucas.loja.domain.Cliente;
import com.lucas.loja.domain.Endereco;
import com.lucas.loja.repository.ClienteRepository;
import com.lucas.loja.service.ClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClienteTest {

	@Autowired
	private ClienteService clienteService;
	
	@MockBean
	private ClienteRepository clienteRepository;

	@Test
	public void testServiceNulo() {
		Assert.assertNotNull(clienteService);
	}
	
	private Calendar instanciaData() {
		Calendar data = Calendar.getInstance();
		data.set(2000, 11, 15);
		return data;
	}
	
	Cliente cliente = new Cliente("123", "João da Silva", instanciaData(), "12345678910", "987654321", "joao@gmail.com", "3210-4321",
			new Endereco("87654321", "Brasil", "São Paulo", "São Paulo", "Laranjeiras", "São Vicente", "472"));
	
	@Test
	public void verificaSave() {
		clienteService.saveCliente(cliente);
		Assert.assertEquals("a", clienteService.findClienteById("134"));
	}
}
