-- 1. Escribe una sentencia SQL para añadir una columna llamada fecha_nacimiento de tipo DATE a la tabla employees.
alter table employees
add column fecha_nacimiento date;

-- 2. Escribe una sentencia SQL para cambiar el nombre de la columna email a correo_electronico en la tabla employees, manteniendo su tipo de dato.
alter table employees
change email correo_electronico
varchar(100) null;

-- 3. Escribe una sentencia SQL para eliminar la columna commission_pct de la tabla employees.
alter table employees
drop column commission_pct;

-- 4. Escribe una sentencia SQL para añadir una restricción UNIQUE a la columna department_name en la tabla departments.
alter table departments
add constraint unique(department_name);