# El modelo no conoce ninguna clase de la vista

El modelo no conoce ninguna clase de la vista: ningún archivo del paquete model importa ni nombra nada del paquete view, ni ViewContract, PresenterContract ni Picture. Solo conoce ModelContract y las clases de Java.

Por qué: es la otra mitad de la regla de que la vista no conoce ninguna clase del modelo (039). El modelo no sabe cómo ni dónde se muestran sus datos, y la separación de MVP se ve en los imports en los dos sentidos.
