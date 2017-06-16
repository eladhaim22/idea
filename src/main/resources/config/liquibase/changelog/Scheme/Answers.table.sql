Create Table Answers(
	id bigint IDENTITY(1,1),
	question_answer nvarchar(max),
	question_id bigint NOT NULL,
	project_id bigint NOT NULL
)

ALTER TABLE Answers
	ADD CONSTRAINT PK_Answers PRIMARY KEY (id);
ALTER TABLE Answers 
	ADD CONSTRAINT FK_Answers_Question FOREIGN KEY (question_id) 
	REFERENCES Questions(Id);
ALTER TABLE Answers 
	ADD CONSTRAINT FK_Answers_Projects FOREIGN KEY (project_id) 
	REFERENCES Projects(Id);

