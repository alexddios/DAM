CREATE TABLE CO_PROVINCE(
    Code VARCHAR2(2) PRIMARY KEY, 
    Name VARCHAR2(20) NOT NULL,
    Total_Amount INT 
); 

CREATE TABLE CO_VENDOR(
    Code INT PRIMARY KEY,
    Name VARCHAR2(20) NOT NULL,
    Percentage INT,
    Degree INT,
    Superior_Officer_Code INT,

    CONSTRAINT Reports_To
    FOREIGN KEY (Superior_Officer_Code)
    REFERENCES CO_VENDOR(Code)
);

CREATE TABLE CO_CUSTOMER(
    DNI VARCHAR2(9) PRIMARY KEY,
    Name VARCHAR2(20) NOT NULL,
    Type VARCHAR2(2),
    Register_Date DATE,
    Total_Purchases INT,
    Province_Code VARCHAR2(2), 
    Vendor_Code INT,           

    CONSTRAINT Located_In
    FOREIGN KEY (Province_Code)
    REFERENCES CO_PROVINCE(Code),

    CONSTRAINT Serves
    FOREIGN KEY (Vendor_Code)
    REFERENCES CO_VENDOR(Code)
);

CREATE TABLE CO_ITEM(
    Code INT PRIMARY KEY,
    Description VARCHAR2(100),
    Price NUMBER(4,2), 
    Stock INT,
    Origin VARCHAR2(100)
);

CREATE TABLE CO_PURCHASE(
    Client_DNI VARCHAR2(9),
    Item_Code INT,
    Purchase_Date DATE,
    Amount INT,

    PRIMARY KEY (Client_DNI, Item_Code),

    CONSTRAINT Makes
    FOREIGN KEY (Client_DNI)
    REFERENCES CO_CUSTOMER(DNI),

    CONSTRAINT Is_Purchased_In
    FOREIGN KEY (Item_Code)
    REFERENCES CO_ITEM(Code)
);
COMMIT;