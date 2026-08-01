#Ejercicio 5
numeros = [1, 2, 3, 4, 5]
cuadrados=[cuadrado*cuadrado for cuadrado in numeros]
print(cuadrados)
#Ejercicio 6
colores = ['rojo', 'azul', 'verde', 'rojo', 'amarillo', 'azul']
sin_duplicados = []

for color in colores:
    if color not in sin_duplicados:
        sin_duplicados.append(color)

print(sin_duplicados)
#Ejercicio 7
matriz = [[1, 2], [3, 4], [5, 6]]
matrizaplanada=[]
for x in matriz:
    for y in x:
        matrizaplanada.append(y)
print(matrizaplanada)
# Iniciales (List Comprehension con String Slicing) Tienes una lista de nombres: nombres = ['Juan', 'Maria', 'Pedro', 'Lucia'].
#
# Crea una nueva lista llamada iniciales que contenga solo la primera letra de cada nombre.
#
# Intenta hacerlo con List Comprehension en una línea.
#
# Resultado esperado: ['J', 'M', 'P', 'L']
nombres = ['Juan', 'Maria', 'Pedro', 'Lucia']
iniciales = [inicial[0] for inicial in nombres ]
print(iniciales)
# Filtrado por Longitud Tienes la lista: palabras = ['sol', 'luna', 'estrella', 'cielo', 'mar'].
#
# Crea una nueva lista que contenga solo las palabras que tengan 4 letras o más.
#
# Imprime la lista filtrada.
palabras = ['sol', 'luna', 'estrella', 'cielo', 'mar']
letras4=[]
for mas4 in palabras:
    if len(mas4)>=4:
        letras4.append(mas4)
print(letras4)
# Inversión Total Dada la lista frase = ['Python', 'es', 'genial'].
#
# Queremos invertir el orden de la lista, pero también invertir cada palabra individualmente.
#
# Resultado esperado: ['laineg', 'se', 'nohtyP'] (nota que 'genial' al revés es el primero, y 'Python' al revés es el último).
frase = ['Python', 'es', 'genial']
frase.reverse()
resultado=[]
for palabra in frase:
    resultado.append(palabra[::-1])

print(resultado)


