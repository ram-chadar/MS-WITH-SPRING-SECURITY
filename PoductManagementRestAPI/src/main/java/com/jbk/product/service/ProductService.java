package com.jbk.product.service;

import java.util.List;

import com.jbk.product.entity.Product;
import com.jbk.product.model.Product_Supplier;

public interface ProductService {
	
	public boolean saveProduct(Product  product);
	public boolean updateProduct(Product product);
	public Product getProductById(int productId);
	public boolean deleteProductById(int productId);
	public List<Product> getAllProduct();
	public List<Product> getProductByName(String productName);
	public List<Product> getProductsBetweenPrice(double lowPrice,double highPrice);
	public List<Product> getMaxPriceProduct();
	
	public int importProduct();   //excel sheet import
	//public int exportToWxcel();
	
	public Product_Supplier getProductWithSupplierByProductId(int productId);
	

}
