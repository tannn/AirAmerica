package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

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

	@Override
	public double calculatePrice() {
		return 0;
	}

	@Override
	public double calculateTax() {
		return 0;
	}
	
	public String getReason() {
		Scanner productFile = null;
		try {
			productFile = new Scanner(new FileReader("data/Products.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Products.dat not found.");
		}
		while (productFile.hasNextLine()) {
			String line = productFile.nextLine();
			String[] productData = line.split(";");
			if (getProductCode().equals(productData[0]) && "SS".equals(productData[1])) {
				return productData[2];
			}
		}
		return null;
	}
    
}
