from turtle import forward,left,penup,pendown,goto,speed,done
def curva_koch(iteraciones, longitud):
    if iteraciones == 0:
        forward(longitud)
    else:
        l = longitud / 4
        curva_koch(iteraciones-1, l)
        left(90)
        curva_koch(iteraciones-1, l)
        left(-90)
        curva_koch(iteraciones-1, l)
        left(-90)
        curva_koch(iteraciones-1, l)
        left(90)
        curva_koch(iteraciones-1, l)

# Configuración de turtle
speed(0) # Máxima velocidad
penup()
goto(-150, 0)
pendown()
# Dibujar curva de Koch
curva_koch( 4, 300) # (tortuga, iteraciones, longitud total)


done()
