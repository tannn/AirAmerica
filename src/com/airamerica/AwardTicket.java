package com.airamerica;

/**
 *
 * @author TMarino
 */
public class AwardTicket extends Ticket{
	
	private String pointsPerMile;

	public AwardTicket(String depAirportCode, String arrAirportCode,
			String depTime, String arrTime, String flightNo, String flightClass,
                        String aircraftType, String date, String seat, Person ticketHolder,
                        String ticketNote, String productCode,String pointsPerMile) {
		super(depAirportCode,arrAirportCode,depTime,arrTime,flightNo,flightClass,
                        aircraftType,date,seat,ticketHolder,ticketNote,productCode, "TA");
		this.pointsPerMile = pointsPerMile;
	}
        
        public AwardTicket(String productCode, String pointsPerMile, String date, String seat,
                Person ticketHolder, String ticketNote) {
		super(productCode,"TO",date,seat,ticketHolder,ticketNote);
                this.pointsPerMile = pointsPerMile;
	}

	public String getPointsPerMile() {
		return pointsPerMile;
	}

}
