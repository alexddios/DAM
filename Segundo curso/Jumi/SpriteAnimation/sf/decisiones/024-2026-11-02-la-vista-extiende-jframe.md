# La vista extiende JFrame

- **Origen:** SpriteAnimation, 2026-11-02, al repasar el código de la demo para los alumnos.
- **Contexto:** `View` tenía un campo `JFrame` que creaba en su constructor. El propietario pidió que la propia vista sea la ventana.
- **Opciones:** A, `View` con un campo `JFrame` (composición); B, `View extends JFrame`.
- **Decisión:** B. `View extends JFrame` e implementa `AnimationContract.View`. Es la forma que los alumnos suelen ver en clase de Swing, y la vista queda más corta.
- **Consecuencias:** `View` hereda los métodos públicos de `JFrame`, pero el presentador solo la ve a través de la interfaz `AnimationContract.View`, así que no los usa. Las tarjetas del documento del MVP que muestren la ventana se ajustan.
