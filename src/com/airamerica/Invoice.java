package com.airamerica;

import java.util.ArrayList;

/**
 *
 * @author EPiquette
 */
public class Invoice {

	private String invoiceCode;
	private String customerCode;
	private String salespersonCode;
	private String invoiceDate;
	private ArrayList<Product> products;

	public Invoice(String invoiceCode, String customerCode, String salespersonCode, String invoiceDate,
			String[] productInfo) {
		this.invoiceCode = invoiceCode;
		this.customerCode = customerCode;
		this.salespersonCode = salespersonCode;
		this.invoiceDate = invoiceDate;
		products = new ArrayList<Product>();
		ArrayList<Person> ticketHolders = new ArrayList<Person>();
		
		for (String s : productInfo) {
			String[] productInvoice = s.split(":");
			if (Product.getProductType(productInvoice[0].trim()).equals("TS")) {
				for (int i = 0; i < Integer.parseInt(productInvoice[2]); i++) {
					ticketHolders.add(new Person(productInvoice[4 + i * 5], productInvoice[5 + i * 5],
							Integer.parseInt(productInvoice[6 + i * 5]), productInvoice[7 + i * 5]));
				}
				products.add(new Ticket(productInvoice[0], "TS", productInvoice[1], productInvoice[3], ticketHolders,
						productInvoice[productInvoice.length - 1]));
			} else if (Product.getProductType(productInvoice[0]).equals("TA")) {
				for (int i = 0; i < Integer.parseInt(productInvoice[2]); i++) {
					ticketHolders.add(new Person(productInvoice[4 + i * 5], productInvoice[5 + i * 5],
							Integer.parseInt(productInvoice[6 + i * 5]), productInvoice[7 + i * 5]));
				}
				products.add(new AwardTicket(productInvoice[0], productInvoice[1], productInvoice[3],
						ticketHolders, productInvoice[productInvoice.length - 1]));
			} else if (Product.getProductType(productInvoice[0]).equals("TO")) {
				for (int i = 0; i < Integer.parseInt(productInvoice[2]); i++) {
					ticketHolders.add(new Person(productInvoice[4 + i * 5], productInvoice[5 + i * 5],
							Integer.parseInt(productInvoice[6 + i * 5]), productInvoice[7 + i * 5]));
				}
				products.add(new OffseasonTicket(productInvoice[0], productInvoice[1],
						productInvoice[3], ticketHolders, productInvoice[productInvoice.length - 1]));
			} else if (Product.getProductType(productInvoice[0]).equals("SI")) {
				products.add(new Insurance(productInvoice[0], Integer.parseInt(productInvoice[1]), productInvoice[2]));
			} else if (Product.getProductType(productInvoice[0]).equals("SS")) {
				products.add(new SpecialAssistance(productInvoice[0], productInvoice[1]));
			} else if (Product.getProductType(productInvoice[0]).equals("SC")) {
				products.add(new CheckedBaggage(productInvoice[0], Integer.parseInt(productInvoice[1])));
			} else if (Product.getProductType(productInvoice[0]).equals("SR")) {
				products.add(new Refreshment(productInvoice[0], Integer.parseInt(productInvoice[1])));
			}

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
		if (Customer.getCustomerType(getCustomerCode()).equals("[CORPORATE]")) 
			return 40.00;
		else
			return 0.00;
	}
	
	public double getTaxes() {
		float total = 0;
		for (Product p : products) {
			total += p.calculateTax();
		}
		return total;
	}
	
	public double getDiscounts() {
		if (Customer.getCustomerType(getCustomerCode()).equals("[CORPORATE]")) 
			return getSubtotals()  * .12;
		else
			return 0.00;
	}
	
	public double getTotals() {
		return getSubtotals() + getFees() + getTaxes() - getDiscounts();
	}
}
