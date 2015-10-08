package com.airamerica;

public class Airport {
	
	private String airportCode;
	private String airportName;
	private Address airportAddress;
	private int airportLatDeg;
	private int airportLatMin;
	private int airportLongDeg;
	private int airportLongMin;
	private float passengerFacilityFee;
	
	public Airport(String airportCode, String airportName, Address airportAddress, int airportLatDeg, int airportLatMin,
			int airportLongDeg, int airportLongMin, float passengerFacilityFee) {
		this.airportCode = airportCode;
		this.airportName = airportName;
		this.airportAddress = airportAddress;
		this.airportLatDeg = airportLatDeg;
		this.airportLatMin = airportLatMin;
		this.airportLongDeg = airportLongDeg;
		this.airportLongMin = airportLongMin;
		this.passengerFacilityFee = passengerFacilityFee;
	}
	
	public static Airport getAirport(String airportCode){
            Scanner airportFile = null;
            try {
		airportFile = new Scanner(new FileReader("data/airports.dat"));
            } catch (FileNotFoundException e) {
		System.out.println("File not found.");
            }
            while(airportFile.hasNextLine()) {  
                String line = airportFile.nextLine();
                String[] airportData = line.split(";");
                if(airportCode.equals(airportData[0])){
                    String[] address = airportData[2].split(",");
                    return new Airport(airportData[0], airportData[1], 
                            new Address(address[0], address[1], address[2], address[3], address[5]),
                            Integer.parseInt(airportData[3]), Integer.parseInt(airportData[4]),
                            Integer.parseInt(airportData[5]), Integer.parseInt(airportData[6]),
                            Float.parseFloat(airportData[7]));
                }
        }
        return null;
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
		return airportLongDeg;
	}

	public int getAirportLongMin() {
		return airportLongMin;
	}

	public float getPassengerFacilityFee() {
		return passengerFacilityFee;
	}

}
