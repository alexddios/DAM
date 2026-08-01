-- 1
create or replace procedure LISTAR_EMPLEADOS_DEPTO(p_id_depto NUMBER) as
    v_count number :=0;
    BEGIN
        FOR reg in (SELECT NOMBRE,CARGO,SALARIO from EMPLEADOS where ID_DEPTO=p_id_depto) LOOP
            dbms_output.put_line(reg.NOMBRE||' '||reg.CARGO||' '||reg.SALARIO);
            v_count := v_count+1;
    end loop;
        if (v_count=0) then
            DBMS_OUTPUT.PUT_LINE('No hay empleados en este departamento');
        end if;
    end;

BEGIN
    LISTAR_EMPLEADOS_DEPTO(10);
END;
begin
    LISTAR_EMPLEADOS_DEPTO(50);
end;
/

-- 2
create or replace function CONTAR_PRODUCTOS(p_categoria VARCHAR2)
    return number is
    cursor cur is SELECT ID_PROD FROM PRODUCTOS WHERE CATEGORIA=p_categoria;

    v_id PRODUCTOS.ID_PROD%type;
    v_count number :=0;

    begin
        open cur;

        loop
            fetch cur into v_id;
            EXIT WHEN cur%NOTFOUND;
            v_count:=v_count+1;
        end loop;
        close cur;
        return v_count;
    end;

DECLARE
    v_total NUMBER;
BEGIN
    v_total := CONTAR_PRODUCTOS('Informatica');
    DBMS_OUTPUT.PUT_LINE('Total: ' || v_total);
END;

-- 3
create or replace procedure ALERTA_STOCK(p_minimo NUMBER) as
    cursor cur(p_lim NUMBER) is select NOMBRE,CATEGORIA,PRECIO,STOCK from PRODUCTOS where stock <= p_lim order by STOCK;

    v_nombre PRODUCTOS.NOMBRE%type;
    v_categoria PRODUCTOS.CATEGORIA%type;
    v_precio PRODUCTOS.PRECIO%type;
    v_stock PRODUCTOS.STOCK%type;
    v_count number :=0;

    begin
        open cur(p_minimo);

        loop
            fetch cur into v_nombre,v_categoria,v_precio,v_stock;
            exit when cur%notfound;

            DBMS_OUTPUT.PUT_LINE(v_nombre || ' | ' || v_categoria || ' | ' || v_precio || ' | ' || v_stock);
            v_count := v_count+1;
        end loop;
        close cur;
        DBMS_OUTPUT.PUT_LINE('Total en alerta: ' || v_count);
    end;
begin
    ALERTA_STOCK(30);
end;

begin
    ALERTA_STOCK(50);
end;

-- 4
CREATE OR REPLACE FUNCTION TOTAL_VENTAS_EMP(p_id_emp NUMBER)
    RETURN NUMBER IS
    v_sum number :=0;
    v_count number :=0;
        begin
            SELECT COUNT(*) INTO v_count
            FROM empleados
            WHERE id_emp = p_id_emp;

            IF v_count = 0 THEN
                RETURN 0;
            END IF;

            for cur in (select TOTAL from VENTAS where ID_EMP=p_id_emp) loop
                v_sum := v_sum+cur.TOTAL;
                end loop;
            return v_sum;

        end;

SELECT nombre,
       TOTAL_VENTAS_EMP(id_emp) AS total_vendido
FROM empleados
WHERE id_depto = 10;

-- 5
CREATE OR REPLACE PROCEDURE RESUMEN_VENTAS_DEPTO IS
    CURSOR cur_deptos IS
        SELECT id_depto, nombre FROM departamentos ORDER BY id_depto;
    CURSOR cur_emps(p_depto NUMBER) IS
        SELECT e.id_emp, e.nombre,
               NVL(SUM(v.total), 0) AS total_vendido
        FROM empleados e
                 LEFT JOIN ventas v ON e.id_emp = v.id_emp
        WHERE e.id_depto = p_depto
        GROUP BY e.id_emp, e.nombre;
    v_sub   NUMBER;
    v_dnom  departamentos.nombre%TYPE;
    v_enom  empleados.nombre%TYPE;
    v_vend  NUMBER;
BEGIN
    FOR rd IN cur_deptos LOOP
            DBMS_OUTPUT.PUT_LINE('[Ventas] -> ' || rd.nombre);
            v_sub := 0;
            OPEN cur_emps(rd.id_depto);
            LOOP
                FETCH cur_emps INTO v_enom, v_enom, v_vend;
                -- Nota: usar %ROWTYPE o variables correctas en práctica real
                EXIT WHEN cur_emps%NOTFOUND;
                DBMS_OUTPUT.PUT_LINE('  Empleado: ' || v_enom ||
                                     ' | Ventas: ' || v_vend);
                v_sub := v_sub + v_vend;
            END LOOP;
            CLOSE cur_emps;
            DBMS_OUTPUT.PUT_LINE('  Subtotal ' || rd.nombre ||
                                 ': ' || v_sub);
        END LOOP;
END;
/
begin
    RESUMEN_VENTAS_DEPTO();
end;
-- 6
CREATE OR REPLACE FUNCTION PROD_MAS_VENDIDO(p_categoria VARCHAR2)
    RETURN VARCHAR2 IS
    CURSOR cur_top IS
        SELECT p.nombre, SUM(v.cantidad) AS total_uds
        FROM productos p
                 JOIN ventas v ON p.id_prod = v.id_prod
        WHERE p.categoria = p_categoria
        GROUP BY p.nombre
        ORDER BY total_uds DESC;
    v_nombre productos.nombre%TYPE;
    v_uds    NUMBER;
BEGIN
    OPEN cur_top;
    FETCH cur_top INTO v_nombre, v_uds;
    CLOSE cur_top;
    IF v_nombre IS NULL THEN
        RETURN 'Sin ventas';
    END IF;
    RETURN v_nombre;
END;
/

SELECT PROD_MAS_VENDIDO('Informatica')  AS mas_vendido_info,
       PROD_MAS_VENDIDO('Mobiliario')    AS mas_vendido_mob
FROM DUAL;

