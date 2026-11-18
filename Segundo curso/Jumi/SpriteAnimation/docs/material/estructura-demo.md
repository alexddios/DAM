---
antetitulo: SpriteAnimation · Material para segundo de DAM
lema: Un mapa del código: // qué hay en cada sitio.
subtitulo: Los paquetes y las clases de la demo, qué hace cada una y cómo encajan.
tipo: Documento explicativo
version: Versión 1.0 · 02/11/2026
estado: Material didáctico
portada: oscura
pie: Material de consulta de SpriteAnimation para segundo de DAM.
destino: proyecto
herramientas: buscador lectura
---
# La estructura de la demo

Código de la demo: [repositorio en GitHub](https://github.com/jumibot/SpriteAnimation)

## Mapa | Un mapa del código {#mapa}

Este documento es el mapa de SpriteAnimation: dónde está cada cosa, qué hace cada clase y a quién conoce. Sirve para orientarte antes de abrir el código o cuando buscas dónde cambiar algo.

El porqué de esta forma de organizarlo (MVP, los contratos, la inyección de dependencias y los hilos) está en el otro documento, [MVP, interfaces e inyección de dependencias](mvp-interfaces-inyeccion.html). Aquí solo se enlaza.

El repositorio tiene pocas carpetas, y el código está todo en una:

| Carpeta | Qué hay |
| --- | --- |
| `src/main/java/` | El código de la demo, en cinco paquetes |
| `src/main/resources/sprites/` | Las dos hojas de sprites, `pato.png` y `explosion.png`, y su origen |
| `src/test/java/` | Las pruebas |
| `docs/` | El material para alumnos, la visión del proyecto y las imágenes |
| `sf/decisiones/` | Por qué la demo es como es, una decisión por archivo |
| La raíz | Los scripts para ejecutar (`ejecutar`) y probar (`probar`), en `.cmd` y `.sh` |

## Paquetes | Los cinco paquetes {#paquetes}

El código se reparte en cinco paquetes ([decisión 023](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/023-2026-11-02-paquetes-main-model-view-presenter-y-contract.md)): uno para cada pieza de MVP, uno para lo que comparten y uno para arrancar. Cada paquete conoce solo lo que necesita, y se ve en sus `import`.

- `main` monta la aplicación y la arranca. Es el único que conoce las clases concretas de las tres piezas.
- `model` guarda el estado: los sprites, su fotograma, su velocidad y su posición.
- `view` es la ventana: pinta y escucha el ratón.
- `presenter` está en medio: lleva los datos del modelo a la vista y los gestos de la vista al modelo.
- `contract` tiene lo que comparten: las tres interfaces y el record que la vista recibe para pintar.

<figure class="vertical">
<svg viewBox="0 0 560 430" role="img" style="font-family:var(--fuente-texto);display:block">
<title>Los cinco paquetes: main crea el modelo, la vista y el presentador; los tres usan el paquete contract; la vista y el modelo no se conocen</title>
<defs><marker id="ed-f" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--texto-suave)"/></marker></defs>
<rect x="200" y="14" width="160" height="56" rx="10" style="fill:var(--fondo-suave);stroke:var(--oscuro)" stroke-width="2"/>
<text x="280" y="40" font-size="18" font-weight="700" text-anchor="middle" style="fill:var(--oscuro)">main</text>
<text x="280" y="60" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">monta y arranca</text>
<g fill="none" style="stroke:var(--texto-suave)" stroke-width="1.5" marker-end="url(#ed-f)">
<path d="M220 70 L110 150"/><path d="M280 70 L280 150"/><path d="M340 70 L450 150"/>
</g>
<g font-size="15" style="fill:var(--texto-suave)">
<text x="128" y="104">crea</text><text x="290" y="114">crea</text><text x="410" y="104">crea</text>
</g>
<g style="fill:var(--fondo);stroke:var(--primario)" stroke-width="2">
<rect x="20" y="150" width="160" height="70" rx="10"/><rect x="200" y="150" width="160" height="70" rx="10"/><rect x="380" y="150" width="160" height="70" rx="10"/>
</g>
<g font-size="18" font-weight="700" text-anchor="middle" style="fill:var(--primario)">
<text x="100" y="180">view</text><text x="280" y="180">presenter</text><text x="460" y="180">model</text>
</g>
<g font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">
<text x="100" y="204">pinta y escucha</text><text x="280" y="204">traduce y decide</text><text x="460" y="204">guarda el estado</text>
</g>
<g fill="none" style="stroke:var(--texto-suave)" stroke-width="1.5" marker-end="url(#ed-f)">
<path d="M100 220 L170 330"/><path d="M280 220 L280 330"/><path d="M460 220 L390 330"/>
</g>
<g font-size="15" style="fill:var(--texto-suave)">
<text x="100" y="292">usa</text><text x="290" y="296">usa</text><text x="436" y="292">usa</text>
</g>
<rect x="100" y="330" width="360" height="80" rx="10" style="fill:var(--fondo-suave);stroke:var(--oscuro)" stroke-width="2"/>
<text x="280" y="358" font-size="18" font-weight="700" text-anchor="middle" style="fill:var(--oscuro)">contract</text>
<text x="280" y="380" font-size="15" text-anchor="middle" style="fill:var(--texto)">ModelContract · ViewContract</text>
<text x="280" y="400" font-size="15" text-anchor="middle" style="fill:var(--texto)">PresenterContract · SpriteView</text>
<rect x="132" y="234" width="296" height="26" rx="6" style="fill:var(--fondo)"/>
<text x="280" y="252" font-size="15" text-anchor="middle" style="fill:var(--acento)">view y model: ningún import entre ellos</text>
</svg>
<figcaption>Las flechas van de quien conoce a quien es conocido. Entre la vista y el modelo no hay ninguna.</figcaption>
</figure>

