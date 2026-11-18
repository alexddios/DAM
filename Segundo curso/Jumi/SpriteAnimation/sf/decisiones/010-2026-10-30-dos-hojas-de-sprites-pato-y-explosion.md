# Dos hojas de sprites: pato y explosión

- **Origen:** SpriteAnimation, 2026-10-30, al elegir las hojas de sprites (tarea 2). El propietario: «descarga el pato y la explosión de Kutejnikov»; y, sobre cómo usarlas, eligió «Dos hojas».
- **Contexto:** las dos animaciones de la demo vienen de autores distintos, cada una en su hoja, y hasta ahora se había dado por hecho que saldrían de una misma hoja.
- **Opciones:** cargar dos hojas, o juntarlas en una sola imagen con las dos animaciones en filas distintas.
- **Decisión:** la demo carga dos hojas, ambas CC0: `pato.png`, un pato volando en 4 fotogramas de 16×16 en columna, y `explosion.png`, una explosión en 8 fotogramas de 48×48 en fila. Están en `src/main/resources/sprites/`, con su origen en `ORIGEN.md`.
- **Consecuencias:** `SpriteSheet` carga cada hoja por separado y recorta en los dos sentidos, en columna y en fila, lo que también se aprovecha para enseñarlo. Las dos animaciones tienen distinto número de fotogramas (4 y 8), lo que ayuda a ver que cada una lleva su ritmo.
