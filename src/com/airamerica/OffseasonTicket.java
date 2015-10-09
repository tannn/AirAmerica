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
                ArrayList<Person> ticketHolders, String ticketNote) {
		super(depAirportCode,arrAirportCode,depTime,arrTime,flightNo,flightClass,
                        aircraftType,date,seat,ticketHolders,ticketNote,productCode,"TO");
                this.rebate = rebate;
                this.seasonEndDate = seasonEndDate;
                this.seasonStartDate = seasonStartDate;
	}
        
        public OffseasonTicket(String productCode, String date, String seat,
                ArrayList<Person> ticketHolders, String ticketNote) {
		super(productCode, "TO", date,seat,ticketHolders,ticketNote);
                Scanner productFile = null;
        try {
		productFile = new Scanner(new FileReader("data/products.dat"));
            } catch (FileNotFoundException e) {
		System.out.println("File not found.");
            }
        while(productFile.hasNextLine()) {  
                String line = productFile.nextLine();
                String[] productData = line.split(";");
                if(productCode.equals(productData[0])&&"TO".equals(productData[1])){
                    this.rebate = productData[2];
                    this.seasonEndDate = productData[3];
                    this.seasonStartDate = productData[11];
                }
        }
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
