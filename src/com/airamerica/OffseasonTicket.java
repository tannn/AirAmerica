/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airamerica;

/**
 *
 * @author TMarino
 */
public class OffseasonTicket extends Ticket{
	
	private String seasonStartDate;
	private String seasonEndDate;
	private String rebate;

	public OffseasonTicket(String productCode, String seasonStartDate,
                String seasonEndDate, String depAirportCode, String arrAirportCode,
		String depTime, String arrTime, String flightNo, String flightClass,
                String aircraftType, String rebate, String date, String seat,
                Person ticketHolder, String ticketNote) {
		super(depAirportCode,arrAirportCode,depTime,arrTime,flightNo,flightClass,
                        aircraftType,date,seat,ticketHolder,ticketNote,productCode,"TO");
                this.rebate = rebate;
                this.seasonEndDate = seasonEndDate;
                this.seasonStartDate = seasonStartDate;
	}
        
        public OffseasonTicket(String productCode, String seasonStartDate,
                String seasonEndDate, String rebate, String date, String seat,
                Person ticketHolder, String ticketNote) {
		super(productCode, "TO", date,seat,ticketHolder,ticketNote);
                this.rebate = rebate;
                this.seasonEndDate = seasonEndDate;
                this.seasonStartDate = seasonStartDate;
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
