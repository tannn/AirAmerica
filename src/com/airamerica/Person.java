package com.airamerica;
/*
/* A partial implementation representing a 
 * Person */
import java.util.ArrayList;
import java.util.List;

public class Person {
	
    private String personCode;
    private Address address;
    private List<String> emails;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private String nationality;
    private String identityNumber;

    // TODO: Add appropriate constructor(s)
    public Person(String personCode, Address address, String firstName, String lastName,
            String phoneNumber, int age, String nationality, String identityNumber) {
        this.personCode = personCode;
        this.address = address;
        this.emails = new ArrayList<String>();
        this.firstName = firstName.replaceAll("\\s","");
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.nationality = nationality;
        this.identityNumber = identityNumber;
    }

    public Person(String personCode, String identityNumber, int age, String nationality) {
        
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

    public int getAge() {
        return age;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    
    
    
	
}
