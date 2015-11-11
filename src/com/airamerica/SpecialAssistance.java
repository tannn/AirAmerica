package com.airamerica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.airamerica.utils.DatabaseInfo;

/**
 *
 * @author EPiquette
 */
public class SpecialAssistance extends Service{
    
    private String typeOfService;
    private String productCode;

	public SpecialAssistance(String productCode, String typeOfService) {
		super(productCode, "SS");
		this.typeOfService = typeOfService;
		this.productCode = productCode;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	@Override
	public double calculatePrice() {
		return 0;
	}

	@Override
	public double calculateTax() {
		return 0;
	}
	
	public String getReason() {
		String data = "";
		
		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT SATypeOfService FROM Product WHERE ProductCode = ?");
			ps.setString(1, productCode);
			ResultSet rs = ps.executeQuery();
			ps.close();
			return rs.getString("ProductType");

		} catch (SQLException e1) {
			log.error("Failed to retrieve special assistance reason under", e1);
		}
		
		return data;
	}
    
}
