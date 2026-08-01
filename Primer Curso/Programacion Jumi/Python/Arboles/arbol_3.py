#
# Versión 3
# Añadiendo cambio de tamaño de las ramas en función del nivel
#

from turtle import *
from colorsys import *
from random import randint, random, choice


def traceBranch(level, size, angle):
    pen_size = 2 + level*4
    pensize(pen_size)

    tono = 0.000 + level/10
    saturacion = max((1-level/9), 0.85)
    brillo = max((1-level/7), 0.10)

    pendown()
    pencolor(hsv_to_rgb(tono, saturacion, brillo))
    left(angle)
    forward(size)


def returnToBranchBase(size, angle):
    penup()
    forward(-size)
    right(angle)


def tree(level, size, angle):
    if (level <= 0):
        return  # ============================>

    # Pintar rama actual
    traceBranch(level, size, angle)

    tree(level-1, size*0.8, -angle)
    tree(level-1, size*0.8, angle)

    returnToBranchBase(size, angle)


speed(0)
penup()
goto(0, -300)
pendown()
hideturtle()
left(90-22)


bgcolor("black")
# tree(10, 50, 17)
tree(3, 110, 22)

exitonclick()
