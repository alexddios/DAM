import os

import colorama


def ajustar_camino_sobre_laberinto(laberinto, entrada, salida, camino_salida):
    # Marca con "·" todas las celdas que forman parte del camino de salida
    # con el objetivo que se pueda visualizar de una forma más gráfica.
    #
    # Para ello usamos la lista "camino_salida" que contiene las coordenadas
    # de todas las celdas que forman parte del camino de salida que se ha
    # calculado previamente.
    #
    # A cada celda que forma parte del camino de salida la he denominado "paso"

    for paso in camino_salida:
        if (paso != entrada) and (paso != salida):
            laberinto[paso[0]][paso[1]] = "·"


def ajustar_entrada_laberinto(laberinto, entrada):
    # Pone una "E" en la casilla de entrada al laberinto
    laberinto[entrada[0]][entrada[1]] = "E"


def ajustar_muros_laberinto(laberinto, muros):
    # Pone 'X' en las casillas donde está especificado
    # que debe haber un muro -> Casillas "NO transitables"

    for ladrillo in muros:
        laberinto[ladrillo[0]][ladrillo[1]] = "X"


def ajustar_salida_laberinto(laberinto, salida):
    # Pone una "S" en la casilla de salida del laberinto

    laberinto[salida[0]][salida[1]] = "S"


def buscar_camino_salida(laberinto, casillas_usadas, dim):
    # Esta función es la principal del programa y su objetivo
    # es calcular un camino de salida entre los que pueda haber.

    # Empezamos directamente en la casilla de entrada.
    # Damos por hecho que la entrada está en la esquina superior izquierda => coordenadas 0,0
    pos_actual = (0, 0)
    camino = [(0, 0)]
    nueva_direccion = ""
    direccion_salida = ["Entrada"]
    casillas_usadas["0-0"] = True

    # Indicamos que todavía no hemos encontrado el camino de salida
    # El bucle while iterara mientras no se haya encontrado la salida y mientras existan
    # celdas que explorar si conducen a una salida.
    salida = False
    while (not salida) and (len(camino) > 0):
        # Damos un paso en busca de la salida
        nuevo_paso, nueva_direccion = dar_un_paso(
            laberinto, casillas_usadas, dim, pos_actual
        )

        if len(nuevo_paso) == 0:
            # No hemos podido dar un paso desde la celda actual -> len(nuevo_paso) == 0
            # Eliminar la última posición del camino porque sabemos
            # que no conduce a la salida, ya que no hay opción de seguir explorando
            camino.pop()
            direccion_salida.pop()

            # ¡DETALLE MUY IMPORTANTE!!!
            # Retrocedemos un paso en el camino: Convertir el último paso del camino en posición actual
            # Al último elemento de una lista se accede con el índice -1
            pos_actual = camino[-1]
        else:
            # Hemos encontrado un celda para explorar
            # Añadimos la nueva celda al camino de salida
            camino.append(nuevo_paso)
            direccion_salida.append(nueva_direccion)

            # Marcamos la casilla como usada para no volverla ha explorar
            marcar_casilla_como_usada(casillas_usadas, nuevo_paso)
            pos_actual = nuevo_paso

        # Marcar casilla como usada en el diccionario
        salida = laberinto[pos_actual[0]][pos_actual[1]] == "S"

    return camino, direccion_salida


def clear():
    # Limpiar la terminal.
    # OJO: No funciona al ejecutar en ventana interactiva

    if os.name == "nt":
        os.system("cls")
    else:
        os.system("clear")


def crear_diccionario_casillas(dim):
    # Creamos un diccionario con todas las casillas del laberinto y
    # un indicador booleano (False) de que no se ha usado

    casillas = {}

    for num_fila in range(0, dim):
        for num_columna in range(0, dim):
            # Añadir una nueva casilla al diccionario
            casillas[str(num_fila) + "-" + str(num_columna)] = False

    return casillas


