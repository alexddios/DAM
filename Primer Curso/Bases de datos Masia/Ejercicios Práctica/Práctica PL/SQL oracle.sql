-- ============================================================
--  BASE DE DATOS: EMPRESA DE ALQUILER DE VEHÍCULOS
--  Oracle PL/SQL  |  Ejercicios prácticos
-- ============================================================

-- ============================================================
--  1. CREACIÓN DE TABLAS
-- ============================================================

-- Ciudades donde opera la empresa
CREATE TABLE CIUDAD (
                        cod_ciudad   NUMBER(4)    PRIMARY KEY,
                        nombre       VARCHAR2(50) NOT NULL,
                        provincia    VARCHAR2(50)
);

-- Categorías de vehículos (Económico, Familiar, SUV, Lujo...)
CREATE TABLE CATEGORIA (
                           cod_categoria  NUMBER(4)    PRIMARY KEY,
                           descripcion    VARCHAR2(40) NOT NULL,
                           precio_dia     NUMBER(8,2)  NOT NULL   -- precio base por día
);

-- Vehículos disponibles
CREATE TABLE VEHICULO (
                          matricula      VARCHAR2(10)  PRIMARY KEY,
                          marca          VARCHAR2(30)  NOT NULL,
                          modelo         VARCHAR2(30)  NOT NULL,
                          anyo_fab       NUMBER(4)     NOT NULL,
                          km_totales     NUMBER(10)    DEFAULT 0,
                          cod_categoria  NUMBER(4)     NOT NULL REFERENCES CATEGORIA(cod_categoria),
                          cod_ciudad     NUMBER(4)     NOT NULL REFERENCES CIUDAD(cod_ciudad),
                          disponible     CHAR(1)       DEFAULT 'S' CHECK (disponible IN ('S','N'))
);

-- Empleados de la empresa
CREATE TABLE EMPLEADO (
                          cod_empleado   NUMBER(6)    PRIMARY KEY,
                          nombre         VARCHAR2(50) NOT NULL,
                          apellidos      VARCHAR2(80) NOT NULL,
                          cargo          VARCHAR2(30) NOT NULL,  -- 'AGENTE','SUPERVISOR','DIRECTOR'
                          salario        NUMBER(9,2)  NOT NULL,
                          fecha_alta     DATE         NOT NULL,
                          cod_ciudad     NUMBER(4)    NOT NULL REFERENCES CIUDAD(cod_ciudad)
);

-- Clientes
CREATE TABLE CLIENTE (
                         cod_cliente    NUMBER(6)    PRIMARY KEY,
                         nombre         VARCHAR2(50) NOT NULL,
                         apellidos      VARCHAR2(80) NOT NULL,
                         dni            VARCHAR2(15) UNIQUE NOT NULL,
                         email          VARCHAR2(80),
                         fecha_nacim    DATE,
                         num_alquileres NUMBER(5)    DEFAULT 0  -- columna a mantener mediante PL/SQL
);

-- Alquileres
CREATE TABLE ALQUILER (
                          cod_alquiler   NUMBER(8)    PRIMARY KEY,
                          cod_cliente    NUMBER(6)    NOT NULL REFERENCES CLIENTE(cod_cliente),
                          matricula      VARCHAR2(10) NOT NULL REFERENCES VEHICULO(matricula),
                          cod_empleado   NUMBER(6)    NOT NULL REFERENCES EMPLEADO(cod_empleado),
                          fecha_inicio   DATE         NOT NULL,
                          fecha_fin      DATE,                   -- NULL si el vehículo no ha sido devuelto
                          km_recorridos  NUMBER(8)    DEFAULT 0,
                          importe_total  NUMBER(10,2)
);

-- Tabla de facturas con descuentos aplicados
CREATE TABLE FACTURA (
                         cod_factura    NUMBER(8)    PRIMARY KEY,
                         cod_alquiler   NUMBER(8)    NOT NULL REFERENCES ALQUILER(cod_alquiler),
                         fecha_emision  DATE         DEFAULT SYSDATE,
                         base_imponible NUMBER(10,2) NOT NULL,
                         descuento_pct  NUMBER(5,2)  DEFAULT 0, -- porcentaje de descuento
                         importe_final  NUMBER(10,2) NOT NULL
);


-- ============================================================
--  2. DATOS DE PRUEBA
-- ============================================================

