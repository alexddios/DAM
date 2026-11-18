# Interfaces y constructores de la demo

- **Origen:** SpriteAnimation, 2026-10-31, al preparar el documento explicativo de MVP (tarea 4). El propietario eligió «Fijarlos ya»: los fragmentos de código del documento fijan las interfaces y los constructores, y el código se escribirá según ellos.
- **Contexto:** «Clases de la demo con Viewer» dejaba para la definición técnica los nombres de las interfaces y los detalles de cada clase. El documento explicativo necesita código concreto.
- **Opciones:** fragmentos como bocetos con aviso, o fijar ya el contrato.
- **Decisión:** el contrato es este, comprobado con un esqueleto que compila y se ejecuta con Java 21:
  - `AnimationView` (lo implementa `View`): `setPresenter(AnimationPresenter)`, `renderFps()`, `setRenderFps(int)`, `start()`, `stop()`.
  - `AnimationPresenter` (lo implementa `Presenter`): `sprites()`, que devuelve una lista de `SpriteState(String name, BufferedImage frame, int x, int y, int fps)`, un `record` declarado dentro de la interfaz; `changeRenderSpeed(int)`, `changeSpriteSpeed(int, int)`, `spriteAt(int, int)`, `moveSprite(int, int, int)` y `close()`.
  - Constructores: `Sprite(String name, BufferedImage[] frames, int fps, int x, int y)`, `Model(List<Sprite>)`, `Presenter(Model, AnimationView)`, `Viewer(int width, int height)` y `View()`, que crea su propio `Viewer`.
  - `SpriteSheet.load(String resource, int frameWidth, int frameHeight, int scale)` es un método estático que recorta y amplía los fotogramas.
  - La inyección de dependencias se hace por constructor en `SpriteAnimation.main`, el único sitio que crea las capas. La dependencia circular entre vista y presentador se cierra con `view.setPresenter(presenter)`.
- **Consecuencias:** el código de la demo se escribe con este contrato. El documento explicativo de MVP (`docs/material/`) lo usa en sus fragmentos. Precisa «Clases de la demo con Viewer» en lo que dejaba abierto.
