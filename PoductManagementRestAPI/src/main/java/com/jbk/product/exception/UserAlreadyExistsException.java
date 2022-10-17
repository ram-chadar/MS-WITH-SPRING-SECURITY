package com.jbk.product.exception;

public class UserAlreadyExistsException extends RuntimeException{
	
	public UserAlreadyExistsException(String msg) {
		super(msg);
	}

}
