-- 1. Muestra el nombre, apellidos y email de todos los empleados de la tabla employees.
select first_name,last_name,email
from employees;
-- 2. Muestra los empleados que fueron contratados antes del año 2021.
select *
from employees
where year(hire_date)<2021;

-- 3. Lista los distintos job_id que existen actualmente en la tabla employees (sin repeticiones)
select distinct job_id
from employees;

-- 4. Muestra los empleados cuyo nombre termina en la letra 'n'.
select first_name
from employees where first_name like '%n';

-- 5. Calcula el salario anual (salario * 12) de todos los empleados y llámalo salario_anual
select first_name,salary*12 as "Salario Anual"
from employees;

-- 6. Muestra el salario mínimo, máximo y la suma total de salarios por cada
select department_id,min(salary),max(salary),sum(salary)
from employees
group by department_id;

-- 7. Muestra los departamentos (department_id) que tienen un salario medio superior a 10000.
select department_id
from employees
group by department_id
having avg(salary)>10000;
-- 8. Muestra los empleados (nombre y salario) que ganan menos que la media de toda la empresa.
select e1.first_name,e1.salary
from employees e1 where salary <
(select avg(e2.salary)
 from employees e2);

-- 9. Muestra el nombre de los empleados que trabajan en el departamento de 'IT'.
select first_name
from employees where department_id =
(select department_id
 from departments
 where department_name='IT');

-- 10. Muestra el nombre del empleado, su salario y el nombre de la ciudad (city) en la que trabaja.
select e.first_name,e.salary,l.city
from employees e join departments d
on e.department_id=d.department_id
join locations l on d.location_id=l.location_id;
-- 11. Muestra el nombre de cada empleado junto con el título de su puesto (job_title) y el salario máximo permitido para ese puesto.
select e.first_name,j.job_title,j.max_salary
from employees e join jobs j
on e.job_id=j.job_id;