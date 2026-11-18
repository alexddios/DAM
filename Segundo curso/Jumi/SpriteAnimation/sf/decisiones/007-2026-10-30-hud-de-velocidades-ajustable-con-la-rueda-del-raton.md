# HUD de velocidades ajustable con la rueda del ratón

- **Origen:** SpriteAnimation, 2026-10-30, al preparar el documento de visión. El propietario: «Crearemos un pequeño huy para las velocidades y habilitaremo alguna forma de cambiarlas... algunas teclas o mejor todavia el raton»; «Con el ratón en tiempo de ejecución»; eligió la rueda sobre cada cifra.
- **Contexto:** la diferencia entre la velocidad de renderización y la de las animaciones (ver «Velocidad de renderización frente a velocidad de animación») tiene que verse y poder tocarse.
- **Opciones:** cifras en pantalla o solo en el código; cambiar constantes, usar teclas o el ratón; con el ratón, la rueda sobre cada cifra, botones + y − dibujados o deslizadores de Swing.
- **Decisión:** un pequeño HUD dibujado en el `Canvas` muestra la velocidad de pintado y la de cada sprite. Cada cifra se sube o se baja con la rueda del ratón mientras la demo se ejecuta.
- **Consecuencias:** el HUD se pinta en el hilo de pintado, como todo lo demás. El gesto del ratón recorre MVP de vuelta: la vista lo recoge, avisa al presentador y este cambia la velocidad del sprite en el modelo, o la de pintado en la vista. Los ajustes de velocidad no son controles de movimiento, así que no chocan con «no es un juego».
