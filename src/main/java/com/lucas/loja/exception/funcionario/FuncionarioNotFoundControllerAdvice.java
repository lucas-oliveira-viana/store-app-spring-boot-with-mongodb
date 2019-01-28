package com.lucas.loja.exception.funcionario;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucas.loja.exception.ErrorBody;

@ControllerAdvice
public class FuncionarioNotFoundControllerAdvice {

	@ExceptionHandler(FuncionarioNotFoundException.class)
	ResponseEntity<ErrorBody> handleControllerException(HttpServletRequest request, FuncionarioNotFoundException e) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorBody err = new ErrorBody(System.currentTimeMillis(), status.value(), "Funcionario não encontrado",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}