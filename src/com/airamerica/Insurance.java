package com.airamerica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.airamerica.utils.DatabaseInfo;

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
		this.setQuantity(quantity);
		this.ticketCode = ticketCode;
	}

	public Insurance(String productCode, int quantity, String ticketCode) {
		super(productCode, "SI");
		this.setQuantity(quantity);
		this.ticketCode = ticketCode;

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("select ProductPrintName, InsuranceAgeClass, "
							+ "InsuranceCostPerMile from Product where ProductCode = ?");
			ps.setString(1, this.getProductCode());
			ResultSet rs = ps.executeQuery();
			this.name = rs.getString("ProductPrintName");
			this.ageClass = rs.getString("InsuranceAgeClass");
			this.costPerMile = rs.getDouble("InsuranceCostPerMile");
			ps.close();

		} catch (SQLException e1) {
			log.error("Failed to retrieve insurance under code " + productCode, e1);
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
		return costPerMile * getMiles();
	}

	@Override
	public double calculateTax() {
		return calculatePrice() * 0.04;
	}

	public double getMiles() {
		return Ticket.getTicket(ticketCode).getDistance();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
