/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airamerica;

import com.airamerica.utils.DatabaseInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TMarino
 */
public class OffseasonTicket extends Ticket{
	
	private String seasonStartDate;
	private String seasonEndDate;
	private double rebate;

        public OffseasonTicket(String productCode, String seasonStartDate,
                String seasonEndDate, String depAirportCode, String arrAirportCode,
		String depTime, String arrTime, String flightNo, String flightClass,
                String aircraftType, String rebate, String date, String seat,
                ArrayList<Person> ticketHolders, String ticketNote) {
		super(depAirportCode,arrAirportCode,depTime,arrTime,flightNo,flightClass,
                        aircraftType,date,seat,ticketHolders,ticketNote,productCode,"TO");
                this.rebate = Double.parseDouble(rebate);
                this.seasonEndDate = seasonEndDate;
                this.seasonStartDate = seasonStartDate;
	}
        
        public OffseasonTicket(String productCode, String date, String seat,
                ArrayList<Person> ticketHolders, String ticketNote) {
		super(productCode, "TO", date,seat,ticketHolders,ticketNote);
                
        
        Connection conn = DatabaseInfo.getConnection();
                PreparedStatement ps;
                ResultSet rs;
                String getInsurance = "select OTRebate, OTSeasonEndDate, "
                        + "OTSeasonStartDate from Product where ProductCode = ?";
            try {                
                ps = conn.prepareStatement(getInsurance);
                ps.setString(1, this.getProductCode());
                rs = ps.executeQuery();
                rs.next();
                this.rebate = rs.getDouble("OTRebate");
                this.seasonEndDate = rs.getString("OTSeasonEndDate");
                this.seasonStartDate = rs.getString("OTSeasonStartDate");
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

	public String getSeasonStartDate() {
		return seasonStartDate;
	}

	public String getSeasonEndDate() {
		return seasonEndDate;
	}

	public double getRebate() {
		return rebate;
	}

}
