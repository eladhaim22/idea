CREATE TABLE Person(
	id bigint Identity(1,1),
	dtype varchar(20) NOT NULL,
	firstname varchar(50) NOT NULL,
	lastname varchar(50) NOT NULL,
	dni varchar(9) NOT NULL UNIQUE,
	age int,
	phonenumber varchar(20),
	email varchar(50),
	filenumber varchar(15) NULL,
	carrer varchar(50) NULL,
	stage int NULL
)

ALTER TABLE Person
	ADD CONSTRAINT PK_Person PRIMARY KEY (Id);
	

