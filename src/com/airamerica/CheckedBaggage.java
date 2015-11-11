
package com.airamerica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.airamerica.utils.DatabaseInfo;

/**
 *
 * @author EPiquette
 */
public class CheckedBaggage extends Service {

	private String ticketCode;
	private int quantity;

	public CheckedBaggage(String productCode, String ticketCode, int quantity) {
		super(productCode, "SC");
		this.ticketCode = ticketCode;
		this.quantity = quantity;
	}

	public CheckedBaggage(String productCode, int quantity) {
		super(productCode, "SC");
		this.quantity = quantity;

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT TicketCode FROM Product WHERE ProductCode = ?");
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			this.ticketCode = rs.getString("TicketCode");
			ps.close();

		} catch (SQLException e1) {
			log.error("Failed to retrieve checked baggage under code " + ticketCode, e1);
		}
	}

	public String getTicketCode() {
		return ticketCode;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public double calculatePrice() {
		if (quantity == 1)
			return 25.00;
		else
			return 25.00 + ((quantity - 1) * 35.00);
	}

	@Override
	public double calculateTax() {
		return calculatePrice() * .04;
	}
}