def crear_laberinto_vacio(dim):
    # Creamos el laberinto como una lista vacía por ahora
    # Las casillas "transitables" contienen un espacio en blanco' '

    l = []  # noqa: E741

    for num_fila in range(0, dim):
        # Creamos una fila como una lista vacía por ahora
        fila = []

        #  Rellenamos la fila
        for num_columna in range(0, dim):
            # Añadir elementos a la nueva fila
            fila.append(" ")

        # Añadir nueva fila al tablero
        l.append(fila)

    return l


def dar_un_paso(laberinto, casillas_usadas, dim, pos_actual):
    # El objetivo de esta función es, estando en una posición del laberinto
    # decidir la siguiente casilla a explorar -> por donde seguir "andando"
    # en busca de la salida.

    # Extraemos las coordendas de la tupla pos_actual que es la que nos
    # indica en que posición del laberinto nos encontramos.
    num_fila = pos_actual[0]
    num_columna = pos_actual[1]

    # Explorar hacia ABAJO
    if paso_posible(laberinto, casillas_usadas, dim, num_fila + 1, num_columna):
        # paso_posible() a devuelto True, por lo que hemos validado que podemos
        # dar un paso hacia la casilla inferior a la actual
        return (num_fila + 1, num_columna), "Abajo"

    # Explorar hacia la DERECHA
    if paso_posible(laberinto, casillas_usadas, dim, num_fila, num_columna + 1):
        # paso_posible() a devuelto True, por lo que hemos validado que podemos
        # dar un paso hacia la casilla derecha a la actual
        return (num_fila, num_columna + 1), "Derecha"

    # Explorar hacia la IZQUIERDA
    if paso_posible(laberinto, casillas_usadas, dim, num_fila, num_columna - 1):
        # paso_posible() a devuelto True, por lo que hemos validado que podemos
        # dar un paso hacia la casilla izquierda a la actual
        return (num_fila, num_columna - 1), "Izquierda"

    # Explorar hacia ARRIBA
    if paso_posible(laberinto, casillas_usadas, dim, num_fila - 1, num_columna):
        # paso_posible() a devuelto True, por lo que hemos validado que podemos
        # dar un paso hacia la casilla superior a la actual
        return (num_fila - 1, num_columna), "Arriba"

    # NO HEMOS ENCONTRADO NINGUNA CASILLA A LA QUE MOVERNOS DESDE LA ACTUAL
    # POR LO QUE DEVOLVEMOS UNA TUPLA VACÍA () PARA INDICAR QUE NO HAY NINGUNA
    # OPCIÓN DE CONTINUAR POR ESTE CAMINO.
    return (), None


def marcar_casilla_como_usada(casillas_usadas, casilla):
    num_fila = casilla[0]
    num_columna = casilla[1]
    casillas_usadas[str(num_fila) + "-" + str(num_columna)] = True


def mostrar_laberinto(titulo, laberinto):
    # Muestra el laberinto con colores
    # Resalta entrada, salida, muros y el camino de salida

    print(colorama.Fore.YELLOW + "*** " + titulo + " ***")
    for fila in laberinto:
        for celda in fila:
            if celda == " ":
                print(colorama.Back.WHITE + "  ", end="")
            elif celda == "X":
                print(colorama.Back.RED + colorama.Fore.BLACK + "X ", end="")
            elif celda == "E":
                print(colorama.Back.GREEN + colorama.Fore.BLACK + "E ", end="")
            elif celda == "S":
                print(colorama.Back.GREEN + colorama.Fore.BLACK + "S ", end="")
            elif celda == "·":
                print(colorama.Back.YELLOW + colorama.Fore.BLACK + "··", end="")
            else:
                print("Error en el laberinto -> Símbolo inesperado!")

        print(colorama.Back.BLACK + colorama.Fore.WHITE)

    print(colorama.Back.BLACK + colorama.Fore.WHITE)


