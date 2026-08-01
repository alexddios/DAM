-- 1. - Erase the constraints that exist in the attribute salary.
-- We add it first
ALTER TABLE STAFF
    ADD CONSTRAINT chk_precio CHECK (SALARY >= 3000 AND SALARY <= 11000);

alter table STAFF
drop constraint chk_precio;
-- 2 Erase the primary key constraint of the table Staff and create a new primary key with the columns: name and
-- cod_dept.
-- First, we remove the foreign keys that point to it
alter table STAFF
drop constraint FK_DEPARTMENT_CODE;
alter table STAFF
    drop constraint FK_SUPERIOR_OFFICER;
-- Now we can remove the primary key
alter table STAFF
drop primary key ;
-- We create the new primary key
alter table STAFF
add primary key (NAME,DEPARTMENT_CODE);

-- 3 Execute the statements:
ALTER TABLE DEPARTMENT
    DROP PRIMARY KEY CASCADE;
UPDATE staff
SET DEPARTMENT_CODE = '22 '
WHERE name = 'Cerda';
-- In the execution of these statements is there any mistake? Why? If we only executed the UPDATE statement (without
-- the previous ALTER) had gone a bug?

-- 4  Insert again the primary key constraint on the table department. Also, you have to add a constraint to salary column
-- in such a way that the salary can only take values between 2000 and 11000.
ALTER TABLE DEPARTMENT
ADD PRIMARY KEY(DEPARTMENT_CODE);

ALTER TABLE STAFF
    ADD CONSTRAINT chk_precio CHECK (SALARY >= 2000 AND SALARY <= 11000);

-- 5. Run the following statements:
ALTER TABLE Staff
    ADD CONSTRAINT foreign_key
        FOREIGN KEY (DEPARTMENT_CODE) REFERENCES DEPARTMENT (DEPARTMENT_CODE) ON DELETE CASCADE ENABLE NOVALIDATE;
DELETE FROM DEPARTMENT
WHERE DEPARTMENT_CODE = '05 ';
-- Explain what the effect of these statements.

-- 6 Add the column comments to the table departments. The field should be VARCHAR2 type and length 80.
ALTER TABLE DEPARTMENT
ADD (COMMENTS VARCHAR2(80));

-- 7. - Add to the programming department the following comment: Programming with Java and SQL
UPDATE DEPARTMENT
SET COMMENTS = 'Programming with Java and SQL'
WHERE DEPARTMENT_CODE = 8;

-- 8. - Modify the new attribute of the department table so as to have a length up to 50 characters.
ALTER TABLE DEPARTMENT
MODIFY (COMMENTS VARCHAR2(50));

-- 9. - Run the sql file called utlexcpt.sql that you can find in c:\oracle\RDBMS\admin. After, execute the following
-- commands:
create table exceptions(row_id rowid,
                        owner varchar2(30),
                        table_name varchar2(30),
                        constraint varchar2(30));
INSERT INTO STAFF VALUES (126, 'Vidal', 'Programmer', 4500, 88, NULL, 413);

alter table STAFF disable constraint fk_superior_officer;
alter table STAFF disable constraint fk_department_code;

INSERT INTO STAFF VALUES (126, 'Vidal', 'Programmer', 4500, 88, NULL, 413);


ALTER TABLE STAFF ENABLE CONSTRAINT FK_SUPERIOR_OFFICER EXCEPTIONS INTO EXCEPTIONS;
ALTER TABLE STAFF ENABLE CONSTRAINT FK_DEPARTMENT_CODE EXCEPTIONS INTO EXCEPTIONS;

SELECT * FROM EXCEPTIONS;
SELECT s.EMPLOYEE_CODE, s.NAME, s.DEPARTMENT_CODE, e.CONSTRAINT
FROM STAFF s, EXCEPTIONS e
WHERE s.rowid = e.row_id;

INSERT INTO DEPARTMENT VALUES (88, 'Ibiza', 'programming-2', NULL);

ALTER TABLE STAFF ENABLE CONSTRAINT FK_DEPARTMENT_CODE EXCEPTIONS INTO EXCEPTIONS;

