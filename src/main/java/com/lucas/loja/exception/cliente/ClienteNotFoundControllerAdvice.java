package com.lucas.loja.exception.cliente;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucas.loja.exception.ErrorBody;

@ControllerAdvice
public class ClienteNotFoundControllerAdvice {

	@ExceptionHandler(ClienteNotFoundException.class)
	ResponseEntity<ErrorBody> handleControllerException(HttpServletRequest request, ClienteNotFoundException e) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorBody err = new ErrorBody(System.currentTimeMillis(), status.value(), "Cliente não encontrado",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}