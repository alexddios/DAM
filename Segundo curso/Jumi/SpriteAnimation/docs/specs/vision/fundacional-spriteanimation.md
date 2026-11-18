---
antetitulo: SpriteAnimation · Documento fundacional · Noviembre 2026
lema: Animar sprites, // separar el estado del dibujo.
subtitulo: Un ejemplo mínimo en Java para enseñar hojas de sprites, MVP e hilos en segundo de DAM.
version: Versión 0.2 · 02/11/2026
estado: Demo construida · Documento de proyecto
portada: oscura
pie: SpriteAnimation es un material de consulta para profesores y alumnos de segundo de DAM. La demo está construida; su valor lo validará su uso en clase.
destino: proyecto
herramientas: buscador lectura
---
# SpriteAnimation

Código de la demo: [repositorio en GitHub](https://github.com/jumibot/SpriteAnimation)

> **Alcance**
> La demo ya está construida, con sus pruebas, en el repositorio del proyecto. Lo que este documento describe de ella es lo que hace su código, y las cifras de velocidad (60 repintados, 8 y 12 fotogramas por segundo) son las de la demo al arrancar. Lo que falta por comprobar es su uso en clase.

## Razón | Razón para desarrollar {#razon}

SpriteAnimation es una demo en Java, con Swing y Java2D, pensada para que un profesor de segundo de DAM enseñe a animar sprites en el módulo optativo de videojuegos y sus alumnos la consulten cuando la necesiten. Su propósito es que, en una sola ventana y con muy poco código, se vean tres ideas: cómo se recorta una hoja de sprites, por qué conviene separar el estado del dibujo y por qué la velocidad de una animación no depende de la de pintado.

Lo que ya existe resuelve otras cosas. El tutorial de Java de Oracle avanza el fotograma con un temporizador de Swing y pide repintar, sin separar capas [[T1]](#src-T1 "Oracle, Performing Animation"){: .ref}. El libro de Andrew Davison explica el pintado activo con todo detalle, en cerca de mil páginas [[T2]](#src-T2 "Davison, 2005"){: .ref}. Los motores como libGDX recortan la hoja y eligen el fotograma según el tiempo, pero lo esconden tras su propia clase `Animation` [[T3]](#src-T3 "libGDX, 2D Animation"){: .ref}. SpriteAnimation se queda a medio camino: pequeño como un tutorial, con lo que un motor esconde a la vista.

La tesis es que **merece la pena un ejemplo mínimo y ejemplar**, y que tendrá valor si los alumnos dejan de atar la animación al repintado y aprenden a repartir el código en capas. Se apoya en dos hechos: los mismos alumnos estudian hilos ese curso, en Programación de Servicios y Procesos [[C1]](#src-C1 "IES Historiador Chabás, 2023-24"){: .ref}, y separar el ritmo de la simulación del de pintado es una práctica asentada en la programación de juegos [[T4]](#src-T4 "Fiedler, Fix Your Timestep"){: .ref}. Lo que falta por saber es si los alumnos lo usan y si sus prácticas cambian, y eso es lo que debe medir el piloto.

::: tarjetas 2

### Los hilos están en el temario

El módulo 0490, Programación de Servicios y Procesos, de segundo de DAM, trata la programación de aplicaciones con varios hilos [[C1]](#src-C1 "IES Historiador Chabás, 2023-24"){: .ref}. El título de DAM lo establece el Real Decreto 450/2010 [[C2]](#src-C2 "BOE, RD 450/2010"){: .ref}.

Lectura para el proyecto
:   Respalda que los alumnos del módulo de videojuegos trabajan los hilos en el mismo curso, así que la demo se apoya en lo que ven en otro módulo. No dice en qué orden llegan los dos temas a lo largo del curso.

### Atar la animación al repintado

El patrón más difundido avanza el fotograma en cada vuelta del temporizador que pide repintar [[T1]](#src-T1 "Oracle, Performing Animation"){: .ref}. La programación de juegos separa, en cambio, el paso del tiempo del pintado [[T4]](#src-T4 "Fiedler, Fix Your Timestep"){: .ref}.

Lectura para el proyecto
:   Respalda que la separación de los dos ritmos es un contenido con fundamento. No mide cuántos alumnos cometen el error.

:::

## Producto | El producto {#producto}

SpriteAnimation abre una ventana con dos animaciones, un pato volando y una explosión, sacadas de dos hojas de sprites. Encima, un pequeño HUD (las cifras que flotan sobre la escena) muestra tres velocidades que se cambian con la rueda del ratón, y los sprites se arrastran sin dejar de animarse.

Por dentro, el código se reparte según la arquitectura MVP (modelo, vista y presentador) con el mínimo de clases con que la separación se ve. Cada sprite avanza en su propio hilo y otro hilo pinta, de modo que pintar y animar son dos ritmos independientes. Todo lo que no sirve para ver estas ideas queda fuera.

::: tarjetas 3

### Clases

**9** clases, 3 contratos y 1 record
{: .metrica}

`SpriteAnimation` monta la aplicación; `Model`, `Sprite` y `SpriteSheet` forman el modelo; `View`, `Viewer`, `Hud` y `MouseHandler`, la vista; y `Presenter`, el presentador. Los contratos son `ModelContract`, `ViewContract` y `PresenterContract`, y el record `SpriteView` lleva a la vista lo que pinta. Cada uno, en su archivo.

### Hojas de sprites

**2** hojas, CC0
{: .metrica}

Un pato de 4 fotogramas de 16×16 en columna [[R1]](#src-R1 "16x16 Duck"){: .ref} y una explosión de 8 fotogramas de 48×48 en fila [[R2]](#src-R2 "Explosion, Kutejnikov"){: .ref}.

### Velocidades

**3** ajustables
{: .metrica}

La de pintado y la de cada sprite, cada una con la rueda del ratón sobre su cifra mientras la demo funciona.

:::

### Cómo se recorta una hoja

`SpriteSheet` carga cada hoja una vez y la parte en fotogramas sueltos, listos para pintar. Recortar es calcular un rectángulo por fotograma a partir de su tamaño y del sentido en que avanzan. Las dos hojas se recortan en sentidos distintos, hacia abajo el pato y hacia la derecha la explosión, para que se vea que el recorte depende de cómo está colocada la hoja.

### Cómo se reparte el trabajo

El reparto sigue MVP, y las capas se hablan por tres contratos, uno por capa: la vista y el modelo no se conocen. La vista se divide en cuatro clases: `View` crea la ventana y tiene el hilo de pintado, `Viewer` es solo el lienzo, un `Canvas`, sobre el que se pinta, `Hud` pinta las velocidades y `MouseHandler` escucha el ratón.

<figure class="vertical">
<svg viewBox="0 0 560 470" role="img" style="font-family:var(--fuente-texto);display:block">
<title>Las clases de SpriteAnimation según MVP, con el recorrido de pintar y el del ratón</title>
<rect x="60" y="10" width="440" height="60" rx="10" fill="none" style="stroke:var(--linea)" stroke-width="2"/>
<text x="80" y="38" font-size="18" font-weight="700" style="fill:var(--primario)">Viewer</text>
<text x="80" y="60" font-size="15" style="fill:var(--texto-suave)">el lienzo, un Canvas</text>
<rect x="60" y="110" width="440" height="80" rx="10" fill="none" style="stroke:var(--linea)" stroke-width="2"/>
<text x="80" y="138" font-size="18" font-weight="700" style="fill:var(--primario)">View</text>
<text x="80" y="160" font-size="15" style="fill:var(--texto-suave)">ventana · hilo de pintado · Hud</text>
<text x="80" y="180" font-size="15" style="fill:var(--texto-suave)">MouseHandler escucha el ratón</text>
<rect x="60" y="250" width="440" height="60" rx="10" fill="none" style="stroke:var(--linea)" stroke-width="2"/>
<text x="80" y="278" font-size="18" font-weight="700" style="fill:var(--primario)">Presenter</text>
<text x="80" y="300" font-size="15" style="fill:var(--texto-suave)">une el modelo y la vista; no pinta ni guarda estado</text>
<rect x="60" y="370" width="440" height="90" rx="10" fill="none" style="stroke:var(--linea)" stroke-width="2"/>
<text x="80" y="398" font-size="18" font-weight="700" style="fill:var(--primario)">Model</text>
<text x="80" y="422" font-size="15" style="fill:var(--texto-suave)">Sprite pato · su hilo, velocidad y posición</text>
<text x="80" y="446" font-size="15" style="fill:var(--texto-suave)">Sprite explosión · ídem</text>
<defs><marker id="fa" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--primario)"/></marker><marker id="fb" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--oscuro)"/></marker></defs>
<line x1="140" y1="108" x2="140" y2="74" style="stroke:var(--primario)" stroke-width="2" marker-end="url(#fa)"/>
<text x="152" y="96" font-size="15" style="fill:var(--primario)">pinta</text>
<line x1="140" y1="192" x2="140" y2="246" style="stroke:var(--primario)" stroke-width="2" marker-end="url(#fa)"/>
<text x="152" y="224" font-size="15" style="fill:var(--primario)">pide SpriteView</text>
<line x1="140" y1="312" x2="140" y2="366" style="stroke:var(--primario)" stroke-width="2" marker-end="url(#fa)"/>
<text x="152" y="344" font-size="15" style="fill:var(--primario)">pide copias</text>
<line x1="420" y1="192" x2="420" y2="246" style="stroke:var(--oscuro)" stroke-width="2" marker-end="url(#fb)"/>
<text x="408" y="224" font-size="15" text-anchor="end" style="fill:var(--oscuro)">rueda, arrastre</text>
<line x1="420" y1="312" x2="420" y2="366" style="stroke:var(--oscuro)" stroke-width="2" marker-end="url(#fb)"/>
<text x="408" y="344" font-size="15" text-anchor="end" style="fill:var(--oscuro)">cambia el modelo</text>
</svg>
<figcaption>A la izquierda, el recorrido de pintar: la vista pide qué pintar y el presentador se lo pide al modelo. A la derecha, el del ratón, que vuelve hasta el modelo.</figcaption>
</figure>

Para pintar, la iniciativa es de la vista: en cada vuelta, su hilo le pide al presentador qué dibujar, y el presentador se lo pide al modelo y se lo da como una lista de `SpriteView`, con una copia de cada fotograma: el sprite no sale del modelo. Para el ratón, `MouseHandler` recoge el gesto y avisa al presentador, que cambia la velocidad o la posición en el modelo. La vista nunca mueve nada por su cuenta.

### Cómo se mantienen los dos ritmos

Cada sprite tiene un hilo que avanza sus fotogramas midiendo el tiempo transcurrido, no contando vueltas, y por eso su ritmo es constante en cualquier equipo. El hilo de pintado dibuja el fotograma que toque en cada instante. Si pinta más rápido que la animación, repite fotogramas; si pinta más despacio, se salta alguno, pero la animación no se ralentiza. Los gestos del ratón llegan por el hilo de eventos de Swing, y el código resuelve con lo mínimo el estado que comparte con el de pintado. Los hilos no se paran uno a uno: al cerrar la ventana, `EXIT_ON_CLOSE` termina el programa y, con él, todos los hilos.

### Qué no hace

No es un juego: no tiene teclas de movimiento, ni física, ni colisiones. Tampoco tiene utilidades, capas ni bibliotecas de concurrencia que no hagan falta para ver los tres temas. Cada clase se justifica por lo que enseña, y lo que falta queda como ejercicio para quien lo amplíe.

## Escenarios | Escenarios de uso {#escenarios}

SpriteAnimation se usa en cuatro situaciones, todas en segundo de DAM. En dos, el profesor la usa para explicar; en otra, la consulta el alumno por su cuenta; en la última, sirve de ejemplo de arquitectura. Los escenarios son compatibles: el mismo grupo puede pasar por varios en un curso.

El valor de todos depende de lo mismo: que el ejemplo sea lo bastante pequeño para leerse y lo bastante correcto para imitarse. Ninguno está medido todavía.

::: tarjetas 2

### S1 · Demostración en clase {: #S1}

El profesor del módulo optativo de videojuegos proyecta la demo en la unidad de animación y cambia las velocidades en vivo.

Cuándo aporta valor
:   Cuando el grupo ya ha intentado animar algo y ha visto el pato cambiar de ritmo según el equipo.

Límite del argumento
:   Ver la diferencia no garantiza que el alumno la aplique en su código.

Qué medir
:   Si los alumnos explican, después de la clase, por qué su animación cambiaba de ritmo.

### S2 · Consulta durante la práctica {: #S2}

El alumno abre la demo mientras hace su práctica y busca en el código cómo se resuelve cada parte.

Cuándo aporta valor
:   Cuando el alumno tiene una duda concreta: cómo recortar la hoja, quién guarda el estado, cómo lleva un hilo su ritmo.

Límite del argumento
:   Un ejemplo que se copia sin entender reproduce la estructura, no la comprensión.

Qué medir
:   Cuántas prácticas separan el pintado del avance de la animación y reparten el código en clases.

### S3 · Ejemplo de hilos en Servicios y Procesos {: #S3}

El profesor de Programación de Servicios y Procesos usa la misma demo, con el mismo grupo, para enseñar a crear hilos, a mantener un ritmo constante y a compartir datos entre ellos sin pisarse [[C1]](#src-C1 "IES Historiador Chabás, 2023-24"){: .ref}.

Cuándo aporta valor
:   Cuando se busca un caso visible: un hilo que se acelera o se frena se ve en pantalla.

Límite del argumento
:   La demo no trata la sincronización a fondo; es un primer ejemplo, no el temario del módulo.

Qué medir
:   Si los alumnos crean hilos y comparten datos entre ellos correctamente en sus propios ejercicios.

### S4 · Ejemplo de arquitectura MVP {: #S4}

El profesor usa la demo para explicar MVP con un caso en el que separar capas es una necesidad: dos hilos tocan el mismo estado.

Cuándo aporta valor
:   Cuando MVP se ha presentado antes con un formulario y los alumnos lo ven como un trámite.

Límite del argumento
:   Nueve clases y tres contratos para una ventana pueden parecer excesivos a quien aún no ha sufrido el código mezclado.

Qué medir
:   Si los alumnos reparten una aplicación propia en modelo, vista y presentador sin que se les pida.

:::

## Riesgos | Riesgos {#riesgos}

El riesgo principal es didáctico: tres temas a la vez (hojas de sprites, MVP e hilos) pueden ser demasiado para una sola unidad. Lo reduce que los hilos se trabajan en paralelo en Programación de Servicios y Procesos. El plan lo detecta pronto, porque la primera fase en el aula ([S1](#S1){: .insignia}) mide si los alumnos entienden la diferencia de ritmos antes de pedirles nada más.

Los otros dos son técnicos y se comprueban en la demo ya construida, antes de llevarla a clase.

::: tarjetas 3

### Tres temas en una unidad

Didáctico
{: .etiqueta-tarjeta}

Mitigación
:   Los hilos son un tema secundario en el módulo de videojuegos y se estudian a fondo en el de servicios y procesos. El código de cada tema vive en su clase, de modo que se puede explicar uno sin los otros.

Se comprueba en
:   La fase 2, en el aula.

### Concurrencia no ejemplar

Técnico
{: .etiqueta-tarjeta}

Mitigación
:   El estado compartido entre hilos se resuelve con lo mínimo y correcto (campos `volatile` y datos que nadie puede cambiar), y un comentario explica por qué.

Se comprueba en
:   La fase 1, con una revisión del código antes de usarlo en clase.

### Canvas dentro de Swing

Técnico
{: .etiqueta-tarjeta}

Mitigación
:   El `Canvas` de AWT es el único componente de dibujo de la ventana, y se prueba en los equipos del aula.

Se comprueba en
:   La fase 1, en dos equipos distintos.

:::

## Demostrar | Qué debe demostrar {#demostrar}

La demo ya está construida; el paso siguiente es comprobarla y usarla en un curso. El piloto tiene que demostrar dos cosas: que el ejemplo es correcto y constante en cualquier equipo, y que cambia la forma en que los alumnos escriben sus prácticas. La primera se comprueba en el laboratorio; la segunda, solo en el aula.

Se propone avanzar por fases, cada una con una condición. Si una no se cumple, se corrige el ejemplo antes de seguir, en lugar de ampliarlo.

::: tarjetas 2

### Fase 1 · Construir y comprobar

Motivo
:   Un ejemplo de consulta tiene que ser correcto antes que completo.

Condición para avanzar
:   Las dos animaciones mantienen su ritmo en dos equipos distintos con velocidades de pintado diferentes; cada tipo está en su archivo, las capas están separadas y cada clase se explica en una línea.

### Fase 2 · Usarla en clase

Motivo
:   Comprobar que la demo se entiende en una unidad real ([S1](#S1){: .insignia}, [S2](#S2){: .insignia}).

Condición para avanzar
:   La mayoría del grupo explica por qué su animación cambiaba de ritmo y dónde va cada parte del código.

### Fase 3 · Comparar las prácticas

Motivo
:   Saber si la demo cambia lo que los alumnos entregan.

Condición para avanzar
:   Las prácticas separan el pintado del avance de la animación con más frecuencia que las del curso anterior.

### Fase 4 · Usarla en Servicios y Procesos

Motivo
:   Aprovechar la demo, con el mismo grupo, en el módulo de hilos ([S3](#S3){: .insignia}) y como ejemplo de MVP ([S4](#S4){: .insignia}).

Condición para avanzar
:   El profesor del módulo la usa sin cambios en el código.

:::

> [!decision]
> **Decisión propuesta**
> Llevar la demo, ya construida, a la unidad de animación del módulo optativo de videojuegos de segundo de DAM. La medida principal es la proporción de prácticas que separan el ritmo de pintado del de la animación, comparada con el curso anterior.

## Fuentes | Fuentes y referencias {#fuentes}

Las fuentes se revisaron el 30 de octubre de 2026. C es currículo; T, técnica; R, recursos gráficos; M, documentos del propio proyecto.

::: fuentes

**[C1] IES Historiador Chabás.** Programación del módulo 0490, Programación de Servicios y Procesos, de 2.º de DAM, curso 2023-24. Es la programación de un centro, no el texto oficial del currículo. [Enlace](https://portal.edu.gva.es/ieschabas/wp-content/uploads/sites/1291/2023/12/2DAM_M0490_PROGRAMACIO_DE_SERVICIS_23_24.pdf)
{: #src-C1}

**[C2] Real Decreto 450/2010**, de 16 de abril, por el que se establece el título de Técnico Superior en Desarrollo de Aplicaciones Multiplataforma. BOE. [Enlace](https://www.boe.es/eli/es/rd/2010/04/16)
{: #src-C2}

**[T1] Oracle.** The Java Tutorials, «Performing Animation». Copia en la Universidad Nacional de Singapur. [Enlace](https://www.comp.nus.edu.sg/~cs3283/ftp/Java/JavaTutorial/uiswing/painting/animation.html)
{: #src-T1}

**[T2] Andrew Davison.** Killer Game Programming in Java. O'Reilly, 2005. [Enlace](https://www.oreilly.com/library/view/killer-game-programming/0596007302/)
{: #src-T2}

**[T3] libGDX.** Wiki, «2D Animation». [Enlace](https://libgdx.com/wiki/graphics/2d/2d-animation)
{: #src-T3}

**[T4] Glenn Fiedler.** «Fix Your Timestep!». Gaffer On Games. [Enlace](https://gafferongames.com/post/fix_your_timestep/)
{: #src-T4}

**[R1] ARoachIFoundOnMyPillow.** «16x16 Duck». OpenGameArt, CC0. [Enlace](https://opengameart.org/content/16x16-duck)
{: #src-R1}

**[R2] Pavel Kutejnikov.** «Explosion». OpenGameArt, CC0. [Enlace](https://opengameart.org/node/132621)
{: #src-R2}

**[M1] SpriteAnimation.** Decisiones del proyecto, en `sf/decisiones/`. [Enlace](../../../sf/decisiones/README.md)
{: #src-M1}

**[M2] SpriteAnimation.** Documento de visión. [Enlace](vision-spriteanimation.html)
{: #src-M2}

:::
