from turtle import *
from colorsys import *
from random import randint, random, choice


def traceSegment(level, size):
    pensize(level*2)
    pencolor(hsv_to_rgb(0 + level/80 % 1, 1, 1))
    forward(size)


def arbol(level, size, angle):
    if level == 0:
        # Pintar una hoja
        begin_fill()
        color = choice(
            ["green", "dark green", "dark green", "brown"])
        pencolor(color)
        fillcolor(color)
        circle(randint(0, 12))
        end_fill()
        return

    # Pintar rama actual
    left(angle)
    traceSegment(level, size)

    # Pintar subarbol izquierda
    arbol(level-1, size*0.85+randint(int(-size*0.3),
          int(size*0.3)), randint(angle-6, angle+6))
    # Pintar subarbol derecho
    arbol(level-1, size*0.85 + randint(int(-size*0.3),
          int(size*0.3)), -randint(angle-6, angle+6))

    # Volver al nodo al finalizar el subarbol completo
    penup()
    forward(-size)
    right(angle)


bgcolor("black")
speed(0)
penup()
goto(0, -325)
pendown()
hideturtle()
left(65)
arbol(7, 85, 18)
exitonclick()
