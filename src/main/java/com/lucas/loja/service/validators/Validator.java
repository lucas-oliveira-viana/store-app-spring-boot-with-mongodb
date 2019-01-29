package com.lucas.loja.service.validators;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.loja.exception.DocumentException;
import com.lucas.loja.exception.cliente.ClienteAlreadyExistsException;
import com.lucas.loja.exception.cliente.InsufficientAgeException;
import com.lucas.loja.exception.funcionario.FuncionarioAlreadyExistsException;
import com.lucas.loja.repository.ClienteRepository;
import com.lucas.loja.repository.FuncionarioRepository;

@Service
public class Validator {

	public static final String REGEX_LETRAS = ".*[a-zA-Z]+.*";
	public static final String FUNCIONARIO = "Funcionario";
	public static final String CLIENTE = "Cliente";

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public String validarRG(String rg) {
		if (naoContemHifen(rg)) {
			rg = inserirPontuacoesRG(rg);
		}
		if (quantidadeDeDigitosDiferenteDe(12, rg)) {
			throw new DocumentException("O RG deve conter 9 digitos!");
		}
		if (contemLetra(rg)) {
			throw new DocumentException("O RG não deve conter letras!");
		}
		return rg;
	}

	public String validarCPF(String cpf) {
		if (naoContemPonto(cpf) && naoContemHifen(cpf)) {
			cpf = inserirPontuacoesCPF(cpf);
		}
		if (quantidadeDeDigitosDiferenteDe(14, cpf)) {
			throw new DocumentException("O CPF deve conter 11 digitos!");
		}
		if (contemLetra(cpf)) {
			throw new DocumentException("O CPF não deve conter letras!");
		}
		return cpf;
	}

	public String validarCEP(String cep) {
		if (naoContemHifen(cep)) {
			cep = inserirPontuacoesCEP(cep);
		}
		if (quantidadeDeDigitosDiferenteDe(9, cep)) {
			throw new DocumentException("O CEP deve conter 8 digitos!");
		}
		if (contemLetra(cep)) {
			throw new DocumentException("O CPF não deve conter letras!");
		}
		return cep;
	}

	private String inserirPontuacoesCPF(String cpf) {
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
	}

	private String inserirPontuacoesRG(String rg) {
		return rg.substring(0, 2) + "." + rg.substring(2, 5) + "." + rg.substring(5, 8) + "-" + rg.substring(8);
	}

	private static boolean quantidadeDeDigitosDiferenteDe(Integer valor, String documento) {
		return documento.length() != valor;
	}

	private String inserirPontuacoesCEP(String cep) {
		return cep.substring(0, 5) + "-" + cep.substring(5);
	}

	private boolean naoContemPonto(String cpf) {
		return !cpf.contains(".");
	}

	private boolean naoContemHifen(String cpf) {
		return !cpf.contains("-");
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
			if (funcionarioRepository.findByRg(rg) != null) {
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
