/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

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
	private Person ticketHolder;
	private String ticketNote;

	public Ticket(String depAirportCode, String arrAirportCode, String depTime, String arrTime, String flightNo,
			String flightClass, String aircraftType, String date, String seat, Person ticketHolder, String ticketNote,
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

	public Ticket(String productCode, String productType, String date, String seat, Person ticketHolder,
			String ticketNote) {
		super(productCode, productType);
		this.date = date;
		this.seat = seat;
		this.ticketHolder = ticketHolder;
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
			if (productCode.equals(productData[0]) && productType.equals(productData[1])) {
				this.depAirportCode = productData[2];
				this.arrAirportCode = productData[3];
				this.depTime = productData[4];
				this.arrTime = productData[5];
				this.flightNo = productData[6];
				this.flightClass = productData[7];
				this.aircraftType = productData[8];
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

	public Person getTicketHolder() {
		return ticketHolder;
	}

	public String getTicketNote() {
		return ticketNote;
	}

}
