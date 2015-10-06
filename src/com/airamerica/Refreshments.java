package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author EPiquette
 */
public class Refreshments extends Services {
    
    
	private String name;
	private String cost;
        private int quantity;

	public Refreshments(String productCode, String name, String cost, int quantity) {
		super(productCode, "SR");
		this.name = name;
		this.cost = cost;
                this.quantity = quantity;
	}

    public Refreshments(String productCode, int quantity) {
        super(productCode, "SR");
        Scanner productFile = null;
        try {
		productFile = new Scanner(new FileReader("data/products.dat"));
            } catch (FileNotFoundException e) {
		System.out.println("File not found.");
            }
        while(productFile.hasNextLine()) {  
                String line = productFile.nextLine();
                String[] productData = line.split(";");
                if(productCode.equals(productData[0])&&"SR".equals(productData[1])){
                    this.name = productData[2];
                    this.cost = productData[3];
                }
        }
    }

	public String getName() {
		return name;
	}

	public String getCost() {
		return cost;
	}
    
}
