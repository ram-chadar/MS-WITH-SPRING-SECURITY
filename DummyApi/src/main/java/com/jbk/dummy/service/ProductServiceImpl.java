package com.jbk.dummy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbk.dummy.dao.ProductDao;
import com.jbk.dummy.entity.Product;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao dao;

	@Override
	public boolean saveProduct(Product product) {
		boolean isSaved = dao.saveProduct(product);

		return isSaved;
	}

	@Override
	public boolean updateProduct(Product product) {
		boolean isUpdated = dao.updateProduct(product);
		return isUpdated;
	}

	@Override
	public Product getProduct(int id) {
		Product product = dao.getProduct(id);
		return product;
	}

	@Override
	public boolean deleteProduct(int id) {

		boolean isDeleted = dao.deleteProduct(id);
		return isDeleted;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> list = dao.getAllProducts();
		return list;
	}

	@Override
	public List<Product> getMaxPriceProducts() {

		List<Product> list=	dao.getMaxPriceProducts();
		return list;
	}

}
