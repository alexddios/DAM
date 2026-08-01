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

-- 3

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
add column llits_lliures int;

UPDATE sanitat.SALA s
SET LLITS_LLIURES = QTAT_LLITS - (
    SELECT COUNT(*)
    FROM sanitat.INGRESSOS i
    WHERE i.HOSPITAL_COD = s.HOSPITAL_COD AND i.SALA_COD = s.SALA_COD
);

DELIMITER //

CREATE TRIGGER trg_ingressos_after_insert
    AFTER INSERT ON sanitat.INGRESSOS
    FOR EACH ROW
BEGIN
    UPDATE sanitat.SALA
    SET LLITS_LLIURES = LLITS_LLIURES - 1
    WHERE HOSPITAL_COD = NEW.HOSPITAL_COD AND SALA_COD = NEW.SALA_COD;
END;
//
insert into MALALT (INSCRIPCIO,COGNOM,SEXE)
values (18104,'Fulanito','H');
insert into INGRESSOS (INSCRIPCIO, HOSPITAL_COD, SALA_COD, LLIT)
values (18104,22,1,1);

CREATE TRIGGER trg_ingressos_after_delete
    AFTER DELETE ON sanitat.INGRESSOS
    FOR EACH ROW
BEGIN
    UPDATE sanitat.SALA
    SET LLITS_LLIURES = LLITS_LLIURES + 1
    WHERE HOSPITAL_COD = OLD.HOSPITAL_COD AND SALA_COD = OLD.SALA_COD;
END;
//
delete from INGRESSOS where INSCRIPCIO=18104;

CREATE TRIGGER trg_ingressos_after_update
    AFTER UPDATE ON sanitat.INGRESSOS
    FOR EACH ROW
BEGIN
    IF OLD.HOSPITAL_COD != NEW.HOSPITAL_COD OR OLD.SALA_COD != NEW.SALA_COD THEN

        UPDATE sanitat.SALA
        SET LLITS_LLIURES = LLITS_LLIURES + 1
        WHERE HOSPITAL_COD = OLD.HOSPITAL_COD AND SALA_COD = OLD.SALA_COD;

        UPDATE sanitat.SALA
        SET LLITS_LLIURES = LLITS_LLIURES - 1
        WHERE HOSPITAL_COD = NEW.HOSPITAL_COD AND SALA_COD = NEW.SALA_COD;

    END IF;
END;
//
DELIMITER ;

-- 6
create trigger bef_insert_6 before insert on INGRESSOS for each row
    begin

    end;