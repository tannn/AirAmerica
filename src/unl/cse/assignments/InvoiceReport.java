package unl.cse.assignments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.airamerica.AwardTicket;
import com.airamerica.CheckedBaggage;
import com.airamerica.Customer;
import com.airamerica.Insurance;
import com.airamerica.Invoice;
import com.airamerica.OffseasonTicket;
import com.airamerica.Person;
import com.airamerica.Product;
import com.airamerica.Refreshment;
import com.airamerica.SpecialAssistance;
import com.airamerica.Ticket;
import com.airamerica.interfaces.InvoiceData;
import com.airamerica.utils.DatabaseInfo;
import com.airamerica.utils.StandardUtils;

public class InvoiceReport {

	public static Logger log = Logger.getLogger(InvoiceData.class);

	private ArrayList<Invoice> invoices = new ArrayList<Invoice>();
	DecimalFormat format = new DecimalFormat("0.##");

	private void generateSummaryReport() {
		float subtotals = 0;
		float fees = 0;
		float taxes = 0;
		float discounts = 0;
		float totals = 0;
		System.out.printf("Executive Summary Report\n");
		System.out.printf("=========================\n");
		System.out.printf("Invoice\tCustomer\t\t\t\t\t\tSalesperson\t\tSubtotal\tFees\tTaxes\tDiscount\tTotal\n");
		for (Invoice i : invoices) {
			System.out.printf("%s\t%s %s\t\t\t\t%s\t$%s\t$%s\t$%s\t$-%s\t$%s\n", i.getInvoiceCode(),
					Customer.getCustomerName(i.getCustomerCode()), Customer.getCustomerType(i.getCustomerCode()),
					i.getSalesperson(), format.format(i.getSubtotals()), format.format(i.getFees()),
					format.format(i.getTaxes()), format.format(i.getDiscounts()), format.format(i.getTotals()));
			subtotals += i.getSubtotals();
			fees += i.getFees();
			taxes += i.getTaxes();
			discounts += i.getDiscounts();
			totals += i.getTotals();
		}
		System.out.printf(
				"=====================================================================================================================================================\n");
		System.out.printf("TOTALS\t\t\t\t\t\t\t\t\t\t\t$%s\t$%s\t$%s\t$-%s\t$%s\n", format.format(subtotals),
				format.format(fees), format.format(taxes), format.format(discounts), format.format(totals));

	}

