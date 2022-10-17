package com.jbk.supplier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbk.supplier.dao.SupplierDao;
import com.jbk.supplier.entity.Supplier;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierDao dao;
	
	@Override
	public boolean saveSupplier(Supplier supplier) {
		return dao.saveSupplier(supplier);
	}

	@Override
	public Supplier getSupplier(int supplierId) {
		return dao.getSupplier(supplierId);
	}

}
