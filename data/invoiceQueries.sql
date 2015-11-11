-- Test Data
INSERT INTO Person (PersonCode, FirstName, LastName, PhoneNumber)
VALUES ('m29e', 'Michael', 'Jordan', '402-949-9284');
INSERT INTO Customer (CustomerCode, CustomerType, ContactPerson_ID, CustomerName, CustomerMiles)
VALUES ('20me', 'V', (SELECT Person_ID FROM Person WHERE PersonFirstName = 'Michael'), 'Apple', 84450);
INSERT INTO Email (Person_ID, Email)
VALUES (LAST_INSERT_ID(), 'michael@jordan.com');
INSERT INTO Person (Email_ID)
VALUES (LAST_INSERT_ID());
INSERT INTO Address (Address, StateProvince, ZIP, City, Country)
VALUES ('3094 Michael Blvd.', 'NE', '68508', 'Lincoln', 'USA');
VALUES INTO Address (Address, StateProvince, ZIP, City, Country)
VALUES('4501 Abbott Dr', 'NE', '68110' , 'Omaha','USA');
VALUES INTO Address (Address, StateProvince, ZIP, City, Country)
VALUES('2100 NW 42nd Ave', 'FL', '33126', 'Miami','USA');
VALUES INTO Address (Address, StateProvince, ZIP, City, Country)
VALUES('2949 Harris Street','KS','66101','Kansas City', 'USA');
VALUES INTO Address (Address, StateProvince, ZIP, City, Country)
VALUES('3094 Tiffany Road', 'Illinois', '46201', 'Indianapolis','USA');
INSERT INTO Person (Address_ID)
VALUES (LAST_INSERT_ID());
INSERT INTO Airport (Address_ID, AiportCode, AirportName, LongDeg, LongMin, LatDeg, LatMin, PassengerFee)
VALUES (1, 'WAN', 'Washington Airport', 34, 45, 46, 45, 34.34);
INSERT INTO Airport (Address_ID, AiportCode, AirportName, LongDeg, LongMin, LatDeg, LatMin, PassengerFee)
VALUES (2, 'LAM', 'Lincoln Airfield', 41, 30, 95, 89, 14.34);
INSERT INTO Invoice (Customer_ID, SalesPerson_ID, InvoiceDate, InvoiceCode)
VALUES (1, 1, 2015-02-30, '0293m');
INSERT INTO Product (Invoice_ID, ProductType, ArrAirport_ID, DepAirport_ID, ArrivalTime, DepartureTime, ProductCode, FlightClass, PlaneName)
VALUES (LAST_INSERT_ID(), 'TS', 1, 2, 10:00, 04:00, 'KJA928', 'EC', 'Airbus-A320');
INSERT INTO InvoiceProduct (Invoice_ID, Product_ID, Passenger_ID, FlightDate)
VALUES (1, LAST_INSERT_ID(), 1, 2015-03-92);
INSERT INTO Product (ProductType, ProductCode, ProductPrintName)
VALUES ('SS', 'sk39', 'PrioritySeating');
INSERT INTO Airport (Address_ID, AirportCode, AirportName, LongDeg, LongMin, LatDeg, LatMin, PassengerFee)
VALUES (3, 'LAX', 'Los Angeles International Airport', );
INSERT INTO Passenger (Product_ID, Person_ID, Nationality, Age, SeatNumber)
VALUES (1, 1, 'United States', 29, '18F');
INSERT INTO Product (ProductType, ProductPrintName, Cost, ProductCode)
VALUES ('SR', 'Italian Buffet', 13.00, '32f4');
INSERT INTO Product (Invoice_ID, ProductType, ArrAirport_ID, DepAirport_ID, ArrivalTime, DepartureTime, ProductCode, FlightClass, PlaneName, ATPointsPerMile)
VALUES (1, 'TA', 1, 2, 19:00, 9:00, 'N725', 'EP', 'Airbus-A319', 110);
INSERT INTO InvoiceProduct (Invoice_ID, Product_ID, Passenger_ID, FlightDate)
VALUES (1, 1, 1, 2016-01-08);


