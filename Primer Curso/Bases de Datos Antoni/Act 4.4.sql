-- 1. Write a SQL statement to add a column surname to the table employee
ALTER TABLE employee ADD COLUMN Surname varchar(255);

-- 2. Write a SQL statement to add a column ID as the first (FIRST) column of the table provider.
ALTER TABLE provider ADD COLUMN ID int first;

-- 3. Write a SQL statement to add a column quantity after barcode to the table toProvide.
alter table toProvide add column quantity int after barcode;

-- 4. Write a SQL statement to drop the column phone from the table provider.
alter table provider drop column phone;

-- 5 Write a SQL statement to change the name of the column name to first_name, keeping the data type and size same.
alter table employee rename column name to first_name;

-- 6. Write a SQL statement to change the data type of the column country_id to integer in the table locations.
alter table locations modify COUNTRY_ID int;

-- 7. Write a SQL statement to add a primary key for the column location_id in the locations table.
alter table locations add constraint primary key(LOCATION_ID);

-- 8. Write a SQL statement to drop the existing primary from the table locations.
alter table locations drop primary key;

-- 9. Write a SQL statement to add a primary key for a combination of columns location_id and country_id.
alter table locations add primary key(LOCATION_ID,COUNTRY_ID);

-- 10. Write a SQL statement to add a foreign key on job_id column of job_history table referencing to the primary key job_id of jobs table.
delete from job_history where EMPLOYEE_ID=0;
ALTER TABLE jobs ENGINE = InnoDB;
ALTER TABLE job_history ENGINE = InnoDB;
alter table countries engine=innodb;
alter table locations engine=innodb;
alter table employees engine=innodb;
alter table job_history add constraint fk_job_id foreign key (JOB_ID) references  jobs(JOB_ID);
