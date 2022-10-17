package com.jbk.dummy.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jbk.dummy.entity.Product;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	List<Product> productList=new ArrayList<>();

	@Override
	public boolean saveProduct(Product product) {

		productList.add(product);
		return true;
	}

	@Override
	public boolean updateProduct(Product product) {

		return false;
	}

	@Override
	public Product getProduct(int id) {
		
		return null;
	}

	@Override
	public boolean deleteProduct(int id) {
		
		return false;
	}

	@Override
	public List<Product> getAllProducts() {
		
		return productList;
	}

	@Override
	public List<Product> getMaxPriceProducts() {
		
		return null;
	}

}
