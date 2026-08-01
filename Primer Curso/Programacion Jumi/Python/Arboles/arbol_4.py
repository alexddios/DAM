from turtle import *
from colorsys import *
from random import randint, random, choice


def traceBranch(level, size, angle):
    pen_size = 1 + level*4
    pensize(pen_size)

    tono = 0.028
    saturacion = max((1-level/9), 0.85)
    brillo = max((1-level/7), 0.10)

    pencolor(hsv_to_rgb(tono, saturacion, brillo))
    pendown()

    left(angle/2)
    forward(size/2)
    left(angle/2)
    forward(size/2)


def returnToBranchBase(size, angle):
    penup()
    forward(-size/2)
    left(-angle/2)
    forward(-size/2)
    left(-angle/2)


def tree(level, size, angle):
    if (level <= 0):
        return  # ============================>

    # Pintar rama actual
    traceBranch(level, size, angle)

    # Pintar arbol izquierdo
    tree(level-1,
         size*0.7+randint(int(-size*0.10), int(size*0.15)),
         randint(angle-5, angle+5))

    # Pintar arbol derecho
    tree(level-1,
         size*0.7 + randint(int(-size*0.10), int(size*0.15)),
         -randint(angle-5, angle+5))

    returnToBranchBase(size, angle)


speed(0)
penup()
goto(0, -325)
pendown()
hideturtle()
left(65)


bgcolor("black")
# tree(10, 50, 17)
tree(5, 125, 25)

exitonclick()
