package com.airamerica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.airamerica.utils.DatabaseInfo;

/**
 *
 * @author TMarino
 */
public class AwardTicket extends Ticket {

	private double pointsPerMile;

	public AwardTicket(String depAirportCode, String arrAirportCode, String depTime, String arrTime, String flightNo,
			String flightClass, String aircraftType, String date, String seat, ArrayList<Person> ticketHolder,
			String ticketNote, String productCode, String pointsPerMile) {
		super(depAirportCode, arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType, date, seat,
				ticketHolder, ticketNote, productCode, "TA");
		this.pointsPerMile = Double.parseDouble(pointsPerMile);
	}

	public AwardTicket(String productCode, String date, String seat, ArrayList<Person> ticketHolders,
			String ticketNote) {
		super(productCode, "TA", date, seat, ticketHolders, ticketNote);
		
		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT ATPointsPerMile "
							+  "FROM Product WHERE ProductCode = ?");
			ps.setString(1, this.getProductCode());
			ResultSet rs = ps.executeQuery();
			rs.next();
			this.pointsPerMile = rs.getDouble("ATPointsPerMile");

			ps.close();
			rs.close();
		} catch (SQLException e1) {
			log.error("Failed to retrieve award ticket details under code " + productCode, e1);
		}
	}
	
	public double getFee() {
		return 30.00;
	}

	public double getPointsPerMile() {
		return pointsPerMile;
	}

	@Override
	public double calculatePrice() {
		return 30.00;
	}

}
