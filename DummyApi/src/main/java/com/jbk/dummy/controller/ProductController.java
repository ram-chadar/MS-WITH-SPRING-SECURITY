package com.jbk.dummy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jbk.dummy.entity.Product;
import com.jbk.dummy.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService service;

	@PostMapping(value = "/saveProduct")
	public boolean saveProduct(@RequestBody Product product) {
		boolean isSaved = service.saveProduct(product);
		return isSaved;

	}
	
	@GetMapping(value = "/getAllProducts")
	public List<Product> getAllProducts(){
	List<Product> list=	service.getAllProducts();
	return list;
	}

}
