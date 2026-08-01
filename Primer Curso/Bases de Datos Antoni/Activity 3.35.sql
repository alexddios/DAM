-- 1
select * from employees where salary > (select avg(salary) from employees);
-- 2
select * from employees where DEPARTMENT_ID =
                              (select DEPARTMENT_ID from employees
                               group by DEPARTMENT_ID
                               order by count(*) desc limit 1);
-- 3
select COUNTRY_NAME from countries where REGION_ID =
                                         (select REGION_ID from countries where COUNTRY_NAME ='Canada')
                                     and COUNTRY_NAME !='Canada';
-- 4
select DEPARTMENT_ID, min(salary)
from employees group by DEPARTMENT_ID;
-- 5
select * from employees where HIRE_DATE > (select min(HIRE_DATE) from employees);
-- 6. Return the jobs where MAX_SALARY is strictly greater than the highest salary paid to any employee.
select JOB_TITLE from jobs where MAX_SALARY > (select max(employees.SALARY) from employees);
-- 7.List employees whose manager’s salary 	is greater than the maximum salary of that employee’s department.
SELECT e.*
FROM employees e
WHERE (SELECT m.salary
       FROM employees m
       WHERE m.employee_id = e.manager_id) >
      (SELECT MAX(e2.salary)
       FROM employees e2
       WHERE e2.department_id = e.department_id);
-- 8.Retrieve all employees whose employee_id exists in the job_history table.
select * from employees e where e.employee_id in (select employee_id from job_history);
-- 9.Using nested subqueries (no joins), return employees whose location_id corresponds to a city that belongs to the same country_id as Tokyo.
select * from employees where  DEPARTMENT_ID in
(select DEPARTMENT_ID from departments where LOCATION_ID in
(select LOCATION_ID from locations where COUNTRY_ID =
(select COUNTRY_ID from locations where CITY = 'Seattle')));
-- 10.List all departments whose manager_id is one of the employees working in department 90.
select * from departments where MANAGER_ID in(select EMPLOYEE_ID from employees where DEPARTMENT_ID = 90);
-- 11. Employees earning more than the avg salary from their job
select * from employees e where SALARY > (select avg(SALARY) from employees where JOB_ID=e.JOB_ID);
-- 12 Job history entries whose duration is greater than the employee’s own average job-history duration
SELECT *
FROM job_history jh
WHERE (jh.end_date - jh.start_date) > (
    SELECT AVG(jh2.end_date - jh2.start_date)
    FROM job_history jh2
    WHERE jh2.employee_id = jh.employee_id
);

