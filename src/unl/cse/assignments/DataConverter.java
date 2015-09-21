package unl.cse.assignments;

/* Phase-I */
import com.airamerica.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

// Include imports for XML/JSON libraries if needed
import com.thoughtworks.xstream.XStream;

public class DataConverter {

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

		int personCount = 0;
		Person[] people = null;

		while (personsFile.hasNextLine()) {
			String line = personsFile.nextLine();
			if (line.length() <= 5 && line.length() > 0) {
				people = new Person[Integer.parseInt(line)];
			} else {
				String[] personVals = line.split(";");
				String[] addressField = personVals[2].split(",");
				String[] name = personVals[1].split(",");
				Address address = new Address(addressField[0], addressField[1], addressField[2], addressField[3],
						addressField[4]);
				people[personCount] = new Person(personVals[0], address, name[1], name[0]);

				if (personVals.length >= 4) {
					for (int i = 3; i < personVals.length; i++) {
						if (personVals[i].contains("-")) {
							people[personCount].addPhoneNumber(personVals[i]);
						} else if (personVals[i].contains("@")) {
							people[personCount].addEmail(personVals[i]);
						}
					}
				}

				personCount++;
			}
		}

		int airportCount = 0;
		Airport[] airports = null;

		while (airportsFile.hasNextLine()) {
			String line = airportsFile.nextLine();
			if (line.length() <= 5 && line.length() > 0)
				airports = new Airport[Integer.parseInt(line)];
			else {
				String[] airportVals = line.split(";");
				String[] addressVals = airportVals[2].split(";");
				Address address = new Address(addressVals[0], addressVals[1], addressVals[2], addressVals[3], addressVals[4]);
				
				String[] longlat = airportVals[3].split(",");
				
				airports[airportCount] = new Airport(airportVals[0], airportVals[1], address, Integer.parseInt(longlat[0]), Integer.parseInt(longlat[1]), Integer.parseInt(longlat[2]), Integer.parseInt(longlat[3]), Integer.parseInt(airportVals[4]));
				
				airportCount++;
			}
		}

		int customerCount = 0;
		Customer[] customers = null;

		while (customersFile.hasNextLine()) {
			String line = customersFile.nextLine();
			if (line.length() <= 5 && line.length() > 0)
				customers = new Customer[Integer.parseInt(line)];
			else {
				String[] customerVals = line.split(";");
				customers[customerCount] = new Customer(customerVals[0], customerVals[1], customerVals[2], customerVals[3]);
				
				if (customerVals.length > 4) {
					customers[customerCount].setAirlineMiles(customerVals[4]);
				}
				
				customerCount++;
			}
		}

		int productCount = 0;
		Product[] products = null;

		while (productsFile.hasNextLine()) {
			String line = productsFile.nextLine();
			if (line.length() <= 5 && line.length() > 0)
				products = new Product[Integer.parseInt(line)];
			else {
				productCount++;
			}
		}

		/*
		 * Uncomment the following line to make this work
		 */
		// toXML();
		// output();

		personsFile.close();
		airportsFile.close();
		customersFile.close();
		productsFile.close();

	}

	public static void output() {

	}

	/*
	 * An example of using XStream API It exports to "data/Person-example.xml"
	 * NOTE: It may be interesting to note how compositions (and relationships
	 * are exported. NOTE: Pay attention how to alias various properties of an
	 * object.
	 */

	public static void toXML() {
		XStream xstream = new XStream();

		Address address1 = new Address("Street1", "City1, State1, Zip1, Country1");
		Person p1 = new Person("PersonCode1", address1);
		p1.addEmail("Email1");
		p1.addEmail("Email2");
		Person p2 = new Person("PersonCode2", address1);
		p2.addEmail("Email3");
		p2.addEmail("Email4");
		xstream.alias("person", Person.class);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("data/Person-example.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		pw.print("<persons>\n");
		pw.print(xstream.toXML(p1) + "\n");
		pw.print(xstream.toXML(p2) + "\n");
		pw.print("</persons>" + "\n");
		pw.close();

		System.out.println("XML generated at 'data/Person-example.xml'");
	}
}
