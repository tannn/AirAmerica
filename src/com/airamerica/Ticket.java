/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

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
	private ArrayList<Person> ticketHolder;
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
		Scanner productFile = null;
		try {
			productFile = new Scanner(new FileReader("data/Products.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Products.dat not found.");
		}
		while (productFile.hasNextLine()) {
			String line = productFile.nextLine();
			String[] productData = line.split(";");
			if (productCode.equals(productData[0]) && productType.equals(productData[1]) && productData[2].length() < 5) {
				this.depAirportCode = productData[2];
				this.arrAirportCode = productData[3];
				this.depTime = productData[4];
				this.arrTime = productData[5];
				this.flightNo = productData[6];
				this.flightClass = productData[7];
				this.aircraftType = productData[8];
			} else if (productCode.equals(productData[0]) && productType.equals(productData[1]) && productData[2].length() > 5){
				this.depAirportCode = productData[4];
				this.arrAirportCode = productData[5];
				this.depTime = productData[6];
				this.arrTime = productData[7];
				this.flightNo = productData[8];
				this.flightClass = productData[9];
				this.aircraftType = productData[10];
			}
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
		Scanner airportFile = null;
		try {
			airportFile = new Scanner(new FileReader("data/Airports.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Airports.dat not found.");
		}
		while (airportFile.hasNextLine()) {
			String line = airportFile.nextLine();
			String[] airportData = line.split(";");
			if (airportData[0].equals(getArrAirportCode())) {
				String[] addressData = airportData[2].split(",");
				return addressData[1] + "," + addressData[2];
			}
		}
		return null;
	}
	
	public String getDepCity() {
		Scanner airportFile = null;
		try {
			airportFile = new Scanner(new FileReader("data/Airports.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Airports.dat not found.");
		}
		while (airportFile.hasNextLine()) {
			String line = airportFile.nextLine();
			String[] airportData = line.split(";");
			if (airportData[0].equals(getDepAirportCode())) {
				String[] addressData = airportData[2].split(",");
				return addressData[1] + "," + addressData[2];
			}
		}
		return null;
	}
	
	public double getDistance() {
        Airport arr = Airport.getAirport(arrAirportCode);
        Airport dep = Airport.getAirport(depAirportCode);
        System.out.println(arrAirportCode);
        System.out.println(depAirportCode);
        System.out.println(arr);
        System.out.println(dep);
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
	
	public double calculateFees() {
        if (flightClass.equals("BC"))
        	return 40.00;
        else
        	return 0.00;
	}

}
