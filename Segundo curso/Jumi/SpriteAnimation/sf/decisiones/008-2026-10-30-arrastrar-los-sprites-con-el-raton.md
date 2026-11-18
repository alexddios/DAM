# Arrastrar los sprites con el ratón

- **Origen:** SpriteAnimation, 2026-10-30, al preparar el documento de visión. El propietario: «ademas usaremos el ratón para poder dragear los sprites». Confirmó que queda fuera mover a los personajes como en un juego (teclas, física, colisiones) y se permite arrastrarlos, y que la animación sigue a su ritmo mientras se arrastra.
- **Contexto:** «Un hilo por sprite y un hilo de pintado» dejaba fuera los controles de movimiento; arrastrar es mover, así que había que aclarar el alcance.
- **Opciones:** mientras se arrastra, la animación sigue a su ritmo o se pausa.
- **Decisión:** cada sprite se puede arrastrar con el ratón, y su animación sigue a su ritmo mientras se arrastra. Siguen fuera los controles de movimiento propios de un juego: teclas, física y colisiones.
- **Consecuencias:** cada `Sprite` guarda su posición en el modelo. El arrastre recorre MVP de vuelta: la vista lo recoge, el presentador mueve el sprite en el modelo y el siguiente pintado lo dibuja donde diga el modelo. Si dos sprites se solapan, se coge el de arriba. Los eventos del ratón llegan por el hilo de eventos de Swing y escriben estado que lee el hilo de pintado; el código lo resuelve de forma mínima y correcta y lo explica en un comentario, sin hacer de ello una lección. Esta decisión precisa el alcance de «Un hilo por sprite y un hilo de pintado».
