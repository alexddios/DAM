# ==============================================================================
# BLOQUE 1 — SEGUIR EL VALOR (traza de ejecución)
# ==============================================================================

# ── Ejercicio 1 ──────────────────────────────────────────────────────────────
print("--- Ejercicio 1 ---")
def f(n):
    if n == 0:
        return 0
    return n + f(n - 1)
print(f(4))
print(f(1))
# Respuesta:
# f(4) → __
# f(1) → __

# ── Ejercicio 2 ──────────────────────────────────────────────────────────────
print("--- Ejercicio 2 ---")
def f(n):
    if n == 0:
        return 1
    return n * f(n - 1)
print(f(5))
print(f(0))
# Respuesta:
# f(5) → __
# f(0) → __
# ── Ejercicio 3 ──────────────────────────────────────────────────────────────
print("--- Ejercicio 3 ---")
def f(n):
    if n <= 0:
        return
    print(n)
    f(n - 2)
f(6)
# Respuesta:
# __
# __
# __
# ── Ejercicio 4 ──────────────────────────────────────────────────────────────
print("--- Ejercicio 4 ---")
def f(n):
    if n <= 0:
        return
    f(n - 1)
    print(n)
f(4)
# Respuesta:
# __
# __
# __
# __

# ── Ejercicio 5 ──────────────────────────────────────────────────────────────
print("--- Ejercicio 5 ---")
def f(n):
    if n == 0:
        print("base")
        return
    print("baixada", n)
    f(n - 1)
    print("pujada", n)
f(3)
# Respuesta:
# __
# __
# __
# __
# __
# __
# __
# ── Ejercicio 6 ──────────────────────────────────────────────────────────────
print("--- Ejercicio 6 ---")
def f(n):
    if n == 0:
        return 0
    if n % 2 == 0:
        return f(n - 1) + 10
    else:
        return f(n - 1) + 1
print(f(5))
# Respuesta:
# f(5) → __
# ── Ejercicio 7 ──────────────────────────────────────────────────────────────
print("--- Ejercicio 7 ---")
def f(n):
    if n == 0:
        return 0
    return f(n - 1) * 2 + 1
print(f(4))
# Respuesta:
# f(4) → __
# ── Ejercicio 8 ──────────────────────────────────────────────────────────────
print("--- Ejercicio 8 ---")
def f(a, b):
    if b == 0:
        return a
    return f(b, a % b)
print(f(12, 8))
print(f(9, 6))
# Respuesta:
# f(12, 8) → __
# f(9, 6) → __
# ── Ejercicio 9 ──────────────────────────────────────────────────────────────
print("--- Ejercicio 9 ---")
def f(n):
    if n <= 1:
        return n
    return f(n - 1) + f(n - 2)
print(f(6))
print(f(3))
# Respuesta:
# f(6) → __
# f(3) → __
# ── Ejercicio 10 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 10 ---")
def f(v, p):
    if p == len(v):
        return 0
    return v[p] + f(v, p + 1)
llista = [3, 1, 4, 1, 5]
print(f(llista, 0))
print(f(llista, 2))
# Respuesta:
# f(llista, 0) → __
# f(llista, 2) → __
# ── Ejercicio 11 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 11 ---")
def f(v, p):
    if p < 0:
        return
    print(v[p])
    f(v, p - 1)
llista = [10, 20, 30]
f(llista, 2)
# Respuesta:
# __
# __
# __
# ── Ejercicio 12 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 12 ---")
def f(n, acc=0):
    if n == 0:
        return acc
    return f(n - 1, acc + n)
