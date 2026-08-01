-- 1
create trigger num1 before delete on SALA for each row
    begin
        delete from INGRESSOS where OLD.SALA_COD=INGRESSOS.SALA_COD and INGRESSOS.HOSPITAL_COD=OLD.HOSPITAL_COD;
        delete from PLANTILLA where OLD.SALA_COD=PLANTILLA.SALA_COD and OLD.SALA_COD=PLANTILLA.SALA_COD;
    end;

delete from SALA where HOSPITAL_COD = 22 and SALA_COD = 2;
-- 2
create trigger num2 before insert on DOCTOR for each row
    begin
        declare n tinyint;
        select HOSPITAL.HOSPITAL_COD into n
        from DOCTOR right join HOSPITAL
                               on DOCTOR.HOSPITAL_COD=HOSPITAL.HOSPITAL_COD
                                   and DOCTOR.ESPECIALITAT= new.ESPECIALITAT
        group by HOSPITAL.HOSPITAL_COD
        order by count(DOCTOR.DOCTOR_NO) limit 1;

        set NEW.HOSPITAL_COD=n;
    end;

insert into DOCTOR values (13,998,'misscardiolog','Cardiologia');

-- 4
create trigger num4 before update on PLANTILLA for each row
begin
    if(OLD.TORN != 'N' and NEW.TORN = 'N') then
        set NEW.SALARI=old.SALARI*1.3;
    elseif(OLD.TORN = 'N' and NEW.TORN != 'N') then
        set NEW.SALARI=OLD.SALARI/1.3;
    end if;
end;

update PLANTILLA set TORN='T' where EMPLEAT_NO=3106;

-- 5
alter table SALA
add column free_beds boolean default false;

