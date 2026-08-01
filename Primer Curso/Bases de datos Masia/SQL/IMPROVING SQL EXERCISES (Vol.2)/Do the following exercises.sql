-- 1. Select the last name of all employees.
select LASTNAME from NEWEMPLOYEES;
-- 2. Select the last name of all employees, without duplicates.
select distinct LASTNAME from NEWEMPLOYEES;
-- 3. Select all the data of employees whose last name is "Smith".
select * from NEWEMPLOYEES where LASTNAME = 'Smith';
-- 4. Select all the data of employees whose last name is "Smith" or "Doe".
select * from NEWEMPLOYEES where LASTNAME in ('Smith','Doe');
-- 5. Select all the data of employees that work in department 14.
select *
from NEWEMPLOYEES where DEPARTMENT=14;
-- 6. Select all the data of employees that work in department 37 or department 77.
select *
from NEWEMPLOYEES
where DEPARTMENT in (37,77);
-- 7. Select all the data of employees whose last name begins with an "S".
select * from NEWEMPLOYEES where LASTNAME like 'S%';
-- 8. Select the sum of all the departments' budgets.
select sum(BUDGET) from NEWDEPARTMENTS;
-- 9. Select the number of employees in each department (you only need to show the department code and the number of employees).
select count(*) as "Number of employees",DEPARTMENT from NEWEMPLOYEES group by DEPARTMENT;
-- 10. Select all the data of employees, including each employee's department's data.
select * from NEWEMPLOYEES left join NEWDEPARTMENTS on NEWEMPLOYEES.DEPARTMENT=NEWDEPARTMENTS.CODE;
-- 11. Select the name and last name of each employee, along with the name and budget of the employee's department.
select NEWEMPLOYEES.NAME,LASTNAME,NEWDEPARTMENTS.NAME,BUDGET
from NEWEMPLOYEES left join NEWDEPARTMENTS
on NEWEMPLOYEES.DEPARTMENT=NEWDEPARTMENTS.CODE;
-- 12. Select the name and last name of employees working for departments with a budget greater than $60,000.
select NEWEMPLOYEES.NAME,LASTNAME,NEWDEPARTMENTS.NAME,BUDGET
from NEWEMPLOYEES left join NEWDEPARTMENTS
on NEWEMPLOYEES.DEPARTMENT=NEWDEPARTMENTS.CODE
where BUDGET>60000;
-- 13. Select the departments with a budget larger than the average budget of all the departments.
select NAME from NEWDEPARTMENTS where BUDGET > (select avg(BUDGET) from NEWDEPARTMENTS);
-- 14. Select the names of departments with more than two employees.
select d.NAME,count(*)
from NEWDEPARTMENTS d
join NEWEMPLOYEES e on e.DEPARTMENT = d.CODE
group by d.NAME
having count(*) > 2;
-- 15. Select the name and last name of employees working for departments with second lowest budget.
select BUDGET,ROWNUM from NEWDEPARTMENTS order by BUDGET;
select BUDGET,ROWNUM from (select BUDGET from NEWDEPARTMENTS order by BUDGET);
select min(BUDGET) from (select BUDGET from NEWDEPARTMENTS order by BUDGET)
where ROWNUM<3;

select NEWEMPLOYEES.NAME,LASTNAME from NEWEMPLOYEES left join NEWDEPARTMENTS
on NEWEMPLOYEES.DEPARTMENT=NEWDEPARTMENTS.CODE
where BUDGET=(select min(BUDGET) from (select BUDGET from NEWDEPARTMENTS order by BUDGET)
              where ROWNUM<3);
-- 16. Add a new department called "Quality Assurance", with a budget of $40,000
-- and departmental code 11. And Add an employee called "Mary Moore" in that
-- department, with SSN 847-21-9811.
INSERT INTO NewDepartments(Code,Name,Budget) VALUES(11,'Quality Assurance',40000);
INSERT INTO NewEmployees(SSN,Name,LastName,Department)
VALUES('847219811','Mary Moore','Manikutty',11);
-- 17. Reduce the budget of all departments by 10%.
update NEWDEPARTMENTS set BUDGET=BUDGET*0.9;
-- 18. Reassign all employees from the Research department (code 77) to the IT
-- department (code 14).
update NEWEMPLOYEES set DEPARTMENT=14 where DEPARTMENT=77;
-- 19. Delete from the table all employees in the IT department (code 14).
delete from NEWEMPLOYEES where DEPARTMENT=14;
-- 20. Delete from the table all employees who work in departments with a budget
-- greater than or equal to $60,000.
delete from NEWEMPLOYEES where DEPARTMENT in (select CODE from NEWDEPARTMENTS where BUDGET>=60000);
-- 21. Delete from the table all employees.
delete from NEWEMPLOYEES;