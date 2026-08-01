-- Display all the data from the employees table.
 select * from employees;

-- List the name and salary of all employees.
select first_Name, salary from employees;
--
-- Show the employees whose salary is greater than 2000.
select * from employees where salary > 2000;
--
-- Show the employees who belong to department 10.
select * from employees where department_id = 10;
--
-- List the employees ordered by salary from highest to lowest.
select * from employees order by salary desc;
--
-- Show the 5 employees with the highest salary.
select * from employees order by salary desc limit 5;
--
-- Show the employees whose name starts with A.
select * from employees where first_name like 'A%';
--
-- List the employees hired after the year 2020.
select * from employees where hire_date > '2020-01-01';
--
-- Show the employees who do not have a commission.
select * from employees where employees.employees.commission_pct is null;
--
-- Show the employees with a salary between 1500 and 3000.
select * from employees where employees.employees.salary between 1500 and 3000;