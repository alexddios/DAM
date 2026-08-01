-- 1
select e.FIRST_NAME,e.LAST_NAME,e.SALARY from employees e
where e.LAST_NAME like '%e%' and e.SALARY > 8000;

-- 2
select d.DEPARTMENT_NAME from departments d
where DEPARTMENT_ID between 10 and 100 and DEPARTMENT_ID not in(50,90);

-- 3
SELECT FIRST_NAME, CONCAT(EMAIL, '@empresa.com') AS "Email" FROM employees;

-- 4
select e.JOB_ID,avg(e.SALARY) as "Salario Promedio" from employees e
group by e.JOB_ID
having `Salario Promedio`>10000;

-- 5
select count(e.MANAGER_ID) from employees e;