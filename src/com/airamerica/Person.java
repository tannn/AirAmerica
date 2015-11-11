package com.airamerica;

/*
/* A partial implementation representing a 
 * Person */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.airamerica.interfaces.InvoiceData;
import com.airamerica.utils.DatabaseInfo;

public class Person {

	public static Logger log = Logger.getLogger(InvoiceData.class);

	private String personCode;
	private Address address;
	private ArrayList<String> emails;
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
		this.personCode = personCode;
		this.identityNumber = identityNumber;
		this.age = age;
		this.nationality = nationality;

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT LastName, FirstName, PhoneNumber, Address_ID, Person_ID FROM Person WHERE PersonCode = ?");
			ps.setString(1, personCode);
			ResultSet rs = ps.executeQuery();

			this.lastName = rs.getString("LastName");
			this.firstName = rs.getString("FirstName");
			this.phoneNumber = rs.getString("PhoneNumber");

			int addressID = rs.getInt("Address_ID");
			int personID = rs.getInt("Person_ID");

			if (rs.getString("Address_ID") != null) {
				ps = DatabaseInfo.getConnection().prepareStatement("SELECT * FROM Address WHERE Address_ID = ?");
				ps.setInt(1, addressID);
				ResultSet addressInfo = ps.executeQuery();

				this.address = new Address(addressInfo.getString("Address"), addressInfo.getString("City"),
						addressInfo.getString("StateProvince"), addressInfo.getString("ZIP"),
						addressInfo.getString("Country"));
			}

			ps = DatabaseInfo.getConnection().prepareStatement("SELECT Email FROM Email WHERE Person_ID = ?");
			ps.setInt(1, personID);
			rs = ps.executeQuery();

			while (rs.next()) {
				this.emails.add(rs.getString("Email"));
			}

			ps.close();

		} catch (SQLException e1) {
			log.error("Failed to retrieve data for person with code " + personCode, e1);
		}

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

	public void setEmails(ArrayList<String> emails) {
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
	 * @param code
	 *            Code of person
	 * @return Person's name (Last, First)
	 */
	public static String getPersonName(String code) {
		String data = "";

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT LastName, FirstName FROM Person WHERE PersonCode = ?");
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();

			data = rs.getString("LastName") + ", " + rs.getString("FirstName");

			ps.close();

		} catch (SQLException e1) {
			log.error("Failed to retrieve person name under code " + code, e1);
		}

		return data;
	}

	/**
	 * 
	 * @param code
	 *            Code of person
	 * @return Name (Last, First), Address, and other location identifiers
	 */
	public static String getContactInfo(String personCode) {
		String data = "";

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT Address_ID, LastName, FirstName FROM Person WHERE PersonCode = ?");
			ps.setString(1, personCode);
			ResultSet rs = ps.executeQuery();

			int addressID = rs.getInt("Address_ID");

			ps = DatabaseInfo.getConnection().prepareStatement("SELECT * FROM Address WHERE Address_ID = ?");
			ps.setInt(1, addressID);
			ResultSet addressInfo = ps.executeQuery();

			data = rs.getString("LastName") + ", " + rs.getString("FirstName") + "\n\t"
					+ addressInfo.getString("Address") + "\n\t" + addressInfo.getString("City") + " "
					+ addressInfo.getString("StateProvince") + " " + addressInfo.getString("ZIP") + " "
					+ addressInfo.getString("Country");

			ps.close();

		} catch (SQLException e1) {
			log.error("Failed to retrieve seat number under code " + personCode, e1);
		}

		return data;
	}

	public String getSeat() {
		String seat = "";

		// What happens if the person has more than one seat? (Such as more than
		// one ticket?)

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT SeatNumber FROM Passenger WHERE Person_ID = ?");
			ps.setString(1, this.personCode);
			ResultSet rs = ps.executeQuery();
			ps.close();
			seat = rs.getString("SeatNumber");

		} catch (SQLException e1) {
			log.error("Failed to retrieve seat number under code " + this.personCode, e1);
		}

		return seat;
	}

}