Las flechas del esquema son los `import`. `main` crea las tres piezas con sus clases concretas. Las tres usan el paquete `contract`, y por él se hablan: el presentador conoce el modelo y la vista solo por sus interfaces ([017](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/017-2026-11-02-el-presentador-recibe-el-modelo-y-la-vista-por-sus-interfaces.md)). La vista no importa nada de `model` ([039](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/039-2026-11-02-la-vista-no-conoce-ninguna-clase-del-modelo.md)) y el modelo no importa nada de `view` ([040](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/040-2026-11-02-el-modelo-no-conoce-ninguna-clase-de-la-vista.md)): no se conocen.

## Clases | Paquete a paquete {#clases}

Son trece archivos, uno por clase, interfaz o record ([026](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/026-2026-11-02-un-archivo-por-contrato-con-sufijo-contract-y-sin-interfaz-contenedora.md), [027](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/027-2026-11-02-no-se-usan-clases-ni-interfaces-contenedoras.md)). Las tres piezas grandes tienen clases internas que nadie más ve: el modelo se ayuda de `Sprite` y `SpriteSheet`, y la vista, de `Viewer`, `Hud` y `MouseHandler`.

<style>.seccion dt code{text-transform:none;letter-spacing:0;font-size:.9rem}</style>

::: tarjetas 2

### main

`SpriteAnimation`
:   Tiene el método `main`. Crea el modelo, la vista y el presentador, les da su configuración (los sprites, el tamaño del lienzo, la velocidad de pintado y sus límites) y arranca la aplicación.

### contract

`ModelContract`
:   Lo que el presentador puede pedirle al modelo: cuántos sprites hay y, de cada uno por su número, el fotograma, la posición, el nombre y la velocidad; cambiar la velocidad, moverlo y arrancar.

`ViewContract`
:   Lo que el presentador puede pedirle a la vista: conocer al presentador, arrancar y leer o cambiar la velocidad de pintado.

`PresenterContract`
:   Lo que la vista puede pedirle al presentador: qué pintar, cuántos sprites hay, cambiar velocidades, mover un sprite y, desde `main`, arrancar.

