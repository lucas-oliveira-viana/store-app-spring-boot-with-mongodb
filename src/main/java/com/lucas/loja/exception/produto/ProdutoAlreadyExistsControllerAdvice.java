package com.lucas.loja.exception.produto;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucas.loja.exception.ErrorBody;

@ControllerAdvice
public class ProdutoAlreadyExistsControllerAdvice {

	@ExceptionHandler(ProdutoAlreadyExistsInEstoqueException.class)
	ResponseEntity<ErrorBody> handleControllerException(HttpServletRequest request, ProdutoAlreadyExistsInEstoqueException e) {
		HttpStatus status = HttpStatus.ALREADY_REPORTED;
		ErrorBody err = new ErrorBody(System.currentTimeMillis(), status.value(), "Produto já existe no estoque",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
