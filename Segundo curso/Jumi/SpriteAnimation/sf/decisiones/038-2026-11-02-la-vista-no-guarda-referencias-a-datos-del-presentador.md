# La vista no guarda referencias a datos del presentador

La vista no guarda en sus campos lo que le da el presentador (la lista de Picture ni ninguna de ellas). Lo pide cuando lo necesita, lo usa y lo suelta: para pintar, en cada vuelta; para saber qué sprite hay bajo el ratón, en ese momento.

Lo único que la vista guarda es su propio estado de interacción, como el número del sprite que se está arrastrando o su velocidad de pintado.

Por qué: la vista siempre trabaja con datos al día y no hay copias viejas que se queden desfasadas. Completa las reglas de datos inmutables sin referencias compartidas (035 a 037).
