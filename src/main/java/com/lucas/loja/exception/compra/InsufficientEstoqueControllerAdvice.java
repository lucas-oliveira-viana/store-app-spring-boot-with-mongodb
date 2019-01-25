package com.lucas.loja.exception.compra;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucas.loja.exception.ErrorBody;

@ControllerAdvice
public class InsufficientEstoqueControllerAdvice {

		@ExceptionHandler(InsufficientEstoqueException.class)
		ResponseEntity<ErrorBody> handleControllerException(HttpServletRequest request, InsufficientEstoqueException e) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			ErrorBody err = new ErrorBody(System.currentTimeMillis(), status.value(), "Estoque insuficiente",
					e.getMessage(), request.getRequestURI());
			return ResponseEntity.status(status).body(err);
		}
}
