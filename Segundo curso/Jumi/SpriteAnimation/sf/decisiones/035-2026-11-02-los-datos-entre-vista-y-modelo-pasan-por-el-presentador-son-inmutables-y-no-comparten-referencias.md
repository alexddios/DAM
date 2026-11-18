# Los datos entre vista y modelo pasan por el presentador, son inmutables y no comparten referencias

Los datos que van del modelo a la vista, o de la vista al modelo, viajan siempre a través del presentador. Nunca hay un camino directo entre vista y modelo.

Esos datos son inmutables (records, números, textos) y no comprometen referencias: nadie recibe una referencia a un objeto interno de otra capa. Si hay que pasar algo que se puede modificar, como una BufferedImage, se pasa una copia.

Por qué: es la regla general detrás de la decisión de que el sprite no salga del modelo (034). Enseña a los alumnos de 2º DAM que cada capa protege sus datos y que el presentador es el único paso entre modelo y vista.
