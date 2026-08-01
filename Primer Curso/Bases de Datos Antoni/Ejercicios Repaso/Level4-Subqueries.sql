-- 31
select e.* from employees e
where e.SALARY>(select avg(e1.SALARY) from employees e1);

-- 32
select e.* from employees e
where e.DEPARTMENT_ID in (select DEPARTMENT_ID from employees  e2 where e2.FIRST_NAME like'John%');

-- 33
select e.* from employees e where e.SALARY = (select max(SALARY) from employees);

-- 34
select e.* from employees e where e.SALARY > (select sum(SALARY) from employees e2 where e2.DEPARTMENT_ID = 20);

-- 35
select * from departments where DEPARTMENT_ID not in
(select DEPARTMENT_ID from employees);

-- 36
select *
from employees e where e.HIRE_DATE = (select min(HIRE_DATE) from employees);
-- 37
select * from employees e where e.SALARY >
(select m.salary from employees m where m.EMPLOYEE_ID = e.MANAGER_ID);

-- 38
select *
from employees e where e.DEPARTMENT_ID =
(select DEPARTMENT_ID from employees e1 group by DEPARTMENT_ID order by count(*) desc limit 1);

-- 39
select * from employees e where e.SALARY in
(select SALARY from employees e1 where e1.DEPARTMENT_ID = 50) and e.DEPARTMENT_ID!=50;

-- 40 Show the employees whose salary is among the top 3 highest.
SELECT *
FROM employees
WHERE salary >= (
    SELECT DISTINCT salary
    FROM employees
    ORDER BY salary DESC
    LIMIT 1 OFFSET 2
)order by salary desc ;
SELECT e.*
FROM employees e
WHERE e.SALARY>(select distinct SALARY from employees order by SALARY desc limit 10,1)
order by e.SALARY;
