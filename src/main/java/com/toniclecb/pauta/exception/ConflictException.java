package com.toniclecb.pauta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
	
	public ConflictException(String exception) {
		super(exception);
	}
	
}