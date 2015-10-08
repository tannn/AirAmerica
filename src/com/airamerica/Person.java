package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

	public Person(String personCode, Address address, String firstName, String lastName, String phoneNumber, int age,
			String nationality, String identityNumber) {
		this.personCode = personCode;
		this.address = address;
		this.emails = new ArrayList<String>();
		this.firstName = firstName.replaceAll("\\s", "");
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

	public Address getAddress() {
		return this.address;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

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
	public static String getContactInfo(String code) {
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
				return personData[1] + "\n\t" + personData[2] + "\n\t" + personData[3] + "," + personData[4] + "," + personData[5] + ","  + personData[6];
			}
		}
		return null;
	}

}