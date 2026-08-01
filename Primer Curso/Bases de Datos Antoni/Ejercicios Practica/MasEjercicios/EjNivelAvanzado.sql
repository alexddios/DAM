-- 15
select  concat(e.FIRST_NAME,' ',e.LAST_NAME) as "Nombre",d.DEPARTMENT_NAME
from employees e join departments d
on e.DEPARTMENT_ID=d.DEPARTMENT_ID;

-- 16
select l.CITY,d.DEPARTMENT_NAME
from departments d left join locations l
on d.LOCATION_ID=l.LOCATION_ID;

-- 17
select e.FIRST_NAME,e.MANAGER_ID,m.EMPLOYEE_ID,m.FIRST_NAME
from employees e left join employees m
on e.MANAGER_ID = m.EMPLOYEE_ID;

-- 18
select REGION_NAME from regions union select COUNTRY_NAME from countries;

-- 19
select sum(e.SALARY) as "Suma salario",j.JOB_TITLE
from employees e join jobs j
on e.JOB_ID=j.JOB_ID
group by j.JOB_ID
having `Suma salario`>20000;

-- 20
select e.LAST_NAME,l.CITY
from employees e join departments d
on e.DEPARTMENT_ID= d.DEPARTMENT_ID join locations l
on d.LOCATION_ID=l.LOCATION_ID;