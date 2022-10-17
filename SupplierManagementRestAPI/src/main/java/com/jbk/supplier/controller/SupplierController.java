package com.jbk.supplier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbk.supplier.entity.Supplier;
import com.jbk.supplier.service.SupplierService;

@RestController
@RequestMapping(value = "/supplier")
public class SupplierController {

	@Autowired
	private SupplierService service;

	@PostMapping(value = "/saveSupplier")
	public ResponseEntity<Boolean> saveSupplier(@RequestBody Supplier supplier) {
		boolean isAdded = service.saveSupplier(supplier);
		if (isAdded) {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.OK);
		}

	}

	@GetMapping(value = "/getSupplierById/{supplierId}")
	public ResponseEntity<Supplier> getSupplierById(@PathVariable int supplierId) {

		Supplier supplier = service.getSupplier(supplierId);
		if (supplier != null) {
			return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
		} else {
			return new ResponseEntity<Supplier>(HttpStatus.NO_CONTENT);

		}

	}
	
	

}
