import turtle


def dibujar_espiga(tortuga, longitud, nivel):
    if nivel == 0:
        return

    # Dibujar el palo principal
    tortuga.forward(longitud)

    if nivel > 1:
        # Guardamos la posición y dirección al final del palo
        posicion_final = tortuga.position()
        direccion_final = tortuga.heading()

        # Volvemos al inicio del palo para colocar ramas a lo largo
        tortuga.backward(longitud)

        # Dibujamos 4 ramas: dos por cada lado
        posiciones = [longitud * 0.3, longitud * 0.6]

        for distancia in posiciones:
            tortuga.forward(distancia)

            posicion = tortuga.position()
            direccion = tortuga.heading()

            # Rama izquierda
            tortuga.left(45)
            dibujar_espiga(tortuga, longitud * 0.4, nivel - 1)
            tortuga.penup()
            tortuga.goto(posicion)
            tortuga.setheading(direccion)
            tortuga.pendown()

            # Rama derecha
            tortuga.right(45)
            dibujar_espiga(tortuga, longitud * 0.4, nivel - 1)
            tortuga.penup()
            tortuga.goto(posicion)
            tortuga.setheading(direccion)
            tortuga.pendown()

        # Volvemos al final del palo principal
        tortuga.penup()
        tortuga.goto(posicion_final)
        tortuga.setheading(direccion_final)
        tortuga.pendown()


# Configuración de la ventana
pantalla = turtle.Screen()
pantalla.title("Espiga recursiva")
pantalla.bgcolor("white")

t = turtle.Turtle()
t.speed(0)
t.left(90)  # Para que empiece dibujando hacia arriba

# Posición inicial
t.penup()
t.goto(0, -250)
t.pendown()

# Dibujar espiga
dibujar_espiga(t, 200, 4)

turtle.done()