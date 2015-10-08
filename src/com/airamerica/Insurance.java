/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author EPiquette
 */
public class Insurance extends Services {

	private String name;
	private String ageClass;
	private String costPerMile;
	private int quantity;
	private String ticketCode;

	public Insurance(String name, String ageClass, String costPerMile, int quantity, String ticketCode,
			String productCode) {
		super(productCode, "SI");
		this.name = name;
		this.ageClass = ageClass;
		this.costPerMile = costPerMile;
		this.quantity = quantity;
		this.ticketCode = ticketCode;
	}

	public Insurance(String productCode, int quantity, String ticketCode) {
		super(productCode, "SI");
		this.quantity = quantity;
		this.ticketCode = ticketCode;
		Scanner productFile = null;
		try {
			productFile = new Scanner(new FileReader("data/Products.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Products.dat not found.");
		}
		while (productFile.hasNextLine()) {
			String line = productFile.nextLine();
			String[] productData = line.split(";");
			if (productCode.equals(productData[0]) && "SI".equals(productData[1])) {
				this.name = productData[2];
				this.ageClass = productData[3];
				this.costPerMile = productData[4];
			}
		}
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
