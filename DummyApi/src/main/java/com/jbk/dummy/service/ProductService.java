package com.jbk.dummy.service;

import java.util.List;

import com.jbk.dummy.entity.Product;

public interface ProductService {
	
	public boolean saveProduct(Product product);
	public boolean updateProduct(Product product);

	public Product getProduct(int id);	
	public boolean deleteProduct(int id);
	
	public List<Product> getAllProducts();
	public List<Product> getMaxPriceProducts();
	
	
}
