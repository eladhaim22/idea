CREATE TABLE Projects(
	id bigint NOT NULL,
	title varchar(100) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date datetime NOT NULL,
	last_modified_by varchar(50),
	last_modified_date datetime
)

ALTER TABLE Projects
	ADD CONSTRAINT PK_Projects PRIMARY KEY (Id);
	

