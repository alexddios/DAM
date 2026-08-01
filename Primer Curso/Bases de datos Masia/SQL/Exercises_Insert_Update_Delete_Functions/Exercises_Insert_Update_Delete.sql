-- 1
INSERT INTO EX2_DEPARTMENT
VALUES (11, 'Consulting', 'Ciutadella');

-- 2
INSERT INTO EX2_STAFF
values ('1200','Gil','Analyst',3000,'05',null,null);

-- 3
INSERT INTO EX2_STAFF
VALUES (1333,'Ospina',null,5000,11,null,null);

-- 4
UPDATE EX2_STAFF
SET START_DATE = TRUNC(sysdate)
WHERE NAME = 'Deya';

-- 5
UPDATE EX2_STAFF
SET JOB='programmer',SALARY=SALARY+SALARY*0.2,SUPERIOR_OFFICER=1003
WHERE NAME = 'Cerda';

-- 6
update EX2_STAFF
set SALARY = SALARY * 1.06;

-- 7
--delete from EX2_STAFF
--where NAME='Franko'; -- You cannot, as he is the superior of another employee.

-- 9
UPDATE EX2_STAFF
SET salary = salary * 1.10
WHERE DEPARTMENT_CODE = (
    SELECT DEPARTMENT_CODE
    FROM (
             SELECT DEPARTMENT_CODE
             FROM EX2_STAFF
             GROUP BY DEPARTMENT_CODE
             ORDER BY COUNT(*) ASC
         )
    WHERE ROWNUM = 1
);
-- 10
UPDATE EX2_STAFF
SET JOB = 'Manager'
WHERE NAME = 'Ospina';

UPDATE EX2_STAFF
SET SALARY = SALARY * 1.05
WHERE JOB = 'Analyst';


