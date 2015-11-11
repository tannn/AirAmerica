package com.airamerica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.airamerica.interfaces.InvoiceData;
import com.airamerica.utils.DatabaseInfo;

public class Customer {

	public static Logger log = Logger.getLogger(InvoiceData.class);

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
	 * @param code
	 *            Code of customer
	 * @return Customer's name (Last, First)
	 */
	public static String getCustomerName(String code) {
		String name = "";
		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT ContactPerson_ID FROM Customer WHERE CustomerCode = ?");
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();

			int personCode = rs.getInt("ContactPerson_ID");

			ps = DatabaseInfo.getConnection().prepareStatement("SELECT * FROM Person WHERE Person_ID = ?");
			ps.setInt(1, personCode);
			rs = ps.executeQuery();

			name = rs.getString("LastName") + ", " + rs.getString("FirstName");

		} catch (SQLException e1) {
			log.error("Failed to retrieve customer name for personCode " + code, e1);
		}
		return name;
	}

	/**
	 * 
	 * @param code
	 *            Code of customer
	 * @return Corporate, general, or government type
	 */
	public static String getCustomerType(String code) {

		String type = "";

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT CustomerType FROM Customer WHERE CustomerCode = ?");
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();

			type = rs.getString("CustomerType");

		} catch (SQLException e1) {
			log.error("Failed to retrieve customer name for personCode " + code, e1);
		}

		if (type.equals("C"))
			return "[CORPORATE]";
		else if (type.equals("G"))
			return "[GENERAL]";
		else if (type.equals("V"))
			return "[GOVERNMENT]";

		return type;
	}

	/**
	 * 
	 * @param code
	 *            Code of customer
	 * @return Code of Person who represents Customer
	 */
	public static String getCustomerContactCode(String code) {
		String personCode = "";

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT CustomerCode FROM Customer WHERE CustomerCode = ?");
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();

			int personID = rs.getInt("ContactPerson_ID");

			ps = DatabaseInfo.getConnection().prepareStatement("SELECT PersonCode FROM Person WHERE Person_ID = ?");
			ps.setInt(1, personID);
			rs = ps.executeQuery();

			personCode = rs.getString("PersonCode");

		} catch (SQLException e1) {
			log.error("Failed to retrieve customer name for personCode " + code, e1);
		}
		
		return personCode;
	}

}
