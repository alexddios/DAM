-- 8
select e.FIRST_NAME from employees e
where e.FIRST_NAME like 'S%';

-- 9
select e.LAST_NAME,e.SALARY from employees e
where e.SALARY between 5000 and 10000;

-- 10
select * from employees e
where e.DEPARTMENT_ID in(30,60,90);

-- 11
select * from employees e
where e.DEPARTMENT_ID=50 and e.SALARY>4000;

-- 12
select
    max(e.SALARY) as "Salario Máximo",
    min(e.SALARY) as "Salario Mínimo",
    avg(e.SALARY) as "Salario Promedio"
from employees e;

-- 13
select e.DEPARTMENT_ID,count(*) as "Empleados por departamento"
from employees e
group by e.DEPARTMENT_ID;

-- 14
select e.DEPARTMENT_ID,count(*) as "Numero de empleados"
from employees e
group by e.DEPARTMENT_ID
having `Numero de empleados`>5;

