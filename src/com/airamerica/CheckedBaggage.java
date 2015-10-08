
package com.airamerica;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author EPiquette
 */
public class CheckedBaggage extends Services {

    private String ticketCode;
    private int quantity;

    public CheckedBaggage(String productCode, String ticketCode, int quantity) {
        super(productCode, "SC");
        this.ticketCode = ticketCode;
        this.quantity = quantity;
    }

    public CheckedBaggage(String productCode, int quantity) {
        super(productCode, "SC");
        this.quantity = quantity;
        Scanner productFile = null;
        try {
		productFile = new Scanner(new FileReader("data/Products.dat"));
            } catch (FileNotFoundException e) {
		System.out.println("Produdcts.dat not found.");
            }
        while(productFile.hasNextLine()) {  
                String line = productFile.nextLine();
                String[] productData = line.split(";");
                if(productCode.equals(productData[0])&&"SC".equals(productData[1])){
                    this.ticketCode = productData[2];
                }
        }
    }

    public String getTicketCode() {
        return ticketCode;
    }
}
