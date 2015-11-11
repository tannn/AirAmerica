package com.airamerica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.airamerica.interfaces.InvoiceData;
import com.airamerica.utils.DatabaseInfo;

abstract public class Product {

	public static Logger log = Logger.getLogger(InvoiceData.class);

	private String productCode;
	private String productType;

	public Product(String productCode, String productType) {
		this.productCode = productCode;
		this.productType = productType;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getProductType() {
		return productType;
	}

	/**
	 * @param code
	 *            Code of product
	 * @return Type of product
	 */
	public static String getProductType(String code) {

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("SELECT ProductType FROM Product WHERE ProductCode = ?");
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			ps.close();
			return rs.getString("ProductType");

		} catch (SQLException e1) {
			log.error("Failed to retrieve airport under code " + code, e1);
		}

		return null;
	}

	public abstract double calculatePrice();

	public abstract double calculateTax();
}
