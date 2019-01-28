package com.lucas.loja.exception.cliente;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucas.loja.exception.ErrorBody;

@ControllerAdvice
public class ClienteAlreadyExistsControllerAdvice {

	@ExceptionHandler(ClienteAlreadyExistsException.class)
	ResponseEntity<ErrorBody> handleControllerException(HttpServletRequest request, ClienteAlreadyExistsException e) {
		HttpStatus status = HttpStatus.ALREADY_REPORTED;
		ErrorBody err = new ErrorBody(System.currentTimeMillis(), status.value(), "Já existe um cliente com esse e-mail",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
