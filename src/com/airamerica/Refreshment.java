package com.airamerica;

import com.airamerica.utils.DatabaseInfo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EPiquette
 */
public class Refreshment extends Service {

	private String name;
	private double cost;
	private int quantity;

	public Refreshment(String productCode, String name, double cost, int quantity) {
		super(productCode, "SR");
		this.name = name;
		this.cost = cost;
		this.quantity = quantity;
	}

	public Refreshment(String productCode, int quantity) {
		super(productCode, "SR");
		this.quantity = quantity;
                
                Connection conn = DatabaseInfo.getConnection();
                PreparedStatement ps;
                ResultSet rs;
                String getRefreshment = "select ProductPrintName, Cost from Product where ProductCode = ?";
            try {                
                ps = conn.prepareStatement(getRefreshment);
                ps.setString(1, this.getProductCode());
                rs = ps.executeQuery();
                rs.next();
                this.name = rs.getString("ProductPrintName");
                this.cost = rs.getDouble("Cost");
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	public String getName() {
		return name;
	}

	public double getCost() {
		return cost;
	}
	
	public int getQuantity() {
		return quantity;
	}

	@Override
	public double calculatePrice() {
		return quantity * cost;
	}

	@Override
	public double calculateTax() {
		return calculatePrice() * .04;
	}

}
