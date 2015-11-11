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

abstract public class Product {
	//calculate price, polymorphism
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
	 * @param code Code of product
	 * @return	Type of product
	 */
	public static String getProductType(String code) {
		String type = null;
                
                
                Connection conn = DatabaseInfo.getConnection();
                PreparedStatement ps;
                ResultSet rs;
                String getType = "select ProductType from Product where ProductCode = ?";
            try {                
                ps = conn.prepareStatement(getType);
                ps.setString(1, code);
                rs = ps.executeQuery();
                rs.next();
                type = rs.getString("ProductType");
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
                
		return type;
	}
        
        abstract double calculateTax();
        abstract double calculatePrice();

}
