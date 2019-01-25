package com.lucas.loja.exception.compra;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucas.loja.exception.ErrorBody;

@ControllerAdvice
public class CompraNotFoundControllerAdvice {

	@ExceptionHandler(CompraNotFoundException.class)
	ResponseEntity<ErrorBody> handleControllerException(HttpServletRequest request, CompraNotFoundException e) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorBody err = new ErrorBody(System.currentTimeMillis(), status.value(), "Compra não encontrada",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
