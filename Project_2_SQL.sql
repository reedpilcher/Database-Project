USE project_2;

CREATE TABLE Customer(
	CustomerID	VARCHAR(10) UNIQUE NOT NULL,
	FirstName	VARCHAR(50) NOT NULL,
	LastName	VARCHAR(50) NOT NULL,
	Email	VARCHAR(100) NOT NULL,
	Address	VARCHAR(200) NOT NULL,
	Zip_Code	VARCHAR(10) NOT NULL,
	City	VARCHAR(100) NOT NULL,
	PRIMARY KEY(CustomerID)
);

CREATE TABLE Designer(
	DesignerID	VARCHAR(10) UNIQUE NOT NULL,
	FirstName	VARCHAR(50) NOT NULL,
	LastName	VARCHAR(50) NOT NULL,
	Email	VARCHAR(100) NOT NULL,
	PhoneNumber	CHAR(10) NOT NULL,
	ManagerID	CHAR(10),
	PreferredStyle	VARCHAR(100),
	PRIMARY KEY(DesignerID)
);

-- Mock table created to fully depict foreign key/entity relationships
CREATE TABLE Document(
	DocumentID	VARCHAR(10) UNIQUE NOT NULL,
	Document_CustomerID VARCHAR(10),
	Document_DesignerID VARCHAR(10),
	Document_ProjectID VARCHAR(10),
	PRIMARY KEY(DocumentID)
);

CREATE TABLE Project(
	ProjectID	VARCHAR(10) UNIQUE NOT NULL,
    Project_CustomerID VARCHAR(10) NOT NULL,
	Scope	VARCHAR(200) NOT NULL,
	Budget	DECIMAL(9,2) NOT NULL,
	Deadline	DATE NOT NULL,
	OtherConsiderations	TEXT,
	PRIMARY KEY(ProjectID)
);

-- Mock table created to fully depict foreign key/entity relationships
CREATE TABLE Project_Product(
	Project_ProductID	VARCHAR(10) UNIQUE NOT NULL,
    Project_Product_ProjectID VARCHAR(10),
	PRIMARY KEY(Project_ProductID)
);

-- Mock table created to fully depict foreign key/entity relationships
CREATE TABLE Questionnaire(
	QuestionnaireID	VARCHAR(10) UNIQUE NOT NULL,
    Questionnaire_CustomerID VARCHAR(10) NOT NULL,
    Questionnaire_ProjectID VARCHAR(10),
	PRIMARY KEY(QuestionnaireID)
);

ALTER TABLE Designer
	ADD CONSTRAINT fk_Designer_Manager
    FOREIGN KEY(ManagerID)
    REFERENCES Designer(DesignerID);
	
ALTER TABLE Document
	ADD CONSTRAINT fk_Document_Customer
    FOREIGN KEY(Document_CustomerID)
    REFERENCES Customer(CustomerID);

ALTER TABLE Document
	ADD CONSTRAINT fk_Document_Designer
    FOREIGN KEY(Document_DesignerID)
    REFERENCES Designer(DesignerID);
	
ALTER TABLE Document
	ADD CONSTRAINT fk_Document_Project
    FOREIGN KEY(Document_ProjectID)
    REFERENCES Project(ProjectID);
    
ALTER TABLE Project
	ADD CONSTRAINT fk_Project_Customer
    FOREIGN KEY(Project_CustomerID)
    REFERENCES Customer(CustomerID);

ALTER TABLE Project_Product
	ADD CONSTRAINT fk_Project_Product_Project
    FOREIGN KEY(Project_Product_ProjectID)
    REFERENCES Project(ProjectID);

ALTER TABLE Questionnaire
	ADD CONSTRAINT fk_Questionnaire_Customer
    FOREIGN KEY(Questionnaire_CustomerID)
    REFERENCES Customer(CustomerID);
	
ALTER TABLE Questionnaire
	ADD CONSTRAINT fk_Questionnaire_Project
    FOREIGN KEY(Questionnaire_ProjectID)
    REFERENCES Project(ProjectID);

-- Inserting values into Customer table
INSERT INTO Customer
VALUES ('JS081281_0', 'John', 'Smith', 'JohnSmith@aol.com', '123 North Street', '12345', 'Jacksonville');
INSERT INTO Customer
VALUES ('NZ092491_0', 'Nick', 'Zovath', 'nickzovath@aol.com', '3420 Forest Hills Lane', '32205', 'Jacksonville');

-- Inserting values into Designer table
INSERT INTO Designer (DesignerID, FirstName, LastName, Email, PhoneNumber, PreferredStyle)
VALUES ('RP123456_0', 'Reed', 'Pilcher', 'reedpilcher@design.com', '9045555555', 'Modern');
INSERT INTO Designer (DesignerID, FirstName, LastName, Email, PhoneNumber, PreferredStyle)
VALUES ('JJ123456_0', 'Jim', 'John', 'jimjohn@design.com', '9043333333', 'Mid-Century Modern');

-- Inserting values into Project table
INSERT INTO Project
VALUES('P000000001', 'JS081281_0', 'Living Room', 4999.99, '2022-12-31', 'Must leave space for billiards table');
INSERT INTO Project
VALUES('P000000002', 'JS081281_0', 'Lounge', 999999.99, '2022-12-31', 'Must implement hidden compartment under bar');

