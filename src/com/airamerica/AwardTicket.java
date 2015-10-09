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
        
       public AwardTicket(String productCode, String date, String seat,
                ArrayList<Person> ticketHolders, String ticketNote) {
		super(productCode,"TO",date,seat,ticketHolders,ticketNote);
                this.pointsPerMile = pointsPerMile;
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
                    this.pointsPerMile = productData[9];
                }
        }
	}

	public String getPointsPerMile() {
		return pointsPerMile;
	}

}
