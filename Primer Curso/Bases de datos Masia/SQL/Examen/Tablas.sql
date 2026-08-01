---------------------------------------------------------
-- 1. CREACIÓN DE TABLAS (DDL) con prefijo ex_
---------------------------------------------------------

-- Tabla de Provincias
CREATE TABLE ex_Province (
                             Code VARCHAR2(10) PRIMARY KEY,
                             Name VARCHAR2(100) NOT NULL,
                             Total_Amount NUMBER(12, 2)
);

-- Tabla de Vendedores (con relación de jerarquía)
CREATE TABLE ex_Vendor (
                           Code VARCHAR2(10) PRIMARY KEY,
                           Name VARCHAR2(100) NOT NULL,
                           Percentage NUMBER(5, 2),
                           Degree VARCHAR2(50),
                           Superior_Officer VARCHAR2(10),
                           CONSTRAINT fk_ex_vendor_sup FOREIGN KEY (Superior_Officer) REFERENCES ex_Vendor(Code)
);

-- Tabla de Clientes
CREATE TABLE ex_Customer (
                             DNI VARCHAR2(20) PRIMARY KEY,
                             Name VARCHAR2(100) NOT NULL,
                             Province_Code VARCHAR2(10),
                             Type VARCHAR2(50),
                             Register_Date DATE,
                             Vendor_Code VARCHAR2(10),
                             Purchases NUMBER(10),
                             CONSTRAINT fk_ex_cust_prov FOREIGN KEY (Province_Code) REFERENCES ex_Province(Code),
                             CONSTRAINT fk_ex_cust_vend FOREIGN KEY (Vendor_Code) REFERENCES ex_Vendor(Code)
);

-- Tabla de Artículos
CREATE TABLE ex_Item (
                         Code VARCHAR2(10) PRIMARY KEY,
                         Description VARCHAR2(200),
                         Price NUMBER(10, 2),
                         Stock NUMBER(10),
                         Origin VARCHAR2(100)
);

-- Tabla de Compras (Clave primaria triple según el examen)
CREATE TABLE ex_Purchase (
                             Client_DNI VARCHAR2(20),
                             Item_Code VARCHAR2(10),
                             Purchase_Date DATE,
                             Amount NUMBER(10, 2),
                             PRIMARY KEY (Client_DNI, Item_Code, Purchase_Date),
                             CONSTRAINT fk_ex_purch_cli FOREIGN KEY (Client_DNI) REFERENCES ex_Customer(DNI),
                             CONSTRAINT fk_ex_purch_itm FOREIGN KEY (Item_Code) REFERENCES ex_Item(Code)
);

---------------------------------------------------------
-- 2. INSERCIÓN DE DATOS (DML) - 10 registros por tabla
---------------------------------------------------------

-- Provincias (Incluyendo Ontario para Ej. 1 y 3)
INSERT INTO ex_Province VALUES ('P01', 'Ontario', 150000);
INSERT INTO ex_Province VALUES ('P02', 'Quebec', 120000);
INSERT INTO ex_Province VALUES ('P03', 'Madrid', 95000);
INSERT INTO ex_Province VALUES ('P04', 'Valencia', 80000);
INSERT INTO ex_Province VALUES ('P05', 'Sevilla', 60000);
INSERT INTO ex_Province VALUES ('P06', 'Barcelona', 110000);
INSERT INTO ex_Province VALUES ('P07', 'Málaga', 45000);
INSERT INTO ex_Province VALUES ('P08', 'Alicante', 38000);
INSERT INTO ex_Province VALUES ('P09', 'Bilbao', 72000);
INSERT INTO ex_Province VALUES ('P10', 'Murcia', 25000);

-- Vendedores (Con jerarquías para Ej. 4, 8 y 10)
INSERT INTO ex_Vendor VALUES ('V01', 'Rick Sanchez', 25.0, 'Senior', NULL);
INSERT INTO ex_Vendor VALUES ('V02', 'Morty Smith', 10.5, 'Junior', 'V01');
INSERT INTO ex_Vendor VALUES ('V03', 'Summer Smith', 12.0, 'Junior', 'V01');
INSERT INTO ex_Vendor VALUES ('V04', 'Beth Smith', 18.0, 'Manager', NULL);
INSERT INTO ex_Vendor VALUES ('V05', 'Jerry Smith', 5.0, 'Trainee', 'V04');
INSERT INTO ex_Vendor VALUES ('V06', 'Birdperson', 15.0, 'Senior', NULL);
INSERT INTO ex_Vendor VALUES ('V07', 'Squanchy', 11.0, 'Junior', 'V06');
INSERT INTO ex_Vendor VALUES ('V08', 'Unity', 22.0, 'Manager', NULL);
INSERT INTO ex_Vendor VALUES ('V09', 'Tammy', 9.0, 'Junior', 'V08');
INSERT INTO ex_Vendor VALUES ('V10', 'Meeseeks', 30.0, 'Specialist', NULL); -- El mayor porcentaje

