# Ejercicio 1: El Rectángulo de Estrellas Queremos dibujar un rectángulo de 3 filas y 5 columnas usando asteriscos (*).
# El resultado debe verse así en la consola:
for fila in  range(3):
    for columna in range(5):
        print('*', end=' ')
    print()
# Ejercicio 2: El Tablero de Coordenadas Escribe un código que imprima las coordenadas (x, y) para una cuadrícula de 3x3.
for x in range(3):
    for y in range(3):
        # f-string para formatear bonito
        print(f"({x},{y})", end=' ')
    print()
