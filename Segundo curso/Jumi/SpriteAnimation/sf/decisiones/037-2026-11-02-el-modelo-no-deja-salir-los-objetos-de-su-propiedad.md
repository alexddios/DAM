# El modelo no deja salir los objetos de su propiedad

El modelo no deja salir ningún objeto de su propiedad: sus sprites, sus hojas, sus fotogramas ni sus listas. Lo que entrega hacia fuera son datos inmutables (números, textos, records) o copias, como la copia del fotograma que devuelve frame(i).

Por qué: el modelo es el dueño de sus datos y el único que puede cambiarlos. Es la regla vista desde el modelo, igual que la de que el presentador nunca pasa objetos del modelo a la vista (036) lo es desde el presentador; las dos concretan la de datos inmutables sin referencias compartidas (035).
