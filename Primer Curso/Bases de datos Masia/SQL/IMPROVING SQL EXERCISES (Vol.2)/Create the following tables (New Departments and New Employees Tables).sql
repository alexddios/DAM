create table vol2_departments(
    Code int primary key,
    Name varchar(30),
    Budget number(8,2)
);

create table vol2_employees(
  SSN int primary key,
  Name varchar(30),
  LastName varchar(30),
  DepartmentCode int,

constraint fk_vol2_department_code
foreign key (DepartmentCode)
references vol2_departments(Code)
);
