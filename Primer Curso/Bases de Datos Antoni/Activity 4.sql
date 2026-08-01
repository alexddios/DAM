-- 1.
select LOCATION_ID,STREET_ADDRESS,CITY,STATE_PROVINCE,COUNTRY_NAME
from locations join countries
on locations.COUNTRY_ID=countries.COUNTRY_ID;

select LOCATION_ID,STREET_ADDRESS,CITY,STATE_PROVINCE,
       (select countries.COUNTRY_NAME from countries where l.COUNTRY_ID=countries.COUNTRY_ID) as "Country"
from locations l;

-- 2.
select concat(FIRST_NAME, ' ', LAST_NAME) as 'Name', employees.department_Id, departments.DEPARTMENT_NAME
from employees left join departments
on employees.DEPARTMENT_ID=departments.DEPARTMENT_ID;

select concat(FIRST_NAME, ' ', LAST_NAME) as 'Name',department_id,
(select DEPARTMENT_NAME from departments where e.DEPARTMENT_ID=DEPARTMENT_ID) from employees e;

-- 3.
select concat(FIRST_NAME, ' ', LAST_NAME) as 'Name', employees.department_Id, departments.DEPARTMENT_NAME,locations.CITY, countries.COUNTRY_NAME
from employees left join departments
on employees.DEPARTMENT_ID=departments.DEPARTMENT_ID
left join locations
on departments.LOCATION_ID=locations.LOCATION_ID
left join countries
on locations.COUNTRY_ID=countries.COUNTRY_ID;

-- 4
select e.FIRST_NAME,e.MANAGER_ID,m.EMPLOYEE_ID,m.FIRST_NAME
from employees e left join employees m
on e.MANAGER_ID = m.EMPLOYEE_ID;

-- 5
select concat(FIRST_NAME,' ',LAST_NAME) as "Name",HIRE_DATE from employees e
where e.HIRE_DATE > (select e1.HIRE_DATE from employees e1 where e1.LAST_NAME='Jones');
SELECT
    concat(e.FIRST_NAME, ' ', e.LAST_NAME) AS 'Name',
    e.HIRE_DATE
FROM employees e
         JOIN employees j ON j.LAST_NAME = 'Jones'
WHERE e.HIRE_DATE > j.HIRE_DATE;

-- 6
select  d.DEPARTMENT_NAME,count(e.EMPLOYEE_ID) as "Number of employees in the department"
from departments d left join  employees e
on d.DEPARTMENT_ID=e.DEPARTMENT_ID
group by d.DEPARTMENT_ID;
-- 7
select h.EMPLOYEE_ID,j.JOB_TITLE,DATEDIFF(h.END_DATE, h.START_DATE) AS "Days"
FROM jobs j
         JOIN job_history h ON j.JOB_ID = h.JOB_ID
WHERE h.DEPARTMENT_ID = 90;

-- 8
SELECT
    d.DEPARTMENT_ID,
    d.DEPARTMENT_NAME,
    e.FIRST_NAME AS Manager_Name
FROM departments d
         left join  employees e ON d.MANAGER_ID = e.EMPLOYEE_ID;
-- 9
select d.DEPARTMENT_NAME, concat(e.FIRST_NAME, ' ',e.LAST_NAME) as "Name", l.CITY
from departments d left join employees e
on d.MANAGER_ID=e.EMPLOYEE_ID
left join locations l on d.LOCATION_ID=l.LOCATION_ID;

-- 10
select j.JOB_TITLE,ROUND(avg(e.SALARY))
from jobs j left join employees e
on j.JOB_ID=e.JOB_ID
group by j.JOB_ID;

-- 11
select j.JOB_TITLE,concat(e.FIRST_NAME, ' ',e.LAST_NAME) as "Name", (e.SALARY-j.MIN_SALARY) as "Salary difference"
    from jobs j  right join employees e
on j.JOB_ID=e.JOB_ID;

select j.JOB_TITLE,concat(e.FIRST_NAME, ' ',e.LAST_NAME) as "Name",
(e.SALARY-(select min(SALARY) from employees where j.JOB_ID=e.JOB_ID)) as "Salary difference"
from jobs j  right join employees e
on j.JOB_ID=e.JOB_ID;
-- 12
select j.*, concat(e.FIRST_NAME,' ',e.LAST_NAME) as "Name",e.SALARY
from job_history j left join employees e
on j.JOB_ID=e.JOB_ID
where e.SALARY>10000;
-- 13 Write a query to display department name, name (first_name, last_name), hire date, salary of the manager for all managers whose experience is more than 15 years.
SELECT
    d.department_name,
    CONCAT(e.first_name, ' ', e.last_name) AS manager_name,
    e.hire_date,
    e.salary
FROM departments d
left JOIN employees e ON d.manager_id = e.employee_id
WHERE DATEDIFF(CURDATE(), e.hire_date) / 365 > 15;