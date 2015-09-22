package com.airamerica;

public class Product {
	
	private String productCode;
	private String productType;
	
	public Product(String productCode, String productType) {
		this.productCode = productCode;
		this.productType = productType;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getProductType() {
		return productType;
	}
	
}
