package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Customer {

	private String customerName;
	private String customerCode;
	private Address customerAddress;
	private String primaryContact;
	private int airlineMiles;
	private String customerType;

	public Customer(String code, String type, String contact, String name) {
		this.customerName = name;
		this.customerCode = code;
		this.customerType = type;
		this.primaryContact = contact;
	}

	public int getAirlineMiles() {
		return airlineMiles;
	}

	public String getCustomerType() {
		return customerType;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setAirlineMiles(int airlineMiles) {
		this.airlineMiles = airlineMiles;
	}

	public Address getCustomerAddress() {
		return customerAddress;
	}

	public String getCustomerName() {
		return customerName;
	}

	/**
	 * 
	 * @param code Code of customer
	 * @return	Customer's name (Last, First)
	 */
	public static String getCustomerName(String code) {
		Scanner customerFile = null;
		try {
			customerFile = new Scanner(new FileReader("data/Customers.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Customers.dat not found.");
		}
		while (customerFile.hasNextLine()) {
			String line = customerFile.nextLine();
			String[] customerData = line.split(";");
			if (customerData[0].equals(code)) {
				return customerData[3];
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param code Code of customer
	 * @return	Corporate, general, or government type
	 */
	public static String getCustomerType(String code) {
		Scanner customerFile = null;
		try {
			customerFile = new Scanner(new FileReader("data/Customers.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Customers.dat not found.");
		}
		while (customerFile.hasNextLine()) {
			String line = customerFile.nextLine();
			String[] customerData = line.split(";");
			if (customerData[0].equals(code)) {
				if (customerData[1].equals("C"))
					return "[CORPORATE]";
				else if (customerData[1].equals("G"))
					return "[GENERAL]";
				else if (customerData[1].equals("V"))
					return "[GOVERNMENT]";
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param code Code of customer
	 * @return	Code of Person who represents Customer
	 */
	public static String getCustomerContactCode(String code) {
		Scanner customerFile = null;
		try {
			customerFile = new Scanner(new FileReader("data/Customers.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Customers.dat not found.");
		}
		while (customerFile.hasNextLine()) {
			String line = customerFile.nextLine();
			String[] customerData = line.split(";");
			if (customerData[0].equals(code)) {
				return customerData[2];
			}
		}
		return null;
	}
	
}
