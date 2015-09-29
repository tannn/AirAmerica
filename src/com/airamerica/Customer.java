package com.airamerica;

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
    
    public void setAirlineMiles(int airlineMiles){
    	this.airlineMiles = airlineMiles;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }
}
