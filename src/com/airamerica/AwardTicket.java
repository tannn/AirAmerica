package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author TMarino
 */
public class AwardTicket extends Ticket {

	private String pointsPerMile;

	public AwardTicket(String depAirportCode, String arrAirportCode, String depTime, String arrTime, String flightNo,
			String flightClass, String aircraftType, String date, String seat, ArrayList<Person> ticketHolder,
			String ticketNote, String productCode, String pointsPerMile) {
		super(depAirportCode, arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType, date, seat,
				ticketHolder, ticketNote, productCode, "TA");
		this.pointsPerMile = pointsPerMile;
	}

	public AwardTicket(String productCode, String date, String seat, ArrayList<Person> ticketHolders,
			String ticketNote) {
		super(productCode, "TA", date, seat, ticketHolders, ticketNote);
		Scanner productFile = null;
		try {
			productFile = new Scanner(new FileReader("data/Products.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("Products.dat not found.");
		}
		while (productFile.hasNextLine()) {
			String line = productFile.nextLine();
			String[] productData = line.split(";");
			if (productCode.equals(productData[0]) && "TA".equals(productData[1])) {
				this.pointsPerMile = productData[9];
			}
		}
	}
	
	public double getFee() {
		return 30.00;
	}

	public String getPointsPerMile() {
		return pointsPerMile;
	}

	@Override
	public double calculatePrice() {
		// TODO Auto-generated method stub
		return 0;
	}

}
