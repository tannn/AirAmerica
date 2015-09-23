package com.airamerica;

public class Ticket extends Product {
	
	private String depAirportCode;
	private String arrAirportCode;
	private String depTime;
	private String arrTime;
	private String flightNo;
	private String flightClass;
	private String aircraftType;

	public Ticket(String productCode, String productType, String depAirportCode, String arrAirportCode,
				  String depTime, String arrTime, String flightNo, String flightClass, String aircraftType) {
		super(productCode, productType);
		this.depAirportCode = depAirportCode;
		this.arrAirportCode = arrAirportCode;
		this.depTime = depTime;
		this.arrTime = arrTime;
		this.flightNo = flightNo;
		this.flightClass = flightClass;
		this.aircraftType = aircraftType;
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

}
