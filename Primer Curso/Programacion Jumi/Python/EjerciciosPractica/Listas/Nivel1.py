
#El Inventario Crea una lista llamada frutas con los siguientes elementos: 'manzana', 'banana', 'cereza', 'durazno', 'kiwi'.

#Imprime el segundo elemento.

#Imprime el último elemento (usando índice negativo).

#Imprime una sub-lista que contenga solo desde la 'banana' hasta el 'durazno' (inclusive).

frutas = ['manzana','banana','cereza','durazno','kiwi']
print(frutas[2])
print(frutas[-1])
print(frutas[1:4])
#Modificación Directa Dada la lista numeros = [10, 20, 30, 40, 50]:

#Cambia el valor 10 por 11.

#Cambia el valor 30 por 33.

#Imprime la lista final.
numeros = [10, 20, 30, 40, 50]
numeros[0] = 11
numeros[2]=33
print(numeros)

#La Fusión de Listas Tienes dos listas de compras: lista_a = ['pan', 'leche'] y lista_b = ['huevos', 'azucar'].

#Crea una nueva lista llamada lista_completa sumando ambas listas (concatenación).

# Agrega 'harina' al final de lista_completa.

# Imprime la lista resultante.
lista_a = ['pan', 'leche']
lista_b = ['huevos', 'azucar']
lista_completa = lista_a+lista_b

lista_completa.append("harina")
print(lista_completa)
# ¿Está en la lista? y Orden Usando la lista_completa del ejercicio anterior:
#
# Cambia el valor 'leche' por 'leche descremada'.
#
# Ordena la lista alfabéticamente (puedes usar el método .sort()).
#
# Usa el operador in para verificar si 'jamon' está en la lista e imprime el resultado (True o False).
lista_completa[1]="leche descremada"
print(lista_completa)
lista_completa.sort()
print("jamon" in lista_completa)