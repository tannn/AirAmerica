package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Airport {

	private String airportCode;
	private String airportName;
	private Address airportAddress;
	private int airportLatDeg;
	private int airportLatMin;
	private int airportLongDeg;
	private int airportLongMin;
	private float passengerFacilityFee;

	public Airport(String airportCode, String airportName, Address airportAddress, int airportLatDeg, int airportLatMin,
			int airportLongDeg, int airportLongMin, float passengerFacilityFee) {
		this.airportCode = airportCode;
		this.airportName = airportName;
		this.airportAddress = airportAddress;
		this.airportLatDeg = airportLatDeg;
		this.airportLatMin = airportLatMin;
		this.airportLongDeg = airportLongDeg;
		this.airportLongMin = airportLongMin;
		this.passengerFacilityFee = passengerFacilityFee;
	}

	public static Airport getAirport(String airportCode) {
		Scanner airportFile = null;
		try {
			airportFile = new Scanner(new FileReader("data/Airports.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Airports.dat not found.");
		}
		while (airportFile.hasNextLine()) {
			String line = airportFile.nextLine();
			String[] airportData = line.split(";");
			System.out.println("DEBUG: " + airportData[0]);
			if (airportCode.equals(airportData[0])) {
				String[] address = airportData[2].split(",");
				String[] location = airportData[3].split(",");

				return new Airport(airportData[0], airportData[1],
						new Address(address[0], address[1], address[2], address[3], address[4]),
						Integer.parseInt(location[0]), Integer.parseInt(location[1]),
						Integer.parseInt(location[2]), Integer.parseInt(location[3]),
						Float.parseFloat(airportData[4]));
			}
		}
		return null;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public String getAirportName() {
		return airportName;
	}

	public Address getAirportAddress() {
		return airportAddress;
	}

	public int getAirportLatDeg() {
		return airportLatDeg;
	}

	public int getAirportLatMin() {
		return airportLatMin;
	}

	public int getAiportLongDeg() {
		return airportLongDeg;
	}

	public int getAirportLongMin() {
		return airportLongMin;
	}

	public float getPassengerFacilityFee() {
		return passengerFacilityFee;
	}

}
