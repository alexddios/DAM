empleados = [
    {'nombre': 'Ana', 'departamento': 'Ventas', 'salario': 3000},
    {'nombre': 'Luis', 'departamento': 'IT', 'salario': 4000},
    {'nombre': 'Marta', 'departamento': 'Ventas', 'salario': 3200}
]

# 1. Nombre del segundo (índice 1)
print(empleados[1]['nombre'])

# 2. Modificar salario de Ana (índice 0)
empleados[0]['salario'] = 3500

# 3. Filtrar por Ventas
print("--- Empleados de Ventas ---")
for e in empleados:
    if e['departamento'] == 'Ventas':
        print(e['nombre'])

# 4. Promedio de salarios
total_salarios = 0
for e in empleados:
    total_salarios += e['salario'] # Sumamos cada salario

promedio = total_salarios / len(empleados)
print(f"Salario promedio: {promedio}")