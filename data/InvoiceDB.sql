-- tables
-- Table Address
DROP TABLE IF EXISTS Address;
CREATE TABLE Address (
    Address_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Address varchar(20)  NOT NULL,
    State varchar(15)  NOT NULL,
    ZIP varchar(11)  NOT NULL,
    City varchar(15)  NOT NULL,
    Country varchar(15)  NOT NULL,
    CONSTRAINT Address_pk PRIMARY KEY (Address_ID)
);

-- Table Airport
DROP TABLE IF EXISTS Airport;
CREATE TABLE Airport (
    Airport_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Address_ID int  NOT NULL,
    AirportCode varchar(10)  NOT NULL,
    AirportName varchar(30)  NOT NULL,
    LongDeg int  NOT NULL,
    LongMin int NOT NULL,
    LatDeg int  NOT NULL,
    LatMin int NOT NULL,
    PassengerFee decimal(3,3) NOT NULL,
    CONSTRAINT Airport_pk PRIMARY KEY (Airport_ID),
    CONSTRAINT Address_fk FOREIGN KEY Address_ID REFERENCES Address(Address_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table AwardTicket
DROP TABLE IF EXISTS AwardTicket;
CREATE TABLE AwardTicket (
    AwardTicket_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Invoice_ID int  NOT NULL,
    FlightInfo_ID int  NOT NULL,
    PointsPerMile int  NOT NULL,
    CONSTRAINT AwardTicket_pk PRIMARY KEY (AwardTicket_ID),
    CONSTRAINT Invoice_fk FOREIGN KEY Invoice_ID REFERENCES Invoice(Invoice_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT FlightInfo_fk FOREIGN KEY FlightInfo_ID REFERENCES FlightInfo(FlightInfo_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table CheckedBaggage
DROP TABLE IF EXISTS CheckedBaggage;
CREATE TABLE CheckedBaggage (
    CheckedBaggage_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Invoice_ID int  NOT NULL,
    Ticket_ID int  NOT NULL,
    Quantity int  NOT NULL,
    CONSTRAINT CheckedBaggage_pk PRIMARY KEY (CheckedBaggage_ID),
    CONSTRAINT Invoice_fk FOREIGN KEY Invoice_ID REFERENCES Invoice(Invoice_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT Ticket_fk FOREIGN KEY Ticket_ID REFERENCES Ticket(Ticket_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Customer
DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer (
    Customer_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    CustomerCode varchar(10)  NOT NULL,
    CustomerType varchar(1)  NOT NULL,
    ContactPerson_ID int  NOT NULL,
    CustomerName varchar(30)  NOT NULL,
    CustomerMiles int  NOT NULL,
    CONSTRAINT Customer_pk PRIMARY KEY (Customer_ID),
    CONSTRAINT ContactPerson_fk FOREIGN KEY ContactPerson_ID REFERENCES Person(ContactPerson_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Email
DROP TABLE IF EXISTS Email;
CREATE TABLE Email (
    Person_ID int  NOT NULL,
    Email_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Email varchar(30)  NOT NULL,
    CONSTRAINT Email_pk PRIMARY KEY (Email_ID),
    CONSTRAINT Person_fk FOREIGN KEY Person_ID REFERENCES Person(Person_ID)
        ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table FlightInfo
DROP TABLE IF EXISTS FlightInfo;
CREATE TABLE FlightInfo (
    FlightInfo_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Ticket_ID int  NOT NULL,
    FlightCode varchar(10)  NOT NULL,
    DepAirport_ID int  NOT NULL,
    DepartureTime varchar(8)  NOT NULL,
    ArrAirport_ID int  NOT NULL,
    FlightClass varchar(2) NOT NULL,
    ArrivalTime varchar(8) NOT NULL,
    CONSTRAINT FlightInfo_pk PRIMARY KEY (FlightInfo_ID),
    CONSTRAINT Ticket_fk FOREIGN KEY Ticket_ID REFERENCES Ticket(Ticket_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT DepAirport_fk FOREIGN KEY DepAirport_ID REFERENCES Airport(DepAirport_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT ArrAirport_fk FOREIGN KEY ArrAirport_ID REFERENCES Airport(ArrAirport_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Insurance
DROP TABLE IF EXISTS Insurance;
CREATE TABLE Insurance (
    Insurance_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Invoice_ID int  NOT NULL,
    Ticket_ID int  NOT NULL,
    Name varchar(10)  NOT NULL,
    AgeClass varchar(10)  NOT NULL,
    CostPerMile decimal(3,3)  NOT NULL,
    Quantity int  NOT NULL,
    CONSTRAINT Insurance_pk PRIMARY KEY (Insurance_ID),
    CONSTRAINT Invoice_fk FOREIGN KEY Invoice_ID REFERENCES Invoice(Invoice_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT Ticket_fk FOREIGN KEY Ticket_ID REFERENCES Ticket(Ticket_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Invoice
DROP TABLE IF EXISTS Invoice;
CREATE TABLE Invoice (
    Invoice_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Customer_ID int  NOT NULL,
    SalesPerson_ID int  NOT NULL,
    InvoiceDate date  NOT NULL,
    InvoiceCode varchar(10)  NOT NULL,
    CONSTRAINT Invoice_pk PRIMARY KEY (Invoice_ID),
    CONSTRAINT Customer_fk FOREIGN KEY Customer_ID REFERENCES Customer(Customer_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT SalesPerson_fk FOREIGN KEY SalesPerson_ID REFERENCES Person(SalesPerson_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table OffseasonTicket
DROP TABLE IF EXISTS OffseasonTicket;
CREATE TABLE OffseasonTicket (
    OffseasonTicket_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Invoice_ID int  NOT NULL,
    FlightInfo_ID int  NOT NULL,
    SeasonStartDate varchar(8)  NOT NULL,
    SeasonEndDate varchar(8)  NOT NULL,
    Rebate decimal(3,3)  NOT NULL,
    CONSTRAINT OffseasonTicket_pk PRIMARY KEY (OffseasonTicket_ID),
    CONSTRAINT Invoice_fk FOREIGN KEY Invoice_ID REFERENCES Invoice(Invoice_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT FlightInfo_fk FOREIGN KEY FlightInfo_ID REFERENCES FlightInfo(FlightInfo_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Passenger
DROP TABLE IF EXISTS Passenger;
CREATE TABLE Passenger (
    Passenger_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Ticket_ID int  NOT NULL,
    Person_ID int  NOT NULL,
    Nationality varchar(15)  NOT NULL,
    Age int  NOT NULL,
    CONSTRAINT Passenger_pk PRIMARY KEY (Passenger_ID),
    CONSTRAINT Ticket_fk FOREIGN KEY Ticket_ID REFERENCES Ticket(Ticket_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT Person_fk FOREIGN KEY Person_ID REFERENCES Person(Person_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Person
DROP TABLE IF EXISTS Person;
CREATE TABLE Person (
    Person_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Email_ID int  NOT NULL,
    Address_ID int  NOT NULL,
    PersonCode varchar(10)  NOT NULL,
    PersonFirstName varchar(10)  NOT NULL,
    PersonLastName varchar(10)  NOT NULL,
    PhoneNumber varchar(15)  NOT NULL,
    CONSTRAINT Person_pk PRIMARY KEY (Person_ID),
    CONSTRAINT Email_fk FOREIGN KEY Email_ID REFERENCES Email(Email_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT Address_fk FOREIGN KEY Address_ID REFERENCES Address(Address_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Refreshment
DROP TABLE IF EXISTS Refreshment;
CREATE TABLE Refreshment (
    Invoice_ID int  NOT NULL,
    Refreshment_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Name varchar(10)  NOT NULL,
    Cost decimal(3,3)  NOT NULL,
    Quantity int  NOT NULL,
    CONSTRAINT Refreshment_pk PRIMARY KEY (Refreshment_ID),
    CONSTRAINT Invoice_fk FOREIGN KEY Invoice_ID REFERENCES Invoice(Invoice_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table SpecialAssistance
DROP TABLE IF EXISTS SpecialAssistance;
CREATE TABLE SpecialAssistance (
    SpecialAssistance_ID int  NOT NULL UNIQUE  AUTO_INCREMENT,
    Invoice_ID int  NOT NULL,
    TypeOfService varchar(15)  NOT NULL,
    CONSTRAINT SpecialAssistance_pk PRIMARY KEY (SpecialAssistance_ID),
    CONSTRAINT Invoice_fk FOREIGN KEY Invoice_ID REFERENCES Invoice(Invoice_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table StandardTicket
DROP TABLE IF EXISTS StandardTicket;
CREATE TABLE StandardTicket (
    StandardTicket_ID int  NOT NULL UNIQUE AUTO_INCREMENT,
    Invoice_ID int  NOT NULL,
    FlightInfo_ID int  NOT NULL,
    CONSTRAINT StandardTicket_pk PRIMARY KEY (StandardTicket_ID),
    CONSTRAINT Invoice_fk FOREIGN KEY Invoice_ID REFERENCES Invoice(Invoice_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT FlightInfo_fk FOREIGN KEY FlightInfo_ID REFERENCES FlightInfo(FlightInfo_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);