-- Clientes (Incluyendo Scooby Doo para Ej. 2 y Shaggy para Ontario)
INSERT INTO ex_Customer VALUES ('123S', 'Scooby Doo', 'P01', 'VIP', DATE '2023-01-10', 'V01', 5);
INSERT INTO ex_Customer VALUES ('456S', 'Shaggy Rogers', 'P01', 'Regular', DATE '2023-02-15', 'V01', 2);
INSERT INTO ex_Customer VALUES ('789V', 'Velma Dinkley', 'P03', 'VIP', DATE '2023-03-20', 'V01', 10);
INSERT INTO ex_Customer VALUES ('000F', 'Fred Jones', 'P02', 'Regular', DATE '2023-04-05', 'V04', 1);
INSERT INTO ex_Customer VALUES ('111D', 'Daphne Blake', 'P02', 'VIP', DATE '2023-05-12', 'V04', 8);
INSERT INTO ex_Customer VALUES ('222H', 'Homer Simpson', 'P03', 'Regular', DATE '2023-06-30', 'V02', 3);
INSERT INTO ex_Customer VALUES ('333B', 'Bart Simpson', 'P04', 'Regular', DATE '2023-07-14', 'V02', 12);
INSERT INTO ex_Customer VALUES ('444L', 'Lisa Simpson', 'P04', 'VIP', DATE '2023-08-22', 'V03', 4);
INSERT INTO ex_Customer VALUES ('555M', 'Marge Simpson', 'P05', 'Regular', DATE '2023-09-01', 'V03', 6);
INSERT INTO ex_Customer VALUES ('666P', 'Peter Griffin', 'P06', 'VIP', DATE '2023-10-18', 'V08', 2);

-- Artículos (Incluyendo 'Beer' para Ej. 7)
INSERT INTO ex_Item VALUES ('B01', 'Beer Premium 500ml', 4.50, 1000, 'Germany');
INSERT INTO ex_Item VALUES ('B02', 'Beer Craft IPA', 6.00, 500, 'Belgium');
INSERT INTO ex_Item VALUES ('I01', 'Laptop Gaming', 1500.00, 20, 'USA');
INSERT INTO ex_Item VALUES ('I02', 'Smartphone Ultra', 900.00, 50, 'South Korea');
INSERT INTO ex_Item VALUES ('I03', 'Mechanical Keyboard', 120.00, 100, 'China');
INSERT INTO ex_Item VALUES ('I04', 'Curved Monitor 32', 350.00, 30, 'Taiwan');
INSERT INTO ex_Item VALUES ('I05', 'Wireless Mouse', 45.00, 200, 'China');
INSERT INTO ex_Item VALUES ('I06', 'Noise Canceling Headphones', 250.00, 40, 'Japan');
INSERT INTO ex_Item VALUES ('I07', 'External SSD 2TB', 180.00, 60, 'USA');
INSERT INTO ex_Item VALUES ('I08', 'Webcam 4K', 95.00, 80, 'China');

-- Compras (Registros cruzados para Ej. 2, 3, 5, 7 y 12)
INSERT INTO ex_Purchase VALUES ('123S', 'I01', DATE '2024-05-15', 1500.00); -- Compra Scooby Doo
INSERT INTO ex_Purchase VALUES ('456S', 'B01', DATE '2024-06-01', 4.50);    -- Compra Shaggy (Ontario + Beer)
INSERT INTO ex_Purchase VALUES ('789V', 'B02', DATE '2024-08-20', 12.00);   -- Compra Velma (Beer)
INSERT INTO ex_Purchase VALUES ('111D', 'I02', DATE '2024-01-10', 900.00);
INSERT INTO ex_Purchase VALUES ('222H', 'I03', DATE '2024-02-14', 120.00);
INSERT INTO ex_Purchase VALUES ('333B', 'I04', DATE '2024-03-20', 350.00);
INSERT INTO ex_Purchase VALUES ('444L', 'I05', DATE '2024-04-05', 45.00);
INSERT INTO ex_Purchase VALUES ('555M', 'I06', DATE '2024-05-12', 250.00);
INSERT INTO ex_Purchase VALUES ('666P', 'I07', DATE '2024-11-30', 180.00);
INSERT INTO ex_Purchase VALUES ('123S', 'B01', DATE '2024-12-01', 9.00);    -- Otra de Scooby (Beer)

COMMIT;