# El record Picture se llama SpriteView y la rueda pide spriteCount()

El record que la vista recibe para mostrar cada sprite (imagen, x, y, nombre y velocidad) se llama SpriteView, en el paquete contract, en lugar de Picture: lleva más que la imagen, es lo que se ve de un sprite.

PresenterContract añade int spriteCount(). La rueda del ratón lo usa para contar las líneas del HUD, en lugar de pedir la lista entera de SpriteView con sus imágenes copiadas.

Por qué: aplica la regla de los datos mínimos (042): quien solo necesita un número recibe solo el número.
