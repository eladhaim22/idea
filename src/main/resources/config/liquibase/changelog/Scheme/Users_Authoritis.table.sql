Create Table users_authorities(
	user_id bigint NOT NULL,
	authority_name varchar(50) NOT NULL
)

ALTER TABLE users_authorities 
	ADD CONSTRAINT PK_Users_Authorities PRIMARY KEY (user_id,authority_name);
ALTER TABLE users_authorities 
	ADD CONSTRAINT FK_Users_Authorities_Users FOREIGN KEY (user_id) REFERENCES Users(Id);
ALTER TABLE users_authorities 
	ADD CONSTRAINT FK_Users_Authorities_Authorities FOREIGN KEY (authority_name) REFERENCES Authorities(Name);
