Create Table Authorities(
	name varchar(50) NOT NULL UNIQUE
)

ALTER TABLE Authorities
	ADD CONSTRAINT PK_Authorities PRIMARY KEY (name);