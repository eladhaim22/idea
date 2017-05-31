Create Table Users(
	id bigint Identity(1,1),
	login varchar(50) UNIQUE NOT NULL,
	password_hash varchar(60),
	first_name varchar(50),
    last_name varchar(50),
    email varchar(100) UNIQUE NULL,
	image_url varchar(256),
	activated bit,
	lang_key varchar(5),
	activation_key varchar(20),
	reset_key varchar(20),
	created_by varchar(20) NOT NULL,
	created_date datetime NOT NULL CONSTRAINT DF_YourTable DEFAULT GETDATE(),
	reset_date datetime NULL,
	last_modified_by varchar(50),
	last_modified_date datetime,
)

ALTER TABLE Users
	ADD CONSTRAINT PK_Users PRIMARY KEY (Id);

CREATE UNIQUE INDEX idx_user_login
	ON Users (login);
CREATE UNIQUE INDEX idx_user_email
	ON Users (email);	
	
	
Create Table Authorities(
	name varchar(50) NOT NULL UNIQUE,
)

ALTER TABLE Authorities
	ADD CONSTRAINT PK_Authorities PRIMARY KEY (name);
	
	
Create Table users_authorities(
	user_id bigint NOT NULL,
	authority_name varchar(50) NOT NULL
)

ALTER TABLE users_authorities 
	ADD CONSTRAINT PK_Users_Authorities PRIMARY KEY (user_id,authority_name);
ALTER TABLE Users_Roles 
	ADD CONSTRAINT FK_Users_Authorities_Users FOREIGN KEY (user_id) REFERENCES Users(Id);
ALTER TABLE Users_Roles 
	ADD CONSTRAINT FK_Users_Authorities_Authorities FOREIGN KEY (authority_name) REFERENCES Authorities(Name);
	


