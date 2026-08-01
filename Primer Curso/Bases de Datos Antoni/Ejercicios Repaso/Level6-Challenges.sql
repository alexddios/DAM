-- 51
select e.FIRST_NAME,salary,abs(round(e.SALARY-
(select avg(e1.SALARY) from employees e1
where e.DEPARTMENT_ID=e1.department_id),2)) as "Diferencia"
from employees e;

-- 52
SELECT e.FIRST_NAME, e.SALARY, e.DEPARTMENT_ID
FROM employees e
WHERE (e.DEPARTMENT_ID, e.SALARY) IN (
    SELECT DEPARTMENT_ID, MAX(SALARY)
    FROM employees
    group by DEPARTMENT_ID
);

-- 53
select sum(e1.SALARY) as SumaSalario,e1.DEPARTMENT_ID from employees e1
group by e1.DEPARTMENT_ID
having SumaSalario>50000;

-- 54
select e.FIRST_NAME as "Empleado",e.SALARY as "Salario Empleado",m.SALARY as "Salario Manager",m.FIRST_NAME as "Manager"
from employees e join employees m on e.MANAGER_ID=m.EMPLOYEE_ID
where e.SALARY>m.SALARY;

-- 55
select e.FIRST_NAME,e.SALARY,(select avg(e1.SALARY) from employees e1
where e.DEPARTMENT_ID=e1.department_id  group by e1.DEPARTMENT_ID)as "Salario Medio Departamento"
from employees e
where e.SALARY>(select avg(e1.SALARY) from employees e1
                where e.DEPARTMENT_ID=e1.department_id  group by e1.DEPARTMENT_ID);

-- 56
select
    r.REGION_NAME,
    COUNT(e.EMPLOYEE_ID) as Total_Empleados
from regions r
         left join countries c on r.REGION_ID = c.REGION_ID
         left join locations l on c.COUNTRY_ID = l.COUNTRY_ID
         left join departments d on l.LOCATION_ID = d.LOCATION_ID
         left join employees e on d.DEPARTMENT_ID = e.DEPARTMENT_ID
group by r.REGION_ID, r.REGION_NAME;

-- 57
select
    c.COUNTRY_NAME,
    round(IFNULL(AVG(e.SALARY), 0)) as "Salario Medio"
from countries c
left join locations l on c.COUNTRY_ID = l.COUNTRY_ID
left join departments d on l.LOCATION_ID = d.LOCATION_ID
left join employees e on d.DEPARTMENT_ID = e.DEPARTMENT_ID
group by c.COUNTRY_NAME,c.COUNTRY_ID;

-- 58
select
    l.CITY,
    COUNT(e.EMPLOYEE_ID) AS NumeroEmpleados
from locations l
         join departments d on l.LOCATION_ID = d.LOCATION_ID
         join employees e on d.DEPARTMENT_ID = e.DEPARTMENT_ID
group by l.LOCATION_ID, l.CITY
having NumeroEmpleados = (
    select COUNT(emp.EMPLOYEE_ID) AS total
    from employees emp
             join departments dep ON emp.DEPARTMENT_ID = dep.DEPARTMENT_ID
             join locations loc ON dep.LOCATION_ID = loc.LOCATION_ID
    group by loc.LOCATION_ID
    order by total desc
    limit 1
    );
-- 59
select distinct e.first_name, e.last_name
from employees e
         join job_history jh on e.employee_id = jh.employee_id
where e.department_id != jh.department_id;
-- 60 Empleados que han tenido más de un JOB_ID
select e.first_name, e.last_name, COUNT(jh.job_id) + 1 as total_puestos
from employees e
         join job_history jh ON e.employee_id = jh.employee_id
group by e.employee_id
having total_puestos > 1;