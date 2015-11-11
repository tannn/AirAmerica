/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airamerica;

import com.airamerica.utils.DatabaseInfo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.airamerica.utils.Haversine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	public ArrayList<Person> ticketHolder;
	private String ticketNote;

	public Ticket(String depAirportCode, String arrAirportCode, String depTime, String arrTime, String flightNo,
			String flightClass, String aircraftType, String date, String seat, ArrayList<Person> ticketHolder, String ticketNote,
			String productCode, String productType) {
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
		this.ticketHolder = ticketHolder;
		this.ticketNote = ticketNote;
	}

	public Ticket(String productCode, String productType, String date, String seat, ArrayList<Person> ticketHolders,
			String ticketNote) {
		super(productCode, productType);
		this.date = date;
		this.seat = seat;
		this.ticketHolder = ticketHolders;
		this.ticketNote = ticketNote;
                
                Connection conn = DatabaseInfo.getConnection();
                PreparedStatement ps;
                ResultSet rs;
                String getTicket = "select ArrAirport_ID, DepAirport_ID, "
                        + "ArrivalTime, DepartureTime, FlightNumber, FlightClass,"
                        + " PlaneName from Product where ProductCode = ?";
            try {                
                ps = conn.prepareStatement(getTicket);
                ps.setString(1, this.getProductCode());
                rs = ps.executeQuery();
                rs.next();
                int arrAirport_ID = rs.getInt("ArrAirport_ID");
                int depAirport_ID = rs.getInt("DepAirport_ID");
                this.depTime = rs.getString("DepartureTime");
                this.arrTime = rs.getString("ArrivalTime");
                this.flightNo = rs.getString("FlightNumber");
                this.flightClass = rs.getString("FlightClass");
                this.aircraftType = rs.getString("PlaneName");
                String getAirport = "select AirportCode from Airport where Airport_ID = ?";
                ps = conn.prepareStatement(getAirport);
                ps.setInt(1, arrAirport_ID);
                rs = ps.executeQuery();
                rs.next();
                this.arrAirportCode = rs.getString("AirportCode");
                ps.setInt(1, depAirport_ID);
                rs = ps.executeQuery();
                rs.next();
                this.depAirportCode = rs.getString("AirportCode");
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
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
		return ticketHolder;
	}

	public String getTicketNote() {
		return ticketNote;
	}
	
	public String getArrCity() {
		String arrCity = null;
                
                Connection conn = DatabaseInfo.getConnection();
                PreparedStatement ps;
                ResultSet rs;
                String getCity = "select City from Address join Airport on "
                        + "Airport.Address_ID = Address.Address_ID where AirportCode = ?";
                 try {                
                ps = conn.prepareStatement(getCity);
                ps.setString(1, this.arrAirportCode);
                rs = ps.executeQuery();
                rs.next();
                arrCity = rs.getString("City");
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
               return arrCity;
	}
	
	public String getDepCity() {
		String depCity = null;
                
                Connection conn = DatabaseInfo.getConnection();
                PreparedStatement ps;
                ResultSet rs;
                String getCity = "select City from Address join Airport on "
                        + "Airport.Address_ID = Address.Address_ID where AirportCode = ?";
                 try {                
                ps = conn.prepareStatement(getCity);
                ps.setString(1, this.depAirportCode);
                rs = ps.executeQuery();
                rs.next();
                depCity = rs.getString("City");
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
               return depCity;
	}
	
	public double getDistance() {
        Airport arr = Airport.getAirport(arrAirportCode);
        Airport dep = Airport.getAirport(depAirportCode);
        return Haversine.getMiles(dep.getAirportLatDeg(), dep.getAirportLatMin(),
                dep.getAiportLongDeg(), dep.getAirportLongMin(), arr.getAirportLatDeg(),
                arr.getAirportLatMin(), arr.getAiportLongDeg(), arr.getAirportLongMin());
	}
	
        @Override
	public double calculatePrice() {
        Airport arr = Airport.getAirport(arrAirportCode);
        Airport dep = Airport.getAirport(depAirportCode);
        double dist = Haversine.getMiles(dep.getAirportLatDeg(), dep.getAirportLatMin(),
                    dep.getAiportLongDeg(), dep.getAirportLongMin(), arr.getAirportLatDeg(),
                    arr.getAirportLatMin(), arr.getAiportLongDeg(), arr.getAirportLongMin());
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
        int segmentTax = ticketHolder.size() * 4;
        double securityTax = ticketHolder.size() * 5.60;
        return baseTax + segmentTax + securityTax;
	}
	
	 public static Ticket getTicket(String ticketCode) {
        ArrayList<Person> passengers = new ArrayList<>();
        Scanner invoiceFile = null;
        try {
            invoiceFile = new Scanner(new FileReader("data/Invoices.dat"));
        } catch (FileNotFoundException e) {
            System.out.println("data/Invoices.dat not found.");
        }
        while (invoiceFile.hasNextLine()) {
            String line = invoiceFile.nextLine();
            if (!(line.length() <= 5 && line.length() > 0)) {
                for (String s : line.split(";")[4].split(",")) {
                    String[] p = s.split(":");
                    if (Product.getProductType(p[0].trim()).equals("TS")) {
                        for (int i = 0; i < Integer.parseInt(p[2]); i++) {
                            passengers.add(new Person(p[4 + i * 5], p[5 + i * 5],
                                    Integer.parseInt(p[6 + i * 5]), p[7 + i * 5]));
                        }
                        return new Ticket(p[0], "TS", p[1], p[3], passengers,
                                p[p.length - 1]);
                    } else if (Product.getProductType(p[0]).equals("TA")) {
                        for (int i = 0; i < Integer.parseInt(p[2]); i++) {
                            passengers.add(new Person(p[4 + i * 5], p[5 + i * 5],
                                    Integer.parseInt(p[6 + i * 5]), p[7 + i * 5]));
                        }
                        return new AwardTicket(p[0], p[1], p[3],
                                passengers, p[p.length - 1]);
                    } else if (Product.getProductType(p[0]).equals("TO")) {
                        for (int i = 0; i < Integer.parseInt(p[2]); i++) {
                            passengers.add(new Person(p[4 + i * 5], p[5 + i * 5],
                                    Integer.parseInt(p[6 + i * 5]), p[7 + i * 5]));
                        }
                        return new OffseasonTicket(p[0], p[1],
                                p[3], passengers, p[p.length - 1]);
                    }
                }
            }
        }
        return null;
    }

}
