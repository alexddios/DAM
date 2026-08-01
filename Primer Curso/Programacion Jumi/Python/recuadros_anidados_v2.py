from colorama import Fore, Back


def es_tapa(dimension, fila, columna):
    # Las tapas nunca están en filas impares

    if fila < (dimension//2):
        ### CUADRANTE SUPERIOR ###

        # Las tapas NO están en filas impares en este cuadrante
        if fila % 2 != 0:
            return False

        # Ver si la columna está entre el inicio y el final de la tapa
        if columna >= fila and columna <= (dimension-fila-1):
            return True
    else:
        ### CUADRANTE INFERIOR ###
        if (dimension-fila-1) % 2 != 0:
            return False

        # Ver si la columna está entre el inicio y el final de la tapa
        if columna >= (dimension-fila-1) and columna <= fila:
            return True

    return False


def es_columna(dimension, fila, columna):
    if columna <= (dimension//2):
        ### CUADRANTE SUPERIOR ###

        # Las columnas no están en columnas impares en este cuadrante
        if columna % 2 != 0:
            return False

        if fila >= columna and fila <= (dimension-columna-1):
            return True

    else:
        ### CUADRANTE INFERIOR ###
        if (dimension-columna-1) % 2 != 0:
            return False

        if fila >= (dimension-columna-1) and fila <= columna:
            return True

    return False


def cabecera_columnas(dimension):
    print("   ", end="")
    for columna in range(dimension):
        if columna % 2 == 0:
            print(Fore.GREEN + f"{columna:2}", end=""+Fore.RESET)
        else:
            print(" ·", end="")
    print()


def cabecera_fila(fila):
    if fila % 2 == 0:
        print(Fore.GREEN+f"{fila:3}", end=" "+Fore.RESET)
    else:
        print("  .", end=" ")


def recuadros(dimension):
    cabecera_columnas(dimension)

    posicion_media = (dimension-1) / 2 

    for fila in range(0, dimension):
        cabecera_fila(fila)

        distancia_fila = abs(posicion_media - fila)
        desfase_fila = posicion_media - distancia_fila
        posible_tapa = (desfase_fila % 2 == 0)

        print(" Posible tapa:", posible_tapa, distancia_fila, desfase_fila, end=" | ")

        for columna in range(dimension):
            distancia_columna = abs(posicion_media - columna)
            desfase_columna = posicion_media - distancia_columna
            posible_columna = (desfase_columna % 2 == 0)


            if (posible_tapa and distancia_columna <= distancia_fila):
                print(Fore.BLACK + Back.RED + "T ",
                      end="" + Fore.RESET+Back.RESET)
            elif posible_columna and distancia_fila <= distancia_columna:
                print(Fore.BLACK + Back.RED + "C ",
                      end="" + Fore.RESET+Back.RESET)
            else:
                print(Back.YELLOW + "  ", end=""+Back.RESET)

        print()  # Cambio de fila


def main():
    d = 8

    print()
    titulo = "CUADRADOS ANIDADOS"
    print(Fore.GREEN + titulo)
    print("=" * len(titulo))
    print()

    print(Fore.GREEN + "Dimensión: ", d, Fore.RESET)
    recuadros(d)
    print()


if __name__ == '__main__':
    main()
