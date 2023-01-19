package com.vishal.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vishal.blog.payloads.ResponseHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFoundExeption(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		return ResponseHandler.generateResponse(message, HttpStatus.NOT_FOUND, null);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> methodArgumentNotValidExeption(MethodArgumentNotValidException ex) {
		Map<String, String> map = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			map.put(fieldName, message);
		});
		return ResponseHandler.generateResponse("Invalid Data", HttpStatus.BAD_REQUEST, map);
	}
}
