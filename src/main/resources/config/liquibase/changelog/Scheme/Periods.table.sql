CREATE TABLE Periods(
	id bigint Identity(1,1),
	starting_date datetime,
	presention_limit_date datetime,
	ending_date datetime,
	active bit
)

ALTER TABLE Periods
	ADD CONSTRAINT PK_Periods PRIMARY KEY (Id);

