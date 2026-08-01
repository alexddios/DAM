###
# DICCIONARIS
###
print()

# Creació explicita
d = {1: 2, "a": "b", 3: "c", "hola": 4}

# Mostrar per consola un diccionari
print(d)

# Mida
print("Mida", len(d))

# Accès a element o items de la llista
print(d["a"])


# Inserció d'elements (directament o amb mètode update())
d["nova_clau"] = "nou_valor"
print("Item afegit", d["nova_clau"])

d.update({"una_altra_clau": 12345})
print(d)

# Modificació d'elements directament o amb mètode update()
d["nova_clau"] = "valor_modificat"
print("Item modificat", d["nova_clau"])

d.update({"nova_clau": "valor_modificat_2", 1:5, 25:"Afegit nou"})
print("Una modificació més...", d["nova_clau"])


# Eliminació d'elements (pop, popitem, del, clear)
d.pop("una_altra_clau")
print(">", d)

print(d)
d.popitem() 
d.popitem() 
print(d)


# "Accès" a valor que no existeixen directament
# print(d["no existeix"])


# Emprant get()
if d.get("no existeix") is None:
    print("No he trobat la clau al diccionari")



# Recorrer un diccionari
for clau in d:
    print("key",clau)

for clau in  d.keys():
    print("key",clau)

for item in d.items():
    print("Item",item)
    a, b = item
    print("a:",a,"b:",b)

for a, b in d.items():
    print("a:",a,"b:",b)

for v in d.values():
    print("Value:",v)


c = d.copy()
d["mismo"] = "1"
print(c)