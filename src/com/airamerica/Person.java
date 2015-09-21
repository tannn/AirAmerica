package com.airamerica;
/*
/* A partial implementation representing a 
 * Person */
import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private String personCode;

    /* Note how Address has been used (Composition Relationship) */
    private Address address;
    /* Note how email is used (a collection of variable size) */
    private List<String> emails;
    /*TODO: Add other fields as necessary (eg. firstName, lastName,
     phoneNo etc) */
    private String firstName;
    private String lastName;
    private List<String> phoneNumbers;

    // TODO: Add appropriate constructor(s)
    public Person(String personCode, Address address, String firstName, String lastName) {
        this.personCode = personCode;
        this.address = address;
        this.emails = new ArrayList<String>();
        this.firstName = firstName.replaceAll("\\s","");
        this.lastName = lastName;
        this.phoneNumbers = new ArrayList<String>();
    }
    
    public Person(String personCode, Address address) {
        this.personCode = personCode;
        this.address = address;
        this.emails = new ArrayList<String>(); 
    }

    // TODO: Add Getters and setters as appropriate
    public Address getAddress() {
        return this.address;
    }

    public String getPersonCode() {
        return personCode;
    }
    
    public void setPhoneNumber(String phoneNumber) {
    	this.phoneNumbers.add(phoneNumber);
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    // TODO: Add additional methods here
    public void addEmail(String email) {
        this.emails.add(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
	
}
