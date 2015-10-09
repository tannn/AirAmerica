package unl.cse.assignments;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.airamerica.AwardTicket;
import com.airamerica.CheckedBaggage;
import com.airamerica.Customer;
import com.airamerica.Insurance;
import com.airamerica.Invoice;
import com.airamerica.OffseasonTicket;
import com.airamerica.Person;
import com.airamerica.Product;
import com.airamerica.Refreshment;
import com.airamerica.Ticket;
import com.airamerica.utils.StandardUtils;

/* Assignment 3,5 and 6 (Project Phase-II,IV and V) */

//bonus: output in sorted last, first format

public class InvoiceReport {

	private ArrayList<Invoice> invoices = new ArrayList<Invoice>();

	private void generateSummaryReport() {
		System.out.printf("Executive Summary Report\n");
		System.out.printf("=========================\n");
		System.out.printf("Invoice\tCustomer\t\t\t\t\t\t\t\tSalesperson\t\tSubtotal\tFees\tTaxes\tDiscount\tTotal\n");
		// TODO:  subtotal, fees, taxes, discount, total
		for (Invoice i : invoices) {
			System.out.printf("%s\t%s %s\t\t\t\t\t\t%s\t\t\n", i.getInvoiceCode(), Customer.getCustomerName(i.getCustomerCode()), Customer.getCustomerType(i.getCustomerCode()), i.getSalesperson());

		}
		System.out.printf(
				"=====================================================================================================================================================\n");
		System.out.printf("TOTALS\t\t\t\t\t\t\t\t\t\t\t\t\t");

	}

	/**
	 * @param invoiceListNum The position of invoice in the array
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
				sb.append(b.getDate() + "\t" + b.getFlightNo() + "\t" + b.getFlightClass() + "\t" + b.getDepCity() + "\t\t" + b.getArrCity() + "\t\t" + b.getAircraftType() + "\n");
				sb.append("\t\t\t\t(" + b.getDepAirportCode() + ") " + b.getDepTime() + "\t\t\t (" + b.getArrAirportCode() + ") " + b.getArrTime() + "\n");
				sb.append("\t\tTRAVELER\t\t\t\tAGE\tSEAT NO.\n"); //TODO: May need another for loop for all the passengers



			}
		}
		//Retrieve customer number and contact person number
		String customerCode = invoices.get(invoiceListNum).getCustomerCode();
		String primaryContactCode = Customer.getCustomerContactCode(customerCode);
		sb.append("CUSTOMER INFORMATION:\n");
		sb.append("\t" + Customer.getCustomerName(customerCode) + " (" + customerCode + ")" + "\n");
		sb.append("\t" + Customer.getCustomerType(customerCode) + "\n");
		sb.append("\t" + Person.getContactInfo(primaryContactCode) + "\n");
		sb.append("SALESPERSON: " + invoices.get(invoiceListNum).getSalesperson() + "\n");
		System.out.println(
				"--------------------------------------------------------------------------------------------------------\n");
		return sb.toString();

	}

	/**
	 * @param invoiceCode Code of invoice
	 * @return Subtotals, discounts, fees, and totals of all fees and services
	 */
	private String getCostSummary(int invoiceListNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("FARES AND SERVICES\n");
		sb.append("==================================================\n");
		sb.append("Code\tItem\t\t\t\t\t\t\t\t\tSubTotal\tTax\tTotal\n");
		for (Product x : invoices.get(invoiceListNum).getProducts()) {
			sb.append(x.getProductCode() + "\t");
			if (x instanceof AwardTicket) {
				//TODO: Extra line containing # of units, @ reward miles/unit && $30 redemption fee
				sb.append("AwardTicket(" + ((AwardTicket) x).getFlightClass() + ") " + ((AwardTicket) x).getDepAirportCode() + " to " + ((AwardTicket) x).getArrAirportCode() + " (" + Math.round(((Ticket) x).getDistance()) + " miles)\n"); //TODO: pricing
//				sb.append(/**/" unit(s) @ " + /* reward miles */ + " reward miles/unit with $30 redemption fee)" );
			} else if (x instanceof OffseasonTicket) {
				//TODO: Extra line containing # of units, @ cost/unit && $20 redemption fee
				System.out.println(x.getProductCode());
				sb.append("OffseasonTicket(" + ((OffseasonTicket) x).getFlightClass() + ") " + ((OffseasonTicket) x).getDepAirportCode() + " to " + ((OffseasonTicket) x).getArrAirportCode() + " (" + Math.round(((OffseasonTicket) x).getDistance()) + " miles)\n"); //TODO: pricing, % off
//				sb.append(/**/" unit(s) @ " + /* cost */ + "unit with $20 fee)" );
			} else if (x instanceof Ticket) {
				//TODO: Extra line containing # of units, @ cost/unit
				sb.append("StandardTicket(" + ((Ticket) x).getFlightClass() + ") " + ((Ticket) x).getDepAirportCode() + " to " + ((Ticket) x).getArrAirportCode() + " (" + Math.round(((Ticket) x).getDistance()) + " miles)\n"); //TODO: pricing, % off
//				sb.append(/**/" unit(s) @ " + /* cost */ + "unit)" );
			} else if (x instanceof Insurance) {
				//TODO: Extra line containing # of units, @ cost/unit * miles
				sb.append("Insurance " + ((Insurance) x).getName() + " (" /* age range */ + ")\n");
			} else if (x instanceof CheckedBaggage) {
				sb.append("Baggage (" + ((CheckedBaggage) x).getQuantity() + " unit(s) @ $25.00 for first and $35.00 onwards)\n"); //TODO: pricing
			} else if (x instanceof Refreshment) {
//				sb.append(((Refreshment) x).getName() + " (" + /*#*/ + " unit(s) @ " + /* price */ + "/unit)");
			}
		}
		// TODO: Add code for generating Cost Summary of all
		// products and services in an Invoice
		sb.append("\t\t\t\t\t\t\t============================================\n");
		sb.append("SUBTOTALS\n");
		sb.append("DISCOUNT\n"); //specify type of discount, corporate
		sb.append("ADDITIONAL FEE\n"); //such as for corporate customers
		sb.append("TOTAL\n");

		return sb.toString();

	}

	/**
	 *
	 * @return
	 */
	public String generateDetailReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("Individual Invoice Detail Reports\n");
		sb.append("==================================================\n");

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

		String details = ir.generateDetailReport();

		ir.generateSummaryReport();
		System.out.println("\n\n");
		System.out.println(details);

		for (int i = 0; i < ir.invoices.size(); i++) {

			String travelSummary = ir.getTravelSummary(i);
			String costSummary = ir.getCostSummary(i);

			System.out.println(ir.invoices.get(i).getInvoiceCode());
			System.out.println(
					"--------------------------------------------------------------------------------------------------------");
			System.out.println("AIR AMERICA\t\t\t\t\t\tPNR");
			System.out.println("ISSUED: " + ir.invoices.get(i).getInvoiceDate() + "\t\t\t\t\t" + StandardUtils.generatePNR());
			System.out.println(
					"--------------------------------------------------------------------------------------------------------");
			System.out.println(travelSummary);
			System.out.println(costSummary);
			System.out.println(
					"--------------------------------------------------------------------------------------------------------");
		}

		System.out.println(
				"======================================================================================================================");
		System.out.println("\n\n");

	}
}
