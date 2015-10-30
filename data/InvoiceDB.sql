-- Drop tables
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS Airport;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Invoice;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS InvoiceProduct;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Email;
DROP TABLE IF EXISTS Passenger;

-- tables
-- Table Address
CREATE TABLE Address (
    Address_ID int  NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    Address varchar(20)  NOT NULL,
    StateProvince varchar(15)  NOT NULL,
    ZIP varchar(11)  NOT NULL,
    City varchar(15)  NOT NULL,
    Country varchar(15)  NOT NULL
);

-- Table Airport
CREATE TABLE Airport (
    Airport_ID int  NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    Address_ID int  NOT NULL,
    AirportCode varchar(10)  NOT NULL,
    AirportName varchar(30)  NOT NULL,
    LongDeg int  NOT NULL,
    LongMin int NOT NULL,
    LatDeg int  NOT NULL,
    LatMin int NOT NULL,
    PassengerFee decimal(3,3) NOT NULL,
    -- Foreign Keys
    FOREIGN KEY `Address_fk` (Address_ID) REFERENCES Address(Address_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Customer
CREATE TABLE Customer (
    Customer_ID int  NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    CustomerCode varchar(10)  NOT NULL,
    CustomerType varchar(1)  NOT NULL,
    ContactPerson_ID int  NOT NULL,
    CustomerName varchar(30)  NOT NULL,
    CustomerMiles int  NOT NULL
);

-- Table Invoice
CREATE TABLE Invoice (
    Invoice_ID int  NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    Customer_ID int  NOT NULL,
    SalesPerson_ID int  NOT NULL,
    InvoiceDate date  NOT NULL,
    InvoiceCode varchar(10)  NOT NULL,
    -- Foreign Keys
    CONSTRAINT Customer_fk FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Product
CREATE TABLE Product (
    Product_ID int UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ArrAirport_ID int NULL,
    DepAirport_ID int NULL,
    ProductType varchar(2) NOT NULL,
    ATPointsPerMile int  NULL,
    OTSeasonStartDate varchar(8)  NULL,
    OTSeasonEndDate varchar(8)  NULL,
    OTRebate decimal(3,3)  NULL,
    ProductPrintName varchar(15)  NULL,
    InsuranceAgeClass varchar(10)  NULL,
    InsuranceCostPerMile decimal(3,3)  NULL,
    SATypeOfService varchar(15)  NULL,
    Cost decimal(3,3)  NULL,
    DepartureTime time  NULL,
    PlaneName varchar(15) NULL,
    ArrivalTime time NULL,
    FlightClass varchar(2)  NULL,
    FlightCode varchar(10)  NULL
    -- Foreign Keys
);

-- Table InvoiceProduct
CREATE TABLE InvoiceProduct (
    InvoiceProduct_ID int UNIQUE AUTO_INCREMENT NOT NULL PRIMARY KEY,
    Invoice_ID int  NOT NULL,
    Product_ID int  NOT NULL,
    Passenger_ID int NULL,
    Quantity int  NULL,
    FlightDate date  NULL,
    Cost decimal(3,3)  NULL,
    -- Foreign Keys
    CONSTRAINT InvoiceProduct_fk FOREIGN KEY (Product_ID) REFERENCES Product(Product_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE,
    CONSTRAINT Invoice_fk FOREIGN KEY (Invoice_ID) REFERENCES Invoice(Invoice_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Person
CREATE TABLE Person (
    Person_ID int  NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    Email_ID int  NOT NULL,
    Address_ID int  NOT NULL,
    PersonCode varchar(10)  NOT NULL,
    FirstName varchar(10)  NOT NULL,
    LastName varchar(10)  NOT NULL,
    PhoneNumber varchar(15)  NOT NULL
    -- Foreign Keys
);

-- Table Email
CREATE TABLE Email (
    Email_ID int  NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    Person_ID int  NOT NULL,
    Email varchar(30)  NOT NULL,
    CONSTRAINT EmailPerson_fk FOREIGN KEY (Person_ID) REFERENCES Person(Person_ID)
    	ON UPDATE CASCADE
    	ON DELETE CASCADE
);

-- Table Passenger
CREATE TABLE Passenger (
    Passenger_ID int  NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    Product_ID int  NOT NULL,
    Person_ID int  NOT NULL,
    Nationality varchar(15)  NOT NULL,
    Age int  NOT NULL,
    SeatNumber varchar(4) NOT NULL
    -- Foreign Keys
);


ALTER TABLE Customer ADD CONSTRAINT ContactPerson_fk FOREIGN KEY (ContactPerson_ID) REFERENCES Person(Person_ID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Invoice ADD CONSTRAINT SalesPerson_fk FOREIGN KEY (SalesPerson_ID) REFERENCES Person(Person_ID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE InvoiceProduct ADD CONSTRAINT Passenger_fk FOREIGN KEY (Passenger_ID) REFERENCES Passenger(Passenger_ID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Product ADD CONSTRAINT ArrAirport_fk FOREIGN KEY (ArrAirport_ID) REFERENCES Airport(Airport_ID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Product ADD CONSTRAINT DepAirport_fk FOREIGN KEY (DepAirport_ID) REFERENCES Airport(Airport_ID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Person ADD CONSTRAINT Email_fk FOREIGN KEY (Email_ID) REFERENCES Email(Email_ID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Person ADD CONSTRAINT Address_fk FOREIGN KEY (Address_ID) REFERENCES Address(Address_ID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Passenger ADD CONSTRAINT Product_fk FOREIGN KEY (Product_ID) REFERENCES Product(Product_ID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Passenger ADD CONSTRAINT Person_fk FOREIGN KEY (Person_ID) REFERENCES Person(Person_ID) ON UPDATE CASCADE ON DELETE CASCADE;
