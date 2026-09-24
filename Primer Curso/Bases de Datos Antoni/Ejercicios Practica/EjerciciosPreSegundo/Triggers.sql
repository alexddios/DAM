-- 1. Crea un trigger que, después de eliminar un país en la tabla countries, guarde el country_name en la tabla log indicando que la operación fue un "DELETE".
create trigger num1 after delete on countries for each row
    begin
        insert  into log (operation,tablename,viejo_valor)
        values ('DELETE','countries',OLD.country_name);
    end;

-- 2.
create trigger num2 after update on employees for each row
    begin
        if(OLD.salary <> NEW.salary) then
            insert into log(operation, tablename, nuevo_valor, viejo_valor)
            VALUES ('UPDATE','employees',NEW.salary,OLD.salary);
        end if;
    end;

-- 3.
create trigger num3 before insert on employees for each row
    begin
        set NEW.email= lower(new.email);
    end;

-- 4.
create trigger num4 before insert on employees for each row
    begin
        if NEW.salary is null then
            set new.salary = 4000;
        end if;
    end;

-- 5.
create trigger num5 before update  on jobs for each row
    begin
        if new.min_salary <0 then
            signal sqlstate '45000' set message_text = 'El salario mínimo no puede ser inferior a 0';
        end if;
    end;