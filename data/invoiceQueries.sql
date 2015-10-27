/*2.*/	
INSERT INTO Email VALUES(1234, lpage@gmail.com);
/*3.*/
UPDATE Airport JOIN Address ON Airport.address_ID = Address.Address_ID SET Address = '54 Wildcress Ave' AND City = 'Paris' AND State = 'Texas' AND ZIP = '59019' AND Country = 'United States' WHERE Airport_ID = 'HOC';
/*4.*/
DELETE FROM Airport WHERE Airport_ID = 'HOC';
/*5.*/
SELECT * FROM CheckedBaggage JOIN AwardTicket JOIN Refreshment JOIN SpecialAssistance JOIN Insurance JOIN OffseasonTicket JOIN StandardTicket ON CheckedBaggage.Invoice_ID = AwardTicket.Invoice_ID AND AwardTicket.Invoice_ID = Refreshment.Invoice_ID AND Refreshment.Invoice_ID = SpecialAssistance.Invoice_ID AND SpecialAssistance.Invoice_ID = Insurance.Invoice_ID AND Insurance.Invoice_ID = OffseasonTicket.Invoice_ID AND OffseasonTicket.Invoice_ID = StandardTicket.Invoice_ID WHERE Invoice_ID = 0;
/*6.*/
SELECT * FROM Invoices WHERE Customer_ID = 7;
/*7.*/
INSERT INTO OffseasonTickets values(0, 1, 2, '20140612', '20150520', 4.3);
/*8.*/
SELECT SUM(PointsPerMile) FROM AwardTicket;
/*9.*/
SELECT COUNT(*) FROM AwardTickets WHERE Date = '2013/04/16';
/*10.*/
SELECT PersonLastName, PersonFirstName, Address, City, State, PhoneNumber, Email, Nationality, Age, PersonCode FROM Person JOIN Passenger JOIN Email JOIN FlightInfo ON Person.Person_ID = Passenger.Person_ID AND Person.Person_ID = Email.Person_ID AND Passenger.Ticket_ID = FlightInfo.Ticket_ID WHERE FlightCode = 'ABCDEF' AND DepartureCity = 'Milwaukee' AND DepartureTime = '07:15';
/*14.*/
SELECT * FROM Invoice WHERE Invoice_ID = (SELECT Invoice_ID FROM FlightInfo JOIN Invoice JOIN AwardTicket ON AwardTicket.Invoice_ID = Invoice.Invoice_ID AND FlightInfo.Ticket_ID = AwardTicket.AwardTicket_ID HAVING COUNT(FlightCode)>1) OR Invoice_ID = (SELECT Invoice_ID FROM FlightInfo JOIN Invoice JOIN StandardTicket ON StandardTicket.Invoice_ID = Invoice.Invoice_ID AND FlightInfo.Ticket_ID = StandardTicket.StandardTicket_ID HAVING COUNT(FlightCode)>1) OR Invoice_ID = (SELECT Invoice_ID FROM FlightInfo JOIN Invoice JOIN OffseasonTicket ON OffseasonTicket.Invoice_ID = Invoice.Invoice_ID AND FlightInfo.Ticket_ID = OffseasonTicket.OffseasonTicket_ID HAVING COUNT(FlightCode)>1);
