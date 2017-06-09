Create Table Users_Projects(
	user_id bigint NOT NULL,
	project_id bigint NOT NULL
)

ALTER TABLE Users_Projects 
	ADD CONSTRAINT PK_Users_Projects PRIMARY KEY (user_id,project_id);
ALTER TABLE Users_Projects
	ADD CONSTRAINT FK_User_Projects_Person FOREIGN KEY (user_id) 
	REFERENCES Users(Id);
ALTER TABLE Users_Projects 
	ADD CONSTRAINT FK_User_Projects_Projects FOREIGN KEY (project_id) 
	REFERENCES Projects(Id);
