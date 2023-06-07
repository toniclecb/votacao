package com.toniclecb.pauta.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.toniclecb.pauta.exception.ConflictException;
import com.toniclecb.pauta.exception.ExceptionResponse;
import com.toniclecb.pauta.exception.ForbiddenException;
import com.toniclecb.pauta.exception.ResourceNotFoundException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

	/**
	 * Converte o erro de validacao padrao para um retorno mais legivel
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		log.error("Handler: Error MethodArgumentNotValidException, message: " + ex.getMessage());
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{

			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Converte o erro de recurso nao encontrado (not found) padrao para um retorno mais legivel
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(Exception ex, WebRequest request) {
		log.error("Handler: Error ResourceNotFoundException, message: " + ex.getMessage());
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConflictException.class)
	public final ResponseEntity<ExceptionResponse> handleConflictException(Exception ex, WebRequest request) {
		log.error("Handler: Error ConflictException, message: " + ex.getMessage());
		ExceptionResponse exceptionResponse =
				new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ForbiddenException.class)
	public final ResponseEntity<ExceptionResponse> handleForbiddenException(Exception ex, WebRequest request) {
		log.error("Handler: Error ForbiddenException, message: " + ex.getMessage());
		ExceptionResponse exceptionResponse =
				new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}
}
