package com.airamerica;

/**
 *
 * @author EPiquette
 */
public class SpecialAssistance extends Services{
    
    private String typeOfService;

	public SpecialAssistance(String productCode, String typeOfService) {
		super(productCode, "SS");
		this.typeOfService = typeOfService;
	}

	public String getTypeOfService() {
		return typeOfService;
	}
    
}
