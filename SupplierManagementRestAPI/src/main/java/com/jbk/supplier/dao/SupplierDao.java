package com.jbk.supplier.dao;

import com.jbk.supplier.entity.Supplier;

public interface SupplierDao {
	
	public boolean saveSupplier(Supplier supplier);
	public Supplier getSupplier(int supplierId);

}
