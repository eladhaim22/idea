CREATE TABLE States(
	id bigint IDENTITY(1,1),
	status int NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date datetime NOT NULL,
	last_modified_by varchar(50),
	last_modified_date datetime,
	active bit NOT NULL,
	project_id bigint NOT NULL
)

ALTER TABLE States
	ADD CONSTRAINT PK_States PRIMARY KEY (Id);
	
ALTER TABLE States 
	ADD CONSTRAINT FK_States_Projects FOREIGN KEY (project_id) REFERENCES Projects(Id);


