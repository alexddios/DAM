# Ejercicio 5: La Pirámide Numérica (Triángulo) Escribe un código que genere este patrón:
# 1
# 1 2
# 1 2 3
# 1 2 3 4
# 1 2 3 4 5

for x in range(1,6):
    for num in range(1,x+1):
        print(num, end=' ')
    print()
# Ejercicio 6: Combinaciones de Dados Imagina que tiras dos dados (del 1 al 6).
#
# Escribe un programa que imprima todas las combinaciones posibles donde la suma de los dos dados sea igual a 7.
#
# Ejemplo de salida: Dado A: 1 + Dado B: 6 = 7
# Dado 1 va del 1 al 6
for dado1 in range(1, 7):
    # Dado 2 va del 1 al 6
    for dado2 in range(1, 7):
        if dado1 + dado2 == 7:
            print(f"Dado A: {dado1} + Dado B: {dado2} = 7")