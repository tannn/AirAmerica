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

/**
 * TODO:
 * - Test all methods (including: set up database on CSE)
 * - Figure out what 'identity' is for addPassengerToInvoice()
 * - Change report generation to read database instead of data files
 */

import java.sql.*;
import com.airamerica.utils.DatabaseInfo;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() { // Not tested

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
	public static void addPerson(String personCode, String firstName, String lastName, String phoneNo, String street,
			String city, String state, String zip, String country) { // Not
																		// tested
		int personID;
		int addressID;

		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Person (PersonCode, FirstName, LastName, PhoneNumber) VALUES (?,?,?,?)");
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, phoneNo);
			ps.executeUpdate();
			ps = DatabaseInfo.getConnection().prepareStatement("SELECT LAST_INSERT_ID()");
			ResultSet rs = ps.executeQuery();
			rs.next();
			personID = rs.getInt("LAST_INSERT_ID()");

			ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Address (Address, City, StateProvince, Zip, Country) VALUES (?,?,?,?,?)");
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
			ps.setInt(2, personID);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Method that removes every airport record from the database
	 */
	public static void removeAllAirports() { // Not tested
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
	public static void addAirport(String airportCode, String name, String street, String city, String state, String zip,
			String country, int latdegs, int latmins, int londegs, int lonmins, double passengerFacilityFee) { // Not
																												// tested
		try {

			int airportID;
			int addressID;

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Airport (AirportCode, AirportName, LatDeg, LatMin, LongDeg, LongMin, PassengerFee) VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, airportCode);
			ps.setString(2, name);
			ps.setInt(3, latdegs);
			ps.setInt(4, latmins);
			ps.setInt(5, londegs);
			ps.setInt(6, lonmins);
			ps.setDouble(7, passengerFacilityFee);
			ps.executeUpdate();
			ps = DatabaseInfo.getConnection().prepareStatement("SELECT LAST_INSERT_ID()");
			ResultSet rs = ps.executeQuery();
			rs.next();
			airportID = rs.getInt("LAST_INSERT_ID()");

			ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Address (Address, City, StateProvince, Zip, Country) VALUES (?,?,?,?,?)");
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

			ps = DatabaseInfo.getConnection()
					.prepareStatement("UPDATE Airport SET Address_ID = ? WHERE Airport_ID = ?");
			ps.setInt(1, addressID);
			ps.setInt(2, airportID);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 */
	public static void addEmail(String personCode, String email) {
		try {
			int emailID;

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Email (Person_ID, Email) VALUES ((SELECT Person_ID FROM Person WHERE PersonCode = ?),?)");
			ps.setString(1, personCode);
			ps.setString(2, email);
			ps.executeUpdate();
			ps = DatabaseInfo.getConnection().prepareStatement("SELECT LAST_INSERT_ID()");
			ResultSet rs = ps.executeQuery();
			rs.next();
			emailID = rs.getInt("LAST_INSERT_ID()");

			ps = DatabaseInfo.getConnection().prepareStatement(
					"UPDATE Person SET Email_ID = ? WHERE Person_ID = (SELECT Person_ID FROM Person WHERE PersonCode = ?)");
			ps.setInt(1, emailID);
			ps.setString(2, personCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() { // Not tested

		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement("DELETE FROM Customer");
			ps.executeQuery();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Method to add a customer record to the database with the provided data.
	 */
	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,
			String name, int airlineMiles) {
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Customer (CustomerCode, CustomerType, ContactPerson_ID, CustomerName, CustomerMiles) VALUES (?,?,(SELECT Person_ID FROM Person WHERE PersonCode = ?),?,?)");
			ps.setString(1, customerCode);
			ps.setString(2, customerType);
			ps.setString(3, primaryContactPersonCode);
			ps.setString(4, name);
			ps.setInt(5, airlineMiles);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Removes all product records from the database
	 */
	public static void removeAllProducts() { // Not tested
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement("DELETE FROM Product");
			ps.executeQuery();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Adds an standardTicket record to the database with the provided data.
	 */
	public static void addStandardTicket(String productCode, String depAirportCode, String arrAirportCode,
			String depTime, String arrTime, String flightNo, String flightClass, String aircraftType) {
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, DepAirport_ID, ArrAirport_ID, DepartureTime, ArrivalTime, FlightClass, PlaneName, FlightNumber) VALUES ('TS',(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),?,?,?,?,?,?)");
			ps.setString(2, productCode);
			ps.setString(3, depAirportCode);
			ps.setString(4, arrAirportCode);
			ps.setString(5, depTime);
			ps.setString(6, arrTime);
			ps.setString(7, flightClass);
			ps.setString(8, aircraftType);
			ps.setString(9, flightNo);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds an offSeasonTicket record to the database with the provided data.
	 */
	public static void addOffSeasonTicket(String productCode, String seasonStartDate, String seasonEndDate,
			String depAirportCode, String arrAirportCode, String depTime, String arrTime, String flightNo,
			String flightClass, String aircraftType, double rebate) {
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, DepAirport_ID, ArrAirport_ID, DepartureTime, ArrivalTime, FlightClass, PlaneName, OTSeasonStartDate, OTSeasonEndDate, OTRebate, FlightNumber) VALUES ('TO',(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),?,?,?,?,?,?,?,?,?)");
			ps.setString(2, productCode);
			ps.setString(3, depAirportCode);
			ps.setString(4, arrAirportCode);
			ps.setString(5, depTime);
			ps.setString(6, arrTime);
			ps.setString(7, flightClass);
			ps.setString(8, aircraftType);
			ps.setString(9, seasonStartDate);
			ps.setString(10, seasonEndDate);
			ps.setDouble(11, rebate);
			ps.setString(12, flightNo);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds an awardsTicket record to the database with the provided data.
	 */
	public static void addAwardsTicket(String productCode, String depAirportCode, String arrAirportCode, String depTime,
			String arrTime, String flightNo, String flightClass, String aircraftType, double pointsPerMile) {
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, DepAirport_ID, ArrAirport_ID, DepartureTime, ArrivalTime, FlightClass, PlaneName, ATPointsPerMile, FlightNumber) VALUES ('TA',(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),?,?,?,?,?,?,?)");
			ps.setString(2, productCode);
			ps.setString(3, depAirportCode);
			ps.setString(4, arrAirportCode);
			ps.setString(5, depTime);
			ps.setString(6, arrTime);
			ps.setString(7, flightClass);
			ps.setString(8, aircraftType);
			ps.setDouble(9, pointsPerMile);
			ps.setString(10, flightNo);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a CheckedBaggage record to the database with the provided data.
	 */
	public static void addCheckedBaggage(String productCode, String ticketCode) {
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, TicketCode) VALUES ('SC',?,?)");
			ps.setString(2, productCode);
			ps.setString(3, ticketCode);

			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a Insurance record to the database with the provided data.
	 */
	public static void addInsurance(String productCode, String productName, String ageClass, double costPerMile) {
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, ProductPrintName, InsuranceAgeClass, InsuranceCostPerMile) VALUES ('SI',?,?,?,?)");
			ps.setString(2, productCode);
			ps.setString(3, productName);
			ps.setString(4, ageClass);
			ps.setDouble(5, costPerMile);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a SpecialAssistance record to the database with the provided data.
	 */
	public static void addSpecialAssistance(String productCode, String assistanceType) { // Not
																							// tested
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, SATypeOfService) VALUES ('SS',?,?)");
			ps.setString(2, productCode);
			ps.setString(3, assistanceType);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a refreshment record to the database with the provided data.
	 */
	public static void addRefreshment(String productCode, String name, double cost) { // Not
																						// tested
		try {

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Product (ProductType, ProductPrintName, Cost, ProductCode) VALUES ('SR',?,?,?)");
			ps.setString(2, name);
			ps.setDouble(2, cost);
			ps.setString(3, productCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Removes all invoice records from the database
	 */
	public static void removeAllInvoices() { // Not tested
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement("DELETE FROM Invoice");
			ps.executeQuery();
			ps = DatabaseInfo.getConnection().prepareStatement("DELETE FROM InvoiceProducts");
			ps.executeQuery();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds an invoice record to the database with the given data.
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) { // Not
																															// tested
		try {

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Invoice (InvoiceCode, Customer_ID, SalesPerson_ID, InvoiceDate) VALUES (?,(SELECT Customer_ID FROM Customer WHERE CustomerCode = ?),(SELECT Person_ID FROM Person WHERE PersonCode = ?),?)");
			ps.setString(1, invoiceCode);
			ps.setString(2, customerCode);
			ps.setString(3, salesPersonCode);
			ps.setString(4, invoiceDate);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a particular Ticket (corresponding to <code>productCode</code>) to
	 * an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given additional details
	 */
	public static void addTicketToInvoice(String invoiceCode, String productCode, String travelDate,
			String ticketNote) {
		try {

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO InvoiceProduct (Invoice_ID, Product_ID, FlightDate, TicketNote) VALUES ((SELECT Invoice_ID FROM Invoice WHERE InvoiceCode = ?),(SELECT Product_ID FROM Product WHERE ProductCode = ?),?,?)");
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setString(3, travelDate);
			ps.setString(4, ticketNote);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a Passenger information to an invoice corresponding to the provided
	 * <code>invoiceCode</code>
	 */
	public static void addPassengerInformation(String invoiceCode, String productCode, String personCode,
			String identity, int age, String nationality, String seat) { // Identity?
		try {

			int passengerID;

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Passenger (Product_ID, Person_ID, Age, Nationality, SeatNumber) VALUES ((SELECT Product_ID FROM Product WHERE ProductCode = ?),(SELECT Person_ID FROM Person WHERE PersonCode = ?),?,?,?)");
			ps.setString(1, productCode);
			ps.setString(2, personCode);
			ps.setInt(3, age);
			ps.setString(4, nationality);
			ps.setString(5, seat);
			ps.executeUpdate();
			ps = DatabaseInfo.getConnection().prepareStatement("SELECT LAST_INSERT_ID()");
			ResultSet rs = ps.executeQuery();
			rs.next();
			passengerID = rs.getInt("LAST_INSERT_ID()");

			ps = DatabaseInfo.getConnection()
					.prepareStatement("UPDATE InvoiceProduct SET Passenger_ID = ? WHERE Invoice_ID = (SELECT Invoice_ID FROM Invoice WHERE InvoiceCode = ?)");
			ps.setInt(1, passengerID);
			ps.setString(2, invoiceCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds an Insurance Service (corresponding to <code>productCode</code>) to
	 * an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of quantity and associated ticket information
	 */
	public static void addInsuranceToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) { // Not
																														// complete
		try {

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO InvoiceProduct (Invoice_ID, Product_ID, Quantity, TicketCode) VALUES ((SELECT Invoice_ID FROM Invoice WHERE InvoiceCode = ?),(SELECT Product_ID FROM Product WHERE ProductCode = ?),?,?)");
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.setString(4, ticketCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a CheckedBaggage Service (corresponding to <code>productCode</code>)
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of quantity.
	 */
	public static void addCheckedBaggageToInvoice(String invoiceCode, String productCode, int quantity) {
		try {

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO InvoiceProduct (Invoice_ID, Product_ID, Quantity) VALUES ((SELECT Invoice_ID FROM Invoice WHERE InvoiceCode = ?),(SELECT Product_ID FROM Product WHERE ProductCode = ?),?)");
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a SpecialAssistance Service (corresponding to
	 * <code>productCode</code>) to an invoice corresponding to the provided
	 * <code>invoiceCode</code> with the given number of quantity.
	 */
	public static void addSpecialAssistanceToInvoice(String invoiceCode, String productCode, String personCode) {
		try {

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO InvoiceProduct (Invoice_ID, Product_ID, Passenger_ID) VALUES ((SELECT Invoice_ID FROM Invoice WHERE InvoiceCode = ?),(SELECT Product_ID FROM Product WHERE ProductCode = ?),(SELECT Person_ID FROM Person WHERE PersonCode = ?))");
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setString(3, personCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a Refreshment service (corresponding to <code>productCode</code>) to
	 * an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of quantity.
	 */
	public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {
		try {

			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO InvoiceProduct (Invoice_ID, Product_ID, Quantity) VALUES ((SELECT Invoice_ID FROM Invoice WHERE InvoiceCode = ?),(SELECT Product_ID FROM Product WHERE ProductCode = ?),?)");
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
