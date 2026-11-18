# El contrato expone una interfaz Sprite

- **Origen:** SpriteAnimation, 2026-11-02, al ordenar el código de la demo en paquetes.
- **Contexto:** `AnimationContract.Model.sprite(int)` devolvía la clase `model.Sprite`, así que `contract` y `presenter` importaban `model`. Con los paquetes, esa dependencia de una clase concreta del modelo se veía en los imports.
- **Opciones:** A, una interfaz `AnimationContract.Sprite` que la clase `Sprite` implementa; B, dejarlo así; C, dejarlo y explicarlo en el documento del MVP.
- **Decisión:** A. Fuera de `model`, solo `main` importa `model`, para montar la demo. Las clases compartidas por las pruebas van en un paquete propio, `check`.
- **Consecuencias:** el contrato gana una interfaz más, con los métodos de `Sprite` que usan el presentador y la vista. Las tarjetas del documento del MVP que muestran el contrato se ajustan.