-- CIUDADES
INSERT INTO CIUDAD VALUES (1, 'Madrid',    'Madrid');
INSERT INTO CIUDAD VALUES (2, 'Barcelona', 'Barcelona');
INSERT INTO CIUDAD VALUES (3, 'Valencia',  'Valencia');
INSERT INTO CIUDAD VALUES (4, 'Sevilla',   'Sevilla');
INSERT INTO CIUDAD VALUES (5, 'Bilbao',    'Vizcaya');

-- CATEGORÍAS
INSERT INTO CATEGORIA VALUES (1, 'Económico',  35.00);
INSERT INTO CATEGORIA VALUES (2, 'Familiar',   55.00);
INSERT INTO CATEGORIA VALUES (3, 'SUV',        80.00);
INSERT INTO CATEGORIA VALUES (4, 'Lujo',      150.00);
INSERT INTO CATEGORIA VALUES (5, 'Furgoneta',  70.00);

-- VEHÍCULOS
INSERT INTO VEHICULO VALUES ('1234-ABC','Seat',       'Ibiza',       2019, 45000, 1, 1, 'S');
INSERT INTO VEHICULO VALUES ('5678-DEF','Toyota',     'Corolla',     2020, 30000, 2, 1, 'S');
INSERT INTO VEHICULO VALUES ('9012-GHI','Nissan',     'Qashqai',     2021, 20000, 3, 2, 'S');
INSERT INTO VEHICULO VALUES ('3456-JKL','BMW',        'Serie 5',     2022,  8000, 4, 2, 'N');
INSERT INTO VEHICULO VALUES ('7890-MNO','Volkswagen', 'Crafter',     2018, 90000, 5, 3, 'S');
INSERT INTO VEHICULO VALUES ('2345-PQR','Renault',    'Clio',        2020, 55000, 1, 3, 'S');
INSERT INTO VEHICULO VALUES ('6789-STU','Ford',       'Galaxy',      2019, 40000, 2, 4, 'S');
INSERT INTO VEHICULO VALUES ('0123-VWX','Audi',       'Q7',          2021, 15000, 3, 4, 'S');
INSERT INTO VEHICULO VALUES ('4567-YZA','Mercedes',   'Clase S',     2023,  2000, 4, 5, 'S');
INSERT INTO VEHICULO VALUES ('8901-BCD','Seat',       'Alhambra',    2017, 110000,2, 1, 'N');
INSERT INTO VEHICULO VALUES ('1111-EFG','Peugeot',    '208',         2021, 25000, 1, 2, 'S');
INSERT INTO VEHICULO VALUES ('2222-HIJ','Hyundai',    'Tucson',      2020, 35000, 3, 5, 'S');

-- EMPLEADOS
INSERT INTO EMPLEADO VALUES (1, 'Carlos',   'García López',    'DIRECTOR',   3500, DATE '2015-03-01', 1);
INSERT INTO EMPLEADO VALUES (2, 'Ana',      'Martínez Ruiz',   'SUPERVISOR', 2400, DATE '2017-06-15', 1);
INSERT INTO EMPLEADO VALUES (3, 'Luis',     'Sánchez Pérez',   'AGENTE',     1800, DATE '2019-01-10', 2);
INSERT INTO EMPLEADO VALUES (4, 'María',    'López Torres',    'AGENTE',     1850, DATE '2018-09-01', 2);
INSERT INTO EMPLEADO VALUES (5, 'Pedro',    'Fernández Gil',   'SUPERVISOR', 2350, DATE '2016-11-20', 3);
INSERT INTO EMPLEADO VALUES (6, 'Laura',    'González Vega',   'AGENTE',     1750, DATE '2021-02-01', 3);
INSERT INTO EMPLEADO VALUES (7, 'Javier',   'Romero Castro',   'AGENTE',     1800, DATE '2020-07-15', 4);
INSERT INTO EMPLEADO VALUES (8, 'Elena',    'Díaz Moreno',     'SUPERVISOR', 2500, DATE '2014-05-10', 4);
INSERT INTO EMPLEADO VALUES (9, 'Tomás',    'Ruiz Navarro',    'AGENTE',     1900, DATE '2022-03-01', 5);
INSERT INTO EMPLEADO VALUES (10,'Sofía',    'Morales Ibáñez',  'DIRECTOR',   3800, DATE '2012-01-01', 5);

