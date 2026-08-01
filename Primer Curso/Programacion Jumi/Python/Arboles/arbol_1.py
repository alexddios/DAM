#
# Versión 0
# Version mínima completa
#

from turtle import *
from colorsys import *


def tree(level=4, size=100, angle=45):
    if (level <= 0):
        return  # ============================>

    # Trazar rama actual
    left(angle)
    pendown()
    forward(size)

    # Crear arbol izquierdo
    tree(level-1, size, -angle)

    # Crear arbol derecho
    tree(level-1, size, angle)

    # Volver al punto inicial del arbol del nivel actual
    penup()
    forward(-size)
    left(-angle)



#
# Main program
#
pendown()
goto(0, -200)

pencolor(hsv_to_rgb(0.3, 1, 1))
pensize(5)
bgcolor("black")
speed(1)

left(90-45)
tree(3)

exitonclick()
