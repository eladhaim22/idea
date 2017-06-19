CREATE TABLE Evaluations(
	id bigint IDENTITY(1,1),
	created_by varchar(50) NOT NULL,
	created_date datetime NOT NULL,
	last_modified_by varchar(50),
	last_modified_date datetime,
	project_id bigint NOT NULL
)

ALTER TABLE Evaluations
	ADD CONSTRAINT PK_Evaluations PRIMARY KEY (Id);
ALTER TABLE Evaluations
	ADD CONSTRAINT FK_Evaluations_Projects FOREIGN KEY (project_id) 
	REFERENCES Projects(Id);