-- CLIENTES
INSERT INTO CLIENTE (cod_cliente,nombre,apellidos,dni,email,fecha_nacim) VALUES
    (1,'Jorge',  'Alonso Vera',    '11111111A','jorge@mail.com',  DATE '1985-04-12');
INSERT INTO CLIENTE (cod_cliente,nombre,apellidos,dni,email,fecha_nacim) VALUES
    (2,'Marta',  'Blanco Ramos',   '22222222B','marta@mail.com',  DATE '1992-08-25');
INSERT INTO CLIENTE (cod_cliente,nombre,apellidos,dni,email,fecha_nacim) VALUES
    (3,'David',  'Campos Nieto',   '33333333C','david@mail.com',  DATE '1978-11-03');
INSERT INTO CLIENTE (cod_cliente,nombre,apellidos,dni,email,fecha_nacim) VALUES
    (4,'Rosa',   'Delgado Pons',   '44444444D','rosa@mail.com',   DATE '1990-01-30');
INSERT INTO CLIENTE (cod_cliente,nombre,apellidos,dni,email,fecha_nacim) VALUES
    (5,'Álvaro', 'Esteve Molina',  '55555555E','alvaro@mail.com', DATE '1988-07-14');
INSERT INTO CLIENTE (cod_cliente,nombre,apellidos,dni,email,fecha_nacim) VALUES
    (6,'Pilar',  'Fuentes Soler',  '66666666F','pilar@mail.com',  DATE '1995-03-22');
INSERT INTO CLIENTE (cod_cliente,nombre,apellidos,dni,email,fecha_nacim) VALUES
    (7,'Manuel', 'Gallego Reyes',  '77777777G','manuel@mail.com', DATE '1972-09-05');
INSERT INTO CLIENTE (cod_cliente,nombre,apellidos,dni,email,fecha_nacim) VALUES
    (8,'Carmen', 'Herrera Leal',   '88888888H','carmen@mail.com', DATE '1999-12-18');

-- ALQUILERES
INSERT INTO ALQUILER VALUES (1, 1,'1234-ABC',3, DATE '2024-01-05', DATE '2024-01-08', 320,  105.00);
INSERT INTO ALQUILER VALUES (2, 2,'9012-GHI',4, DATE '2024-01-10', DATE '2024-01-15', 550,  400.00);
INSERT INTO ALQUILER VALUES (3, 3,'5678-DEF',3, DATE '2024-02-01', DATE '2024-02-05', 410,  220.00);
INSERT INTO ALQUILER VALUES (4, 1,'2345-PQR',6, DATE '2024-02-14', DATE '2024-02-16', 180,   70.00);
INSERT INTO ALQUILER VALUES (5, 4,'3456-JKL',7, DATE '2024-03-01', DATE '2024-03-03', 200,  300.00);
INSERT INTO ALQUILER VALUES (6, 5,'6789-STU',7, DATE '2024-03-10', DATE '2024-03-14', 430,  220.00);
INSERT INTO ALQUILER VALUES (7, 6,'0123-VWX',8, DATE '2024-04-01', DATE '2024-04-06', 500,  400.00);
INSERT INTO ALQUILER VALUES (8, 2,'7890-MNO',5, DATE '2024-04-15', DATE '2024-04-18', 600,  210.00);
INSERT INTO ALQUILER VALUES (9, 7,'1111-EFG',3, DATE '2024-05-01', DATE '2024-05-04', 290,  105.00);
INSERT INTO ALQUILER VALUES (10,8,'4567-YZA',9, DATE '2024-05-20', NULL,              0,    NULL);
INSERT INTO ALQUILER VALUES (11,3,'2222-HIJ',5, DATE '2024-06-01', DATE '2024-06-07', 480,  480.00);
INSERT INTO ALQUILER VALUES (12,1,'9012-GHI',4, DATE '2024-06-15', DATE '2024-06-18', 310,  240.00);

