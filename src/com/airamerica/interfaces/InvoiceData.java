package com.airamerica.interfaces;
/* Assignment 5 - (Phase IV) */
/* NOTE: Do not change the package name or any of the method signatures.
 *  
 * There are 23 methods in total, all of which need to be completed as a 
 * bare minimum as part of the assignment.You can add additional methods 
 * for testing if you feel.
 * 
 * It is also recommended that you write a separate program to read
 * from the .dat files and test these methods to insert data into your 
 * database.
 * 
 * Do not forget to change your reports generation classes to read from 
 * your database instead of the .dat files.
 */

import java.sql.*;
import com.airamerica.utils.DatabaseInfo;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {
	
	static int personID;
	static int addressID;

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() { //Not tested
		
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement("DELETE FROM Person");
			ps.executeQuery();
			
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	/**
	 * Method to add a person record to the database with the provided data. 
	 */
	public static void addPerson(String personCode, String firstName, String lastName, 
			String phoneNo, String street, String city, String state, 
			String zip, String country) { //Not tested
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement("INSERT INTO Person (PersonCode, FirstName, LastName, PhoneNumber) VALUES (?,?,?,?)");
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, phoneNo);
			ps.executeUpdate();
			ps = DatabaseInfo.getConnection().prepareStatement("SELECT LAST_INSERT_ID()");
			ResultSet rs = ps.executeQuery();
			rs.next();
			personID = rs.getInt("LAST_INSERT_ID()");
			
			ps = DatabaseInfo.getConnection().prepareStatement("INSERT INTO Address (Address, City, StateProvince, Zip, Country) VALUES (?,?,?,?,?)");
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			ps = DatabaseInfo.getConnection().prepareStatement("SELECT LAST_INSERT_ID()");
			rs = ps.executeQuery();
			rs.next();
			addressID = rs.getInt("LAST_INSERT_ID()");
			
			ps = DatabaseInfo.getConnection().prepareStatement("UPDATE Person SET Address_ID = ? WHERE Person_ID = ?");
			ps.setInt(1, addressID);
			ps.setInt(2,  personID);
			ps.executeUpdate();
			
			personID = 0;
			addressID = 0;
			
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Method that removes every airport record from the database
	 */
	public static void removeAllAirports() { //Not tested
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement("DELETE FROM Airport");
			ps.executeQuery();
			
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Method to add a airport record to the database with the provided data. 
	 */
	public static void addAirport(String airportCode, String name, String street, 
			String city, String state, String zip, String country, 
			int latdegs, int latmins, int londegs, int lonmins, 
			double passengerFacilityFee) { }
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 */
	public static void addEmail(String personCode, String email) { }
	
	/**
	 * Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() { }

	/**
	 * Method to add a customer record to the database with the provided data. 
	 */
	public static void addCustomer(String customerCode, String customerType, 
			String primaryContactPersonCode, String name, 
			int airlineMiles) {	}

	/**
	 * Removes all product records from the database
	 */
	public static void removeAllProducts() { //Not tested
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement("DELETE FROM Product");
			ps.executeQuery();
			
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	/**
	 * Adds an standardTicket record to the database with the
	 * provided data.  
	 */
	public static void addStandardTicket(String productCode,String depAirportCode, 
			String arrAirportCode, String depTime, String arrTime, 
			String flightNo, String flightClass, String aircraftType) { }
		
	
	 /** 
	 * Adds an offSeasonTicket record to the database with the
	 * provided data.  
	 */
	public static void addOffSeasonTicket(String productCode, String seasonStartDate, 
			String seasonEndDate, String depAirportCode, String arrAirportCode, 
			String depTime, String arrTime,	String flightNo, String flightClass, 
			String aircraftType, double rebate) { }
	 
	 /** Adds an awardsTicket record to the database with the
	 * provided data.  
	 */
	public static void addAwardsTicket(String productCode,String depAirportCode, 
			String arrAirportCode, String depTime, String arrTime, 
			String flightNo, String flightClass, 
			String aircraftType, double pointsPerMile) { } 
	
	/**
	 * Adds a CheckedBaggage record to the database with the
	 * provided data.  
	 */
	public static void addCheckedBaggage(String productCode, String ticketCode) { }

	/**
	 * Adds a Insurance record to the database with the
	 * provided data.  
	 */
	public static void addInsurance(String productCode, String productName, 
			String ageClass, double costPerMile) {	}
	
	/**
	 * Adds a SpecialAssistance record to the database with the
	 * provided data.  
	 */
	public static void addSpecialAssistance(String productCode, String assistanceType) { }

	/**
	 * Adds a refreshment record to the database with the
	 * provided data.  
	 */
	public static void addRefreshment(String productCode, String name, double cost) { }
	
	/**
	 * Removes all invoice records from the database
	 */
	public static void removeAllInvoices() { //Does this include InvoiceProduct? Not tested
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement("DELETE FROM Invoice");
			ps.executeQuery();
			//ResultSet rs =
			
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Adds an invoice record to the database with the given data.  
	 */
	public static void addInvoice(String invoiceCode, String customerCode, 
			String salesPersonCode, String invoiceDate) { }
	
	/**
	 * Adds a particular Ticket (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * additional details
	 */
	public static void addTicketToInvoice(String invoiceCode, String productCode, 
			String travelDate, String ticketNote) { }
	
	/**
	 * Adds a Passenger information to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> 
	 */
	public static void addPassengerInformation(String invoiceCode, String productCode, 
			String personCode, 
			String identity, int age, String nationality, String seat){ }
	
	/**
	 * Adds an Insurance Service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity and associated ticket information
	 */
	public static void addInsuranceToInvoice(String invoiceCode, String productCode, 
			int quantity, String ticketCode) { }

	/**
	 * Adds a CheckedBaggage Service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 */
	public static void addCheckedBaggageToInvoice(String invoiceCode, String productCode, 
			int quantity) { }
		
	/**
	 * Adds a SpecialAssistance Service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 */
	public static void addSpecialAssistanceToInvoice(String invoiceCode, String productCode, 
			String personCode) { }
	
	/**
	 * Adds a Refreshment service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 */
	public static void addRefreshmentToInvoice(String invoiceCode, 
			String productCode, int quantity) { }
}
