package com.krakedev.apijdbc.videojuegos.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorRespuesta> manejarRuntimeException(RuntimeException ex) {
		ErrorRespuesta error = new ErrorRespuesta(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
