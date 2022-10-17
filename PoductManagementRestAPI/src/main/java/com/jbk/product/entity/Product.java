package com.jbk.product.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
	
	@Id
	private int productId;
	private String productName;
	private double productPrice;
	private int qtyPerUnit;
	private String productType;
	private int supplierId;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Product(int productId, String productName, double productPrice, int qtyPerUnit, String productType,
			int supplierId) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.qtyPerUnit = qtyPerUnit;
		this.productType = productType;
		this.supplierId = supplierId;
	}



	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getQtyPerUnit() {
		return qtyPerUnit;
	}

	public void setQtyPerUnit(int qtyPerUnit) {
		this.qtyPerUnit = qtyPerUnit;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}



	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", qtyPerUnit=" + qtyPerUnit + ", productType=" + productType + ", supplierId=" + supplierId + "]";
	}
	
	
	
}
