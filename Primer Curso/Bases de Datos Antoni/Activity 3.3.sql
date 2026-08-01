-- 1
select count(*),`SALARY` from employees
group by `SALARY`;

-- 2
select min(`SALARY`),`MANAGER_ID` from employees
group by `MANAGER_ID`;

-- 3
select sum(`SALARY`),`JOB_ID` from employees
group by `JOB_ID`;

-- 4
select avg(`SALARY`), `JOB_ID` from employees
  where `JOB_ID`!= (select `JOB_ID` from jobs where `JOB_TITLE`="Programmer")
group by `JOB_ID`; 

-- 5
select sum(`SALARY`),max(`SALARY`),min(`SALARY`), avg(`SALARY`),`JOB_ID` from employees
where `DEPARTMENT_ID`=90
group by `JOB_ID`;

-- 6
select max(`SALARY`), `JOB_ID` from employees
group by `JOB_ID`
having max(`SALARY`) between 4000 and 9000;

-- 7
select avg(`SALARY`), `DEPARTMENT_ID` from employees
group by `DEPARTMENT_ID`
having count(*)>10;

-- 8
select count(*), (`SALARY`>6000) from employees
group by (`SALARY`>6000);