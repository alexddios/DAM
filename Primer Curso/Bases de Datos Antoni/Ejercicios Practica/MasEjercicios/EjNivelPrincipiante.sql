-- 1
select * from employees;

-- 2
select employees.LAST_NAME,employees.SALARY from employees;

-- 3
select distinct e.JOB_ID from employees e;

-- 4
select concat(e.FIRST_NAME, ' ',e.LAST_NAME) as "Name",e.SALARY from employees e
where e.SALARY=17000;

-- 5
select * from employees e
where e.SALARY>=15000;

-- 6
select d.DEPARTMENT_NAME from departments d
order by d.DEPARTMENT_NAME;

-- 7
select e.HIRE_DATE from employees e
order by HIRE_DATE desc
limit 5;