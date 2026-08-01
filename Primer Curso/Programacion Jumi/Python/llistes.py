###
# LLISTES
###


# Creació explicita
l1 = [1, 2, 3, "h"]


# Mostrar per consola
print(l1)


# Mida de la llista
print("Mida", len(l1))


# Accès a element o items de la llista
print("Accès per index", l1[0])
print("Darrer element", l1[len(l1)-1])
print("Darrer element", l1[3])
print("Darrer element", l1[-1])
print("Penúltim element", l1[-2])
print("Antepenúltim element", l1[-3])



# Modificació de la llista (dels seus items)
l1[2] = "dos"
print("Valor modificat emprant accès indexat", l1[2])

l2 = l1*2
print("Multiplicar una llista", l2)

l3 = l1 + ["hola", "radiola"]
print("Sumar llistes", l3)

l4 = [0]*100
print("Inicialitzar llistes\n", l4)

l1.append("tres")
print("l1", l1)

l1.remove(2)
l1.remove("dos")
print("l1", l1)

print("Darrer element", l1.pop())
print("l1", l1)


# Cercar la posició d'un element a una llista
print("Posició de 'h'", l1.index("h"))

# Comprovar si un element existeix a la llista
print("'h' existeix a la llista?", "h" in l1)

# Comprovar si un element no existeix a la llista
print("'h' NO existeix a la llista", "h" not in l1)

# Ordenar una llista
l4 = ["un", "dos", "tres", "quatre", "cinq", "01", "1"]
l4.sort()
print("l4", l4)

# Recorrer una llista per processar els seus items
print("Recorrer llista mode 1")
for item in l1:
    print("Item", item)

print("Recorrer llista mode 2")
for idx in range(len(l1)):
    print(idx, "=>", l1[idx])

print("Recorrer llista mode 3")
for idx, value in enumerate(l1):
    print(idx, "=>", value)

print("Recorrer llista mode 4")
idx = 0
while idx < len(l1):
    print(idx, "=>", l1[idx])
    idx += 1


# Llistes múltiples o de múltiples dimensions
lm = [[1, 2], [3, 4], [7, "hola", "adeu"], [9, 10]]
print("Llistes múltiples", lm)
print("Valor de la quarta posició", lm[3])
print("Valor del primer element de la quarta posició", lm[3][0])
print("Valor del darrer element de la quarta posició", lm[3][-1])

print("Recorrer llistes múltiples")
for idx, subllista in enumerate(lm):
    for item_subllista in subllista:
        print("    ", item_subllista, end="")
    print()


# Creació de subllistes
l4 = ["un", "dos", "cinq", 1, 2, 5, 6]
print("Subllistes exemple  1",  l4[2:4])
print("Subllistes exemple  2",  l4[2:])
print("Subllistes exemple  3",  l4[2:None])
print("Subllistes exemple  4",  l4[:3])
print("Subllistes exemple  5",  l4[:])
print("Subllistes exemple  6",  l4[::2])
print("Subllistes exemple  7",  l4[-2:None])
print("Subllistes exemple  8",  l4[-1:-3:-1])
print("Subllistes exemple  9",  l4[-1:None:-1])
print("Subllistes exemple 10",  l4[None:None:-1])

# Accès als identificadors de llistes i altre objectes
lm = [1, 2, 3]
print("Identificador lm exemple 1", id(lm))
lm = [1, 2, 3]
print("Identificador lm exemple 2", id(lm))


# Concatenar llistes
l5 = [1, 2, 3]
l6 = [4, 5, 6]
id_l5_a = id(l5)
l5 = l5 + l6
id_l5_b = id(l5)
print("Concatenar llistes mode 1", l5,
    f"Identificador l5: {id_l5_a} -> {id_l5_b}")

l5 = list(l5 + l6)
id_l5_c = id(l5)
print("Concatenar llistes mode 2", l5,
    f"Identificador l5: {id_l5_b} -> {id_l5_c}")

for item in l6:
    l5.append(item)

id_l5_d = id(l5)
print("Concatenar llistes mode 3", l5,
    f"Identificador l5: {id_l5_c} -> {id_l5_d}")
