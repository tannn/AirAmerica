package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

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

	/**
	 * @param code
	 * @return
	 */
	public static String getProductType(String code) {
		System.out.println("Checking for: " + code);
		Scanner productFile = null;
		try {
			productFile = new Scanner(new FileReader("data/Products.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("data/Products.dat not found.");
		}
		while (productFile.hasNextLine()) {
			String line = productFile.nextLine();
			String[] productData = line.split(";");
			if (productData[0].equals(code))
				return productData[1]; //for some reason, this results in a NullPointerException
		}
		return null;
	}

}
