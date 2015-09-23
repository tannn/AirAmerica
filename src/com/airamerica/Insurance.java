package com.airamerica;

public class Insurance extends Services {

	private String name;
	private String ageClass;
	private String costPerMile;

	public Insurance(String productCode, String productType, String name, String ageClass, String costPerMile) {
		super(productCode, productType);
		this.name = name;
		this.ageClass = ageClass;
		this.costPerMile = costPerMile;
	}

	public String getName() {
		return name;
	}

	public String getAgeClass() {
		return ageClass;
	}

	public String getCostPerMile() {
		return costPerMile;
	}

}