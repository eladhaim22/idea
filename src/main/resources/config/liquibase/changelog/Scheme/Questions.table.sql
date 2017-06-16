Create Table Questions(
	id bigint IDENTITY(1,1),
	section nvarchar(50) NULL,
	subsection nvarchar(50) NULL,
	detail nvarchar(400) NOT NULL,
	order_section bigint NOT NULL,
	order_subsection bigint NULL,
	order_question bigint NOT NULL,
	template_id bigint NOT NULL
)

ALTER TABLE Questions
	ADD CONSTRAINT PK_Questions PRIMARY KEY (id);
ALTER TABLE Questions 
	ADD CONSTRAINT FK_Questions_Templates FOREIGN KEY (template_id) 
	REFERENCES Templates(Id);
