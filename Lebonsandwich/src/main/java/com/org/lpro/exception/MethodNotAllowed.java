package com.org.lpro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowed extends RuntimeException{	
	private static final long serialVersionUID = 1L;
	
	public MethodNotAllowed(String message) {
		super(message);
	}
	
	public MethodNotAllowed(String message, Throwable cause) {
		super(message,cause);
	}
}