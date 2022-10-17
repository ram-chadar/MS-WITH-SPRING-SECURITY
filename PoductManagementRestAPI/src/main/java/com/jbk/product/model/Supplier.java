package com.jbk.product.model;


public class Supplier {
	
	private int supplierId;
	private String supplierName;
	private String phone;
	private String address;
	
	public Supplier() {
		// TODO Auto-generated constructor stub
	}

	public Supplier(int supplierId, String supplierName, String phone, String address) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.phone = phone;
		this.address = address;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierName=" + supplierName + ", phone=" + phone
				+ ", address=" + address + "]";
	}
	
	

}
