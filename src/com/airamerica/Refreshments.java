package com.airamerica;

public class Refreshments extends Services {
	
	private String name;
	private String cost;

	public Refreshments(String productCode, String productType, String name, String cost) {
		super(productCode, productType);
		this.name = name;
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public String getCost() {
		return cost;
	}

}
