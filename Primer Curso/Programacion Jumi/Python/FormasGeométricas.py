# Cuadrado
medida = 20
for fila in range(medida):
    for columna in range(medida):
        print("* ", end="")
    print()

print("="*20)

# Imprimir un triangulo rectangulo
for fila in range(10):
    for columna in range(fila + 1):
        print("*", end="*")
    print()

print("="*20)

# Imprrimir un triangulo rectangulo invertido
for fila in range(10, 0, -1):
    for columna in range(fila):
        print("* ", end="")
    print()

print('='*20)

# Imprimir un triangulo rectangulo alineado a la derecha
for fila in range(1, 11):
    num_blancos = 10 - fila

    for blancos in range(num_blancos):
        print("  ", end="")

    for asteriscos in range(fila):
        print("*", end=" ")

    print()

print('='*20)

# Imprimir un triangulo rectangulo invertido alineado a la derecha
for fila in range(10, 0, -1):
    num_blancos = 10 - fila

    for blancos in range(num_blancos):
        print("  ", end="")

    for asteriscos in range(fila):
        print("*", end=" ")

    print()
print('='*20)

# Imprimir un triangulo isosceles
altura = 10
num_columnas = altura * 2 - 1
for fila in range(1, altura + 1):
    num_asteriscos = fila * 2 - 1
    num_blancos = (num_columnas - num_asteriscos) // 2
    for b in range(num_blancos):
        print(" ", end="")
    for a in range(num_asteriscos):
        print("*", end="")
    print()
print('='*20)

# Imprimir triangulo isosceles con if
altura = 10
num_columnas = altura * 2 - 1
for fila in range(1, altura + 1):
    for columna in range(1, num_columnas + 1):
        num_asteriscos = fila * 2 - 1
        num_blancos = (num_columnas - num_asteriscos) // 2
        if columna <= num_blancos:
            print(" ", end="")
        elif columna <= num_blancos + num_asteriscos:
            print("*", end="")
    print()
print('='*20)
# Imprimir triangulo cuadrado
altura = 5
anchura = altura
for fila in range(1, altura + 1):
    for columna in range(1, anchura + 1):
        if columna <= fila:
            print("*", end="")
        else:
            print(" ", end="")
    print()
print('='*20)
# Imprimir trangulo cuadrado hueco
altura = 5
anchura = altura
for fila in range(1, altura + 1):
    for col in range(1, anchura + 1):
        if col == 1 or col == fila or fila == altura:
            print("*", end="")
        else:
            print(" ", end="")
    print()
print('='*20)
# Imprimir cuadrado hueco
altura = 5
anchura = altura
for fila in range(1, altura + 1):
    for col in range(1, anchura + 1):
        if fila == 1 or fila == altura or col == 1 or col == anchura:
            print("* ", end="")
        else:
            print("  ", end="")
    print()
print('='*20)
# Imprimir triangulo isosceles de otro modo
print("Triángulo isósceles (otro modo):")
altura = 10
num_columnas = altura * 2 - 1
for fila in range(1, altura + 1):
    for columna in range(1, num_columnas + 1):
        if columna >= altura - (fila - 1) and columna <= altura + (fila - 1):
            print("* ", end="")
        else:
            print("  ", end="")
    print()
print('='*20)
#Imprimir triangulo isosceles hueco
print("Triángulo isósceles hueco:")
altura = 10
num_columnas = altura * 2 - 1
for fila in range(1, altura + 1):
    for columna in range(1, num_columnas + 1):
        if columna == altura - (fila - 1) or columna == altura + (fila - 1) or fila == altura:
            print("* ", end="")
        else:
            print("  ", end="")
    print()
print('='*20)
#Imprimir un cuadrado dentro de otro cuadrado separados por un espacio
# Pedimos la altura del cuadrado
try:
    entrada = input("Introduce la altura del cuadrado (ej. 15): ")
    altura = int(entrada)
except ValueError:
    altura = 15
anchura = altura
for fila in range(1, altura + 1):
    for columna in range(1, anchura + 1):
        # 1. Calcular distancia al borde vertical más cercano (Arriba o Abajo)
        if fila <= altura / 2:
            dist_y = fila - 1      # Estamos en la mitad superior
        else:
            dist_y = altura - fila # Estamos en la mitad inferior
        # 2. Calcular distancia al borde horizontal más cercano (Izquierda o Derecha)
        if columna <= anchura / 2:
            dist_x = columna - 1      # Estamos en la mitad izquierda
        else:
            dist_x = anchura - columna # Estamos en la mitad derecha
    # La "capa" es la menor de las dos distancias calculadas
        if dist_x < dist_y:
            capa = dist_x
        else:
            capa = dist_y
        # CONDICIÓN NECESARIA:
        # Si la capa es par (0, 2, 4...), pintamos. Si es impar, hueco.
        if capa % 2 == 0:
            print("* ", end="")
        else:
            print("  ", end="")
    print()
print('=' * 20)
def dibujar_figura(n):
    # Ancho total de la figura
    ancho_total = n * 2
    
    # PARTE SUPERIOR (Incluyendo la línea del medio)
    # Va desde 1 hasta n
    for i in range(1, n + 1):
        asteriscos = "*" * i
        espacios = " " * (ancho_total - (i * 2))
        print(asteriscos + espacios + asteriscos)

    # PARTE INFERIOR
    # Va desde n-1 hasta 1 (cuenta regresiva)
    for i in range(n - 1, 0, -1):
        asteriscos = "*" * i
        espacios = " " * (ancho_total - (i * 2))
        print(asteriscos + espacios + asteriscos)

# --- Ejecución del programa ---
# El número 4 es el tamaño de la figura en tu imagen
dibujar_figura(4)
print('=' * 20)
def dibujar_figura_hueca(n):
    # n = tamaño de la mitad (4 en tu ejemplo)
    ancho_total = n * 2

    # --- PARTE SUPERIOR (desde 1 hasta n-1) ---
    # Dibujamos las filas crecientes, excepto la del medio
    for i in range(1, n):
        # 1. Construir el lado izquierdo hueco
        if i == 1:
            # La primera fila siempre es solo un asterisco a cada lado
            lado = "*"
        else:
            # Para i > 1, el lado es: un '*' + espacios internos + un '*'
            # La cantidad de espacios internos es (i - 2)
            espacios_internos = " " * (i - 2)
            lado = "*" + espacios_internos + "*"

        # 2. Calcular el espacio grande del centro
        # El espacio central sigue la misma lógica que la figura sólida
        espacio_centro = " " * (ancho_total - (i * 2))

        # 3. Imprimir la fila completa
        print(lado + espacio_centro + lado)


    # --- LÍNEA CENTRAL (fila n) ---
    # En la figura sólida, esta era una línea completa "********"
    # En la figura hueca, solo son los extremos.
    print("*" + " " * (ancho_total - 2) + "*")


    # --- PARTE INFERIOR (desde n-1 hasta 1) ---
    # Es un espejo exacto de la parte superior
    for i in range(n - 1, 0, -1):
        if i == 1:
            lado = "*"
        else:
            espacios_internos = " " * (i - 2)
            lado = "*" + espacios_internos + "*"

        espacio_centro = " " * (ancho_total - (i * 2))
        print(lado + espacio_centro + lado)

# --- Ejecución del programa ---
# Usamos n=4 para que coincida con el tamaño de tu imagen original
dibujar_figura_hueca(4)
print('=' * 20)


