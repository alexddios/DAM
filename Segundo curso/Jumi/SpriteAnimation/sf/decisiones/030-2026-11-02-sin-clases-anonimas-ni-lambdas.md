# Sin clases anónimas ni lambdas

En el código de la demo y en sus pruebas no se usan clases anónimas ni lambdas: todo el código está en métodos y clases con nombre. Donde Java pide un Runnable o similar, se pasa una referencia a un método con nombre (this::renderLoop).

La ventana se cierra con setDefaultCloseOperation(EXIT_ON_CLOSE), en lugar de un WindowAdapter anónimo.

Por qué: proyecto KISS para alumnos de 2º DAM; el código con nombre se lee y se sigue mejor que el anónimo.