-- FACTURAS
INSERT INTO FACTURA VALUES (1,  1,  DATE '2024-01-08',  105.00,  0,   105.00);
INSERT INTO FACTURA VALUES (2,  2,  DATE '2024-01-15',  400.00, 10,   360.00);
INSERT INTO FACTURA VALUES (3,  3,  DATE '2024-02-05',  220.00,  0,   220.00);
INSERT INTO FACTURA VALUES (4,  4,  DATE '2024-02-16',   70.00,  5,    66.50);
INSERT INTO FACTURA VALUES (5,  5,  DATE '2024-03-03',  300.00, 15,   255.00);
INSERT INTO FACTURA VALUES (6,  6,  DATE '2024-03-14',  220.00,  0,   220.00);
INSERT INTO FACTURA VALUES (7,  7,  DATE '2024-04-06',  400.00,  5,   380.00);
INSERT INTO FACTURA VALUES (8,  8,  DATE '2024-04-18',  210.00,  0,   210.00);
INSERT INTO FACTURA VALUES (9,  9,  DATE '2024-05-04',  105.00,  0,   105.00);
INSERT INTO FACTURA VALUES (10,11,  DATE '2024-06-07',  480.00, 10,   432.00);
INSERT INTO FACTURA VALUES (11,12,  DATE '2024-06-18',  240.00,  0,   240.00);

COMMIT;


-- ============================================================
--  3. EJERCICIOS PL/SQL
-- ============================================================

/*
══════════════════════════════════════════════════════════════
 EJERCICIO 1
══════════════════════════════════════════════════════════════
Añade dos columnas a la tabla VEHICULO:
    - VALOR_ESTIMADO   NUMBER(10,2)
    - ANTIGUEDAD_ANIOS NUMBER(3)

A continuación, crea un bloque PL/SQL anónimo que rellene
automáticamente estas columnas según las siguientes reglas:
  · VALOR_ESTIMADO  = precio_dia de su categoría × 365 × 0.15
  · ANTIGUEDAD_ANIOS = año actual − anyo_fab


*/
ALTER TABLE VEHICULO ADD (
    valor_estimado   NUMBER(10,2),
    antiguedad_anios NUMBER(3)
    );
-- Tu solución aquí:
BEGIN
    update VEHICULO v set VALOR_ESTIMADO = (select precio_dia from CATEGORIA c
                                                              where c.cod_categoria=v.COD_CATEGORIA),
    antiguedad_anios = EXTRACT(YEAR FROM SYSDATE) - v.anyo_fab;
END;




/*
══════════════════════════════════════════════════════════════
 EJERCICIO 2
══════════════════════════════════════════════════════════════
Usando la tabla EMPLEADO, crea una FUNCIÓN que reciba como
parámetro un cargo (VARCHAR2) y devuelva el número de
empleados que tienen ese cargo.

Ejemplo de llamada:
    SELECT contar_empleados_cargo('AGENTE') FROM DUAL;
*/

CREATE OR REPLACE FUNCTION contar_empleados_cargo(p_cargo IN VARCHAR2)
    RETURN NUMBER IS
    n integer;
BEGIN
    select count(*) into n
    from EMPLEADO
    where cargo = p_cargo;
    return n;
END;
/
SELECT contar_empleados_cargo('AGENTE') FROM DUAL;

/*
══════════════════════════════════════════════════════════════
 EJERCICIO 3
══════════════════════════════════════════════════════════════
Usando la tabla CATEGORIA, crea un PROCEDIMIENTO que incremente
en 5 euros el precio por día de las categorías cuyo precio
actual sea inferior a un valor dado (parámetro del procedimiento).

Si alguna categoría, al actualizarse, superase el límite de
200 €/día, NO se realizará ninguna actualización y el
procedimiento deberá informar de ello mediante una excepción
o un mensaje de salida (DBMS_OUTPUT).
*/

CREATE OR REPLACE PROCEDURE subir_precio_categoria(p_limite IN NUMBER) IS
    v_cuantas NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO v_cuantas
    FROM CATEGORIA
    WHERE precio_dia < p_limite
      AND precio_dia + 5 > 200;

    IF v_cuantas > 0 THEN
        DBMS_OUTPUT.PUT_LINE('No se ha actualizado: alguna categoria superaria los 200 euros/dia.');
    ELSE
        UPDATE CATEGORIA
        SET precio_dia = precio_dia + 5
        WHERE precio_dia < p_limite;

        DBMS_OUTPUT.PUT_LINE('Categorias actualizadas: ' || SQL%ROWCOUNT);
    END IF;
END;
/
call subir_precio_categoria(100);

