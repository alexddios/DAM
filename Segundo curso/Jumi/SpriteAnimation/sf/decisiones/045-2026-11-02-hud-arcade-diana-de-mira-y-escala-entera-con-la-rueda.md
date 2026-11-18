# HUD arcade, diana de mira y escala entera con la rueda

- HUD con estilo arcade retro: fondo casi negro, marco doble rosa y cian, letra monoespaciada amarilla con etiquetas en mayúsculas, número a la derecha y barra de bloques verde, amarilla y roja. La línea bajo el ratón se resalta con una banda amarilla, texto negro y un triángulo a la izquierda.
- Diana: el sprite bajo el ratón lleva encima una mira roja fija (dos círculos y cuatro trazos de cruz con el centro libre), en su propia clase de la vista.
- Escala: con la rueda sobre un sprite (no sobre el HUD, que tiene prioridad), su escala entera sube o baja de uno en uno entre 1 y 8 por defecto, configurable desde main. El modelo guarda los fotogramas originales y amplía al dar la copia de frame(i). El sprite crece desde su centro, lo resuelve el modelo. El HUD no muestra la escala.
- Ratón: la vista guarda solo la posición del ratón (mouseX y mouseY); el HUD y la diana calculan al pintar qué hay debajo.

Por qué: aspecto de juego sin perder legibilidad del código para los alumnos de 2º DAM, y un único estado de ratón compartido.