def paso_posible(laberinto, casillas_usadas, dim, num_fila, num_columna):
    # El objetivo de esta función es validar si una casilla puede formar
    # parte del camino de salida. Para que una casilla forme parte del
    # camino de salida tiene que cumplir unas condiciones:
    #   * Que este dentro del laberinto, es decir, NO FUERA DE RANGO.
    #   * Que no sea parte de un muro
    #   * Que no ha sido explorada previamente.

    # Comprobar si fila fuera de rango
    if (num_fila < 0) or (num_fila >= dim):
        return False

    # Comprobar si columna fuera de rango
    if (num_columna < 0) or (num_columna >= dim):
        return False

    # Comprobar si hay un ladrillo
    if laberinto[num_fila][num_columna] == "X":
        return False

    # Comprobar si es la casilla de entrada
    if laberinto[num_fila][num_columna] == "E":
        return False

    # Comprobar si esta casilla ya se exploró
    if casillas_usadas[str(num_fila) + "-" + str(num_columna)]:
        return False

    # Como no se dan las condiciones anteriores significa que la
    # casilla es candidata a ser parte del camino de salida y por
    # ello devolveremos True.
    return True


def main():
    # Creación tupla con las posiciones del muro
    muros = (
        (0, 1),
        (0, 2),
        (0, 3),
        (0, 4),
        (1, 1),
        (2, 1),
        (3, 7),
        (2, 3),
        (3, 3),
        (4, 0),
        (4, 1),
        (4, 2),
        (4, 3),
        (4, 7),
        (4, 9),
        (5, 3),
        (5, 4),
        (5, 5),
        (5, 6),
        (5, 7),
        (5, 8),
        (5, 9),
        (6, 1),
        (6, 12),
        (6, 13),
        (7, 1),
        (7, 2),
        (7, 3),
        (7, 4),
        (7, 5),
        (7, 6),
        (7, 7),
        (7, 8),
        (7, 9),
        (7, 10),
        (7, 11),
        (7, 12),
        (10, 0),
        (10, 1),
        (11, 2),
        (12, 2),
    )

    # Ancho y alto del laberinto, ya que se supone cuadrado
    dimension = 14
    # Coordenadas de entrada y de salida del laberinto
    entrada = (0, 0)
    salida = (11, 1)

    # Camino de salida -> Lista de coordenadas de casillas que conducen a la salida
    camino_salida = []

    # Dirección de salida -> Instrucciones para ir hasta la salida en forma de direccion
    direccion_salida = []

    clear()

    # Crear el laberinto con muros, entrada y salida
    laberinto = crear_laberinto_vacio(dimension)
    ajustar_muros_laberinto(laberinto, muros)
    ajustar_entrada_laberinto(laberinto, entrada)
    ajustar_salida_laberinto(laberinto, salida)
    mostrar_laberinto("Laberinto", laberinto)

    # Crear un diccionario que se usara para saber las casillas que ya se han explorado
    # con la finalidad de evitar ciclos en los que se vuelva a explorar una casilla
    # una y otra vez ...
    casillas_usadas = crear_diccionario_casillas(dimension)

    # Esta función es la principal y calcula el camino de salida
    camino_salida, direccion_salida = buscar_camino_salida(
        laberinto, casillas_usadas, dimension
    )

    # Marca el camino de salida sobre el laberinto
    ajustar_camino_sobre_laberinto(laberinto, entrada, salida, camino_salida)
    mostrar_laberinto("Camino de salida", laberinto)

    # Mostrar las listas con información del camino de salida
    print(
        colorama.Fore.YELLOW
        + "*** Dirección de salida: Dirección ha seguir para salir ***"
        + colorama.Fore.WHITE
    )
    print(direccion_salida)
    print()
    print(
        colorama.Fore.YELLOW
        + "*** Camino de salida: celdas que forman parte del camino de salida ***"
        + colorama.Fore.WHITE
    )
    print(camino_salida)


if __name__ == "__main__":
    colorama.init()
    main()
