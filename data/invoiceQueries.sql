-- Random data needed to make queries work
INSERT INTO Address values(1,'42 Wallaby Way', 'Victoria', '30349', 'Sydney', 'Australia');
INSERT INTO Address values(2,'24 Hollywood Blvd', 'California', '90210', 'Los Angeles', 'United States');
INSERT INTO Airport values(1, 1, 'BLT', 'Mukarkum Air', 1, 2, 3, 4, 1.2);
INSERT INTO Airport values(2, 2, 'MLT', 'Kumarkum Air', 4, 3, 2, 1, 2.1);
INSERT INTO Person(Person_ID, Address_ID, PersonCode, FirstName, LastName, PhoneNumber) values(2, 1, 'AZ', 'Annie', 'Zeya', '555-350-0864');

-- Query 1
SELECT Person.Person_ID, Person.PersonCode, Person.FirstName, Person.LastName, Person.PhoneNumber FROM Person 
JOIN Email ON Person.Person_ID = Email.Person_ID 
JOIN Address ON Person.Address_ID = Address.Address_ID;

-- Query 2
INSERT INTO Email (Person_ID, Email) VALUES(2, 'lpage@gmail.com');

-- Query 3
UPDATE Airport JOIN Address ON Airport.Address_ID = Address.Address_ID SET Address = '54 Wildcress Ave' AND City = 'Paris' AND StateProvince = 'Texas' AND ZIP = '59019' AND Country = 'United States' WHERE Airport_ID = 1;

-- Query 4
DELETE FROM Airport WHERE Airport_ID = 1;

-- Query 5
SELECT * FROM Product JOIN InvoiceProduct ON Product.Product_ID=InvoiceProduct.Product_ID WHERE Invoice_ID = 0;

-- Query 6
SELECT * FROM InvoiceProduct WHERE Passenger_ID = 1;

-- Query 7 (NULL data!)
INSERT INTO Product (Product_ID, ArrAirport_ID, DepAirport_ID, ProductType, ProductPrintName, Cost, DepartureTime, PlaneName, ArrivalTime, FlightClass, FlightCode) values(1, 1, 2, 'TS', 'Ticket', 4.3, '07:40', 'Janice', '09:10', 'FC', '16904A2');

-- Query 8
SELECT SUM(ATPointsPerMile) FROM Product;

-- Query 9
SELECT COUNT(*) FROM InvoiceProduct JOIN Product ON InvoiceProduct.Product_ID = Product.Product_ID WHERE FlightDate = 2013-04-16 AND ProductType = 'TA';

-- Query 10
SELECT LastName, FirstName, Address, City, StateProvince, PhoneNumber, Email, Nationality, Age, PersonCode FROM Person JOIN Passenger JOIN Email JOIN Address JOIN Product JOIN Airport ON Person.Person_ID = Passenger.Person_ID AND Person.Person_ID = Email.Person_ID AND Person.Address_ID = Address.Address_ID AND Product.Product_ID = Passenger.Product_ID AND Airport.Airport_ID = Product.DepAirport_ID AND Address.Address_ID = Airport.Address_ID WHERE FlightCode = 'ABCDEF' AND City = 'Milwaukee' AND DepartureTime = '7:15:00';

-- Query 11
SELECT 
(SELECT COUNT(ArrAirport_ID) FROM Product WHERE Airport_ID = 3) +
(SELECT COUNT(DepAirport_ID) FROM Product WHERE Airport_ID = 3) 
AS NumberOfInvoices, SUM(PassengerFee) FROM Airport WHERE Airport_ID = 3;

-- Query 12
SELECT SUM(Product.Cost) AS 'Total Cost' FROM Product JOIN InvoiceProduct ON Product.Product_ID = InvoiceProduct.Product_ID WHERE Invoice_ID = (SELECT Invoice_ID FROM Invoice WHERE InvoiceDate = 2015-08-03) AND ProductType = 'SR';

-- Query 13
SELECT
(SELECT Count(Product_ID) AS CheckedBaggageQuantity FROM Product WHERE ProductType = 'SC'),
(SELECT COUNT(Product_ID) AS InsuranceCount FROM Product WHERE ProductType = 'SI'), 
(SELECT COUNT(Product_ID) AS SpecialAssistanceCount FROM Product WHERE ProductType = 'SS'), 
(SELECT COUNT(Product_ID) As RefreshmentCount FROM Product WHERE ProductType = 'SR');

-- Query 14
SELECT * FROM Invoice WHERE Invoice_ID = (SELECT Invoice_ID FROM InvoiceProduct JOIN Product ON InvoiceProduct.Product_ID = Product.Product_ID HAVING COUNT(FlightCode)>1);

-- Query 15
SELECT Invoice_ID FROM Invoice JOIN Customer ON Invoice.Customer_ID = Customer.Customer_ID WHERE ContactPerson_ID = SalesPerson_ID;