/*
══════════════════════════════════════════════════════════════
 EJERCICIO 4
══════════════════════════════════════════════════════════════
Usando la tabla ALQUILER, crea una FUNCIÓN que reciba una
matrícula de vehículo y devuelva el total de kilómetros
recorridos por ese vehículo en todos sus alquileres.

Ejemplo de llamada:
    SELECT km_totales_vehiculo('1234-ABC') FROM DUAL;
*/
CREATE OR REPLACE FUNCTION km_totales_vehiculo(p_matricula IN VARCHAR2)
    RETURN NUMBER IS
    km_total NUMBER := 0;
BEGIN
    -- Ensure NULL is returned if there are no matching records
    SELECT NVL(SUM(km_recorridos), 0)
    INTO km_total
    FROM ALQUILER
    WHERE matricula = p_matricula;

    RETURN km_total;
EXCEPTION
    WHEN OTHERS THEN
        -- Handle any unexpected errors
        RETURN NULL;
END;
/
SELECT km_totales_vehiculo('1234-ABC')
FROM DUAL;

/*
══════════════════════════════════════════════════════════════
 EJERCICIO 5
══════════════════════════════════════════════════════════════
Usando la tabla EMPLEADO, crea un PROCEDIMIENTO que ascienda
al empleado con más antigüedad que tenga el cargo de 'AGENTE'
a 'SUPERVISOR', aumentándole el salario un 30%.

  · Si hay más de un agente con la misma fecha de alta más
    antigua, el procedimiento NO realizará ningún cambio y
    mostrará un aviso por DBMS_OUTPUT.
  · Si no hay ningún agente, también se mostrará un mensaje
    informativo.
*/

CREATE OR REPLACE PROCEDURE ascender_agente_antiguo IS
    fecha date;
    n int;
BEGIN
    select min(fecha_alta) into fecha
    from EMPLEADO
    where cargo='AGENTE';

    select count(*) into n
    from EMPLEADO
    where fecha_alta=fecha and cargo='AGENTE';

    if (fecha is null or n=0) then
        DBMS_OUTPUT.PUT_LINE('No hay agentes');
    elsif(n>1) then
        DBMS_OUTPUT.PUT_LINE('Hay mas de un agente con la misma fecha');
    else
        update EMPLEADO
        set salario=salario*1.3,cargo='SUPERVISOR'
        where fecha_alta=fecha and cargo='AGENTE';
    end if;
END;
/


/*
══════════════════════════════════════════════════════════════
 EJERCICIO 6
══════════════════════════════════════════════════════════════
Usando la tabla ALQUILER y FACTURA, crea un PROCEDIMIENTO que
aplique un descuento adicional del 2% (acumulado al
descuento_pct actual) en las facturas de los alquileres
gestionados por un empleado concreto (parámetro: cod_empleado).
El importe_final de cada factura deberá recalcularse también.
*/

CREATE OR REPLACE PROCEDURE aplicar_descuento_empleado(p_cod_empleado IN NUMBER) IS
BEGIN
    UPDATE FACTURA F
    SET descuento_pct = descuento_pct + 2,
        importe_final = base_imponible - (base_imponible * (1-(descuento_pct + 2) / 100))
    WHERE cod_alquiler in(
        SELECT cod_alquiler
        FROM ALQUILER A
        WHERE cod_empleado=p_cod_empleado
        AND F.cod_alquiler=A.cod_alquiler
        );
END;
/


/*
══════════════════════════════════════════════════════════════
 EJERCICIO 7
══════════════════════════════════════════════════════════════
Añade a la tabla CLIENTE la columna NUM_ALQUILERES NUMBER(5).
(Ya existe con valor por defecto 0 en la definición original.)

Crea un PROCEDIMIENTO que recorra todos los clientes y
actualice esa columna con el número real de alquileres que
tiene registrados en la tabla ALQUILER.
*/
CREATE OR REPLACE PROCEDURE actualizar_num_alquileres1 IS
    cli CLIENTE.COD_CLIENTE%type;
    cont int;

    cursor get_clientes is select cod_cliente from CLIENTE;
BEGIN
    open get_clientes;

    loop
        fetch get_clientes into cli;
        exit when get_clientes%NOTFOUND;

        select count(*) into cont
        from ALQUILER
        where cod_cliente = cli;

        update CLIENTE set NUM_ALQUILERES=cont where cod_cliente=cli;
    end loop;
    close get_clientes;
