package com.lucas.loja.test;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

	private static final String EXEMPLO_ID = "123";
	private static final String EXEMPLO_RG = "987654321";
	private static final String EXEMPLO_CPF = "12345678910";
	private static final String EXEMPLO_CEP = "87654321";

	@Autowired
	private ClienteService clienteService;

	@MockBean
	private ClienteRepository clienteRepository;

	public Calendar instanciaData() {
		Calendar data = Calendar.getInstance();
		data.set(2000, 11, 15);
		return data;
	}

	Cliente cliente = new Cliente(EXEMPLO_ID, "João da Silva", instanciaData(), EXEMPLO_CPF, EXEMPLO_RG, "joao@gmail.com", "3210-4321",
			new Endereco(EXEMPLO_CEP, "Brasil", "São Paulo", "São Paulo", "Laranjeiras", "São Vicente", "472"));
	
	Cliente cliente2 = new Cliente(EXEMPLO_ID, "João da Silva2", instanciaData(), EXEMPLO_CPF, EXEMPLO_RG, "joao@gmail.com2", "3210-4321",
			new Endereco(EXEMPLO_CEP, "Brasil", "São Paulo", "São Paulo", "Laranjeiras", "São Vicente", "472"));

	@Test
	public void testServiceNulo() {
		Assert.assertNotNull(clienteService);
	}
	
	@Test
	public void testFindAll() {
		
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(cliente);
		clientes.add(cliente2);
		
		Assert.assertNotNull(when(clienteService.findAllClientes()).thenReturn(clientes));
	}

	@Test
	public void verificaGetCPF() {
		Assert.assertNotNull(when(clienteService.findClienteByCpf(cliente.getCPF())).thenReturn(cliente));
	}
}