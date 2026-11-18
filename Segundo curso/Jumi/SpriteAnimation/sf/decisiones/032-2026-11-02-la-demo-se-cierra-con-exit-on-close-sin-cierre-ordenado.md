# La demo se cierra con EXIT_ON_CLOSE, sin cierre ordenado

Al pulsar la X, la ventana termina la aplicación con EXIT_ON_CLOSE. Se quita el cierre ordenado: PresenterContract.close, ViewContract.stop, ModelContract.stop, Sprite.stop, el campo running de View con su join y sus pruebas.

Por qué: KISS. Con EXIT_ON_CLOSE nadie lo llamaba, y el código que la demo no usa confunde al alumno. Se pierde el ejemplo de parar hilos con interrupt y join, que no es objetivo de esta demo.
