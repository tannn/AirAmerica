package com.airamerica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.airamerica.interfaces.InvoiceData;
import com.airamerica.utils.DatabaseInfo;
import org.apache.log4j.Logger;

public class Airport {

	public static Logger log = Logger.getLogger(InvoiceData.class);

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

	public Airport(String airportCode, String airportName, int airportLatDeg, int airportLatMin, int airportLongDeg,
			int airportLongMin, float passengerFacilityFee) {
		this.airportCode = airportCode;
		this.airportName = airportName;
		this.airportLatDeg = airportLatDeg;
		this.airportLatMin = airportLatMin;
		this.airportLongDeg = airportLongDeg;
		this.airportLongMin = airportLongMin;
		this.passengerFacilityFee = passengerFacilityFee;
	}

	public static Airport getAirport(String airportCode) {
		Airport a = null;

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT * FROM Airport WHERE AirpotCode = ?");
			ps.setString(1, airportCode);
			ResultSet rs = ps.executeQuery();

			int addressID = rs.getInt("Address_ID");

			if (rs.getString("Address_ID") == null) {
				a = new Airport(rs.getString("AirportCode"), rs.getString("AirportName"), rs.getInt("LatDeg"),
						rs.getInt("LatMin"), rs.getInt("LongDeg"), rs.getInt("LongMin"), rs.getFloat("PassengerFee"));
			} else {
				ps = DatabaseInfo.getConnection().prepareStatement("SELECT * FROM Address WHERE Address_ID = ?");
				ps.setInt(1, addressID);
				ResultSet addressInfo = ps.executeQuery();

				Address address = new Address(addressInfo.getString("Address"), addressInfo.getString("City"),
						addressInfo.getString("StateProvince"), addressInfo.getString("ZIP"),
						addressInfo.getString("Country"));

				a = new Airport(rs.getString("AirportCode"), rs.getString("AirportName"), address, rs.getInt("LatDeg"),
						rs.getInt("LatMin"), rs.getInt("LongDeg"), rs.getInt("LongMin"), rs.getFloat("PassengerFee"));
			}
			ps.close();
		} catch (SQLException e1) {
			log.error("Failed to retrieve airport under code " + airportCode, e1);
		}

		return a;

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