`SpriteView`
:   Un record con lo que la vista enseña de un sprite: una copia de su imagen, su posición, su nombre y su velocidad. No es un contrato: son datos ([043](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/043-2026-11-02-el-record-picture-se-llama-spriteview-y-la-rueda-pide-spritecount.md)).

### model

`Model`
:   El modelo de MVP. Guarda la lista de sprites e implementa `ModelContract`. Hacia fuera da números, textos y copias de los fotogramas.

`Sprite`
:   Una animación con su propio hilo, que cambia de fotograma según el tiempo que pasa. No es pública: fuera de `model` no se ve.

`SpriteSheet`
:   Carga una hoja de sprites y la recorta en fotogramas ampliados. Tampoco es pública.

### presenter

`Presenter`
:   El presentador de MVP. Recibe el modelo y la vista por sus interfaces, prepara los `SpriteView` para pintar y aplica las reglas a cada gesto, como los límites de velocidad.

### view

`View`
:   La vista de MVP, y también la ventana: extiende `JFrame` ([024](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/024-2026-11-02-la-vista-extiende-jframe.md)). Tiene el hilo de pintado, que en cada vuelta pide al presentador qué pintar.

`Viewer`
:   El lienzo, un `Canvas`. No pinta: es la superficie sobre la que pinta el hilo de la vista ([009](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/009-2026-10-30-clases-de-la-demo-con-viewer.md)).

`Hud`
:   El recuadro de arriba con las velocidades. Lo pinta y dice sobre qué línea está el ratón.

`MouseHandler`
:   Escucha el ratón sobre el lienzo y avisa al presentador: la rueda cambia una velocidad o una escala, el arrastre mueve un sprite y Ctrl+clic pide un pato.

:::

## Reglas | Quién conoce a quién {#reglas}

La estructura no es solo dónde está cada archivo: también qué puede ver cada uno. Estas reglas se comprueban mirando el código, y casi todas, solo con los `import`.

1. La vista y el modelo no se conocen. Todo lo que va de uno a otro pasa por el presentador ([035](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/035-2026-11-02-los-datos-entre-vista-y-modelo-pasan-por-el-presentador-son-inmutables-y-no-comparten-referencias.md), [039](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/039-2026-11-02-la-vista-no-conoce-ninguna-clase-del-modelo.md), [040](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/040-2026-11-02-el-modelo-no-conoce-ninguna-clase-de-la-vista.md)).
2. El sprite no sale del modelo. La vista recibe de cada sprite un `SpriteView` con lo justo para enseñarlo, nunca un objeto del modelo ([034](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/034-2026-11-02-el-sprite-no-sale-del-modelo-la-vista-solo-recibe-lo-que-pinta.md), [036](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/036-2026-11-02-el-presentador-nunca-pasa-objetos-del-modelo-a-la-vista.md), [037](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/037-2026-11-02-el-modelo-no-deja-salir-los-objetos-de-su-propiedad.md)).
3. Lo que cruza un contrato son datos mínimos y que no se pueden cambiar: números, textos, records y listas fijas. La imagen va como copia ([042](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/042-2026-11-02-las-interfaces-entregan-los-datos-minimos-inmutables-si-se-puede-nunca-objetos-internos.md)).
4. El presentador no pinta ni guarda estado. Cada vez que le piden algo, lo pregunta al modelo y lo entrega ([041](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/041-2026-11-02-el-presentador-no-pinta-ni-guarda-estado.md)).
5. La vista no guarda lo que le da el presentador: lo pide, lo usa y lo suelta ([038](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/038-2026-11-02-la-vista-no-guarda-referencias-a-datos-del-presentador.md)).
6. Lo que solo usa `main` al montar no está en los contratos: `addSprite` del modelo, `setCanvasSize` de la vista y `setFpsLimits`, `setScaleLimits` y `setMaxSprites` del presentador ([028](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/028-2026-11-02-la-configuracion-que-solo-usa-main-queda-fuera-de-los-contratos.md)). Por eso `main` usa las clases concretas, y el presentador no puede crear animaciones ([015](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/015-2026-11-02-main-pide-los-sprites-al-modelo-no-el-presentador.md)).

