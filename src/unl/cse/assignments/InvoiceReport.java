package unl.cse.assignments;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

import com.airamerica.Invoice;
import com.airamerica.Product;
import com.airamerica.Ticket;

/* Assignment 3,5 and 6 (Project Phase-II,IV and V) */

//bonus: output in sorted last, first format

public class InvoiceReport {

	private List<Invoice> invoices;

	/**
	 * @return
	 */
	private String generateSummaryReport() {
		StringBuilder sb = new StringBuilder();

		sb.append("Executive Summary Report\n");
		sb.append("=========================\n");
		sb.append("Invoice\tCustomer\t\t\t\t\t\t\t\t\tSalesperson\t\tSubtotal\tFees\tTaxes\tDiscount\tTotal\n");
		// TODO: Add code for generating summary of all Invoices
		// for-loop through all invoices
		// for each invoice, do: invoice, customer, salesperson, subtotal, fees,
		// taxes, discount, total
		sb.append(
				"=====================================================================================================================================================\n");
		sb.append("TOTALS\t\t\t\t\t\t\t\t\t\t\t\t\t");

		return sb.toString();
	}

	/**
	 * @param invoiceListNum
	 * @return
	 */
	private String getTravelSummary(int invoiceListNum) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("FLIGHT INFORMATION\n");
		sb.append("==================================================\n");
		sb.append("DATE\t\tFLIGHT\tCLASS\tDEPARTURE CITY AND TIME\tARRIVAL CITY AND TIME\t\tAIRCRAFT\n");
		for (Product x : invoices.get(invoiceListNum).getProducts()) {
			if (x instanceof Ticket) {
				Ticket b = (Ticket) x;
				//TODO: need proper way to get arrival/departure city
				sb.append(b.getDate() + "\t\t" + b.getFlightNo() + "\t" + b.getFlightClass() + "\t" + b.getDepAirportCode() + "\t" + b.getArrAirportCode() + "\t\t" + b.getAircraftType() + "\n");
				sb.append("\t\t\t\t\t\t\t(" + b.getDepAirportCode() + ") " + b.getDepTime() + "\t\t\t (" + b.getArrAirportCode() + ") " + b.getArrTime() + "\n");
				sb.append("\t\tTRAVELER\t\t\t\tAGE\tSEAT NO.\n");
				
				
				
			}
		}
		sb.append("CUSTOMER INFORMATION:\n");
		sb.append("\t"); //TODO: company name and customer number
		sb.append("\t"); //TODO: corporate, gov, gen
		sb.append("\t"); //TODO: contact person name
		sb.append("\t"); //TODO: contact person address
		sb.append("\t"); //TODO: contact person country, state zip etc
		sb.append("SALESPERSON: " + invoices.get(invoiceListNum).getSalesperson() + "\n");
		return sb.toString();

	}

	/**
	 * @param invoiceCode
	 * @return
	 */
	private String getCostSummary(int invoiceCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("FARES AND SERVICES\n");
		sb.append("==================================================\n");
		sb.append("Code\tItem\t\t\t\t\t\t\t\t\tSubTotal\tTax\tTotal\n");

		// TODO: Add code for generating Cost Summary of all
		// products and services in an Invoice

		return sb.toString();

	}

	public String generateDetailReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("Individual Invoice Detail Reports\n");
		sb.append("==================================================\n");

		/*
		 * TODO: Loop through all invoices and call the getTravelSummary() and
		 * getCostSummary() for each invoice
		 */
		// for-loop through all invoices
		// for each invoice, do:
		// getTravelSummary
		// customer info
		// fares and services

		return sb.toString();
	}

	public static void main(String args[]) {

		InvoiceReport ir = new InvoiceReport();

		Scanner invoiceFile = null;

		try {
			invoiceFile = new Scanner(new FileReader("data/Invoices.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("data/Invoices.dat not found.");
		}

		while (invoiceFile.hasNextLine()) {
			String line = invoiceFile.nextLine();
			if (!(line.length() <= 5 && line.length() > 0)) {
				String[] invoice = line.split(";");
				String[] productInfo = invoice[4].split(",");
				ir.invoices.add(new Invoice(invoice[0], invoice[1], invoice[2], invoice[3], productInfo));
			}
		}

		String summary = ir.generateSummaryReport();
		String details = ir.generateDetailReport();

		System.out.println(summary);
		System.out.println("\n\n");
		System.out.println(details);

		for (int i = 0; i < ir.invoices.size(); i++) {
			
			String travelSummary = ir.getTravelSummary(i);
			String costSummary = ir.getCostSummary(i);
			
			System.out.println();
			System.out.println(
					"--------------------------------------------------------------------------------------------------------\n");
			System.out.println("AIR AMERICA\n");
			System.out.println("ISSUED: " + ir.invoices.get(i).getInvoiceDate() + "\n");
			System.out.println(
					"--------------------------------------------------------------------------------------------------------\n");
			System.out.println(travelSummary);
			//extra info
			//lines
			//customer info
			//salesperson info
			System.out.println(costSummary);
		}

		System.out.println(
				"======================================================================================================================");
		System.out.println("\n\n");

	}
}