	/**
	 * @param invoiceListNum
	 *            The position of invoice in the array
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
				sb.append(b.getDate() + "\t" + b.getFlightNo() + "\t" + b.getFlightClass() + "\t" + b.getDepCity()
						+ "\t\t" + b.getArrCity() + "\t\t" + b.getAircraftType() + "\n");
				sb.append("\t\t\t\t(" + b.getDepAirportCode() + ") " + b.getDepTime() + "\t\t\t ("
						+ b.getArrAirportCode() + ") " + b.getArrTime() + "\n");
				sb.append("TRAVELER\t\tAGE\tSEAT NO.\n");
				for (Person f : ((Ticket) x).getTicketHolder()) {
					sb.append(f.getLastName() + ", " + f.getFirstName() + "\t\t" + f.getAge() + "\t" + f.getSeat()
							+ "\n");
				}

			}
		}
		// Retrieve customer number and contact person number
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
	 * @param invoiceCode
	 *            Code of invoice
	 * @return Subtotals, discounts, fees, and totals of all fees and services
	 */
	private String getCostSummary(int invoiceListNum) {
		double subtotal = 0;
		double taxes = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("FARES AND SERVICES\n");
		sb.append("==================================================\n");
		sb.append("Code\tItem\t\t\t\t\t\t\t\t\tSubTotal\tTax\tTotal\n");
		// Print all the fares and services
		for (Product x : invoices.get(invoiceListNum).getProducts()) {
			sb.append(x.getProductCode() + "\t");
			if (x instanceof AwardTicket) {
				sb.append("AwardTicket(" + ((AwardTicket) x).getFlightClass() + ") "
						+ ((AwardTicket) x).getDepAirportCode() + " to " + ((AwardTicket) x).getArrAirportCode() + " ("
						+ Math.round(((Ticket) x).getDistance()) + " miles)\t\t\t\t $"
						+ format.format(((AwardTicket) x).calculatePrice()) + "\t$"
						+ format.format(((AwardTicket) x).calculateTax()) + "\t$"
						+ format.format(((AwardTicket) x).calculatePrice() + ((AwardTicket) x).calculateTax()) + "\n");
				sb.append("\t(1 unit @ "
						+ format.format(((AwardTicket) x).getPointsPerMile() * ((AwardTicket) x).getDistance())
						+ " reward miles/unit with $30 redemption fee)\n");
				subtotal += ((AwardTicket) x).calculatePrice();
				taxes += ((AwardTicket) x).calculateTax();
			} else if (x instanceof OffseasonTicket) {
				System.out.println(x.getProductCode());
				sb.append("OffseasonTicket(" + ((OffseasonTicket) x).getFlightClass() + ") "
						+ ((OffseasonTicket) x).getDepAirportCode() + " to " + ((OffseasonTicket) x).getArrAirportCode()
						+ " (" + format.format(((OffseasonTicket) x).getDistance()) + " miles)\t\t\t\t $"
						+ format.format(((OffseasonTicket) x).calculatePrice()) + "\t$"
						+ format.format(((OffseasonTicket) x).calculateTax()) + "\t$"
						+ format.format(((OffseasonTicket) x).calculatePrice() + ((OffseasonTicket) x).calculateTax())
						+ "\n");
				sb.append("\t(1 unit @ " + format.format(((OffseasonTicket) x).calculatePrice())
						+ "/unit with $20 fee)\n");
				subtotal += ((OffseasonTicket) x).calculatePrice();
				taxes += ((OffseasonTicket) x).calculateTax();
			} else if (x instanceof Ticket) {
				sb.append("StandardTicket(" + ((Ticket) x).getFlightClass() + ") " + ((Ticket) x).getDepAirportCode()
						+ " to " + ((Ticket) x).getArrAirportCode() + " (" + format.format(((Ticket) x).getDistance())
						+ " miles)\t\t\t\t $" + format.format(((Ticket) x).calculatePrice()) + "\t$"
						+ format.format(((Ticket) x).calculateTax()) + "\t$"
						+ format.format((((Ticket) x).calculatePrice() + ((Ticket) x).calculateTax())) + "\n");
				sb.append("\t(1 unit @ " + format.format(((Ticket) x).calculatePrice()) + "/unit)\n");
				subtotal += ((Ticket) x).calculatePrice();
				taxes += ((Ticket) x).calculateTax();
			} else if (x instanceof Insurance) {
				sb.append("Insurance " + ((Insurance) x).getName() + " (" + ((Insurance) x).getAgeClass()
						+ ")\t\t\t\t\t$" + format.format(((Insurance) x).calculatePrice()) + "\t$"
						+ format.format(((Insurance) x).calculateTax()) + "\t$"
						+ format.format(((Insurance) x).calculatePrice() + ((Insurance) x).calculateTax()) + "\n");
				sb.append("\t(" + format.format(((Insurance) x).getCostPerMile()) + "/mile @ "
						+ format.format(((Insurance) x).getMiles()) + " miles)\n");
				subtotal += 0;
				taxes += 0;
			} else if (x instanceof CheckedBaggage) {
				sb.append("Baggage (" + ((CheckedBaggage) x).getQuantity()
						+ " unit(s) @ $25.00 for first and $35.00 onwards)" + "\t\t$"
						+ format.format(((CheckedBaggage) x).calculatePrice()) + "\t$"
						+ format.format(((CheckedBaggage) x).calculateTax()) + "\t$"
						+ format.format(((CheckedBaggage) x).calculatePrice() + ((CheckedBaggage) x).calculateTax())
						+ "\n");
				subtotal += ((CheckedBaggage) x).calculatePrice();
				taxes += ((CheckedBaggage) x).calculateTax();
			} else if (x instanceof Refreshment) {
				sb.append(((Refreshment) x).getName() + " (" + ((Refreshment) x).getQuantity() + " unit(s) @ "
						+ format.format(((Refreshment) x).getCost()) + "/unit)\t$"
						+ format.format(((Refreshment) x).calculatePrice()) + "\t$"
						+ format.format(((Refreshment) x).calculateTax()) + "\t$"
						+ format.format(((Refreshment) x).calculatePrice() + ((Refreshment) x).calculateTax()) + "\n");
				subtotal += ((Refreshment) x).calculatePrice();
				taxes += ((Refreshment) x).calculateTax();
			} else if (x instanceof SpecialAssistance) {
				sb.append(((SpecialAssistance) x).getReason() + "\n");
			}
		}
		sb.append("\t\t\t\t\t\t\t============================================\n");
		sb.append("SUBTOTALS\t\t\t\t\t\t\t\t$" + format.format(subtotal) + "\t$" + format.format(taxes) + "\t$"
				+ format.format(invoices.get(invoiceListNum).getSubtotals() + invoices.get(invoiceListNum).getTaxes())
				+ "\n");
		sb.append("DISCOUNT\t\t\t\t\t\t\t\t\t\t$-" + format.format(invoices.get(invoiceListNum).getDiscounts()) + "\n");
		sb.append("ADDITIONAL FEE\t\t\t\t\t\t\t\t\t\t$" + format.format(invoices.get(invoiceListNum).getFees()) + "\n");
		sb.append("TOTAL\t\t\t\t\t\t\t\t\t\t\t$" + format.format(invoices.get(invoiceListNum).getTotals()) + "\n");

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
		
		ArrayList<Integer> products = new ArrayList<Integer>();

		try {
			PreparedStatement ps = DatabaseInfo.getConnection()
					.prepareStatement("select Invoice_ID, InvoiceCode, CustomerCode, "
							+ "PersonCode, InvoiceDate from Invoice join Customer join Person on "
							+ "Invoice.Customer_ID = Customer.Customer_ID AND "
							+ "Invoice.SalesPerson_ID = Person.Person_ID");
			ResultSet rs = ps.executeQuery();
			
			while (!rs.isLast()) {
				rs.next();
				
				PreparedStatement ps2 = DatabaseInfo.getConnection()
						.prepareStatement("select Product_ID from InvoiceProduct where Invoice_ID = ?");
				ps2.setInt(1, rs.getInt("Invoice_ID"));
				ResultSet rs2 = ps2.executeQuery();
				
				while (!rs2.isLast()) {
					rs2.next();
					products.add(rs2.getInt("Product_ID"));
				}
				
				
				rs2.close();
				ps2.close();
				
				ir.invoices.add(new Invoice(rs.getString("InvoiceCode"), rs.getString("CustomerCode"), rs.getString("PersonCode"),rs.getString("InvoiceDate"), products));
				
				products = new ArrayList<Integer>();
				
				
				
			}
			
			
			rs.close();
			ps.close();

		} catch (SQLException e1) {
			log.error("Failed to retrieve invoices", e1);
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
			System.out.println(
					"ISSUED: " + ir.invoices.get(i).getInvoiceDate() + "\t\t\t\t\t" + StandardUtils.generatePNR());
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
