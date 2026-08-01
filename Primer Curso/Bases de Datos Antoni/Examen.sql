-- 1
select *
from employees  e where e.COMMISSION_PCT=0;

-- 2
select employees.EMPLOYEE_ID,concat(first_name,' ', last_name) from employees where salary > 4000;

-- 3
select first_name from employees where first_name like '%z';

-- 4
select max(salary),min(salary),avg(employees.SALARY) from employees;

-- 5
select *
from employees where manager_id in (102,103,105);

-- 6
select *
from employees where first_name like '____';

-- 7
select *
from employees
group by job_id;
-- 8
select employees.DEPARTMENT_ID,sum(employees.SALARY) from employees group by department_id having (sum(salary))>10000;

-- 9
select *
from employees e where e.SALARY > (select avg(SALARY) from employees);

-- 10
select * from departments where DEPARTMENT_ID not in (select DEPARTMENT_ID from employees);
select *
from departments where manager_id = 0;
-- 11
select day(employees.HIRE_DATE),count(*) from employees group by day(hire_date);

-- 12
select employees.FIRST_NAME,employees.LAST_NAME,jobs.JOB_TITLE from employees join jobs
on employees.JOB_ID=jobs.JOB_ID;

-- 13
select locations.CITY,countries.COUNTRY_NAME,regions.REGION_NAME from locations join countries
on locations.COUNTRY_ID=countries.COUNTRY_ID join regions
on countries.REGION_ID=regions.REGION_ID;

-- 14
select * from employees where job_id in (select JOB_ID from employees where first_name = 'John');

-- 15
select *
from employees e join employees m on e.MANAGER_ID=m.EMPLOYEE_ID where e.SALARY > m.SALARY;