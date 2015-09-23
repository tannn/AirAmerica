package com.airamerica;

public class CheckedBaggage extends Services{

	private String ticketCode;

	public CheckedBaggage(String productCode, String productType, String ticketCode) {
		super(productCode, productType);
		this.ticketCode = ticketCode;
	}

	public String getTicketCode() {
		return ticketCode;
	}

}