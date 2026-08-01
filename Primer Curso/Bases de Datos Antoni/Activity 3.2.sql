-- 1.
SELECT CONCAT(first_name, ' ', last_name) AS "Full Name",salary 
from employees 
where salary not between 10000 and 15000;
-- 2.
SELECT CONCAT(first_name, ' ',last_name) as "Full Name", department_id 
from employees 
where DEPARTMENT_ID =30 or `DEPARTMENT_ID`=100;
-- 3.
select concat(`FIRST_NAME`, ' ', `LAST_NAME`) as "Full Name",`SALARY` 
from employees 
where `SALARY` not between 10000 and 15000 and `DEPARTMENT_ID`=30 or `DEPARTMENT_ID`=100; 
-- 4.
select concat(`FIRST_NAME`, ' ', `LAST_NAME`) as "Full Name", `HIRE_DATE` 
from employees 
where `HIRE_DATE` Like "1987%";
-- 5.
select `FIRST_NAME` 
from employees 
where `FIRST_NAME` like "%b%" and `FIRST_NAME` like "%c%";
-- 6.
select `LAST_NAME`,`JOB_ID`,`SALARY` 
from employees 
where (not `JOB_ID`='IT_PROG' or not `JOB_ID`='SH_CLERK') AND (not `SALARY`=4500 or not `SALARY`=10000 or not 15000);
-- 7.
select `LAST_NAME` from employees where `LAST_NAME` like "______";
-- 8.
select `LAST_NAME` from employees where `LAST_NAME` like "__e%";
-- 9.
SELECT * FROM employees 
LIMIT 10;
-- 10.
select * from employees 
where `LAST_NAME`='Blake' or `LAST_NAME`='Scott' or `LAST_NAME`='King' or `LAST_NAME`='Ford';




