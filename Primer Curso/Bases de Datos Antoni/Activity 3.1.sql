-- 1. Mostrar nombres usando alias "First Name", "Last Name"
SELECT first_name AS "First Name", last_name AS "Last Name" 
FROM employees;

-- 2. Obtener IDs de departamento únicos
SELECT DISTINCT department_id 
FROM employees;

-- 3. Obtener todos los detalles de empleados ordenados por nombre descendente
SELECT * FROM employees 
ORDER BY first_name DESC;

-- 4. Obtener nombres, salario y PF (15% del salario)
SELECT first_name, last_name, salary, salary * 0.15 AS PF 
FROM employees;

-- 5. Obtener ID, nombre completo (concatenado) y salario ordenado ascendentemente
-- Nota: En MySQL se usa CONCAT para unir cadenas
SELECT employee_id, CONCAT(first_name, ' ', last_name) AS "Full Name", salary 
FROM employees 
ORDER BY salary ASC;

-- 6. Obtener el total de salarios a pagar
SELECT SUM(salary) 
FROM employees;

-- 7. Obtener el salario máximo y mínimo
SELECT MAX(salary) as "Salario Máximo", MIN(salary) as "Salario Mínimo" 
FROM employees;

-- 8. Obtener el salario promedio y el número de empleados
SELECT AVG(salary), COUNT(*) 
FROM employees;

-- 9. Obtener el número de empleados en el departamento 90
SELECT COUNT(*) 
FROM employees 
WHERE department_id = 90;

-- 10. Obtener el número de trabajos (jobs) distintos disponibles
SELECT COUNT(DISTINCT job_id) 
FROM employees;

-- 11. Obtener todos los nombres en mayúsculas
SELECT UPPER(first_name) 
FROM employees;
-- 14. Seleccionar los primeros 10 registros

SELECT * FROM employees 
LIMIT 10;