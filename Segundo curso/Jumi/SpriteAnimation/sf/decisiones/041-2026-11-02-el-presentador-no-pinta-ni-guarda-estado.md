# El presentador no pinta ni guarda estado

El presentador no pinta: no usa Graphics ni compone imágenes ni textos; eso es de la vista.

El presentador no guarda estado de la aplicación: ni posiciones, ni velocidades, ni listas de sprites o de Picture, ni nada que copie lo que tiene el modelo o lo que muestra la vista. Cada vez que le piden algo, lo pregunta al modelo, lo traduce y lo entrega.

Solo tiene sus conexiones (el modelo y la vista que recibe al montarse) y su configuración (los límites de velocidad que pone main), que no cambian mientras funciona.

Por qué: el presentador es un traductor y un árbitro sin memoria propia. El estado vive en el modelo, y lo visual en la vista; así no hay dos copias que se desincronicen.
