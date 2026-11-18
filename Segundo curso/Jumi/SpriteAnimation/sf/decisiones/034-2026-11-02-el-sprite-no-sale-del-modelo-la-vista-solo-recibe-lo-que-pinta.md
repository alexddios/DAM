# El sprite no sale del modelo: la vista solo recibe lo que pinta

Ningún objeto sprite sale del paquete model, y nadie fuera del modelo tiene referencias a sus objetos. Desaparece SpriteContract: sustituye por completo a la decisión de que el contrato exponga una interfaz Sprite (025) y cambia en parte la 012 y la 017.

- Modelo: ModelContract responde por número de sprite: frame(i), x(i), y(i), name(i), fps(i), setFps(i, fps), moveTo(i, x, y), spriteCount(), start(). frame(i) devuelve una copia del fotograma, nunca la imagen que guarda el modelo.
- Lo que se visualiza: la vista pide al presentador una lista de records a visualizar, List<Picture>, con record Picture en contract, que sustituye a SpriteState. Cada Picture lleva la imagen (la copia) y todo lo que la vista necesita para mostrar ese sprite: coordenadas, nombre y velocidad. La vista ve imágenes y datos, nunca sprites ni objetos del modelo.
- HUD: el presentador no escribe textos. La vista monta el texto del HUD con los datos de cada Picture.
- Ratón: la vista se encarga de la interacción con el usuario. Sabe qué sprite hay bajo el ratón con las Picture que ha pintado (el de encima gana); spriteAt sale del presentador. Al arrastrar, manda la posición nueva con presenter.moveSprite(i, x, y). Las acciones las decide el presentador.

Por qué: separación clara de MVP para alumnos de 2º DAM. El modelo guarda sus objetos y solo entrega copias y datos; el presentador traduce y decide; la vista pinta e interactúa con el usuario sin conocer el modelo. Un solo record con todo lo que se visualiza es lo más simple (KISS).
