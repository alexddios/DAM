-- 1
CREATE TABLE STAFF (
    -- Cod_emp: Clave primaria
                       Cod_emp varchar2(4) PRIMARY KEY,

    -- Name: Valor diferente para cada fila (UNIQUE)
                       Name VARCHAR(100) UNIQUE,

    -- Job: Solo permite valores específicos (CHECK)
                       Job VARCHAR(20) CHECK (Job IN ('director', 'analyst', 'programmer', 'project manager')),

    -- Salary: Valores entre 3000 y 10000 (CHECK)
                       Salary DECIMAL(10, 2) CHECK (Salary BETWEEN 3000 AND 10000),

    -- Date of entry: Valor por defecto es la fecha del sistema (DEFAULT)
                       Date_entry DATE DEFAULT CURRENT_DATE,

    -- Cod_dept: Clave ajena, no puede ser nula (NOT NULL)
                       Cod_dept varchar(10) NOT NULL,

    -- Cod_sup: Valor por defecto es cero (DEFAULT)
                       Cod_sup INT DEFAULT 0,

    -- Definición de la clave ajena (asumiendo que la tabla de departamentos se llama DEPARTMENT)
                       FOREIGN KEY (Cod_dept) REFERENCES DEPARTMENT(DEPARTMENT_CODE)
);
create table DEPARTMENT
(
    DEPARTMENT_CODE VARCHAR2(10) not null
        primary key,
    NAME            VARCHAR2(50),
    CITY            VARCHAR2(50)
);
-- 2

INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('0368', 'Acebo', 'project manager', 9700, '12', NULL);
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('1008', 'Garcia', 'project manager', 7800, '10', '0368');
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('1190', 'Ghegoiu', 'project manager', 8000, '10', '0368');
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('0413', 'Bennasar', 'analyst', 6000, '05', '1008');
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('0545', 'Cerda', 'analyst', 5600, '05', '1008');
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('0552', 'De Dios', 'analyst', 5500, '05', '1190');
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('0663', 'De Prada', 'analyst', 6700, '05', '1190');
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('1003', 'Franko', 'analyst', 6600, '05', '1190');
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('0765', 'Deya', 'programmer', 3800, '08', '0413');
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('0998', 'Fedeli', 'programmer', 4300, '08', '0413');
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('1087', 'Gelabert', 'programmer', 4000, '08', '1003');


insert into STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
values ('0998', 'Petraroia', 'analyst', 5700, '05', '0444');
-- It fails because the PK is repeated.

insert into  STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
values ('1004', 'Rigo', 'driver', 7500, '10', NULL);
--It fails because the job cannot be a “driver.”

insert into STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('1200', 'Rodriguez', 'project manager', 2500, '10', '0413');
-- The salary of 2500 is less than the minimum of 3000

insert into STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES ('1110', 'Sanchez', 'programmer', 4000, '06', '1008');
-- The department code 06 does not exist.

-- 3 Modify data so they can be accepted for the database.
-- 3.1. Petraroia: The code is changed from 0998 to 1301 because 0998 already exists.
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES (1301, 'Petraroia', 'analyst', 5700, 05, 0413);

-- 3.2. Rigo: The position is changed from 'driver' to 'programmer' (permitted value).
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept)
VALUES (1004, 'Rigo', 'programmer', 7500, 10);

-- 3.3. Rodriguez: The code is changed to 1302 and the salary is raised to the minimum of 3000.
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES (1302, 'Rodriguez', 'project manager', 3000, 10, 0413);

-- 3.4. Sanchez: Department 06 is changed to 05 because 06 does not exist in DEPARTMENT.
INSERT INTO STAFF (Cod_emp, Name, Job, Salary, Cod_dept, Cod_sup)
VALUES (1110, 'Sanchez', 'programmer', 4000, 05, 1008);

-- 5
Select * from staff; -- Select all data from the staff table.
create synonym p for staff; -- Create a shortcut to refer to staff
Select * from p; -- Select all data from the staff table.
Drop SYNONYM p; -- Delete the shortcut

-- 7
-- Updating the entry date for employee 1008 (Garcia)
UPDATE STAFF
SET Date_entry = TO_DATE('2023-05-15 14:30', 'YYYY-MM-DD HH24:MI')
WHERE Cod_emp = 1008;

-- Updating the entry date for employee 1190 (Ghegoiu)
UPDATE STAFF
SET Date_entry = TO_DATE('2022-09-01 09:15', 'YYYY-MM-DD HH24:MI')
WHERE Cod_emp = 1190;


