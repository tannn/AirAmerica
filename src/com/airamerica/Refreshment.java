package com.airamerica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.airamerica.utils.DatabaseInfo;

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

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT ProductPrintName, Cost FROM Product WHERE ProductCode = ?");
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			
            this.name = rs.getString("ProductPrintName");
            this.cost = rs.getDouble("Cost");
			
			ps.close();
			

		} catch (SQLException e1) {
			log.error("Failed to retrieve product under code " + productCode, e1);
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
