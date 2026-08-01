#Ejercicio 3
invitados = []
invitados.append('Ana')
invitados.append('Luis')
invitados.append('Pedro')
invitados.insert(0,'Marta')
invitados.remove('Luis')
print(invitados)
print(len(invitados))
#Ejercicio 4
valores = [4, 8, 15, 16, 23, 42]
pares=[]
for par in valores:
    if par % 2==0:
        pares.append(par)

print(pares)
# El Analista de Notas Tienes las siguientes notas de un alumno: notas = [7.5, 9.0, 6.5, 10.0, 4.0, 9.0].
#
# Elimina la nota más baja de la lista (pista: usa min() para encontrarla y remove() para borrarla).
#
# Calcula el promedio de las notas restantes (suma de notas dividido por la cantidad de notas).
#
# Imprime el promedio.
notas = [7.5, 9.0, 6.5, 10.0, 4.0, 9.0]
notas.remove(min(notas))
print(notas)
promedio= sum(notas)/len(notas)
print(promedio)
# Buscando Posiciones y Repetidos Dada la lista letras = ['a', 'b', 'c', 'd', 'b', 'e']:
#
# Encuentra e imprime cuántas veces aparece la letra 'b' (método .count()).
#
# Encuentra e imprime en qué índice (posición) aparece la letra 'd' por primera vez (método .index()).
letras = ['a', 'b', 'c', 'd', 'b', 'e']
vecesb=letras.count('b')
print(vecesb)
indiced=letras.index('d')
print(indiced)