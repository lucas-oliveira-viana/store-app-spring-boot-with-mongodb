package com.lucas.loja.service.validator;

import java.util.Calendar;

import com.lucas.loja.exception.cliente.InsufficientAgeException;

public class ValidatorCalendar {

	public static Calendar verificaSeTemIdadeMinima(Calendar dataNascimento) {

		long hojeEmMilisegundos = Calendar.getInstance().getTimeInMillis();
		long dataNascimentoEmilisegundos = dataNascimento.getTimeInMillis();
		long dezesseisAnosEmMilisegundos = calcularDezesseisAnosEmMS();

		if (hojeEmMilisegundos - dataNascimentoEmilisegundos < dezesseisAnosEmMilisegundos) {
			throw new InsufficientAgeException("A idade minima e de 16 anos");
		}
		return dataNascimento;
	}

	private static long calcularDezesseisAnosEmMS() {

		Calendar data1 = Calendar.getInstance();
		Calendar data2 = Calendar.getInstance();

		data1.set(2000, Calendar.JANUARY, 20);
		data2.set(2016, Calendar.JANUARY, 20);

		return data1.getTimeInMillis() - data2.getTimeInMillis();
	}
}
