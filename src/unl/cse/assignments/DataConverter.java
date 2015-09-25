package unl.cse.assignments;

/* Phase-I */
import com.airamerica.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

// Include imports for XML/JSON libraries if needed
import com.thoughtworks.xstream.XStream;

public class DataConverter {

                static int personCount = 0;
		static Person[] people = null;
                
                static int airportCount = 0;
		static Airport[] airports = null;
                
                static int productCount = 0;
		static Product[] products = null;
                
                static int customerCount = 0;
		static Customer[] customers = null;
    
	public static void main(String args[]) {

		Scanner personsFile = null;
		Scanner airportsFile = null;
		Scanner customersFile = null;
		Scanner productsFile = null;

		try {
			personsFile = new Scanner(new FileReader("data/persons.dat"));
			airportsFile = new Scanner(new FileReader("data/airports.dat"));
			customersFile = new Scanner(new FileReader("data/customers.dat"));
			productsFile = new Scanner(new FileReader("data/products.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

		while (personsFile.hasNextLine()) {
			String line = personsFile.nextLine();
			if (line.length() <= 5 && line.length() > 0) 
				people = new Person[Integer.parseInt(line)];
			else {
				String[] personVals = line.split(";");
				String[] addressField = personVals[2].split(",");
				String[] name = personVals[1].split(",");
				Address address = new Address(addressField[0], addressField[1], addressField[2], addressField[3],
						addressField[4]);
				people[personCount] = new Person(personVals[0], address, name[1], name[0]);

				if (personVals.length >= 4) {
					for (int i = 3; i < personVals.length; i++) {
						if (personVals[i].contains("-"))
							people[personCount].addPhoneNumber(personVals[i]);
						else if (personVals[i].contains("@")) {
							people[personCount].addEmail(personVals[i]);
						}
					}
				}
				personCount++;
			}
		}

		

		while (airportsFile.hasNextLine()) {
			String line = airportsFile.nextLine();
			if (line.length() <= 5 && line.length() > 0)
				airports = new Airport[Integer.parseInt(line)];
			else {
				String[] airportVals = line.split(";");
				String[] addressVals = airportVals[2].split(",");
				Address address = new Address(addressVals[0], addressVals[1], addressVals[2], addressVals[3], addressVals[4]);
				String[] longlat = airportVals[3].split(",");
				airports[airportCount] = new Airport(airportVals[0], airportVals[1], address, Integer.parseInt(longlat[0]), Integer.parseInt(longlat[1]), Integer.parseInt(longlat[2]), Integer.parseInt(longlat[3]), Float.parseFloat(airportVals[4]));
				airportCount++;
			}
		}

		

		while (customersFile.hasNextLine()) {
			String line = customersFile.nextLine();
			if (line.length() <= 5 && line.length() > 0)
				customers = new Customer[Integer.parseInt(line)];
			else {
				String[] customerVals = line.split(";");
				customers[customerCount] = new Customer(customerVals[0], customerVals[1], customerVals[2], customerVals[3]);
				if (customerVals.length > 4) 
					customers[customerCount].setAirlineMiles(customerVals[4]);
				customerCount++;
			}
		}


		while (productsFile.hasNextLine()) {
			String line = productsFile.nextLine();
			if (line.length() <= 5 && line.length() > 0)
				products = new Product[Integer.parseInt(line)];
			else {
				String[] productVals = line.split(";");
				if (productVals[1].equals("TS"))
					products[productCount] = new Ticket(productVals[0], productVals[1], productVals[2], productVals[3], productVals[4], productVals[5], productVals[6], productVals[7], productVals[8]);
				else if (productVals[1].equals("TO"))
					products[productCount] = new OffseasonTicket(productVals[0], productVals[1], productVals[2], productVals[3], productVals[4], productVals[5], productVals[6], productVals[7], productVals[8], productVals[9], productVals[10], productVals[11]);
				else if (productVals[1].equals("TA"))
					products[productCount] = new AwardTicket(productVals[0], productVals[1], productVals[2], productVals[3], productVals[4], productVals[5], productVals[6], productVals[7], productVals[8], productVals[9]);
				else if (productVals[1].equals("SC"))
					products[productCount] = new CheckedBaggage(productVals[0], productVals[1], productVals[2]);
				else if (productVals[1].equals("SI"))
					products[productCount] = new Insurance(productVals[0], productVals[1], productVals[2], productVals[3], productVals[4]);
				else if (productVals[1].equals("SR"))
					products[productCount] = new Refreshments(productVals[0], productVals[1], productVals[2], productVals[3]);
				else if (productVals[1].equals("SS"))
					products[productCount] = new SpecialAssistance(productVals[0], productVals[1], productVals[2]);
				productCount++;
			}
		}


		/*
		 * Uncomment the following line to make this work
		 */
		// toXML();
		 output();

		personsFile.close();
		airportsFile.close();
		customersFile.close();
		productsFile.close();

	}

	public static void output() {
            
                XStream xstream = new XStream();
		
//                persons output
		xstream.alias("person", Person.class);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("data/Persons.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		pw.print("<?xml version=\"1.0\"?>" + "\n");
		pw.print("<persons>\n");
                for(Person p : people)
                {
                    pw.print("\t" + xstream.toXML(p) + "\n");
                }
		pw.print("</persons>" + "\n");
		pw.close();

                
//                airports output
		xstream.alias("airport", Airport.class);
		pw = null;
		try {
			pw = new PrintWriter(new File("data/Airports.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		pw.print("<?xml version=\"1.0\"?>" + "\n");
		pw.print("<airports>\n");
                for(Airport a : airports)
                {
                    pw.print("\t" + xstream.toXML(a) + "\n");
                }
		pw.print("</airports>" + "\n");
		pw.close();
                
//                customers output
                xstream.alias("customer", Customer.class);
		pw = null;
		try {
			pw = new PrintWriter(new File("data/Customers.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		pw.print("<?xml version=\"1.0\"?>" + "\n");
		pw.print("<customers>\n");
                for(Customer c : customers)
                {
                    pw.print("\t" + xstream.toXML(c) + "\n");
                }
		pw.print("</customers>" + "\n");
		pw.close();
                
//                products output
                xstream.alias("product", Product.class);
		pw = null;
		try {
			pw = new PrintWriter(new File("data/Products.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		pw.print("<?xml version=\"1.0\"?>" + "\n");
		pw.print("<products>\n");
                pw.print("<tickets>\n");
                for(Product p : products)
                {
                    if(Ticket.class.isInstance(p)&&!AwardTicket.class.isInstance(p)
                            &&!OffseasonTicket.class.isInstance(p)){
                        xstream.alias("ticket", Ticket.class);
                        pw.print("\t" + xstream.toXML(p) + "\n");
                    }
                }
                pw.print("<awardTickets>\n");
                for(Product p : products)
                {
                    if(AwardTicket.class.isInstance(p)){
                        xstream.alias("awardTicket", AwardTicket.class);
                        pw.print("\t" + xstream.toXML(p) + "\n");
                    }
                }
                pw.print("</awardTickets>" + "\n");
                pw.print("<offSeasonTickets>\n");
                for(Product p : products)
                {
                    if(OffseasonTicket.class.isInstance(p)){
                        xstream.alias("offSeasonTicket", OffseasonTicket.class);
                        pw.print("\t" + xstream.toXML(p) + "\n");
                    }
                }
                pw.print("</offSeasonTickets>" + "\n");
                pw.print("</tickets>" + "\n");
                pw.print("<services>\n");
                pw.print("<checkedBaggage>\n");
                for(Product p : products)
                {
                    if(CheckedBaggage.class.isInstance(p)){
                        xstream.alias("checkedBaggageService", CheckedBaggage.class);
                        pw.print("\t" + xstream.toXML(p) + "\n");
                    }   
                }
                pw.print("</checkedBaggage>" + "\n");
                pw.print("<insurance>\n");
                for(Product p : products)
                {
                    if(Insurance.class.isInstance(p)){
                        xstream.alias("insuranceService", Insurance.class);
                        pw.print("\t" + xstream.toXML(p) + "\n");
                    }   
                }
                pw.print("</insurance>" + "\n");
                pw.print("<refreshments>\n");
                for(Product p : products)
                {
                    if(Refreshments.class.isInstance(p)){
                        xstream.alias("refreshmentsService", Refreshments.class);
                        pw.print("\t" + xstream.toXML(p) + "\n");
                    }   
                }
                pw.print("</refreshments>" + "\n");
                pw.print("<specialAssistance>\n");
                for(Product p : products)
                {
                    if(SpecialAssistance.class.isInstance(p)){
                        xstream.alias("specialAssistanceService", SpecialAssistance.class);
                        pw.print("\t" + xstream.toXML(p) + "\n");
                    }   
                }
                pw.print("</specialAssistance>" + "\n");
                pw.print("</services>" + "\n");
		pw.print("</products>" + "\n");
		pw.close();

	}

}
