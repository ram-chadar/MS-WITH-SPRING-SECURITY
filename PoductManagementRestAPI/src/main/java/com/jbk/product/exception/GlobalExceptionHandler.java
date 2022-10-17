package com.jbk.product.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public static HashMap<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		HashMap<String, String> map = new HashMap<>();
		//map.put("Time", new Date());
		/*
		 * ex.getBindingResult().getFieldErrors().forEach(error -> {
		 * map.put(error.getField(), error.getDefaultMessage()); });
		 */
		
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			map.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return map;
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> productNotFoundEx(ProductNotFoundException ex){
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
		
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> userNotFoundEx(UserNotFoundException ex){
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
		
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> userNotFoundEx(UserAlreadyExistsException ex){
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
		
	}
}
