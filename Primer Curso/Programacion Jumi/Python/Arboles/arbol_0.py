'''
    Versión 0
    Estructura para versión minima de arbol binario gráfico
'''

from turtle import pendown, penup, left, forward, goto, pencolor, pensize, bgcolor, speed, exitonclick
from colorsys import hsv_to_rgb


# · Version without angle parameter
def tree2(level=4, size=100):
    # Condición de parada de recursividad
    if (level==0):
        return
    
    # Trazar una rama
    pendown()
    forward(size)
    penup()

    # Crear subarbol izquierdo
    left(-45)
    tree2(level-1, size)
    left(45)

    # Crear subarbol derecho
    left(45)
    tree2(level-1, size)
    left(-45)

    forward(-size)

# · Version with angle parameter
# · This is a more general version that allows to specify 
#   the angle of the branches, but it is more complex to 
#   understand and to implement
def tree(level=4, angle=45, size=100):
    # Condición de parada de recursividad
    if (level==0):
        return
    
    # Trazar una rama
    pendown()
    left(angle)
    forward(size)
    penup()

    # Crear dos subarboles
    tree(level-1, angle, size)
    tree(level-1, -angle, size)

    forward(-size)
    left(-angle)


def main():
    pendown()
    speed(1)
    bgcolor("black")

    goto(-30, -200)
    pencolor(hsv_to_rgb(0.5, 1, 1))
    pensize(5)
    left(90)
    tree2(3,50)

    goto(30, -200)
    pencolor(hsv_to_rgb(0.3, 1, 1))
    pensize(5)
    angle = 45
    left(-angle)
    tree(4, angle, 75)
    left(angle)


    exitonclick()


if __name__ == "__main__":
    main()