# Ejercicio 3: Suma de la Matriz Tienes la siguiente matriz (lista de listas): matriz = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
#
# Usa dos bucles for para recorrer cada número.
#
# Calcula la suma total de todos los números dentro de la matriz.
#
# Imprime el resultado final (Debería ser 45).
matriz = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
total=0;
for x in matriz:
    for num in x:
        total+=num
print(total)

# Ejercicio 4: Contador de Pares en Sub-listas
# Dada la matriz: datos = [[1, 2], [3, 4, 5], [6, 7, 8, 9]] (Nota que tienen distintos tamaños).
#
# Recorre la matriz y cuenta cuántos números pares hay en total en todas las sub-listas.
datos = [[1, 2], [3, 4, 5], [6, 7, 8, 9]]
contador=0
for x in datos:
    for num in x:
        if num%2==0:
            contador+=1
print(contador)