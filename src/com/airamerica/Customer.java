package com.airamerica;

public class Customer {
	
	
    private String customerName;
    private String customerCode;
    private Address customerAddress;
    private String primaryContact;
    private String airlineMiles;
    private String customerType;
   

    /*TODO: Add other fields as necessary (eg. firstName, lastName,
     phoneNo etc) */
    // TODO: Add constructor(s)
    
//    public Customer(Person cust) {
//        customerName = cust.getFirstName() + ' ' + cust.getLastName();
//        customerCode = cust.getPersonCode();
//        customerAddress = cust.getAddress();
//        primaryContact = cust;
//    }

    public Customer(String code, String type, String contact, String name) {
        this.customerName = name;
        this.customerCode = code;
        this.customerType = type;
        this.primaryContact = contact;
    }

    /*TODO: Add Getters and setters */
    public String getPrimaryContact() {
        return primaryContact;
    }

    //TODO: Add additional methods if needed */
    public String getCustomerCode() {
        return customerCode;
    }
    
    public void setAirlineMiles(String airlineMiles){
    	this.airlineMiles = airlineMiles;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }
}
