from turtle import *

def triangulo_sierpinski(nivel,size):
    if nivel == 0:
        return
    else:
        triangulo_sierpinski(nivel-1,size/2)
        forward(size)
        left(-120)
        triangulo_sierpinski(nivel-1,size/2)
        forward(size)
        left(-120)
        triangulo_sierpinski(nivel-1,size/2)
        forward(size)
        left(-120)


def main():
    speed(0)
    left(60)
    triangulo_sierpinski(10,150)
    done()
if __name__ == '__main__':
    main()
