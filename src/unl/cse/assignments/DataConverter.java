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
		
		try{
			personsFile = new Scanner(new FileReader("data/persons.dat"));
			airportsFile = new Scanner(new FileReader("data/airports.dat"));
			customersFile = new Scanner(new FileReader("data/customers.dat"));
			productsFile = new Scanner(new FileReader("data/products.dat"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} finally {
			personsFile.close();
			airportsFile.close();
			customersFile.close();
			productsFile.close();
		}
		
		int personCount = 0;
		Person[] people = null;
		
		while(personsFile.hasNextLine()){
			String line = personsFile.nextLine();
			 if (line.length() <= 5) {
				people = new Person[Integer.parseInt(line)]; //will this work?
			} else {
				String[] personVals = line.split(";");
				String[] addressField = personVals[2].split(", ");	
				String[] name = personVals[1].split(", ");
				Address address = new Address(addressField[0], addressField[1], addressField[2], addressField[3], addressField[4]);
				people[personCount] = new Person(personVals[0], address, name[1], name[0]);
				personCount++;
			}
		}
		
		while(airportsFile.hasNextLine()){
			String line = airportsFile.nextLine();
		}
		
		while(customersFile.hasNextLine()){
			String line = customersFile.nextLine();
		}
		
		while(productsFile.hasNextLine()){
			String line = productsFile.nextLine();
		}
		
		/*
		 * Uncomment the following line to make this work
		 */
		//toXML();
	}

	/*
	 * An example of using XStream API It exports to "data/Person-example.xml"
	 * NOTE: It may be interesting to note how compositions (and relationships
	 * are exported. NOTE: Pay attention how to alias various properties of an
	 * object.
	 */
	public static void toXML() {
		XStream xstream = new XStream();

		Address address1 = new Address("Street1", "City1");
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
