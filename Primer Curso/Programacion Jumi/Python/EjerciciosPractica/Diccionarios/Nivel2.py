# La Caja Registradora Tienes un diccionario con productos y sus precios: precios = {'manzana': 50, 'pan': 20, 'leche': 30}
#
# Imprime una lista con solo los nombres de los productos (las claves).
#
# Calcula el costo total de comprar uno de cada producto (suma de los valores). Pista: usa sum(precios.values()).
precios = {'manzana': 50, 'pan': 20, 'leche': 30}
print(list(precios.keys()))
costoTotal=sum(precios.values())
print(costoTotal)
# Inventario Detallado Usando el mismo diccionario precios:
#
# Usa un bucle for para imprimir frases como: "El precio de manzana es 50".
#
# Pista: Usa .items() para obtener clave y valor al mismo tiempo: for producto, precio in precios.items():
for producto,precio in precios.items():
    print(f"El precio de {producto} es {precio}")
