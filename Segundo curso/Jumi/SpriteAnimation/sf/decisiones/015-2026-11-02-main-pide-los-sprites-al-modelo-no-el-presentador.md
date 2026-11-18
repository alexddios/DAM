# main pide los sprites al modelo, no el presentador

- **Origen:** SpriteAnimation, 2026-11-02, al revisar el montaje del documento explicativo de MVP (tarea 6). El propietario: las llamadas a `model.addSprite(…)` van «en el main, si no, el presentador [queda] acoplado».
- **Contexto:** «Los sprites se crean en el modelo y el presentador arranca la aplicación» ponía las llamadas a `addSprite` dentro de `Presenter.start()`. Así el presentador conocía las hojas, los tamaños y las posiciones de cada animación concreta.
- **Decisión:** `main` llama a `model.addSprite(…)` para cada animación, tras crear el modelo. El presentador no sabe qué animaciones hay: `Presenter.start()` conecta la vista (`view.setPresenter(this)`) y arranca el modelo y la vista. El modelo sigue creando cada `Sprite` dentro de `addSprite`, con la misma firma.
- **Consecuencias:** precisa la decisión anterior en dónde se piden los sprites; lo demás no cambia. El documento explicativo de MVP, sus viñetas y el ejercicio de la tercera animación (que ahora cambia `main`, no `Presenter`) se adaptan.
