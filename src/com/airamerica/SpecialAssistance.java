package com.airamerica;

public class SpecialAssistance extends Services {

	private String typeOfService;

	public SpecialAssistance(String productCode, String productType, String typeOfService) {
		super(productCode, productType);
		this.typeOfService = typeOfService;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

}