-- Updating value in Customer table
UPDATE Customer
SET FirstName = 'Jimmy'
WHERE CustomerID = 'JS081281_0';

-- Updating value in Designer table
UPDATE Designer
SET ManagerID = 'RP123456_0'
WHERE DesignerID = 'JJ123456_0';

-- Updating value in Project table
UPDATE Project
SET Deadline = '2023-01-30'
WHERE ProjectID = 'P000000001';

-- Deleting obsolete data from Customer table
DELETE FROM Customer
WHERE CustomerID = 'NZ092491_0';

-- Selecting CustomerID from Customer table
SELECT CustomerID
FROM Customer;

-- Selecting DesignerID from Designer table, based on ManagerID
SELECT DesignerID
FROM Designer
WHERE ManagerID = 'RP123456_0';

-- Selecting ProjectID and Deadline from Project table, descending order by Deadline
SELECT ProjectID, Deadline
FROM Project
ORDER BY Deadline DESC;

-- Milestone 3 Begin

-- Adding Demand attribute to designer for use in following function and procedure
ALTER TABLE Designer
ADD COLUMN Demand VARCHAR(20);

-- Inserting mock designers to Designer table to calculate Designer demand
INSERT INTO Designer (DesignerID, FirstName, LastName, Email, PhoneNumber, PreferredStyle)
VALUES ('TA123456_0', 'Tim', 'Allen', 'timallen@design.com', '9044444444', 'Contemporary');
INSERT INTO Designer (DesignerID, FirstName, LastName, Email, PhoneNumber, PreferredStyle)
VALUES ('BW123456_0', 'Bruce', 'Willis', 'brucewillis@design.com', '9045555555', 'Medieval');
-- End insert

-- Inserting mock contracts to Document table to calculate Designer demand
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000000','RP123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000001','RP123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000002','RP123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000003','RP123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000004','RP123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000005','RP123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000006','TA123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000007','TA123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000008','TA123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000009','TA123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000010','BW123456_0');
INSERT INTO Document(DocumentID, Document_DesignerID)
VALUES('DOC_000011','BW123456_0');
-- End insert

-- Creating table to track Designers that are eligible for bonus
CREATE TABLE DESIGNER_BONUS(
	BonusID INT UNSIGNED AUTO_INCREMENT,
    Bonus_DesignerID VARCHAR(10),
	Bonus_Amount SMALLINT UNSIGNED DEFAULT 0,
    PRIMARY KEY(BonusID)
    );

/* 	DemandCalc:
	Calculates Designer demand for a given Designer by counting the number of contracts
	associated with their ID in the Document table.
    
    testID is assigned the same value as DesignerID
    Returns 'No Demand', 'Low Demand', or 'High Demand' depending on the number of contracts
*/
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `DemandCalc`(testID VARCHAR(10)) RETURNS varchar(20) CHARSET utf8mb4
BEGIN
	DECLARE counter TINYINT;
	SET counter = (SELECT COUNT(*) FROM Document
					WHERE Document_DesignerID = testID);
	IF counter < 1 THEN RETURN 'No Demand';
	ELSEIF counter < 3 THEN RETURN 'Low Demand';
	ELSEIF counter < 5 THEN RETURN 'Moderate Demand';
	ELSE RETURN 'High Demand';
	END IF;
END //

/*  AddDesignerBonus:
	Iterates through DesignerIDs where the Demand value equals 'High Demand'
	Inserts these designers into the DESIGNER_BONUS table.
    Takes no parameters, returns no values
*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddDesignerBonus`()
BEGIN
    
    DECLARE bonus_designID VARCHAR(10);
    DECLARE demandValue VARCHAR(20);
    DECLARE counter INT DEFAULT 0;
	DECLARE c CURSOR FOR(
		SELECT DesignerID, Demand FROM Designer
		WHERE Demand = 'High Demand'
		);
        
	DECLARE EXIT HANDLER FOR 1329
		BEGIN
			SELECT 'End of query';
		END;
        
		OPEN c;
		WHILE (counter != (SELECT COUNT(*) FROM Designer)) DO
			FETCH c INTO bonus_designID, demandValue;
				INSERT INTO DESIGNER_BONUS
                VALUES(NULL,bonus_designID, 200);
                SET counter = counter + 1;
                
		END WHILE;
		CLOSE c;
	END $$

    -- Calls the DemandCalc function by setting the Demand attribute to the returned value
    UPDATE Designer
	SET Demand = DemandCalc('RP123456_0')
    WHERE DesignerID = 'RP123456_0';
    
	UPDATE Designer
	SET Demand = DemandCalc('JJ123456_0')
    WHERE DesignerID = 'JJ123456_0';

	UPDATE Designer
	SET Demand = DemandCalc('TA123456_0')
    WHERE DesignerID = 'TA123456_0';
    
	UPDATE Designer
	SET Demand = DemandCalc('BW123456_0')
    WHERE DesignerID = 'BW123456_0';
    -- End DemandCalc
    
    -- Adds any designers that have been calculated and deemed 'High Value' to DESIGNER_BONUS
    CALL AddDesignerBonus();
    
    -- Verify the demand values assigned to Demand attribute
	SELECT *
    FROM DESIGNER;
    
    -- Verify the designers who have been added to DESIGNER_BONUS
    SELECT *
    FROM DESIGNER_BONUS;