package com.airamerica;
/*
/* A partial implementation representing a 
 * Person */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Person {
	
    private String personCode;
    private Address address;
    private ArrayList<String> emails;
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
        this.personCode = personCode;
        this.identityNumber = identityNumber;
        this.age = age;
        this.nationality = nationality;
        Scanner personFile = null;
		try {
			personFile = new Scanner(new FileReader("data/Persons.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("data/Persons.dat not found.");
		}
		while (personFile.hasNextLine()) {
			String line = personFile.nextLine();
			String[] personData = line.split(";");
			if (personData[0].equals(personCode)) {
                            String[] name = personData[1].split(",");
                            this.lastName = name[0];
                            this.firstName = name[1];
                            String[] add = personData[2].split(",");
                            this.address = new Address(add[0], add[1], add[2], add[3], add[4]);
                            this.phoneNumber = personData[3];
                            if(personData.length>5)
                            this.emails.addAll(Arrays.asList(personData[4].split(",")));
                        }
		}
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

    public void setEmails(ArrayList<String> emails) {
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
    
    
    /**
	 * 
	 * @param code Code of person
	 * @return Person's name (Last, First)
	 */
	public static String getPersonName(String code) {
		Scanner personFile = null;
		try {
			personFile = new Scanner(new FileReader("data/Persons.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("data/Persons.dat not found.");
		}
		while (personFile.hasNextLine()) {
			String line = personFile.nextLine();
			String[] personData = line.split(";");
			if (personData[0].equals(code)) {
				return personData[1];
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param code Code of person
	 * @return	Name (Last, First), Address, and other location identifiers
	 */
	public static String getContactInfo(String personCode) {
		Scanner personFile = null;
		try {
			personFile = new Scanner(new FileReader("data/Persons.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("data/Persons.dat not found.");
		}
		while (personFile.hasNextLine()) {
			String line = personFile.nextLine();
			String[] personData = line.split(";");
			if (personData[0].equals(personCode)) {
				return personData[1] + "\n\t" + personData[2] + "\n\t" + personData[3] + "," + personData[4];
			}
		}
		return null;
	}
    
	
}
