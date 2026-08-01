# Show the employees from department 10 or 20.
select *
from employees e where e.DEPARTMENT_ID in (10,20);

# List the employees whose salary is not between 1000 and 2000.
select *
from employees e where e.SALARY not between 1000 and 2000;
#
# Show the maximum, minimum, and average salary of the employees.
select max(e.SALARY),min(e.SALARY),avg(e.SALARY)
from employees e;
#
# Show the total number of employees.
select count(*) from employees;
#
# Show the number of employees per department.
select e.DEPARTMENT_ID,count(*) from employees e group by e.DEPARTMENT_ID;
#
# Show the employees whose name has exactly 5 letters.
select * from employees e where e.FIRST_NAME like '_____';
#
# Show the employees whose name ends with ez.
select * from employees e where e.FIRST_NAME like '%ez';
#
# Show the salary rounded to 2 decimal places.
select round(e.SALARY,2) from employees e;
#
# Show the salary (salary * 12).
select e.SALARY * 12 from employees e;
#
# Show the employees ordered by seniority (oldest first).
select * from employees e order by e.HIRE_DATE;