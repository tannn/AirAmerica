package com.airamerica;

public class Airport {
	
	private String airportCode;
	private String airportName;
	private Address airportAddress;
	private int airportLatDeg;
	private int airportLatMin;
	private int aiportLongDeg;
	private int airportLongMin;
	private float passengerFacilityFee;
	
	public Airport(String airportCode, String airportName, Address airportAddress, int airportLatDeg, int airportLatMin,
			int aiportLongDeg, int airportLongMin, float passengerFacilityFee) {
		this.airportCode = airportCode;
		this.airportName = airportName;
		this.airportAddress = airportAddress;
		this.airportLatDeg = airportLatDeg;
		this.airportLatMin = airportLatMin;
		this.aiportLongDeg = aiportLongDeg;
		this.airportLongMin = airportLongMin;
		this.passengerFacilityFee = passengerFacilityFee;
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
		return aiportLongDeg;
	}

	public int getAirportLongMin() {
		return airportLongMin;
	}

	public float getPassengerFacilityFee() {
		return passengerFacilityFee;
	}

}
