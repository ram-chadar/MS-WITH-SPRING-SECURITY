package com.jbk.product.dao;

import java.util.List;

import com.jbk.product.entity.Product;

public interface ProductDao {

	public boolean saveProduct(Product  product);
	public boolean updateProduct(Product product);
	public Product getProductById(int productId);
	public boolean deleteProductById(int productId);
	public List<Product> getAllProduct();
	public List<Product> getProductByName(String productName);
	public List<Product> getProductsBetweenPrice(double lowPrice,double highPrice);
	public List<Product> getMaxPriceProduct();
	public int uploadSheet(List<Product> list);

}
