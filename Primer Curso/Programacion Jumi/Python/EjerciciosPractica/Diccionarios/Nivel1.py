# La Ficha del Jugador Crea un diccionario llamado jugador con la siguiente información:
# nombre: 'Mario'
# vidas: 3
# puntos: 100
# Tareas:
# Imprime solo el valor de la clave 'nombre'.
# El jugador perdió una vida: Modifica el valor de 'vidas' a 2.
# El jugador encontró un ítem: Agrega una nueva clave llamada 'inventario' con el valor 'hongo'.
# Imprime el diccionario completo.
jugador={"Nombre":"Mario","Vidas":3,"Puntos":100}
print(jugador.get("Nombre"))
jugador.update({"Inventario":"Hongo"})
print(jugador)
# Traductor Simple Tienes un diccionario español-inglés: colores = {'rojo': 'red', 'azul': 'blue', 'verde': 'green'}.
# Imprime la traducción de 'azul'.
# Agrega el color 'negro' y su traducción 'black'.
# Reto: Intenta imprimir una clave que no existe (ej: 'blanco') usando el método .get('blanco', 'No encontrado').
#  Esto evita que el programa falle si la clave no está.
colores = {'rojo': 'red', 'azul': 'blue', 'verde': 'green'}
print(colores["azul"])
colores["negro"]="black"
print(colores)
print(colores.get("blanco","no encontrado"))
