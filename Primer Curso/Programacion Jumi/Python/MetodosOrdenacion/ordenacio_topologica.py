# ALGORITMO (Ver, por ejemplo, en wikipedia)
# ==========================================
# CALCULAR los items sin precedentes
# MIENTRAS [items_sin_precendentes no es vacío]: // hay nodos que no tienen precedentes
#       INSERTAR lista_ordenada TODOS los items_sin_precendentes que hay en la lista
#       ELIMINAR de lista TODOS los nodos_sin_precedentes
#       ELIMINAR de restricciones TODAS las aristas QUE SALEN de nodos_sin_precedentes
#       RE-CALCULAR los nodos_sin_precedentes
#
from os import system


def mostrar_lista(titulo, lista):
    print(titulo)
    for idx, value in enumerate(lista):
        print("\t", idx, value)


def calcular_items_sin_precedentes(lista, restricciones):
    item_sin_precendentes = []

    for item in lista:
        item_sin_precendentes.append(item)
        for r in restricciones:
            if item == r[1]:
                item_sin_precendentes.pop()
                break  # ==========>

    return item_sin_precendentes


def insertar_items_lista(lista, items_a_insertar):
    for item in items_a_insertar:
        lista.append(item)

    return lista


def eliminar_items_lista(lista, items_eliminar):
    for item in items_eliminar:
        lista.remove(item)

    return lista


def eliminar_restricciones(restricciones, restricciones_a_eliminar):
    # Para cada nodo sin precendentes
    for item in restricciones_a_eliminar:
        # Buscar restricciones del nodo concreto
        for idx, r in enumerate(restricciones):
            if r[0] == item:
                # Eliminar esta restricción
                restricciones[idx] = (None, None)

    return restricciones


def ordenacion_topologica(items_a_ordenar, restricciones):

    lista_ordenada = []
    items_sin_precendentes = calcular_items_sin_precedentes(items_a_ordenar, restricciones)

    while items_sin_precendentes:
        lista_ordenada = insertar_items_lista(lista_ordenada, items_sin_precendentes)

        items_a_ordenar = eliminar_items_lista(items_a_ordenar, items_sin_precendentes)

        restricciones = eliminar_restricciones(restricciones, items_sin_precendentes)

        items_sin_precendentes = calcular_items_sin_precedentes(
            items_a_ordenar, restricciones
        )

    return lista_ordenada


def main():
    lista = [
        "planta 1",
        "planta 2",
        "planta 3",
        "cimientos",
        "instalaciones fontaneria",
        "instalaciones electrica",
        "ascensor",
    ]
    restricciones = [
        ("planta 1", "planta 2"),
        ("planta 2", "planta 3"),
        ("cimientos", "planta 1"),
        ("instalaciones electrica", "ascensor"),
        ("planta 3", "instalaciones fontaneria"),
        ("planta 3", "instalaciones electrica"),
    ]

    system("cls")
    print("Ordenación Topológica")
    print("=====================")
    mostrar_lista("> Lista original", lista)
    mostrar_lista("> Restricciones", restricciones)

    lista_ordenada = ordenacion_topologica(lista, restricciones)

    mostrar_lista("+ Lista ordenada", lista_ordenada)
    mostrar_lista("+ Pendientes de ordenar", lista)


if __name__ == "__main__":
    main()