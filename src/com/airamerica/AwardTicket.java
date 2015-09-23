package com.airamerica;

public class AwardTicket extends Ticket {
	
	private String pointsPerMile;

	public AwardTicket(String productCode, String productType, String depAirportCode, String arrAirportCode,
			String depTime, String arrTime, String flightNo, String flightClass, String aircraftType, String pointsPerMile) {
		super(productCode, productType, depAirportCode, arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType);
		this.pointsPerMile = pointsPerMile;
	}

	public String getPointsPerMile() {
		return pointsPerMile;
	}

}
