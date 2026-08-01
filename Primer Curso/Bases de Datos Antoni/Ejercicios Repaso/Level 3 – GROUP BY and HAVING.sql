# Show the average salary per department.
select avg(e.SALARY) from employees e group by e.DEPARTMENT_ID;
#
# Show the departments with more than 3 employees.
select d.DEPARTMENT_ID from departments d
group by d.DEPARTMENT_ID having count(*) > 3;
#
# Show the department with the highest average salary.
select e.DEPARTMENT_ID, avg(e.SALARY) from employees e
group by e.DEPARTMENT_ID order by avg(e.SALARY) desc limit 1;
#
# Show the number of employees hired each year.
select year(e.HIRE_DATE), count(*) from employees e
group by year(e.HIRE_DATE);
#
# Show the departments whose total salary exceeds 20,000.
select e.DEPARTMENT_ID, sum(e.SALARY) from employees e
group by e.DEPARTMENT_ID having sum(e.SALARY) > 20000;
#
# Show the maximum salary per department, only if it exceeds 3000.
select max(e.SALARY) from employees e
group by e.DEPARTMENT_ID having max(e.SALARY) > 3000;
#
# Show the number of employees with commission and without commission.
SELECT COUNT(*) FROM employees WHERE commission_pct > 0;
SELECT COUNT(*) FROM employees WHERE commission_pct = 0 OR commission_pct IS NULL;
#
# Show the departments with at least one employee earning more than 4000.
select d.DEPARTMENT_ID from departments d, employees e
where d.DEPARTMENT_ID = e.DEPARTMENT_ID and e.SALARY > 4000;
#
# Show the average salary per job position.
select avg(e.SALARY) from employees e group by e.JOB_ID;
#
# Show the job positions with more than 2 employees.
select e.JOB_ID from employees e group by e.JOB_ID having count(*) > 2;
