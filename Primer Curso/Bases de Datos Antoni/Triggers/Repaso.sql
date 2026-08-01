-- 1
create trigger num1 after insert on employees for each row
    begin
        insert into log(tablename, operation, new_value)
        values ('employees','insert',NEW.first_name);
    end;


-- Test insert
# insert into employees (employee_id, first_name, last_name, email, hire_date, job_id)
# values (998, 'Johna', 'Doe', 'JDOEA', CURDATE(), 'IT_PROG');

-- 2
create trigger num2 after delete on departments for each row
    begin
        insert into log (tablename, operation, old_value)
            values ('departments','delete',old.DEPARTMENT_NAME);
    end;

-- 3
create trigger num3 after insert on jobs for each row
    begin
        insert into log(tablename, operation, new_value)
        values ('jobs','insert',NEW.job_title);
    end;

-- 4
create trigger num4 after insert on employees for each row
    begin
        insert into job_history
        values (NEW.employee_id,new.hire_date,null,NEW.job_id,new.department_id);
    end;

-- 7
create trigger num7 after update on employees for each row
begin
    if(OLD.SALARY <> NEW.SALARY) then
        insert into log(tablename, operation, old_value, new_value)
        VALUES ('employees', 'update', CONCAT(OLD.salary, ' - ', OLD.employee_id), CONCAT(NEW.salary, ' - ', NEW.employee_id));
    end if;
end;

-- 8
create trigger num8 after update on departments for each row
    begin
        if(old.MANAGER_ID<>NEW.MANAGER_ID) then
            insert into log(tablename, operation, old_value, new_value)
            VALUES ('departments','update',old.MANAGER_ID,NEW.MANAGER_ID);
        end if;
    end;

-- 9
create trigger num9 after update on jobs for each row
    begin
        if(old.MAX_SALARY<>NEW.MAX_SALARY) then
            insert into log(tablename, operation, old_value, new_value)
            VALUES ('jobs','update',old.MAX_SALARY,NEW.MAX_SALARY);
        end if;
    end;

-- 11
create trigger num11 before insert on employees for each row
    begin
        set NEW.first_name = upper(new.FIRST_NAME);
        set new.last_name = upper(new.last_name);
    end;

-- 12
create trigger num12 before insert on employees for each row
    begin
        if(new.hire_date is null ) then
            set NEW.hire_date = now();
        end if;
    end;

-- 13
create trigger num13 before insert on departments for each row
    begin
        set NEW.DEPARTMENT_NAME = trim(NEW.DEPARTMENT_NAME);
    end;

-- 14
create trigger num14 before insert on employees for each row
    begin
        if(new.COMMISSION_PCT is null) then
            set new.COMMISSION_PCT = 0.00;
        end if;
    end;

-- 15
create trigger num15 before insert on jobs for each row
    begin
        if(NEW.MIN_SALARY<1000) then
            set NEW.MIN_SALARY = 1000;
        end if;
    end;

-- 16
create trigger num16 before update on employees for each row
    begin
        if(NEW.salary >= old.salary*1.30) then
            signal sqlstate '45000' set message_text = 'No se puede subir tanto el salario';
        end if;
    end;

-- 17
create trigger num17 before insert on employees for each row
    begin
        if(NEW.salary <2000) then
            set NEW.job_id = 'ST_CLERK';
        end if;
    end;

-- 19
create trigger num19 before update on job_history for each row
begin
    if (NEW.END_DATE <= NEW.START_DATE) then
        signal sqlstate '45000' set message_text = 'La fecha de salida debe ser estrictamente mayor a la entrada';
    end if;
end;

-- 20
create trigger num20 before insert on departments for each row
    begin
        if(new.MANAGER_ID is null ) then
            set NEW.MANAGER_ID = 0;
        end if;
    end;

-- 21
create trigger num21 before delete on departments for each row
    begin
        signal sqlstate '45000' set message_text = 'No puedes eliminar departamentos';
    end;

-- 22
create trigger num22 before insert on employees for each row
    begin
        if(NEW.salary >70000) then
            signal sqlstate '45000' set message_text  = 'Salary exceeds executive limit';
        end if;
    end;

-- 23
create trigger num23update
    before update
    on employees
    for each row
begin
    if (dayname(now()) = 'Sunday' and OLD.salary <> NEW.salary) then
        signal sqlstate '45000' set message_text = 'Cannot update salary on Sunday';
    end if;
end;

create trigger num23delete
    before delete
    on employees
    for each row
begin
    if (dayname(now()) = 'Sunday') then
        signal sqlstate '45000' set message_text = 'Cannot delete employees on Sunday';
    end if;
end;

-- 25
create trigger num25
    before update
    on employees
    for each row
    begin
        if(old.employee_id<>new.employee_id) then
            signal sqlstate '45000' set message_text ='No puedes modificar los id';
        end if;
    end;

-- 26
create trigger num26
    before update
    on employees
    for each row
    begin
        declare mins decimal(6,0);
        declare maxs decimal(6,0);

    if(OLD.salary<>new.salary ) then
        select MIN_SALARY,MAX_SALARY into mins,maxs
        from jobs
        where jobs.JOB_ID=new.job_id;

        if(new.salary not between mins and maxs) then
            signal sqlstate '45000' set message_text = 'No es posbile este salario';
        end if;
    end if;

    end;

-- 29
create trigger num29 before insert on departments for each row
    begin
        if not exists(select 1 from employees e where NEW.MANAGER_ID=e.EMPLOYEE_ID) then
            signal sqlstate '45000' set message_text = 'El empleado no existe';
        end if;
    end;

create trigger num29 before insert on departments for each row
    begin
        if (new.MANAGER_ID not in (select employees.EMPLOYEE_ID from employees)) then
            signal sqlstate '45000' set message_text = 'No existe el manager';
        end if;
    end;

-- 30
ALTER TABLE employees
    ADD COLUMN salary_tax DECIMAL(8,2);


CREATE TRIGGER num30
    BEFORE INSERT ON employees
    FOR EACH ROW
BEGIN
    SET NEW.salary_tax = NEW.salary * 0.1;
END;

-- Cuando metemos un empleado hay que buscar un manager con menos empleados a su cargo
create trigger  num31
    before insert on employees
    for each row
    begin
        set new.manager_id = (select MANAGER_ID
                              from employees
                              group by MANAGER_ID
                              order by count(*)
                              limit 1);
    end;