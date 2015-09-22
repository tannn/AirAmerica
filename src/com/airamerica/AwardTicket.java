package com.airamerica;

public class AwardTicket extends StandardTicket{
	
	private int pointsPerMile;

	public AwardTicket(String productCode, String productType, String depAirportCode, String arrAirportCode,
			String depTime, String arrTime, String flightNo, String flightClass, String aircraftType, int pointsPerMile) {
		super(productCode, productType, depAirportCode, arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType);
		this.pointsPerMile = pointsPerMile;
	}

	public int getPointsPerMile() {
		return pointsPerMile;
	}

}
