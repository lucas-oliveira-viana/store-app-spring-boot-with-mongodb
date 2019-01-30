package com.lucas.loja.service.validator;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.controller.utils.Decoder;
import com.lucas.loja.exception.DocumentException;
import com.lucas.loja.exception.cliente.ClienteAlreadyExistsException;
import com.lucas.loja.exception.cliente.InsufficientAgeException;
import com.lucas.loja.exception.funcionario.FuncionarioAlreadyExistsException;
import com.lucas.loja.repository.ClienteRepository;
import com.lucas.loja.repository.FuncionarioRepository;

@Service
public class Validator {

	private static final int QUANTIDADE_DIGITOS_PADRAO_CEP = 8;
	private static final int QUANTIDADE_DIGITOS_PADRAO_RG = 9;
	private static final int QUANTIDADE_DIGITOS_PADRAO_CPF = 11;
	public static final String REGEX_LETRAS = ".*[a-zA-Z]+.*";
	public static final String FUNCIONARIO = "Funcionario";
	public static final String CLIENTE = "Cliente";

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public static String validarCPF(String cpf) {
		if (contemPonto(cpf) || contemHifen(cpf)) {
			cpf = Decoder.removerPontuacoes(cpf);
		}
		if (quantidadeDigitosDiferente(QUANTIDADE_DIGITOS_PADRAO_CPF, cpf)) {
			throw new DocumentException("O CPF deve conter 11 numeros!");
		}
		if (contemLetra(cpf)) {
			throw new DocumentException("O CPF não deve conter letras!");
		}
		return cpf = Decoder.inserirPontuacoesCPF(cpf);
	}
	
	public static String validarRG(String rg) {
		if (contemHifen(rg)) {
			rg = Decoder.removerPontuacoes(rg);
		}
		if (quantidadeDigitosDiferente(QUANTIDADE_DIGITOS_PADRAO_RG, rg)) {
			throw new DocumentException("O CPF deve conter 9 numeros!");
		}
		if (contemLetra(rg)) {
			throw new DocumentException("O RG não deve conter letras!");
		}
		return Decoder.inserirPontuacoesRG(rg);
	}


	public static String validarCEP(String cep) {
		if (contemHifen(cep)) {
			cep = Decoder.removerPontuacoes(cep);
		}
		if (quantidadeDigitosDiferente(QUANTIDADE_DIGITOS_PADRAO_CEP, cep)) {
			throw new DocumentException("O CEP deve conter 8 numeros!");
		}
		if (contemLetra(cep)) {
			throw new DocumentException("O CEP não deve conter letras!");
		}
		return Decoder.inserirPontuacoesCEP(cep);
	}
	
	private static boolean quantidadeDigitosDiferente(Integer quantidadeDigitosPadraoCpf, String cpf) {
		return cpf.length() != quantidadeDigitosPadraoCpf;
	}

	private static boolean contemPonto(String cpf) {
		return cpf.contains(".");
	}

	private static boolean contemHifen(String cpf) {
		return cpf.contains("-");
	}

	private static boolean contemLetra(String rg) {
		return rg.matches(REGEX_LETRAS);
	}

	public void verificaSeCPFJaExiste(String cpf, String tipoDeUsuario) {

		if (tipoDeUsuario.equals(FUNCIONARIO)) {
			if (funcionarioRepository.findByCpf(cpf) != null) {
				throw new DocumentException("Já existe um funcionario com esse cpf");
			}
		}

		if (tipoDeUsuario.equals(CLIENTE)) {
			if (clienteRepository.findByCpf(cpf) != null) {
				throw new DocumentException("Já existe um cliente com esse cpf");
			}
		}
	}

	public void verificaSeRGJaExiste(String rg, String tipoDeUsuario) {
		
		if (tipoDeUsuario.equals(FUNCIONARIO)) {
			if (funcionarioRepository.findByRg(rg).size() == 1) {
				throw new DocumentException("Já existe um funcionario com esse rg");
			}
		}

		if (tipoDeUsuario.equals(CLIENTE)) {
			if (clienteRepository.findByRg(rg) != null) {
				throw new DocumentException("Já existe um cliente com esse rg");
			}
		}
	}

	public void verificaSeUsuarioJaExiste(String email, String tipoDeUsuario) {
		
		if (tipoDeUsuario.equals(FUNCIONARIO)) {
			if (funcionarioRepository.findByEmail(email) != null) {
				throw new FuncionarioAlreadyExistsException("Já existe um funcionário com esse e-mail");
			}
		}

		if (tipoDeUsuario.equals(CLIENTE)) {
			if (clienteRepository.findByEmail(email) != null) {
				throw new ClienteAlreadyExistsException("Já existe um cliente com esse e-mail");
			}
		}
	}

	public Calendar verificaSeTemIdadeMinima(Calendar dataNascimento) {

		long hojeEmMilisegundos = Calendar.getInstance().getTimeInMillis();
		long dataNascimentoEmilisegundos = dataNascimento.getTimeInMillis();
		long dezesseisAnosEmMilisegundos = calcularDezesseisAnosEmMS();

		if (hojeEmMilisegundos - dataNascimentoEmilisegundos < dezesseisAnosEmMilisegundos) {
			throw new InsufficientAgeException("A idade minima e de 16 anos");
		}
		return dataNascimento;
	}

	private long calcularDezesseisAnosEmMS() {
		
		Calendar data1 = Calendar.getInstance();
		Calendar data2 = Calendar.getInstance();
		
		data1.set(2000, Calendar.JANUARY, 20);
		data2.set(2016, Calendar.JANUARY, 20);
		
		return data1.getTimeInMillis() - data2.getTimeInMillis();
	}

}