En qué orden monta `main` estas piezas y cuándo arrancan sus hilos se cuenta en [Cómo se monta la aplicación](mvp-interfaces-inyeccion.html#montaje).

## Anexo | Las decisiones que dan forma a la estructura {#decisiones}

Aquí están solo las decisiones que fijan la arquitectura: las capas y sus contratos, quién conoce a quién, qué datos cruzan, los hilos y el montaje. Cada una cuenta qué se decidió y por qué. Las de estilo y detalle están con ellas en la carpeta `sf/decisiones/` del repositorio.

| Nº | Decisión |
| --- | --- |
| 003 | [Un hilo por sprite y un hilo de pintado](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/003-2026-10-30-un-hilo-por-sprite-y-un-hilo-de-pintado.md) |
| 009 | [Clases de la demo con Viewer](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/009-2026-10-30-clases-de-la-demo-con-viewer.md) |
| 014 | [Los sprites se crean en el modelo y el presentador arranca la aplicación](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/014-2026-11-02-los-sprites-se-crean-en-el-modelo-y-el-presentador-arranca-la-aplicacion.md) |
| 015 | [main pide los sprites al modelo, no el presentador](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/015-2026-11-02-main-pide-los-sprites-al-modelo-no-el-presentador.md) |
| 016 | [El constructor del presentador conecta y start() solo arranca](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/016-2026-11-02-el-constructor-del-presentador-conecta-y-start-solo-arranca.md) |
| 017 | [El presentador recibe el modelo y la vista por sus interfaces](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/017-2026-11-02-el-presentador-recibe-el-modelo-y-la-vista-por-sus-interfaces.md) |
| 023 | [Paquetes main, model, view, presenter y contract](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/023-2026-11-02-paquetes-main-model-view-presenter-y-contract.md) |
| 027 | [No se usan clases ni interfaces contenedoras](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/027-2026-11-02-no-se-usan-clases-ni-interfaces-contenedoras.md) |
| 028 | [La configuración que solo usa main queda fuera de los contratos](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/028-2026-11-02-la-configuracion-que-solo-usa-main-queda-fuera-de-los-contratos.md) |
| 034 | [El sprite no sale del modelo: la vista solo recibe lo que pinta](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/034-2026-11-02-el-sprite-no-sale-del-modelo-la-vista-solo-recibe-lo-que-pinta.md) |
| 035 | [Los datos entre vista y modelo pasan por el presentador, son inmutables y no comparten referencias](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/035-2026-11-02-los-datos-entre-vista-y-modelo-pasan-por-el-presentador-son-inmutables-y-no-comparten-referencias.md) |
| 036 | [El presentador nunca pasa objetos del modelo a la vista](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/036-2026-11-02-el-presentador-nunca-pasa-objetos-del-modelo-a-la-vista.md) |
| 037 | [El modelo no deja salir los objetos de su propiedad](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/037-2026-11-02-el-modelo-no-deja-salir-los-objetos-de-su-propiedad.md) |
| 038 | [La vista no guarda referencias a datos del presentador](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/038-2026-11-02-la-vista-no-guarda-referencias-a-datos-del-presentador.md) |
| 039 | [La vista no conoce ninguna clase del modelo](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/039-2026-11-02-la-vista-no-conoce-ninguna-clase-del-modelo.md) |
| 040 | [El modelo no conoce ninguna clase de la vista](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/040-2026-11-02-el-modelo-no-conoce-ninguna-clase-de-la-vista.md) |
| 041 | [El presentador no pinta ni guarda estado](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/041-2026-11-02-el-presentador-no-pinta-ni-guarda-estado.md) |
| 042 | [Las interfaces entregan los datos mínimos, inmutables si se puede, nunca objetos internos](https://github.com/jumibot/SpriteAnimation/blob/main/sf/decisiones/042-2026-11-02-las-interfaces-entregan-los-datos-minimos-inmutables-si-se-puede-nunca-objetos-internos.md) |
