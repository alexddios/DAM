import turtle
#
#
# # Pregunta 1
# def ejercicio_1(l, n):
#     if n == 0:
#         turtle.forward(l)
#         return
#
#     ejercicio_1(l / 2, n - 1)
#     turtle.left(90)
#     ejercicio_1(l / 2, n - 1)
#     turtle.right(90)
#     ejercicio_1(l / 2, n - 1)
#     turtle.right(90)
#     ejercicio_1(l / 2, n - 1)
#     turtle.left(90)
#     ejercicio_1(l / 2, n - 1)
#
#
# ejercicio_1(120, 2)
#
#
# # Pregunta 2
# def ejercicio_2(l, n):
#     if n == 0:
#         turtle.forward(l)
#         return
#
#     ejercicio_2(l / 2, n - 1)
#     turtle.left(90)
#     ejercicio_2(l / 2, n - 1)
#     turtle.right(90)
#     ejercicio_2(l / 2, n - 1)
#
#
# ejercicio_2(200, 2)
#
#
# Pregunta 3
def ejercicio_3(l, n):
    if n == 0:
        turtle.forward(l)
        return

    turtle.left(45)
    ejercicio_3(l, n - 1)
    turtle.right(90)
    ejercicio_3(l, n - 1)
    turtle.left(45)


ejercicio_3(50, 4)
#
#
# # Pregunta 4
# def ejercicio_4(l, n):
#     if n == 0:
#         turtle.forward(l)
#         return
#
#     ejercicio_4(l * 1, n - 1)
#     turtle.right(90)
#     ejercicio_4(l * 0.75, n - 1)
#
#
# ejercicio_4(200, 2)
#
#
# # Pregunta 5
# def ejercicio_5(l, n):
#     if n == 0:
#         return
#     turtle.forward(l)
#     turtle.backward(l)
#     turtle.right(30)
#     ejercicio_5(l * 0.8, n - 1)
#
#
# ejercicio_5(100, 4)
#
#
# # Pregunta 6
# def ejercicio_6(l, n):
#     if n == 0:
#         return
#     turtle.forward(l)
#     turtle.left(30)
#     ejercicio_6(l * 0.6, n - 1)
#     turtle.right(60)
#     ejercicio_6(l * 0.6, n - 1)
#     turtle.left(30)
#     turtle.backward(l)
#
#
# ejercicio_6(100, 4)
#
#
# # Pregunta 7
# def ejercicio_7(l, n):
#     if n == 0:
#         return
#     turtle.forward(l)
#     turtle.right(90)
#     ejercicio_7(l + 5, n - 1)
#
#
# ejercicio_7(10, 10)
#
#
# # Pregunta 8
# def ejercicio_8(lado, n):
#     if n == 0:
#         return
#     for _ in range(3):
#         turtle.forward(lado)
#         turtle.left(120)
#     turtle.left(20)
#     ejercicio_8(lado * 0.8, n - 1)
#
#
# ejercicio_8(100, 4)
#
#
# # Pregunta 9
# def ejercicio_9(n):
#     if n == 0:
#         return 1
#     if n == 1:
#         return 2
#     return ejercicio_9(n - 1) + 2 * ejercicio_9(n - 2)
#
#
# print(ejercicio_9(3))
#
#
# # Pregunta 10
# def ejercicio_10(n):
#     if n == 1:
#         return 1
#     return n + 2 * ejercicio_10(n - 1)
#
#
# print(ejercicio_10(3))