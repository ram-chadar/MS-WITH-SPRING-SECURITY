package com.jbk.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbk.product.entity.Product;
import com.jbk.product.model.Product_Supplier;
import com.jbk.product.service.ProductService;

/**
 * @author RAM
 *
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	private ProductService service;

	@PostMapping(value = "/saveProduct")
	public ResponseEntity<Boolean> saveProduct(@RequestBody Product product) {
		boolean isAdded = service.saveProduct(product);
		if (isAdded) {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/getAllProduct")
	
	public ResponseEntity<List<Product>> getAllProduct() {
System.out.println(1111);
		List<Product> list = service.getAllProduct();
		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);

		}
	}

	@GetMapping(value = "/getProductById/{productId}") // using path Variable {productId}
	public ResponseEntity<Product> getProductById(@PathVariable int productId) {

		Product product = service.getProductById(productId);
		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		}
	}
	
	@GetMapping(value = "/getProductByName/{productName}") // using path Variable {productId}
	public ResponseEntity<List<Product>> getProductByName(@PathVariable String productName) {

		List<Product> products = service.getProductByName(productName);
		if (!products.isEmpty()) {
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		}
	}

	@DeleteMapping(value = "/deleteProductById")
	public ResponseEntity<Boolean> deleteProductById(@RequestParam int productId) {

		boolean isDeleted = service.deleteProductById(productId);
		
		if(isDeleted) {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.NO_CONTENT);

		}
	}
	
	@PutMapping(value = "/updateProduct")
	public ResponseEntity<Boolean> updateProduct(@RequestBody Product product){
		boolean isUpdated = service.updateProduct(product);
		if (isUpdated) {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(value = "/getMaxPriceProduct") // using path Variable {productId}
	public ResponseEntity<List<Product>> getMaxPriceProduct() {

		List<Product> products = service.getMaxPriceProduct();
		if (!products.isEmpty()) {
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		}
	}
	
	@GetMapping(value = "/getProductBetweenPrice/{low}/{high}")
	public List<Product> getProductBetweenPrice(@PathVariable int low,@PathVariable int high){
		return null;
		
	}
	
	
	@PostMapping(value = "/importExcel")
	public String importExcel(){
	int size=	service.importProduct();
		return size +" products added";
		
	}
	
	@GetMapping(value = "/getProductWithSupplierByProductId/{productId}")
	public ResponseEntity<Product_Supplier> getProductWithSupplierByProductId(@PathVariable int productId){
	Product_Supplier product_Supplier=	service.getProductWithSupplierByProductId(productId);
	
	
		return new ResponseEntity<Product_Supplier> (product_Supplier,HttpStatus.OK);
		
	}

}