print(f(5))
print(f(3, 10))
# Respuesta:
# f(5) → __
# f(3, 10) → __
# ── Ejercicio 13 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 13 ---")
def f(n):
    if n == 0:
        return 0
    return 1 + f(n // 2)
print(f(8))
print(f(16))
# Respuesta:
# f(8) → __
# f(16) → __
# ── Ejercicio 14 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 14 ---")
def f(s):
    if len(s) == 0:
        return True
    if s[0] != s[-1]:
        return False
    return f(s[1:-1])
print(f("radar"))
print(f("python"))
print(f("a"))
# Respuesta:
# f("radar") → __
# f("python") → __
# f("a") → __
# ── Ejercicio 15 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 15 ---")
def f(v, n):
    if not v:
        return 0
    return (1 if v[0] == n else 0) + f(v[1:], n)
llista = [1, 3, 1, 2, 1]
print(f(llista, 1))
print(f(llista, 5))
# Respuesta:
# f(llista, 1) → __
# f(llista, 5) → __
# ── Ejercicio 16 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 16 ---")
x = 0
def f(n):
    global x
    if n == 0:
        return
    x += n
    f(n - 1)
f(4)
print(x)
f(2)
print(x)
# Respuesta:
# primera print → __
# segunda print → __
# ── Ejercicio 17 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 17 ---")
def f(n):
    if n <= 0:
        return 0
    return f(n - 3) + f(n - 1)
print(f(4))
# Respuesta:
# f(4) → __
# ── Ejercicio 18 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 18 ---")
def f(v, p=0):
    if p >= len(v):
        return
    if v[p] % 2 == 0:
        print(v[p])
    f(v, p + 1)
f([1, 2, 3, 4, 5, 6])
# Respuesta:
# __
# __
# __
# ── Ejercicio 19 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 19 ---")
def f(n, prof=0):
    if n == 0:
        print(" " * prof + "0")
        return
    print(" " * prof + str(n))
    f(n - 1, prof + 2)
    print(" " * prof + str(n))
f(3)
# Respuesta:
# __
# __
# __
# __
# __
# __
# __
# ── Ejercicio 20 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 20 ---")
def f(d, n):
    if n == 0:
        return d
    nou = {}
    for k in d:
        nou[k] = d[k] * 2
    return f(nou, n - 1)
resultat = f({"a": 1, "b": 2}, 3)
print(resultat)
# Respuesta:
# __
# ==============================================================================
# BLOQUE 2 — EJERCICIOS SIN FINALIDAD PRÁCTICA (traza pura, como los exámenes)
# ==============================================================================

# ── Ejercicio 21 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 21 ---")
def f(n, k):
    if n <= 0:
        return k
    return f(n - 1, k + n) + f(n - 2, k)
print(f(3, 0))
# Respuesta:
# __
# ── Ejercicio 22 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 22 ---")
resultat = []
def f(n):
    if n == 0:
        return
    f(n - 1)
    resultat.append(n * 2)
f(4)
print(resultat)
print(len(resultat))
# Respuesta:
# resultat → __
# len → __
# ── Ejercicio 23 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 23 ---")
def f(a, b, depth=0):
    if a > b:
        return 0
    mid = (a + b) // 2
    print(depth, mid)
    return f(a, mid - 1, depth + 1) + f(mid + 1, b, depth + 1) + 1
print(f(1, 7))
# Respuesta:
# (escribe cada print en orden)
# __
# __
# __
# __
# __
# __
# __
# print final → __
# ── Ejercicio 24 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 24 ---")
def f(v):
    if len(v) <= 1:
        return v
    mig = len(v) // 2
    return f(v[mig:]) + f(v[:mig])
print(f([1, 2, 3, 4]))
print(f([10, 20]))
# Respuesta:
# f([1,2,3,4]) → __
# f([10,20]) → __
# ── Ejercicio 25 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 25 ---")
crides = 0
def f(n):
    global crides
    crides += 1
    if n <= 1:
        return n
    return f(n - 1) + f(n - 2)
resultat = f(5)
print(resultat)
print(crides)
# Respuesta:
# resultat → __
# crides → __
print("--- Ejercicio 26 ---")
def f(d, n):
    if n == 0 or not d:
        return 0
    clau = list(d.keys())[0]
    resta = {k: d[k] for k in list(d.keys())[1:]}
    return d[clau] + f(resta, n - 1)
dades = {"a": 3, "b": 5, "c": 2}
print(f(dades, 2))
print(f(dades, 10))
# Respuesta:
# f(dades, 2) → __
# f(dades, 10) → __
# ── Ejercicio 27 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 27 ---")
def f(s, i=0):
    if i >= len(s):
        return 0
    return (1 if s[i].isupper() else 0) + f(s, i + 1)
print(f("HolaMon"))
print(f("python"))
# Respuesta:
# f("HolaMon") → __
# f("python") → __

# ── Ejercicio 28 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 28 ---")
def f(n):
    if n == 1:
        return [1]
    anterior = f(n - 1)
    return anterior + [anterior[-1] * 2]
print(f(5))
# Respuesta:
# __
# ── Ejercicio 29 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 29 ---")
def f(v, esquerra, dreta):
    if esquerra > dreta:
        return -1
    mig = (esquerra + dreta) // 2
    print("mirant", mig, v[mig])
    if v[mig] == 7:
        return mig
    elif v[mig] < 7:
        return f(v, mig + 1, dreta)
    else:
        return f(v, esquerra, mig - 1)
v = [1, 3, 5, 7, 9, 11]
print(f(v, 0, len(v) - 1))
# Respuesta:
# (escribe cada print en orden)
# __
# __
# __
# ── Ejercicio 30 ─────────────────────────────────────────────────────────────
print("--- Ejercicio 30 ---")
def f(n, indent=0):
    if n == 0:
        return 0
    a = f(n - 1, indent + 1)
    b = f(n - 1, indent + 1)
    total = a + b + n
    print(" " * indent + str(total))
    return total
f(3)
# Respuesta:
# (escribe cada print en orden, con sus espacios)
# __
# __
# __
# __
# __
# __
# __
# ==============================================================================
# BLOQUE 3 — TURTLE: DESCRIBE EL DIBUJO
# ==============================================================================
# En estos ejercicios NO se ejecuta código: describes qué dibuja la tortuga.

# ── Ejercicio 31 ─────────────────────────────────────────────────────────────
# --- Ejercicio 31 ---
# from turtle import *
# speed(0)
# def f(level, size):
#     if level == 0:
#         forward(size)
#         return
#     f(level - 1, size / 3)
#     left(60)
#     f(level - 1, size / 3)
#     right(120)
#     f(level - 1, size / 3)
#     left(60)
#     f(level - 1, size / 3)
# for i in range(3):
#     f(1, 300)
#     right(120)
# done()
# Describe el dibujo resultante:
# _______________________
# _______________________

# ── Ejercicio 32 ─────────────────────────────────────────────────────────────
# --- Ejercicio 32 ---
# from turtle import *
# speed(0)
# def f(level, size):
#     if level == 0:
#         forward(size)
#         return
#     f(level - 1, size / 3)
#     left(60)
#     f(level - 1, size / 3)
#     right(120)
#     f(level - 1, size / 3)
#     left(60)
#     f(level - 1, size / 3)
# for i in range(3):
#     f(3, 300)
#     right(120)
# done()
# ¿En qué se diferencia del ejercicio 31?
# _______________________
# _______________________

# ── Ejercicio 33 ─────────────────────────────────────────────────────────────
# --- Ejercicio 33 ---
# from turtle import *
# speed(0)
# def arbre(level, size):
#     if level == 0:
#         return
#     forward(size)
#     left(30)
#     arbre(level - 1, size * 0.7)
#     right(60)
#     arbre(level - 1, size * 0.7)
#     left(30)
#     backward(size)
# penup()
# goto(0, -200)
# setheading(90)
# pendown()
# arbre(5, 100)
# done()
# Describe la forma general del dibujo y explica qué hace backward(size):
# _______________________
# _______________________

# ── Ejercicio 34 ─────────────────────────────────────────────────────────────
# --- Ejercicio 34 ---
# from turtle import *
# speed(0)
# def arbre(level, size):
#     if level == 0:
#         return
#     forward(size)
#     left(30)
#     arbre(level - 1, size * 0.7)
#     right(60)
#     arbre(level - 1, size * 0.7)
#     left(30)
#     backward(size)
# penup()
# goto(0, -200)
# setheading(90)
# pendown()
# arbre(5, 100)
# # Ahora se elimina backward(size):
# def arbre_sense_back(level, size):
#     if level == 0:
#         return
#     forward(size)
#     left(30)
#     arbre_sense_back(level - 1, size * 0.7)
#     right(60)
#     arbre_sense_back(level - 1, size * 0.7)
#     left(30)
#     # SIN backward
# penup()
# goto(200, -200)
# setheading(90)
# pendown()
# arbre_sense_back(4, 80)
# done()
# ¿Qué diferencia visual hay entre arbre y arbre_sense_back?
# _______________________
# _______________________

# ── Ejercicio 35 ─────────────────────────────────────────────────────────────
# --- Ejercicio 35 ---
# from turtle import *
# speed(0)
# def sierpinski(level, size):
#     if level == 0:
#         return
#     sierpinski(level - 1, size / 2)
#     forward(size)
#     left(-120)
#     sierpinski(level - 1, size / 2)
#     forward(size)
#     left(-120)
#     sierpinski(level - 1, size / 2)
#     forward(size)
#     left(-120)
# penup()
# goto(-150, -100)
# pendown()
# sierpinski(4, 300)
# done()
# a) ¿Qué figura genera este código a nivel alto?
# b) ¿Cuántos triángulos "vacíos" hay en el nivel 2 (level=2)?
# _______________________
# _______________________

# ── Ejercicio 36 ─────────────────────────────────────────────────────────────
# --- Ejercicio 36 ---
# from turtle import *
# speed(0)
# def quadrat(level, size):
#     if level == 0:
#         for _ in range(4):
#             forward(size)
#             right(90)
#         return
#     quadrat(level - 1, size / 3)
#     forward(size)
#     quadrat(level - 1, size / 3)
#     right(90)
#     forward(size)
#     quadrat(level - 1, size / 3)
#     left(90)
#     forward(size)
#     quadrat(level - 1, size / 3)
# penup()
# goto(-150, 150)
# pendown()
# quadrat(2, 270)
# done()
# a) ¿Qué dibuja cuando level == 0?
# b) ¿Cuántas llamadas recursivas hay por nivel?
# _______________________
# _______________________

# ── Ejercicio 37 ─────────────────────────────────────────────────────────────
# --- Ejercicio 37 ---
# from turtle import *
# speed(0)
# bgcolor("black")
# pencolor("white")
# def espiral(level, size, angle):
#     if level == 0:
#         return
#     forward(size)
#     right(angle)
#     espiral(level - 1, size + 5, angle)
# espiral(60, 5, 89)
# done()
# Describe la forma que genera espiral(60, 5, 89).
# ¿Qué pasaría si angle fuera 90 exacto en lugar de 89?
# _______________________
# _______________________

# ── Ejercicio 38 ─────────────────────────────────────────────────────────────
# --- Ejercicio 38 ---
# from turtle import *
# speed(0)
# def hilbert(level, angle, size):
#     if level == 0:
#         return
#     right(angle)
#     hilbert(level - 1, -angle, size)
#     forward(size)
#     left(angle)
#     hilbert(level - 1, angle, size)
#     forward(size)
#     hilbert(level - 1, angle, size)
#     left(angle)
#     forward(size)
#     hilbert(level - 1, -angle, size)
#     right(angle)
# penup()
# goto(-150, 150)
# pendown()
# setheading(0)
# hilbert(3, 90, 20)
# done()
# a) ¿Qué hace pasar -angle en algunas llamadas recursivas?
# b) ¿Qué propiedad especial tiene la curva de Hilbert respecto al espacio?
# _______________________
# _______________________

# ── Ejercicio 39 ─────────────────────────────────────────────────────────────
# --- Ejercicio 39 ---
# from turtle import *
# speed(0)
# bgcolor("black")
# colors = ["#FF0000", "#FF7700", "#FFFF00", "#00FF00", "#0000FF"]
# def estrella(level, size):
#     if level == 0:
#         return
#     pencolor(colors[level % len(colors)])
#     for _ in range(5):
#         forward(size)
#         right(144)
#     estrella(level - 1, size * 0.6)
# estrella(5, 200)
# done()
# a) ¿Cuántas estrellas se dibujan en total?
# b) ¿Cada estrella tiene el mismo tamaño? Explica la relación entre niveles.
# c) ¿Qué controla colors[level % len(colors)]?
# _______________________
# _______________________

# ── Ejercicio 40 ─────────────────────────────────────────────────────────────
# --- Ejercicio 40 ---
# from turtle import *
# speed(0)
# bgcolor("black")
# pencolor("white")
# def branques(level, size, angle):
#     if level == 0:
#         return
#     pensize(level)
#     forward(size)
#     left(angle)
#     branques(level - 1, size * 0.75, angle)
#     right(angle * 2)
#     branques(level - 1, size * 0.75, angle)
#     left(angle)
#     backward(size)
# penup()
# goto(0, -250)
# setheading(90)
# pendown()
# for a in [20, 35]:
#     branques(6, 100, a)
# done()
# a) ¿Por qué se llama branques dos veces desde el programa principal con ángulos distintos?
# b) ¿Qué efecto tiene pensize(level) en el dibujo?
# c) ¿Qué hace left(angle) / right(angle*2) / left(angle) juntos?
# _______________________
# _______________________
# _______________________