Create Table Person_Projects(
	person_id bigint NOT NULL,
	project_if bigint NOT NULL
)

ALTER TABLE Person_Projects 
	ADD CONSTRAINT PK_Person_Projects PRIMARY KEY (person_id,project_id);
ALTER TABLE Person_Projects 
	ADD CONSTRAINT FK_Person_Projects_Person FOREIGN KEY (person_id) 
	REFERENCES Person(Id);
ALTER TABLE Person_Projects 
	ADD CONSTRAINT FK_Person_Projects_Projects FOREIGN KEY (project_id) 
	REFERENCES Projects(Id);
