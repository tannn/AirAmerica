package com.airamerica;

import com.airamerica.interfaces.InvoiceData;
import com.airamerica.utils.DatabaseInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author EPiquette
 */
public class Invoice {

	public static Logger log = Logger.getLogger(InvoiceData.class);

	private String invoiceCode;
	private String customerCode;
	private String salespersonCode;
	private String invoiceDate;
	private ArrayList<Product> products;

	public Invoice(String invoiceCode, String customerCode, String salespersonCode, String invoiceDate,
			ArrayList<Integer> productInfo) {
		this.invoiceCode = invoiceCode;
		this.customerCode = customerCode;
		this.salespersonCode = salespersonCode;
		this.invoiceDate = invoiceDate;
		products = new ArrayList<Product>();
		ArrayList<Person> ticketHolders = new ArrayList<Person>();

		Connection conn = DatabaseInfo.getConnection();

		for (Integer i : productInfo) {

			String productType = null;
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				PreparedStatement ps1 = conn.prepareStatement(
						"select * from Product join InvoiceProduct on Product.Product_ID = InvoiceProduct.Product_ID join Passenger "
						+ "on InvoiceProduct.Passenger_ID = Passenger.Passenger_ID where Product.Product_ID = ?");
				ps1.setInt(1, i);
				ResultSet rs1 = ps1.executeQuery();
				while (rs1.next()) {
					productType = rs1.getString("ProductType");

					PreparedStatement ps2 = conn.prepareStatement("select PersonCode, Age, Nationality, IdentityNumber from "
							+ "Passenger join Person on Passenger.Person_ID = "
							+ "Person.Person_ID where Product_ID = ?");
					ps2.setInt(1, i);
					ResultSet rs2 = ps2.executeQuery();
					rs2.next();

					if (productType.equals("TS")) {
						while (!rs2.isLast()) {
							ticketHolders.add(new Person(rs2.getString("PersonCode"), rs2.getString("IdentityNumber"),
									rs2.getInt("Age"), rs2.getString("Nationality")));
							rs2.next();
						}
						products.add(new Ticket(rs1.getString("ProductCode"), "TS", rs1.getString("FlightDate"),
								rs1.getString("SeatNumber"), ticketHolders, rs1.getString("TicketNote")));
					} else if (productType.equals("TA")) {
						while (!rs2.isLast()) {
							ticketHolders.add(new Person(rs2.getString("PersonCode"), rs2.getString("IdentityNumber"),
									rs2.getInt("Age"), rs2.getString("Nationality")));
							rs2.next();
						}
						products.add(new AwardTicket(rs1.getString("ProductCode"), rs1.getString("FlightDate"),
								rs1.getString("SeatNumber"), ticketHolders, rs1.getString("TicketNote")));
					} else if (productType.equals("TO")) {
						while (!rs2.isLast()) {
							ticketHolders.add(new Person(rs2.getString("PersonCode"), rs2.getString("IdentityNumber"),
									rs2.getInt("Age"), rs2.getString("Nationality")));
							rs2.next();
						}
						products.add(new OffseasonTicket(rs1.getString("ProductCode"), rs1.getString("FlightDate"),
								rs1.getString("SeatNumber"), ticketHolders, rs1.getString("TicketNote")));
					} else if (productType.equals("SI")) {
						products.add(new Insurance(rs1.getString("ProductCode"), rs1.getInt("Quantity"),
								rs1.getString("TicketCode")));
					} else if (productType.equals("SS")) {
						products.add(
								new SpecialAssistance(rs1.getString("ProductCode"), rs1.getString("SATypeOfService")));
					} else if (productType.equals("SC")) {
						products.add(new CheckedBaggage(rs1.getString("ProductCode"), rs1.getInt("Quantity")));
					} else if (productType.equals("SR")) {
						products.add(new Refreshment(rs1.getString("ProductCode"), rs1.getInt("Quantity")));
					}

					rs2.close();
					ps2.close();
				}
				rs1.close();
				ps1.close();
				conn.close();
			} catch (SQLException ex) {
				log.error("Failed to create invoice ", ex);
			} finally{
				try {
					ps.close();
				} catch (SQLException e1) {
					log.error("Failed to close PrepareStatement connection", e1);
				}
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("Failed to close ResultSet connection", e);
				}
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("Failed to close connection", e);
				}
			}
			ticketHolders = new ArrayList<Person>();
		}

	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getSalespersonCode() {
		return salespersonCode;
	}

	/**
	 *
	 * @return Name (Last, First) of the salesperson
	 */
	public String getSalesperson() {
		if (salespersonCode.equalsIgnoreCase("online")) {
			return "ONLINE";
		} else {
			return Person.getPersonName(salespersonCode);
		}
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public double getSubtotals() {
		float total = 0;
		for (Product p : products) {
			total += p.calculatePrice();
		}
		return total;
	}

	public double getFees() {
		if (Customer.getCustomerType(getCustomerCode()).equals("[CORPORATE]")) {
			return 40.00;
		} else {
			return 0.00;
		}
	}

	public double getTaxes() {
		float total = 0;
		for (Product p : products) {
			total += p.calculateTax();
		}
		return total;
	}

	public double getDiscounts() {
		if (Customer.getCustomerType(getCustomerCode()).equals("[CORPORATE]")) {
			return getSubtotals() * .12;
		} else if (Customer.getCustomerType(getCustomerCode()).equals("[GOVERNMENT]")) {
			return getTaxes();
		} else {
			return 0;
		}
	}

	public double getTotals() {
		return getSubtotals() + getFees() + getTaxes() - getDiscounts();
	}
}
