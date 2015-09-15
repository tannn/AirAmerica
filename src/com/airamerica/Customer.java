package com.airamerica;

public class Customer {
	
	
    private String customerName;
    private String customerCode;
    private Address customerAddress;
    private Person primaryContact;
   

    /*TODO: Add other fields as necessary (eg. firstName, lastName,
     phoneNo etc) */
    // TODO: Add constructor(s)
    public Customer(Person cust) {
        customerName = cust.getFirstName() + ' ' + cust.getLastName();
        customerCode = cust.getPersonCode();
        customerAddress = cust.getAddress();
        primaryContact = cust;
    }

    public Customer(String name, String code, Address address, Person contact) {
        customerName = name;
        customerCode = code;
        customerAddress = address;
        primaryContact = contact;
    }

    /*TODO: Add Getters and setters */
    public Person getPrimaryContact() {
        return primaryContact;
    }

    //TODO: Add additional methods if needed */
    public String getCustomerCode() {
        return customerCode;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }
}
