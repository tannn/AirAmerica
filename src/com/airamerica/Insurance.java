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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EPiquette
 */
public class Insurance extends Service {

	private String name;
	private String ageClass;
	private double costPerMile;
	private int quantity;
	private String ticketCode;

	public Insurance(String name, String ageClass, double costPerMile, int quantity, String ticketCode,
			String productCode) {
		super(productCode, "SI");
		this.name = name;
		this.ageClass = ageClass;
		this.costPerMile = costPerMile;
		this.quantity = quantity;
		this.ticketCode = ticketCode;
	}

	public Insurance(String productCode, int quantity, String ticketCode) {
		super(productCode, "SI");
		this.quantity = quantity;
		this.ticketCode = ticketCode;
                
                
                Connection conn = DatabaseInfo.getConnection();
                PreparedStatement ps;
                ResultSet rs;
                String getInsurance = "select ProductPrintName, InsuranceAgeClass, "
                        + "InsuranceCostPerMile from Product where ProductCode = ?";
            try {                
                ps = conn.prepareStatement(getInsurance);
                ps.setString(1, this.getProductCode());
                rs = ps.executeQuery();
                rs.next();
                this.name = rs.getString("ProductPrintName");
                this.ageClass = rs.getString("InsuranceAgeClass");
                this.costPerMile = rs.getDouble("InsuranceCostPerMile");
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
                
	}

	public String getName() {
		return name;
	}

	public String getAgeClass() {
		return ageClass;
	}

	public double getCostPerMile() {
		return costPerMile;
	}
	

        @Override
    public double calculatePrice() {
        return costPerMile*getMiles();
    }


        @Override
    public double calculateTax() {
        return calculatePrice()*0.04;
    }
	
	    public double getMiles(){
        return Ticket.getTicket(ticketCode).getDistance();
    }

}
