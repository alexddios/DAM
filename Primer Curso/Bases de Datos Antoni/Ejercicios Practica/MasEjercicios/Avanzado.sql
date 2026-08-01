-- 6
select
    concat(e.FIRST_NAME,' ',e.LAST_NAME) as "Name",
    d.DEPARTMENT_NAME,
    l.CITY,
    c.COUNTRY_NAME
from employees e left join departments d
on e.DEPARTMENT_ID=d.DEPARTMENT_ID
left join locations l
on d.LOCATION_ID = l.LOCATION_ID
left join countries c
on l.COUNTRY_ID=c.COUNTRY_ID;

-- 7
SELECT d.DEPARTMENT_NAME, COUNT(e.EMPLOYEE_ID)
FROM departments d LEFT JOIN employees e
ON d.DEPARTMENT_ID = e.DEPARTMENT_ID GROUP BY d.DEPARTMENT_NAME;

-- 8
select e.FIRST_NAME,e.LAST_NAME from employees e
join departments d on e.DEPARTMENT_ID=d.DEPARTMENT_ID
WHERE d.DEPARTMENT_NAME = 'IT' OR d.DEPARTMENT_NAME = 'Marketing';

-- 9
select e.* from employees e
left join  jobs j on e.JOB_ID=j.JOB_ID
where e.SALARY<j.MIN_SALARY or e.SALARY>j.MAX_SALARY;

-- 10
SELECT 'DEPARTAMENTO' AS "Tipo de Entidad", DEPARTMENT_NAME AS "Nombre"
FROM departments

UNION

SELECT 'CIUDAD' AS "Tipo de Entidad", CITY AS "Nombre"
FROM locations;
-- 11
select e.FIRST_NAME,e.SALARY from employees e
where e.SALARY> (select avg(SALARY) from employees);

-- 12
SELECT c.COUNTRY_NAME, COUNT(d.DEPARTMENT_ID) as "Departamentos asignados" from countries c
left join locations l on c.COUNTRY_ID=l.COUNTRY_ID
left join departments d on l.LOCATION_ID=d.LOCATION_ID
GROUP BY c.COUNTRY_NAME;

-- 13
select max(e.SALARY) as "Salrio Máximo",d.DEPARTMENT_NAME from employees e
left join departments d on e.DEPARTMENT_ID=d.DEPARTMENT_ID
group by e.DEPARTMENT_ID
having `Salrio Máximo`>10000
ORDER BY "Salrio Máximo" DESC;
