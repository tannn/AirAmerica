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
public class OffseasonTicket extends Ticket {

	private String seasonStartDate;
	private String seasonEndDate;
	private double rebate;

	public OffseasonTicket(String productCode, String seasonStartDate, String seasonEndDate, String depAirportCode,
			String arrAirportCode, String depTime, String arrTime, String flightNo, String flightClass,
			String aircraftType, String rebate, String date, String seat, ArrayList<Person> ticketHolders,
			String ticketNote) {
		super(depAirportCode, arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType, date, seat,
				ticketHolders, ticketNote, productCode, "TO");
		this.rebate = Double.parseDouble(rebate);
		this.seasonEndDate = seasonEndDate;
		this.seasonStartDate = seasonStartDate;
	}

	public OffseasonTicket(String productCode, String date, String seat, ArrayList<Person> ticketHolders,
			String ticketNote) {
		super(productCode, "TO", date, seat, ticketHolders, ticketNote);
		
		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT OTRebate, OTSeasonEndDate, "
							+  "OTSeasonStartDate FROM Product WHERE ProductCode = ?");
			ps.setString(1, this.getProductCode());
			ResultSet rs = ps.executeQuery();
			this.rebate = rs.getDouble("OTRebate");
			this.seasonEndDate = rs.getString("OTSeasonEndDate");
			this.seasonStartDate = rs.getString("OTSeasonStartDate");
			ps.close();
			rs.close();
		} catch (SQLException e1) {
			log.error("Failed to retrieve offseason ticket details under code " + productCode, e1);
		}
	}
	
	public double getFee() {
		return 20.00;
	}

	public String getSeasonStartDate() {
		return seasonStartDate;
	}

	public String getSeasonEndDate() {
		return seasonEndDate;
	}

	public Double getRebate() {
		return rebate;
	}

}
