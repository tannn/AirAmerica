package com.airamerica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.airamerica.utils.DatabaseInfo;
import com.airamerica.utils.Haversine;

/**
 *
 * @author TMarino
 */
public class Ticket extends Product {

	private String depAirportCode;
	private String arrAirportCode;
	private String depTime;
	private String arrTime;
	private String flightNo;
	private String flightClass;
	private String aircraftType;
	private String date;
	private String seat;
	private ArrayList<Person> ticketHolders;
	private String ticketNote;

	public Ticket(String depAirportCode, String arrAirportCode, String depTime, String arrTime, String flightNo,
			String flightClass, String aircraftType, String date, String seat, ArrayList<Person> ticketHolders,
			String ticketNote, String productCode, String productType) {
		super(productCode, productType);
		this.depAirportCode = depAirportCode;
		this.arrAirportCode = arrAirportCode;
		this.depTime = depTime;
		this.arrTime = arrTime;
		this.flightNo = flightNo;
		this.flightClass = flightClass;
		this.aircraftType = aircraftType;
		this.date = date;
		this.seat = seat;
		this.ticketHolders = ticketHolders;
		this.ticketNote = ticketNote;
	}

	public Ticket(String productCode, String productType, String date, String seat, ArrayList<Person> ticketHolders,
			String ticketNote) {
		super(productCode, productType);
		this.date = date;
		this.seat = seat;
		this.ticketHolders = ticketHolders;
		this.ticketNote = ticketNote;
		
		Connection conn = null;

		try {
			conn = DatabaseInfo.getConnection();
			
			PreparedStatement ps = conn
					.prepareStatement("SELECT ArrAirport_ID, DepAirport_ID, "
							+ "ArrivalTime, DepartureTime, FlightNumber, FlightClass,"
							+ " PlaneName FROM Product WHERE ProductCode = ?");
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int arrAirport_ID = rs.getInt("ArrAirport_ID");
			int depAirport_ID = rs.getInt("DepAirport_ID");
			this.depTime = rs.getString("DepartureTime");
			this.arrTime = rs.getString("ArrivalTime");
			this.flightNo = rs.getString("FlightNumber");
			this.flightClass = rs.getString("FlightClass");
			this.aircraftType = rs.getString("PlaneName");

			ps = conn.prepareStatement("SELECT AirportCode FROM Airport WHERE Airport_ID = ?");
			ps.setInt(1, arrAirport_ID);
			rs = ps.executeQuery();
			rs.next();
			this.arrAirportCode = rs.getString("AirportCode");
			ps.setInt(1, depAirport_ID);
			rs = ps.executeQuery();
			rs.next();
			this.depAirportCode = rs.getString("AirportCode");

			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to ticket info for ticket code " + productCode, e1);
		}

	}

	public String getDepAirportCode() {
		return depAirportCode;
	}

	public String getArrAirportCode() {
		return arrAirportCode;
	}

	public String getDepTime() {
		return depTime;
	}

	public String getArrTime() {
		return arrTime;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public String getDate() {
		return date;
	}

	public String getSeat() {
		return seat;
	}

	public ArrayList<Person> getTicketHolder() {
		return ticketHolders;
	}

	public String getTicketNote() {
		return ticketNote;
	}

	public String getArrCity() {
		String data = "";
		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("select City from Address join Airport on "
							+ "Airport.Address_ID = Address.Address_ID where AirportCode = ?");
			ps.setString(1, this.arrAirportCode);
			ResultSet rs = ps.executeQuery();
			data = rs.getString("City");
			ps.close();
			rs.close();
		} catch (SQLException e1) {
			log.error("Failed to retrieve arrival city ", e1);
		}

		return data;
	}

	public String getDepCity() {
		String data = "";
		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("select City from Address join Airport on "
							+ "Airport.Address_ID = Address.Address_ID where AirportCode = ?");
			ps.setString(1, this.depAirportCode);
			ResultSet rs = ps.executeQuery();
			data = rs.getString("City");
			ps.close();
			rs.close();
		} catch (SQLException e1) {
			log.error("Failed to retrieve departure city ", e1);
		}

