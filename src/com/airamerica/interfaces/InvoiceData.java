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
import org.apache.log4j.Logger;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	public static Logger log = Logger.getLogger(InvoiceData.class);

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		
		Connection conn = null;

		try {
			
			conn = DatabaseInfo.getConnection();
			
			PreparedStatement ps  = conn.prepareStatement("DELETE FROM Person");
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to delete Person table", e1);
		}

	}

	/**
	 * Method to add a person record to the database with the provided data.
	 */
	@SuppressWarnings("resource")
	public static void addPerson(String personCode, String firstName, String lastName, String phoneNo, String street,
			String city, String state, String zip, String country) {
		int personID;
		int addressID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DatabaseInfo.getConnection();
			ps = conn.prepareStatement(
					"INSERT INTO Person (PersonCode, FirstName, LastName, PhoneNumber) VALUES (?,?,?,?)");
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, phoneNo);
			ps.executeUpdate();
			ps = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			rs = ps.executeQuery();
			rs.next();
			personID = rs.getInt("LAST_INSERT_ID()");

			ps = conn.prepareStatement(
					"INSERT INTO Address (Address, City, StateProvince, Zip, Country) VALUES (?,?,?,?,?)");
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			ps = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			rs = ps.executeQuery();
			rs.next();
			addressID = rs.getInt("LAST_INSERT_ID()");

			ps = conn.prepareStatement("UPDATE Person SET Address_ID = ? WHERE Person_ID = ?");
			ps.setInt(1, addressID);
			ps.setInt(2, personID);
			ps.executeUpdate();

		} catch (SQLException e1) {
			log.error("Failed to add new person to Person", e1);
		} finally{
			try {
				ps.close();
			} catch (SQLException e1) {
				log.error("Failed to close PrepareStatement connection", e1);
			}
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Failed to close ResultSet connection", e);
			}
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Failed to close connection", e);
			}
		}
	}

	/**
	 * Method that removes every airport record from the database
	 */
	public static void removeAllAirports() {
		
		Connection conn = null;
		try {
			
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Airport");
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to delete Airport table", e1);
		}
	}

	/**
	 * Method to add a airport record to the database with the provided data.
	 */
	@SuppressWarnings("resource")
	public static void addAirport(String airportCode, String name, String street, String city, String state, String zip,
			String country, int latdegs, int latmins, int londegs, int lonmins, double passengerFacilityFee) { 
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseInfo.getConnection();
			int airportID;
			int addressID;

			ps = conn.prepareStatement(
					"INSERT INTO Airport (AirportCode, AirportName, LatDeg, LatMin, LongDeg, LongMin, PassengerFee) VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, airportCode);
			ps.setString(2, name);
			ps.setInt(3, latdegs);
			ps.setInt(4, latmins);
			ps.setInt(5, londegs);
			ps.setInt(6, lonmins);
			ps.setDouble(7, passengerFacilityFee);
			ps.executeUpdate();
			ps =conn.prepareStatement("SELECT LAST_INSERT_ID()");
			rs = ps.executeQuery();
			rs.next();
			airportID = rs.getInt("LAST_INSERT_ID()");

			ps = conn.prepareStatement(
					"INSERT INTO Address (Address, City, StateProvince, Zip, Country) VALUES (?,?,?,?,?)");
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			ps = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			rs = ps.executeQuery();
			rs.next();
			addressID = rs.getInt("LAST_INSERT_ID()");

			ps = conn.prepareStatement("UPDATE Airport SET Address_ID = ? WHERE Airport_ID = ?");
			ps.setInt(1, addressID);
			ps.setInt(2, airportID);
			ps.executeUpdate();

			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to add new Airport to Airport", e1);
		} finally{
			try {
				ps.close();
			} catch (SQLException e1) {
				log.error("Failed to close PrepareStatement connection", e1);
			}
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Failed to close ResultSet connection", e);
			}
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Failed to close connection", e);
			}
		}
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 */
	@SuppressWarnings("resource")
	public static void addEmail(String personCode, String email) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			conn = DatabaseInfo.getConnection();
			
			int emailID;
			int personID;
			
			ps = conn.prepareStatement("SELECT Person_ID FROM Person WHERE PersonCode = ?");
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			rs.next();
			personID = rs.getInt("Person_ID");

			ps = conn.prepareStatement(
					"INSERT INTO Email (Person_ID, Email) VALUES (?,?)");
			ps.setInt(1, personID);
			ps.setString(2, email);
			ps.executeUpdate();
			ps = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			rs = ps.executeQuery();
			rs.next();
			emailID = rs.getInt("LAST_INSERT_ID()");

			ps = conn.prepareStatement(
					"UPDATE Person SET Email_ID = ? WHERE Person_ID = ?");
			ps.setInt(1, emailID);
			ps.setInt(2, personID);
			ps.executeUpdate();

			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to add new email to Email", e1);
		} finally{
			try {
				ps.close();
			} catch (SQLException e1) {
				log.error("Failed to close PrepareStatement connection", e1);
			}
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Failed to close ResultSet connection", e);
			}
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Failed to close connection", e);
			}
		}
	}

	/**
	 * Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {

		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Customer");
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to delete Customer table", e1);
		}

	}

	/**
	 * Method to add a customer record to the database with the provided data.
	 */
	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,
			String name, int airlineMiles) {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO Customer (CustomerCode, CustomerType, ContactPerson_ID, CustomerName, CustomerMiles) VALUES (?,?,(SELECT Person_ID FROM Person WHERE PersonCode = ?),?,?)");
			ps.setString(1, customerCode);
			ps.setString(2, customerType);
			ps.setString(3, primaryContactPersonCode);
			ps.setString(4, name);
			ps.setInt(5, airlineMiles);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to add new customer to Customer", e1);
		}
	}

	/**
	 * Removes all product records from the database
	 */
	public static void removeAllProducts() {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Product");
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to delete Product table", e1);
		}

	}

	/**
	 * Adds an standardTicket record to the database with the provided data.
	 */
	public static void addStandardTicket(String productCode, String depAirportCode, String arrAirportCode,
			String depTime, String arrTime, String flightNo, String flightClass, String aircraftType) {
		try {
			PreparedStatement ps = DatabaseInfo.getConnection().prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, DepAirport_ID, ArrAirport_ID, DepartureTime, ArrivalTime, FlightClass, PlaneName, FlightNumber) VALUES ('TS',?,(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),?,?,?,?,?)");
			ps.setString(1, productCode);
			ps.setString(2, depAirportCode);
			ps.setString(3, arrAirportCode);
			ps.setString(4, depTime);
			ps.setString(5, arrTime);
			ps.setString(6, flightClass);
			ps.setString(7, aircraftType);
			ps.setString(8, flightNo);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			log.error("Failed to add new standardTicket to Product", e1);
		}
	}

	/**
	 * Adds an offSeasonTicket record to the database with the provided data.
	 */
	public static void addOffSeasonTicket(String productCode, String seasonStartDate, String seasonEndDate,
			String depAirportCode, String arrAirportCode, String depTime, String arrTime, String flightNo,
			String flightClass, String aircraftType, double rebate) {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, DepAirport_ID, ArrAirport_ID, DepartureTime, ArrivalTime, FlightClass, PlaneName, OTSeasonStartDate, OTSeasonEndDate, OTRebate, FlightNumber) VALUES ('TO',?,(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),?,?,?,?,?,?,?,?)");
			ps.setString(1, productCode);
			ps.setString(2, depAirportCode);
			ps.setString(3, arrAirportCode);
			ps.setString(4, depTime);
			ps.setString(5, arrTime);
			ps.setString(6, flightClass);
			ps.setString(7, aircraftType);
			ps.setString(8, seasonStartDate);
			ps.setString(9, seasonEndDate);
			ps.setDouble(10, rebate);
			ps.setString(11, flightNo);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to add new offseasonTicket to Product", e1);
		}
	}

	/**
	 * Adds an awardsTicket record to the database with the provided data.
	 */
	public static void addAwardsTicket(String productCode, String depAirportCode, String arrAirportCode, String depTime,
			String arrTime, String flightNo, String flightClass, String aircraftType, double pointsPerMile) {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, DepAirport_ID, ArrAirport_ID, DepartureTime, ArrivalTime, FlightClass, PlaneName, ATPointsPerMile, FlightNumber) VALUES ('TA',?,(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),(SELECT Airport_ID FROM Airport WHERE AirportCode = ?),?,?,?,?,?,?)");
			ps.setString(1, productCode);
			ps.setString(2, depAirportCode);
			ps.setString(3, arrAirportCode);
			ps.setString(4, depTime);
			ps.setString(5, arrTime);
			ps.setString(6, flightClass);
			ps.setString(7, aircraftType);
			ps.setDouble(8, pointsPerMile);
			ps.setString(9, flightNo);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to add new awardsTicket to Product", e1);
		}
	}

	/**
	 * Adds a CheckedBaggage record to the database with the provided data.
	 */
	public static void addCheckedBaggage(String productCode, String ticketCode) {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO Product (ProductType, ProductCode, TicketCode) VALUES ('SC',?,?)");
			ps.setString(1, productCode);
			ps.setString(2, ticketCode);

			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to add new checkedBaggage to Product", e1);
		}
	}

	/**
	 * Adds a Insurance record to the database with the provided data.
	 */
	public static void addInsurance(String productCode, String productName, String ageClass, double costPerMile) {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, ProductPrintName, InsuranceAgeClass, InsuranceCostPerMile) VALUES ('SI',?,?,?,?)");
			ps.setString(1, productCode);
			ps.setString(2, productName);
			ps.setString(3, ageClass);
			ps.setDouble(4, costPerMile);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to add new insurance to Product", e1);
		}
	}

	/**
	 * Adds a SpecialAssistance record to the database with the provided data.
	 */
	public static void addSpecialAssistance(String productCode, String assistanceType) {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO Product (ProductType, ProductCode, SATypeOfService) VALUES ('SS',?,?)");
			ps.setString(1, productCode);
			ps.setString(2, assistanceType);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Adds a refreshment record to the database with the provided data.
	 */
	public static void addRefreshment(String productCode, String name, double cost) {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO Product (ProductType, ProductPrintName, Cost, ProductCode) VALUES ('SR',?,?,?)");
			ps.setString(1, name);
			ps.setDouble(2, cost);
			ps.setString(3, productCode);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to add new refreshment to Product", e1);
		}
	}

	/**
	 * Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Invoice");
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to delete Invoice", e1);
		}
	}

	/**
	 * Adds an invoice record to the database with the given data.
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) {
		Connection conn = null;
		try {
			conn = DatabaseInfo.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO Invoice (InvoiceCode, Customer_ID, SalesPerson_ID, InvoiceDate) VALUES (?,(SELECT Customer_ID FROM Customer WHERE CustomerCode = ?),(SELECT Person_ID FROM Person WHERE PersonCode = ?),?)");
			ps.setString(1, invoiceCode);
			ps.setString(2, customerCode);
			ps.setString(3, salesPersonCode);
			ps.setString(4, invoiceDate);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e1) {
			log.error("Failed to add new invoice to Invoice", e1);
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
			log.error("Failed to add new ticket to invoice in InvoiceProduct", e1);
		}
	}

	/**
	 * Adds a Passenger information to an invoice corresponding to the provided
	 * <code>invoiceCode</code>
	 */
	@SuppressWarnings("resource")
	public static void addPassengerInformation(String invoiceCode, String productCode, String personCode,
			String identity, int age, String nationality, String seat) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {

			conn = DatabaseInfo.getConnection();
			
			int passengerID;

			ps = conn.prepareStatement(
					"INSERT INTO Passenger (Product_ID, Person_ID, Age, Nationality, SeatNumber, IdentityNumber) VALUES ((SELECT Product_ID FROM Product WHERE ProductCode = ?),(SELECT Person_ID FROM Person WHERE PersonCode = ?),?,?,?,?)");
			ps.setString(1, productCode);
			ps.setString(2, personCode);
			ps.setInt(3, age);
			ps.setString(4, nationality);
			ps.setString(5, seat);
			ps.setString(6, identity);
			ps.executeUpdate();
			ps = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			rs = ps.executeQuery();
			rs.next();
			passengerID = rs.getInt("LAST_INSERT_ID()");

			ps = conn.prepareStatement(
					"UPDATE InvoiceProduct SET Passenger_ID = ? WHERE Invoice_ID = (SELECT Invoice_ID FROM Invoice WHERE InvoiceCode = ?)");
			ps.setInt(1, passengerID);
			ps.setString(2, invoiceCode);
			ps.executeUpdate();

			ps.close();
			rs.close();
		} catch (SQLException e1) {
			log.error("Failed to add new passenger to Passenger", e1);
		} finally{
			try {
				ps.close();
			} catch (SQLException e1) {
				log.error("Failed to close PrepareStatement connection", e1);
			}
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Failed to close ResultSet connection", e);
			}
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Failed to close connection", e);
			}
		}
	}

	/**
	 * Adds an Insurance Service (corresponding to <code>productCode</code>) to
	 * an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of quantity and associated ticket information
	 */
	public static void addInsuranceToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) {
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
			log.error("Failed to add new insurance to invoice in InvoiceProduct", e1);
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
			log.error("Failed to add new checked baggage to invoice in InvoiceProduct", e1);
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
			log.error("Failed to add special asisstance to invoice in InvoiceProduct", e1);
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
			log.error("Failed to add new refreshment to invoice in InvoiceProduct", e1);
		}
	}
}
