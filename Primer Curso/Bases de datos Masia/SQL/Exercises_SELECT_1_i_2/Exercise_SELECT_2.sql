-- 1.List different jobs that exist in the company.
select distinct JOB from EX2_STAFF;
-- 2.Get the names, jobs and salaries of employees of the company, sorted in ascending order by
-- their job.
select  NAME,JOB,SALARY from EX2_STAFF
order by JOB;
-- 3.Get the names, jobs and salaries of company employees with a salary greater than 4500, sorted
-- in descending order by salary.
select NAME,JOB,SALARY from EX2_STAFF
where SALARY>4500
order by SALARY desc;
-- 4.Get the names, jobs, salaries and department codes for company employees, sorted by
-- department code, and if they belong to the same department, the salary from highest to lowest.
select NAME,JOB,SALARY,DEPARTMENT_CODE from EX2_STAFF
order by DEPARTMENT_CODE asc,
         SALARY desc;
-- 5. Make a list of the name, job and amount of the wage increase would correspond to each
-- employee if we decide to increase all wages by 10%.
select NAME,JOB,SALARY+SALARY*0.1 from EX2_STAFF;
-- 6.Make a list of the previous exercise but ordered by job and within it, for salary increase from
-- lowest to highest.
select NAME,JOB,SALARY+SALARY*0.1 from EX2_STAFF
order by JOB,SALARY;
-- 7.Obtain the average wage, the minimum and maximum of the employees of the company
select avg(SALARY),min(SALARY),max(SALARY) from EX2_STAFF;
-- 8.List total wages paid to employees and analysts. Count the number of employees with this job.
select sum(SALARY),count(*) from EX2_STAFF
where JOB='analyst';
-- 9. Make a list as in exercise 7, but group data by department.
select avg(SALARY) as "Average Wage",min(SALARY) as "Minimum Salary",max(SALARY) as "Maximum Salary",DEPARTMENT_CODE from EX2_STAFF
group by DEPARTMENT_CODE;
-- 10.  Get the average salary for each job but only for the placements with an average salary greater
-- than the average salary of the company.
select avg(SALARY),JOB from EX2_STAFF
group by JOB
having avg(SALARY) > (select avg(SALARY) from EX2_STAFF);