END;
/
CREATE OR REPLACE PROCEDURE actualizar_num_alquileres2 IS
    cont int;
BEGIN
    -- El bucle declara 'registro', abre el cursor, extrae la fila y cierra solo.
    FOR registro IN (SELECT cod_cliente FROM CLIENTE) LOOP

            select count(*) into cont
            from ALQUILER
            where cod_cliente = registro.cod_cliente;

            update CLIENTE
            set NUM_ALQUILERES = cont
            where cod_cliente = registro.cod_cliente;

        END LOOP;
END;
/
CREATE OR REPLACE PROCEDURE actualizar_num_alquileres3 IS
BEGIN
    UPDATE CLIENTE C
    SET NUM_ALQUILERES = (
        SELECT COUNT(*)
        FROM ALQUILER A
        WHERE A.cod_cliente = C.cod_cliente
    );
END;
/

/*
══════════════════════════════════════════════════════════════
 EJERCICIO 8  (ejercicio desafío)
══════════════════════════════════════════════════════════════
Crea un PROCEDIMIENTO que, dado un rango de fechas
(p_fecha_ini, p_fecha_fin), genere un informe por pantalla
(DBMS_OUTPUT) con el siguiente formato por cada ciudad:

  Ciudad: Madrid
    Vehículos alquilados : 5
    Importe total facturado : 1.250,00 €
    Empleado con más alquileres : Carlos García López (3 alquileres)

  Ciudad: Barcelona
    ...

Las ciudades sin actividad en ese período deben omitirse.
Usa cursores explícitos para recorrer los resultados.
*/

CREATE OR REPLACE PROCEDURE informe_por_ciudad(
    p_fecha_ini IN DATE,
    p_fecha_fin IN DATE
) IS
    CURSOR c_ciudades IS
        SELECT c.cod_ciudad,
               c.nombre as nombre_ciudad,
               COUNT(a.cod_alquiler) as total_vehiculos,
               SUM(f.importe_final) as total_dinero
        FROM CIUDAD c
                 JOIN EMPLEADO e ON c.cod_ciudad = e.cod_ciudad
                 JOIN ALQUILER a ON e.cod_empleado = a.cod_empleado
                 JOIN FACTURA f ON a.cod_alquiler = f.cod_alquiler
        WHERE a.fecha_inicio BETWEEN p_fecha_ini AND p_fecha_fin
        GROUP BY c.cod_ciudad, c.nombre;
        v_mejor_emp EMPLEADO.NOMBRE%type;
        v_max_alquileres int;
        v_ciudad_actual CIUDAD.COD_CIUDAD%type;
        v_nombre_ciudad CIUDAD.NOMBRE%type;
        v_total_vehiculos int;
        v_total_dinero ALQUILER.importe_total%type;
BEGIN
    open c_ciudades;

    loop
        FETCH c_ciudades INTO v_ciudad_actual, v_nombre_ciudad, v_total_vehiculos, v_total_dinero;
        EXIT WHEN c_ciudades%NOTFOUND;

        SELECT e.nombre || ' ' || e.apellidos, COUNT(a.cod_alquiler)
        INTO v_mejor_emp, v_max_alquileres
        FROM EMPLEADO e
                 JOIN ALQUILER a ON e.cod_empleado = a.cod_empleado
        WHERE e.cod_ciudad = v_ciudad_actual
          AND a.fecha_inicio BETWEEN p_fecha_ini AND p_fecha_fin
        GROUP BY e.nombre, e.apellidos
        ORDER BY COUNT(a.cod_alquiler) DESC
            FETCH FIRST 1 ROWS ONLY;

        DBMS_OUTPUT.PUT_LINE('Ciudad: ' || v_nombre_ciudad);
        DBMS_OUTPUT.PUT_LINE('  Vehículos alquilados : ' || v_total_vehiculos);
        DBMS_OUTPUT.PUT_LINE('  Importe total facturado : ' || v_total_dinero);
        DBMS_OUTPUT.PUT_LINE('  Empleado con mas alquileres : ' || v_mejor_emp);
    end loop;
    close c_ciudades;
END;
/
call informe_por_ciudad(DATE '2024-01-01', DATE '2024-01-31');
call informe_por_ciudad(DATE '2024-02-01', DATE '2024-02-28');