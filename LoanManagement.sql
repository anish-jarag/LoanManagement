create database loanmanagement;
use loanmanagement;

-- customer table
create table customer (
    customerid int primary key auto_increment,
    name varchar(100) not null,
    emailaddress varchar(100) unique not null,
    phonenumber varchar(15),
    address varchar(255),
    creditscore int
);

-- loan table
create table loan (
    loanid int primary key auto_increment,
    customerid int,
    principalamount double,
    interestrate double,
    loanterm int,
    loantype varchar(50),
    loanstatus enum('PENDING', 'APPROVED', 'REJECTED'),
    foreign key (customerid) references customer(customerid)
);

-- homeloan table
create table homeloan (
    loanid int primary key,
    propertyaddress varchar(255),
    propertyvalue decimal(15,2),
    foreign key (loanid) references loan(loanid)
);

-- carloan table
create table carloan (
    loanid int primary key,
    carmodel varchar(100),
    carvalue double(15,2),
    foreign key (loanid) references loan(loanid)
);

insert into customer (name, emailaddress, phonenumber, address, creditscore) values
('Anish Jarag', 'anishjarag@gmail.com', '9876543210', 'Mumbai, Maharashtra', 900),
('Kashish Jadhav', 'kashishjadhav@gmail.com', '9123456789', 'Pune, Maharashtra', 850),
('Karthik Gavali', 'karthikgavali@gmail.com', '9988776655', 'Nashik, Maharashtra', 780),
('Tanishq Vankudre', 'tanishqvankudre@gmail.com', '8899776655', 'Kolhapur, Maharashtra', 870),
('Aditya Chavan', 'adityachavan@gmail.com', '8001234567', 'Nagpur, Maharashtra', 850),
('Sneha Patil', 'snehapatil@gmail.com', '8901122334', 'Aurangabad, Maharashtra', 640),
('Rohit Sharma', 'rohitsharma@gmail.com', '9112233445', 'Thane, Maharashtra', 670),
('Prachi Deshmukh', 'prachideshmukh@gmail.com', '9334455667', 'Satara, Maharashtra', 800),
('Omkar Kulkarni', 'omkarkulkarni@gmail.com', '9556677889', 'Sangli, Maharashtra', 760),
('Meera Joshi', 'meerajoshi@gmail.com', '9667788990', 'Solapur, Maharashtra', 500);

select * from customer;

insert into loan (customerid, principalamount, interestrate, loanterm, loantype, loanstatus) values
(1, 500000, 7.5, 60, 'homeloan', 'pending'),
(2, 300000, 6.8, 48, 'carloan', 'pending'),
(3, 250000, 8.2, 36, 'carloan', 'pending'),
(4, 1000000, 7.0, 84, 'homeloan', 'pending'),
(5, 200000, 9.0, 24, 'carloan', 'pending'),
(6, 750000, 7.3, 72, 'homeloan', 'pending'),
(7, 150000, 8.5, 36, 'carloan', 'pending'),
(8, 900000, 6.9, 60, 'homeloan', 'pending'),
(9, 350000, 7.8, 48, 'carloan', 'pending'),
(10, 600000, 7.2, 60, 'homeloan', 'pending');

insert into homeloan (loanid, propertyaddress, propertyvalue) values
(1, '789 Skyline Penthouse', 800000),
(4, '455 Highland Rd', 1500000),
(6, '222 Ocean View', 1000000),
(8, '901 Mountain Ridge', 1100000),
(10, '321 Country Club Ln', 950000);

insert into carloan (loanid, carmodel, carvalue) values
(1, 'Porche 911', 450000),
(2, 'BMW X7', 350000),
(3, 'Mercedes Benz AMG', 300000),
(5, 'G-Wagon', 220000),
(7, 'Ford EcoSport', 180000),
(9, 'Kia Seltos', 400000);
