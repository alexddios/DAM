# La vista no conoce ninguna clase del modelo

La vista no conoce ninguna clase del modelo: ningún archivo del paquete view importa ni nombra nada del paquete model, ni ModelContract. Solo conoce el paquete contract (PresenterContract, ViewContract y los records como Picture) y las clases de Java.

Por qué: la vista solo habla con el presentador. Si la vista no puede ni nombrar el modelo, la separación de MVP se ve en los imports, y el alumno de 2º DAM la comprueba de un vistazo.
