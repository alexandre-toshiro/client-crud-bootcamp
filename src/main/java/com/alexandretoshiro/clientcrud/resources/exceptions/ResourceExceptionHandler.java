package com.alexandretoshiro.clientcrud.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alexandretoshiro.clientcrud.services.exceptions.ClientNotFoundException;
import com.alexandretoshiro.clientcrud.services.exceptions.DatabaseException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ClientNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ClientNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;

		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);

	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;

		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Database Excepetion");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);

	}
}
