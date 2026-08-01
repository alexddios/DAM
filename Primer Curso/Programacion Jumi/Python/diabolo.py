def dibujar_diabolo_un_loop(n):
    # Iteramos desde el negativo hasta el positivo
    # Si n=5, el rango será: -4, -3, -2, -1, 0, 1, 2, 3, 4
    for fila in range(-(n - 1), n):
        
        # Calculamos los espacios y asteriscos basándonos en el valor absoluto
        # Cuanto más lejos del 0 (centro), más asteriscos
        num_asteriscos = abs(fila) * 2 + 1
        num_espacios = (n - 1) - abs(fila)
        
        print(" " * num_espacios + "*" * num_asteriscos)

# --- Prueba ---
n = int(input("Tamaño del diábolo: "))
dibujar_diabolo_un_loop(n)
def dibujar_diabolo_hueco(n):
    # Iteramos desde el negativo hasta el positivo (espejo)
    for i in range(-(n - 1), n):
        row = abs(i) # Esto nos dice en qué "nivel" de anchura estamos
        
        # Espacios a la izquierda para centrar la figura
        espacios_fuera = " " * ((n - 1) - row)
        
        if row == n - 1:
            # Tapa superior o inferior (línea llena)
            print(f"{espacios_fuera}{'*' * (2 * row + 1)}")
            
        elif row == 0:
            # El centro exacto (un solo punto)
            print(f"{espacios_fuera}*")
            
        else:
            # Las paredes huecas (asterisco + espacio + asterisco)
            espacios_dentro = " " * (2 * row - 1)
            print(f"{espacios_fuera}*{espacios_dentro}*")

# --- Prueba ---
try:
    n = int(input("Introduce el tamaño del diábolo hueco: "))
    dibujar_diabolo_hueco(n)
except ValueError:
    print("Introduce un número válido.")