		return data;
	}

	public double getDistance() {
		Airport arr = Airport.getAirport(arrAirportCode);
		Airport dep = Airport.getAirport(depAirportCode);
		return Haversine.getMiles(dep.getAirportLatDeg(), dep.getAirportLatMin(), dep.getAiportLongDeg(),
				dep.getAirportLongMin(), arr.getAirportLatDeg(), arr.getAirportLatMin(), arr.getAiportLongDeg(),
				arr.getAirportLongMin());
	}

	@Override
	public double calculatePrice() {
		Airport arr = Airport.getAirport(arrAirportCode);
		Airport dep = Airport.getAirport(depAirportCode);
		double dist = Haversine.getMiles(dep.getAirportLatDeg(), dep.getAirportLatMin(), dep.getAiportLongDeg(),
				dep.getAirportLongMin(), arr.getAirportLatDeg(), arr.getAirportLatMin(), arr.getAiportLongDeg(),
				arr.getAirportLongMin());
		if (flightClass.equals("EC"))
			return dist * .15;
		else if (flightClass.equals("BC"))
			return dist * .5;
		else
			return dist * .2;
	}

	@Override
	public double calculateTax() {
		double baseTax = calculatePrice() * .075;
		int segmentTax = ticketHolders.size() * 4;
		double securityTax = ticketHolders.size() * 5.60;
		return baseTax + segmentTax + securityTax;
	}

	/**
	 * 
	 * @param ticketCode
	 *            Code of Ticket
	 * @return Ticket object representing ticketCode
	 */
	public static Ticket getTicket(String ticketCode) {

		ArrayList<Person> passengers = new ArrayList<Person>();

		PreparedStatement ps1;
		PreparedStatement ps2;
		ResultSet rs1;
		ResultSet rs2;
		
		Connection conn = DatabaseInfo.getConnection();
		
		String getProductInfo = "select * from Product join InvoiceProduct join Passenger"
				+ "on Product.Product_ID = InvoiceProduct.Product_ID and "
				+ "InvoiceProduct.Passenger_ID = Passenger.Passenger_ID" + "where ProductCode = ?";

		String getPersonInfo = "select PersonCode, Age, Nationality from "
				+ "Passenger join Person on Passenger.Person_ID = " + "Person.Person_ID where Product_ID = ?";
		String productType = null;
		String product_ID = null;
		try {
			ps1 = conn.prepareStatement(getProductInfo);
			ps1.setString(1, ticketCode);
			rs1 = ps1.executeQuery();
			rs1.next();
			productType = rs1.getString("ProductType");
			product_ID = rs1.getString("Product_ID");

			ps2 = conn.prepareStatement(getPersonInfo);
			ps2.setString(1, product_ID);
			rs2 = ps2.executeQuery();
			rs2.next();

			if (productType.equals("TS")) {
				while (!rs2.isLast()) {
					passengers.add(new Person(rs2.getString("PersonCode"), rs2.getString("IdentityNumber"),
							rs2.getInt("Age"), rs2.getString("Nationality")));
					rs2.next();
				}
				return new Ticket(rs1.getString("ProductCode"), "TS", rs1.getString("FlightDate"),
						rs1.getString("SeatNumber"), passengers, rs1.getString("TicketNote"));
			} else if (productType.equals("TA")) {
				while (!rs2.isLast()) {
					passengers.add(new Person(rs2.getString("PersonCode"), rs2.getString("IdentityNumber"),
							rs2.getInt("Age"), rs2.getString("Nationality")));
					rs2.next();
				}
				return new AwardTicket(rs1.getString("ProductCode"), rs1.getString("FlightDate"),
						rs1.getString("SeatNumber"), passengers, rs1.getString("TicketNote"));
			} else if (productType.equals("TO")) {
				while (!rs2.isLast()) {
					passengers.add(new Person(rs2.getString("PersonCode"), rs2.getString("IdentityNumber"),
							rs2.getInt("Age"), rs2.getString("Nationality")));
					rs2.next();
				}
				return new OffseasonTicket(rs1.getString("ProductCode"), rs1.getString("FlightDate"),
						rs1.getString("SeatNumber"), passengers, rs1.getString("TicketNote"));
			}
			rs2.close();
			ps2.close();
			rs1.close();
			ps1.close();
			conn.close();
		} catch (SQLException e1) {
			log.error("Failed to retrieve product under code " + ticketCode, e1);
		}

		return null;

	}

}
