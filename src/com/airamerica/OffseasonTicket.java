package com.airamerica;

public class OffseasonTicket extends StandardTicket{
	
	private String seasonStartDate;
	private String seasonEndDate;
	private String rebate;

	public OffseasonTicket(String productCode, String productType, String seasonStartDate, String seasonEndDate, String depAirportCode, String arrAirportCode,
			String depTime, String arrTime, String flightNo, String flightClass, String aircraftType, String rebate) {
		super(productCode, productType, depAirportCode, arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType);
		
	}

	public String getSeasonStartDate() {
		return seasonStartDate;
	}

	public String getSeasonEndDate() {
		return seasonEndDate;
	}

	public String getRebate() {
		return rebate;
	}

}
