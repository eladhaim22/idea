CREATE TABLE Templates(
	id bigint IDENTITY(1,1),
	name nvarchar(50) NOT NULL
)

ALTER TABLE Templates
	ADD CONSTRAINT PK_Templates PRIMARY KEY (Id);
