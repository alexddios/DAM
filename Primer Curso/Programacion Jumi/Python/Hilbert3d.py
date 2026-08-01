import turtle
import math


def hilbert_index_to_xyz(index, orden):
    """
    Convierte un índice de la curva de Hilbert 3D en coordenadas x, y, z.
    """
    n = 3
    x = [0, 0, 0]

    for i in range(orden):
        for j in range(n):
            bit = (index >> (n * i + j)) & 1
            x[j] |= bit << i

    # Transformación Gray inversa
    t = x[n - 1] >> 1

    for i in range(n - 1, 0, -1):
        x[i] ^= x[i - 1]

    x[0] ^= t

    # Corrección de rotaciones
    q = 2
    while q != (1 << orden):
        p = q - 1

        for i in range(n - 1, -1, -1):
            if x[i] & q:
                x[0] ^= p
            else:
                t = (x[0] ^ x[i]) & p
                x[0] ^= t
                x[i] ^= t

        q <<= 1

    return x[0], x[1], x[2]


def proyectar_3d_a_2d(x, y, z, escala):
    """
    Proyección isométrica de un punto 3D a coordenadas 2D.
    """
    angulo = math.radians(30)

    pantalla_x = (x - y) * math.cos(angulo) * escala
    pantalla_y = ((x + y) * math.sin(angulo) - z) * escala

    return pantalla_x, pantalla_y


def hilbert_3d(orden=3, escala=40):
    """
    Dibuja una curva de Hilbert 3D usando turtle.

    orden:
        Nivel de detalle de la curva.
        Valores recomendados: 1, 2, 3 o 4.

    escala:
        Tamaño visual de cada paso.
    """
    if orden < 1:
        return

    tortuga = turtle.Turtle()
    tortuga.speed(0)
    tortuga.hideturtle()
    tortuga.pensize(2)

    pantalla = turtle.Screen()
    pantalla.bgcolor("white")
    pantalla.title("Curva de Hilbert 3D con Turtle")
    pantalla.tracer(0)

    total_puntos = 8 ** orden
    max_coord = (2 ** orden) - 1

    puntos = []

    for i in range(total_puntos):
        x, y, z = hilbert_index_to_xyz(i, orden)

        # Centramos la figura
        x -= max_coord / 2
        y -= max_coord / 2
        z -= max_coord / 2

        px, py = proyectar_3d_a_2d(x, y, z, escala)
        puntos.append((px, py))

    tortuga.penup()
    tortuga.goto(puntos[0])
    tortuga.pendown()

    for punto in puntos[1:]:
        tortuga.goto(punto)

    pantalla.update()
    turtle.done()


# Ejemplo de uso
hilbert_3d(orden=1, escala=35)