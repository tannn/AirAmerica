package unl.cse.assignments;

/* Assignment 3,5 and 6 (Project Phase-II,IV and V) */

//bonus: output in sorted last, first format

public class InvoiceReport {
	
	private String generateSummaryReport() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Executive Summary Report\n");
		sb.append("=========================\n");
		sb.append("Invoice\tCustomer\t\t\t\t\t\t\t\t\tSalesperson\t\tSubtotal\tFees\tTaxes\tDiscount\tTotal");
		//TODO: Add code for generating summary of all Invoices
		//for-loop through all invoices
		//for each invoice, do: invoice, customer, salesperson, subtotal, fees, taxes, discount, total
		sb.append("=====================================================================================================================================================");
		sb.append("TOTALS\t\t\t\t\t\t\t\t\t\t\t\t\t");

		return sb.toString();
	}
	

	private String getTravelSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append("FLIGHT INFORMATION");
		sb.append("==================================================\n");
		sb.append("DATE\t\tFLIGHT\tCLASS\tDEPARTURE CITY AND TIME\tARRIVAL CITY AND TIME\t\tAIRCRAFT");

		//TODO: Add code for generating Travel Information of an Invoice
		
		return sb.toString();
		
	}
	
	private String getCostSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append("FARES AND SERVICES");
		sb.append("==================================================\n");
		sb.append("Code\tItem\t\t\t\t\t\t\t\t\tSubTotal\tTax\tTotal\n");

		//TODO: Add code for generating Cost Summary of all 
		//products and services in an Invoice
		
		return sb.toString();
		
	}

	public String generateDetailReport() {
	StringBuilder sb = new StringBuilder();		
	sb.append("Individual Invoice Detail Reports\n");
	sb.append("==================================================\n");
	
	/* TODO: Loop through all invoices and call the getTravelSummary() and 
	getCostSummary() for each invoice*/
	//for-loop through all invoices
	//for each invoice, do:
	//
	
	
	return sb.toString();
	}

	public static void main(String args[]) {
		
		InvoiceReport ir = new InvoiceReport();
		String summary = ir.generateSummaryReport();
		String details = ir.generateDetailReport();
				
		System.out.println(summary);
		System.out.println("\n\n");
		System.out.println(details);
		
		System.out.println("======================================================================================================================");
		System.out.println("\n\n");
		
	}
}