-- Query 1
SELECT Person.Person_ID, Person.PersonCode, Person.FirstName, Person.LastName, Person.PhoneNumber FROM Person 
JOIN Email ON Person.Person_ID = Email.Person_ID 
JOIN Address ON Person.Address_ID = Address.Address_ID;

-- Query 2
INSERT INTO Email (Person_ID, Email) VALUES(1, 'lpage@gmail.com');

-- Query 3
UPDATE Airport JOIN Address ON Airport.Address_ID = Address.Address_ID SET Street = '54 Wildcress Ave' AND City = 'Paris' AND State = 'Texas' AND ZIP = '59019' AND Country = 'United States' WHERE Airport_ID = 1;

-- Query 4
DELETE FROM Airport WHERE Airport_ID = 1;

-- Query 5 (redo)
SELECT * FROM Product JOIN InvoiceProduct ON Product.Product_ID=InvoiceProduct.Product_ID WHERE Invoice_ID = 1;

-- Query 6
SELECT * FROM InvoiceProduct WHERE Passenger_ID = 1;

-- Query 7 (redo)
INSERT INTO Product (Product_ID, ArrAirport_ID, DepAirport_ID, ProductType, ProductPrintName, Cost, DepartureTime, PlaneName, ArrivalTime, FlightClass, FlightCode) values(1, 1, 2, 'TS', 'Ticket', 4.3, '07:40', 'Janice', '09:10', 'FC', '16904A2');

-- Query 8 (redo)
SELECT SUM(ATPointsPerMile) FROM Product;

-- Query 9 (redo)
SELECT COUNT(*) FROM InvoiceProduct JOIN Product ON InvoiceProduct.Product_ID = Product.Product_ID WHERE FlightDate = 2013-04-16 AND ProductType = 'TA';

-- Query 10 (redo)
SELECT LastName, FirstName, Address, City, StateProvince, PhoneNumber, Email, Nationality, Age, PersonCode FROM Person JOIN Passenger JOIN Email JOIN Address JOIN Product JOIN Airport ON Person.Person_ID = Passenger.Person_ID AND Person.Person_ID = Email.Person_ID AND Person.Address_ID = Address.Address_ID AND Product.Product_ID = Passenger.Product_ID AND Airport.Airport_ID = Product.DepAirport_ID AND Address.Address_ID = Airport.Address_ID WHERE FlightCode = 'ABCDEF' AND City = 'Milwaukee' AND DepartureTime = '7:15:00';

-- Query 11
SELECT 
(SELECT COUNT(ArrAirport_ID) FROM Product WHERE Airport_ID = 3) +
(SELECT COUNT(DepAirport_ID) FROM Product WHERE Airport_ID = 3) 
AS NumberOfInvoices, SUM(PassengerFee) FROM Airport WHERE Airport_ID = 3;

-- Query 12 (redo)
SELECT SUM(Product.Cost) AS 'Total Cost' FROM Product JOIN InvoiceProduct ON Product.Product_ID = InvoiceProduct.Product_ID WHERE Invoice_ID = (SELECT Invoice_ID FROM Invoice WHERE InvoiceDate = 2015-08-03) AND ProductType = 'SR';

-- Query 13 (redo)
SELECT
(SELECT Count(Product_ID) AS CheckedBaggageQuantity FROM Product WHERE ProductType = 'SC'),
(SELECT COUNT(Product_ID) AS InsuranceCount FROM Product WHERE ProductType = 'SI'), 
(SELECT COUNT(Product_ID) AS SpecialAssistanceCount FROM Product WHERE ProductType = 'SS'), 
(SELECT COUNT(Product_ID) As RefreshmentCount FROM Product WHERE ProductType = 'SR');

-- Query 14 (redo)
SELECT * FROM Invoice WHERE Invoice_ID = (SELECT Invoice_ID FROM InvoiceProduct JOIN Product ON InvoiceProduct.Product_ID = Product.Product_ID HAVING COUNT(FlightCode)>1);

-- Query 15 (redo)
SELECT Invoice_ID FROM Invoice JOIN Customer ON Invoice.Customer_ID = Customer.Customer_ID WHERE ContactPerson_ID = SalesPerson_ID;
