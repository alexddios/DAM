# Clases de la demo e interfaces MVP

**Sustituida** el 2026-10-30 por «Clases de la demo con Viewer».

- **Origen:** SpriteAnimation, 2026-10-30, al preparar el documento de visión. El propietario eligió vista y presentador «con interfaces» y «dos sprites, animaciones distintas», y sobre el reparto propuesto: «si pero añadiria SpriteSheet (carga y preparación de la hoja) y a main con el nombre del proyecto».
- **Contexto:** con el mínimo de clases y de código (ver «Ejemplo mínimo de clases y código»), hay que fijar qué clases tiene la demo, si MVP lleva interfaces y cuántos sprites y animaciones muestra.
- **Opciones:** clases concretas sin interfaces o interfaces para la vista y el presentador; dos sprites con una animación, varios sprites con una animación o dos sprites con animaciones distintas; el reparto en cinco clases propuesto o uno con menos clases.
- **Decisión:** la demo tiene estas clases, como punto de partida para la definición técnica: `SpriteAnimation` (la principal, con el nombre del proyecto), `SpriteSheet` (carga y preparación de la hoja), `Sprite`, `Model`, `View` y `Presenter`. La vista y el presentador se definen con interfaces, para que se vea el contrato entre las capas. La demo muestra dos sprites con animaciones distintas.
- **Consecuencias:** la hoja de sprites tiene al menos dos animaciones, y `SpriteSheet` recorta más de una. Los nombres de las interfaces y los detalles de cada clase se fijan en la definición técnica.
