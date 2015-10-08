package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author EPiquette
 */
public class Invoice {

	private String invoiceCode;
	private String customerCode;
	private String salespersonCode;
	private String invoiceDate;
	private List<Product> products;

	public Invoice(String invoiceCode, String customerCode, String salespersonCode, String invoiceDate,
			String[] productInfo) {
		this.invoiceCode = invoiceCode;
		this.customerCode = customerCode;
		this.salespersonCode = salespersonCode;
		this.invoiceDate = invoiceDate;
		for (String s : productInfo) {
			String[] productInvoice = s.split(":");
			if (Product.getProductType(productInvoice[0]).equals("TS")) {
				for (int i = 0; i < Integer.parseInt(productInvoice[2]); i++) {
                        ticketHolders.add(new Person(productInvoice[4 + i * 5], productInvoice[5 + i * 5],
                                Integer.parseInt(productInvoice[6 + i * 5]), productInvoice[7 + i * 5]));
                    }
                    products.add(new Ticket(productInvoice[0], "TS", productInvoice[1],
                            productInvoice[3], ticketHolders,
                            productInvoice[productInvoice.length - 1]));
			} else if (Product.getProductType(productInvoice[0]).equals("TA")) {
				for (int i = 0; i < Integer.parseInt(productInvoice[2]); i++) {
                        ticketHolders.add(new Person(productInvoice[4 + i * 5], productInvoice[5 + i * 5],
                                Integer.parseInt(productInvoice[6 + i * 5]), productInvoice[7 + i * 5]));
                    }
                    products.add(new AwardTicket(productInvoice[0], null, productInvoice[1],
                            productInvoice[3], ticketHolders, productInvoice[productInvoice.length - 1]));
			} else if (Product.getProductType(productInvoice[0]).equals("TO")) {
				for (int i = 0; i < Integer.parseInt(productInvoice[2]); i++) {
                        ticketHolders.add(new Person(productInvoice[4 + i * 5], productInvoice[5 + i * 5],
                                Integer.parseInt(productInvoice[6 + i * 5]), productInvoice[7 + i * 5]));
                    }
                    products.add(new OffseasonTicket(productInvoice[0], null, null, null,
                            productInvoice[1], productInvoice[3], ticketHolders, productInvoice[productInvoice.length - 1]));
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

	public List<Product> getProducts() {
		return products;
	}
}
