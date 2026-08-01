-- 41
select e.first_name, e.last_name, d.department_name
from employees e join departments d on e.department_id = d.department_id;

-- 42
select e.*
from employees e left join departments d on e.department_id = d.department_id;

-- 43
select d.DEPARTMENT_NAME,l.CITY
from departments d left join locations l on d.location_id = l.location_id;

-- 44
select l.CITY,c.COUNTRY_NAME
from locations l left join countries c on l.country_id = c.country_id;

-- 45
select c.COUNTRY_NAME, r.REGION_NAME
from countries c left join regions r on c.region_id = r.region_id;

-- 46
select concat(e.first_name,' ',e.last_name) as Employee,d.department_name,l.city,c.country_name,r.region_name
from employees e left join departments d on e.department_id = d.department_id
left join locations l on d.location_id = l.location_id
left join countries c on l.country_id = c.country_id
left join regions r on c.region_id = r.region_id;

-- 47
select e.first_name, e.last_name, j.job_title
from employees e left join jobs j on e.job_id = j.job_id;

-- 48
select e.salary, j.MIN_SALARY, j.MAX_SALARY
from employees e join jobs j on e.job_id = j.job_id;

-- 49
select e.*
from employees e left join jobs j on e.job_id = j.job_id
where e.SALARY not between j.MIN_SALARY and j.MAX_SALARY;

-- 50
select e.first_name, e.last_name, concat(m.first_name,' ',m.last_name) as Manager
from employees e left join employees m on e.manager_id = m.employee_id;
