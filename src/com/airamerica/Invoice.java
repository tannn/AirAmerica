package com.airamerica;

import com.airamerica.utils.DatabaseInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            ArrayList<Integer> productInfo) {
        this.invoiceCode = invoiceCode;
        this.customerCode = customerCode;
        this.salespersonCode = salespersonCode;
        this.invoiceDate = invoiceDate;
        products = new ArrayList<>();
        ArrayList<Person> ticketHolders = new ArrayList<>();

        for (Integer i : productInfo) {
            Connection conn = DatabaseInfo.getConnection();
            PreparedStatement ps1;
            PreparedStatement ps2;
            ResultSet rs1;
            ResultSet rs2;
            String getProductInfo = "select * from Product join InvoiceProduct join Passenger"
                    + "on Product.Product_ID = InvoiceProduct.Product_ID and "
                    + "InvoiceProduct.Passenger_ID = Passenger.Passenger_ID"
                    + "where Product_ID = ?";

            String getPersonInfo = "select PersonCode, Age, Nationality from "
                    + "Passenger join Person on Passenger.Person_ID = "
                    + "Person.Person_ID where Product_ID = ?";
            String productType = null;
            try {
                ps1 = conn.prepareStatement(getProductInfo);
                ps1.setInt(1, i);
                rs1 = ps1.executeQuery();
                rs1.next();
                productType = rs1.getString("ProductType");

                ps2 = conn.prepareStatement(getPersonInfo);
                ps2.setInt(1, i);
                rs2 = ps2.executeQuery();
                rs2.next();

                if (productType.equals("TS")) {
                    while (!rs2.isLast()) {
                        ticketHolders.add(new Person(rs2.getString("PersonCode"),
                                rs2.getInt("Age"), rs2.getString("Nationality")));
                        rs2.next();
                    }
                    products.add(new Ticket(rs1.getString("ProductCode"), "TS",
                            rs1.getString("FlightDate"), rs1.getString("SeatNumber"),
                            ticketHolders, rs1.getString("TicketNote")));
                } else if (productType.equals("TA")) {
                    while (!rs2.isLast()) {
                        ticketHolders.add(new Person(rs2.getString("PersonCode"),
                                rs2.getInt("Age"), rs2.getString("Nationality")));
                        rs2.next();
                    }
                    products.add(new AwardTicket(rs1.getString("ProductCode"),
                            rs1.getString("FlightDate"), rs1.getString("SeatNumber"),
                            ticketHolders, rs1.getString("TicketNote")));
                } else if (productType.equals("TO")) {
                    while (!rs2.isLast()) {
                        ticketHolders.add(new Person(rs2.getString("PersonCode"),
                                rs2.getInt("Age"), rs2.getString("Nationality")));
                        rs2.next();
                    }
                    products.add(new OffseasonTicket(rs1.getString("ProductCode"),
                            rs1.getString("FlightDate"), rs1.getString("SeatNumber"),
                            ticketHolders, rs1.getString("TicketNote")));
                } else if (productType.equals("SI")) {
                    products.add(new Insurance(rs1.getString("ProductCode"),rs1.getInt("Quantity"),rs1.getString("TicketCode")));
                } else if (productType.equals("SS")) {
                    products.add(new SpecialAssistance(rs1.getString("ProductCode"),rs1.getString("SATypeOfService")));
                } else if (productType.equals("SC")) {
                    products.add(new CheckedBaggage(rs1.getString("ProductCode"),rs1.getInt("Quantity")));
                } else if (productType.equals("SR")) {
                    products.add(new Refreshment(rs1.getString("ProductCode"),rs1.getInt("Quantity")));
                }

                rs2.close();
                ps2.close();
                rs1.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
            ticketHolders = new ArrayList<>();
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
