CREATE TABLE Projects(
	id bigint Identity(1,1),
	title varchar(100) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date datetime NOT NULL,
	last_modified_by varchar(50),
	last_modified_date datetime,
	period_id bigint NOT NULL,
	comment varchar(max)
)

ALTER TABLE Projects
	ADD CONSTRAINT PK_Projects PRIMARY KEY (Id);

ALTER TABLE Projects 
	ADD CONSTRAINT FK_Projects_Periods FOREIGN KEY (period_id) 
	REFERENCES Periods(Id);