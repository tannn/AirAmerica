-- Query 1
SELECT Person_ID, PersonCode, PersonFirstName, PersonLastName, PhoneNumber FROM Person 
JOIN Email ON Person.Person_ID = Email.Person_ID 
JOIN Address ON Person.Address_ID = Address_ID;

-- Query 2
INSERT INTO Email (Person_ID, Email) VALUES(1, 'lpage@gmail.com');

-- Query 3
UPDATE Airport JOIN Address ON Airport.Address_ID = Address.Address_ID SET Street = '54 Wildcress Ave' AND City = 'Paris' AND State = 'Texas' AND ZIP = '59019' AND Country = 'United States' WHERE Airport_ID = 1;

-- Query 4
DELETE FROM Airport WHERE Airport_ID = 1;

-- Query 5 (redo)
SELECT * FROM CheckedBaggage JOIN AwardTicket JOIN Refreshment JOIN SpecialAssistance JOIN Insurance JOIN OffseasonTicket JOIN StandardTicket ON CheckedBaggage.InvoiceID = AwardTicket.InvoiceID AND AwardTicket.InvoiceID = Refreshment.InvoiceID AND Refreshment.InvoiceID = SpecialAssistance.InvoiceID AND SpecialAssistance.InvoiceID = Insurance.InvoiceID AND Insurance.InvoiceID = OffseasonTicket.InvoiceID AND OffseasonTicket.InvoiceID = StandardTicket.InvoiceID WHERE InvoiceID = 0;

-- Query 6
SELECT * FROM Invoice WHERE Customer_ID = 1;

-- Query 7 (redo)
INSERT INTO OffseasonTickets values(0, 1, 2, '20140612', '20150520', 4.3);

-- Query 8 (redo)
SELECT SUM(PointsPerMile) FROM AwardTicket;

-- Query 9 (redo)
SELECT COUNT(AwardTicket_ID) FROM AwardTicket JOIN FlightDate ON AwardTicket_ID = Ticket_ID WHERE FlightDate = 2013-04-16;

-- Query 10 (redo)
SELECT PersonLastName, PersonFirstName, Address, City, State, PhoneNumber, Email, Nationality, Age, PersonCode FROM Person JOIN Passenger JOIN Email JOIN FlightInfo ON Person.Person_ID = Passenger.Person_ID AND Person.Person_ID = Email.Person_ID AND Passenger.Ticket_ID = FlightInfo.Ticket_ID WHERE FlightCode = 'ABCDEF' AND DepartureCity = 'Milwaukee' AND DepartureTime = '07:15';

-- Query 11 (redo)
SELECT 
(SELECT COUNT(ArrAirport_ID) FROM FlightInfo WHERE Airport_ID = 3) +
(SELECT COUNT(DepAirport_ID) FROM FlightInfo WHERE Airport_ID = 3) 
AS NumberOfInvoices, SUM(PassengerFee) FROM Airport WHERE Airport_ID = 3;

-- Query 12 (redo)
SELECT SUM(Cost) FROM Refreshment WHERE Invoice_ID = (SELECT Invoice_ID FROM Invoice WHERE InvoiceDate = 2015-08-03);

-- Query 13 (redo)
SELECT
(SELECT Count(CheckedBaggage.Invoice_ID) AS CheckedBaggageQuantity FROM CheckedBaggage),
(SELECT COUNT(Insurance.Invoice_ID) AS InsuranceCount FROM Insurance), 
(SELECT COUNT(SpecialAssistance.Invoice_ID) AS SpecialAssistanceCount FROM SpecialAssistance), 
(SELECT COUNT(Refreshment.Invoice_ID) As RefreshmentCount FROM Refreshment)

-- Query 14 (redo)
SELECT * FROM Invoice WHERE Invoice_ID = (SELECT Invoice_ID FROM FlightInfo JOIN Invoice JOIN AwardTicket ON AwardTicket.Invoice_ID = Invoice.Invoice_ID AND FlightInfo.Ticket_ID = AwardTicket.AwardTicket_ID HAVING COUNT(FlightCode)>1) OR Invoice_ID = (SELECT Invoice_ID FROM FlightInfo JOIN Invoice JOIN StandardTicket ON StandardTicket.Invoice_ID = Invoice.Invoice_ID AND FlightInfo.Ticket_ID = StandardTicket.StandardTicket_ID HAVING COUNT(FlightCode)>1) OR Invoice_ID = (SELECT Invoice_ID FROM FlightInfo JOIN Invoice JOIN OffseasonTicket ON OffseasonTicket.Invoice_ID = Invoice.Invoice_ID AND FlightInfo.Ticket_ID = OffseasonTicket.OffseasonTicket_ID HAVING COUNT(FlightCode)>1);

-- Query 15 (redo)
SELECT Invoice_ID FROM Invoice 
WHERE SalesPerson_ID = Customer.ContactPerson_ID 
JOIN Customer ON Invoice.Customer_ID = Customer.Customer_ID;


-- Test Data
INSERT INTO Person (PersonCode, Email_ID, Address_ID, PersonFirstName, PersonLastName, PersonPhoneNumber)
VALUES ('m29e', 1, 1, 'Michael', 'Jordan', 402-949-9284);
INSERT INTO Customer (CustomerCode, CustomerType, ContactPerson_ID, CustomerName, CustomerMiles)
VALUES ('20me', 'V', (SELECT Person_ID FROM Person WHERE PersonFirstName = 'Michael'), 'Apple', 84450);
INSERT INTO Email (Person_ID, Email)
VALUES ((SELECT Person_ID FROM Person WHERE PersonFirstName = 'Michael'), 'michael@jordan.com';
INSERT INTO Address (Address, State, ZIP, City, Country)
VALUES ('3094 Michael Blvd.', 'NE', '68508', 'Lincoln', 'USA');
INSERT INTO Airport (Address_ID, AiportCode, AirportName, LongDeg, LongMin, LatDeg, LatMin, PassengerFee)
VALUES (1, '3mrf', 'Washington Airport', 34, 45, 46, 45, 34.34);
INSERT INTO Invoice (Customer_ID, SalesPerson_ID, InvoiceDate, InvoiceCode)
VALUES (1, 1, 2015-02-30, '0293m');
INSERT INTO StandardTicket (Invoice_ID, FlightInfo_ID)
VALUES (1, 1);
INSERT INTO FlightInfo (Ticket_ID, FlightCode, DepartureTime, ArrAirport_ID, ArrivalTime, FlightClass, DepAirport_ID, FlightDate)
VALUES (1, '20me', 11:29:23, 1, 03:03:02, 'EC', 1, 2015-03-92);
