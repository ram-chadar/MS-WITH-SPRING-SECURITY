package com.jbk.product.exception;

public class ProductNotFoundException extends RuntimeException{
	
	public ProductNotFoundException(String msg) {
		super(msg);
	}

}
