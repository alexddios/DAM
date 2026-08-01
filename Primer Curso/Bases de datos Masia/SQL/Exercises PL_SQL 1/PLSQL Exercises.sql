-- 1
ALTER TABLE FACTORIAL ADD SQUARE NUMBER(5);
ALTER TABLE FACTORIAL ADD ROOT NUMBER(8,5);

CREATE OR REPLACE PROCEDURE sq(k IN NUMBER) AS
    n NUMBER(2);
    f NUMBER(5);
BEGIN
    n:=1;
    f:=1;
    FOR n IN 1..k LOOP
            f:=n*n;
            UPDATE FACTORIAL SET SQUARE=f where NUM=n;
        END LOOP;
END;
/
call sq(10);
CREATE OR REPLACE PROCEDURE root(k IN NUMBER) AS
    n NUMBER(2);
    f NUMBER(8,5);
BEGIN
    n:=1;
    f:=1;
    FOR n IN 1..k LOOP
            f:=sqrt(n);
            UPDATE FACTORIAL SET ROOT=f where NUM=n;
        END LOOP;
END;
/
call ROOT(10);

-- 2
create or replace function countEmp (EMPLOYEENAME in char)
return number is amount number(3);
    begin
        SELECT COUNT(*) into amount
        FROM STAFF
        WHERE JOB=EMPLOYEENAME;
        return (amount);
    end;
/
select countEmp('analyst') from dual;

-- 3
create or replace procedure raiseTax(v in number) as
    n number(2);

    begin
        select count(*) into n
        from LANDREGISTRY
        where TAX_RATE+1>= 7 and VALUE<v;

        if (n!= 0) then
            DBMS_OUTPUT.PUT_LINE('No se ha podido completar la instrucción');
        else
            update LANDREGISTRY set TAX_RATE=TAX_RATE+1 where VALUE<v;
        end if;
    end;
/
-- 4
CREATE OR REPLACE FUNCTION countEmpCity (p_city IN VARCHAR2)
    RETURN NUMBER IS
    amount NUMBER(2);
BEGIN
    SELECT COUNT(DISTINCT EMPLOYEE_CODE) INTO amount
    FROM STAFF s, DEPARTMENT d
    WHERE s.DEPARTMENT_CODE = d.DEPARTMENT_CODE
      AND d.CITY = p_city;

    RETURN amount;
END;

SELECT countEmpCity('Barcelona') FROM dual;

-- 5
create or replace procedure oldestAnalist as
    d date;
    n number(2);
begin
    select min(START_DATE) into d
    from STAFF
    where JOB='analyst';

    select count(*) into n
    from STAFF where START_DATE=d and JOB='analyst';

    if(n=1) then
        UPDATE STAFF SET JOB='Project Manager',DEPARTMENT_CODE=10 where START_DATE=d and JOB='analyst';
    end if;
end;
/
call oldestAnalist();

-- 6
create or replace procedure increaseSalaryDepartment (d in number) as
    begin
        update STAFF set SALARY=SALARY*1.01 where DEPARTMENT_CODE = d;
    end;

-- 7
alter table TAXPAYER add number_estates int;

create or replace procedure numberEstates as
        begin
            UPDATE TAXPAYER t
            SET t.NUMBER_ESTATES = (
                SELECT COUNT(*)
                FROM ESTATE_OWNER e
                WHERE e.PAYER = t.COD_PAYER
            );
        end;
call numberEstates();

