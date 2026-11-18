# Decisiones

Decisiones tomadas por el propietario, con su porqué.

**Cómo se registra una decisión.** Un archivo por decisión en esta carpeta, `NNN-AAAA-MM-DD-titulo.md`: el número de orden total (el siguiente al más alto), la fecha del origen y el título sin tildes ni signos. El nombre lo da `indice_decisiones.py sf/decisiones --nombre "Título"`. Empieza por `# Título` y lleva:

- **Origen:** proyecto, fecha y palabras del propietario cuando las hay.
- **Contexto:** el problema.
- **Opciones:** las que se valoraron, si las hubo.
- **Decisión:** lo decidido y por qué.
- **Consecuencias:** lo que cambia y lo que queda pendiente.
- **Aplicada en:** los documentos, skills o piezas que la recogen.

Si una decisión cambia otra anterior, la nueva lo dice («Sustituye a…») y la antigua se marca, sin borrarla, con una línea bajo el título: «**Sustituida** el AAAA-MM-DD por «…»» (o **Sustituida en parte**).

Este índice no se escribe a mano: se regenera con `python -I -X utf8 tools/indice_decisiones.py sf/decisiones` (`tools/` del plugin `sf`) al añadir una decisión y al juntar ramas. Lo que hay antes de «Índice» se conserva.

## Índice

| Nº | Fecha | Decisión |
| --- | --- | --- |
| 47 | 2026-11-02 | [Ctrl+clic: la vista pide un pato al presentador y este al modelo](047-2026-11-02-ctrl-clic-la-vista-pide-un-pato-al-presentador-y-este-al-modelo.md) |
| 46 | 2026-11-02 | [Visión y fundacional al día en su versión 0.2, con la fase 1 medida por claridad](046-2026-11-02-vision-y-fundacional-al-dia-en-su-version-0-2-con-la-fase-1-medida-por-claridad.md) |
| 45 | 2026-11-02 | [HUD arcade, diana de mira y escala entera con la rueda](045-2026-11-02-hud-arcade-diana-de-mira-y-escala-entera-con-la-rueda.md) |
| 44 | 2026-11-02 | [Documento de la estructura de la demo en docs/material](044-2026-11-02-documento-de-la-estructura-de-la-demo-en-docs-material.md) |
| 43 | 2026-11-02 | [El record Picture se llama SpriteView y la rueda pide spriteCount()](043-2026-11-02-el-record-picture-se-llama-spriteview-y-la-rueda-pide-spritecount.md) |
| 42 | 2026-11-02 | [Las interfaces entregan los datos mínimos, inmutables si se puede, nunca objetos internos](042-2026-11-02-las-interfaces-entregan-los-datos-minimos-inmutables-si-se-puede-nunca-objetos-internos.md) |
| 41 | 2026-11-02 | [El presentador no pinta ni guarda estado](041-2026-11-02-el-presentador-no-pinta-ni-guarda-estado.md) |
| 40 | 2026-11-02 | [El modelo no conoce ninguna clase de la vista](040-2026-11-02-el-modelo-no-conoce-ninguna-clase-de-la-vista.md) |
| 39 | 2026-11-02 | [La vista no conoce ninguna clase del modelo](039-2026-11-02-la-vista-no-conoce-ninguna-clase-del-modelo.md) |
| 38 | 2026-11-02 | [La vista no guarda referencias a datos del presentador](038-2026-11-02-la-vista-no-guarda-referencias-a-datos-del-presentador.md) |
| 37 | 2026-11-02 | [El modelo no deja salir los objetos de su propiedad](037-2026-11-02-el-modelo-no-deja-salir-los-objetos-de-su-propiedad.md) |
| 36 | 2026-11-02 | [El presentador nunca pasa objetos del modelo a la vista](036-2026-11-02-el-presentador-nunca-pasa-objetos-del-modelo-a-la-vista.md) |
| 35 | 2026-11-02 | [Los datos entre vista y modelo pasan por el presentador, son inmutables y no comparten referencias](035-2026-11-02-los-datos-entre-vista-y-modelo-pasan-por-el-presentador-son-inmutables-y-no-comparten-referencias.md) |
| 34 | 2026-11-02 | [El sprite no sale del modelo: la vista solo recibe lo que pinta](034-2026-11-02-el-sprite-no-sale-del-modelo-la-vista-solo-recibe-lo-que-pinta.md) |
| 33 | 2026-11-02 | [Los accesos a la propia clase se escriben con this](033-2026-11-02-los-accesos-a-la-propia-clase-se-escriben-con-this.md) |
| 32 | 2026-11-02 | [La demo se cierra con EXIT_ON_CLOSE, sin cierre ordenado](032-2026-11-02-la-demo-se-cierra-con-exit-on-close-sin-cierre-ordenado.md) |
| 31 | 2026-11-02 | [Valores por defecto en la configuración y nombres completos](031-2026-11-02-valores-por-defecto-en-la-configuracion-y-nombres-completos.md) |
| 30 | 2026-11-02 | [Sin clases anónimas ni lambdas](030-2026-11-02-sin-clases-anonimas-ni-lambdas.md) |
| 29 | 2026-11-02 | [El proyecto sigue el principio KISS](029-2026-11-02-el-proyecto-sigue-el-principio-kiss.md) |
| 28 | 2026-11-02 | [La configuración que solo usa main queda fuera de los contratos](028-2026-11-02-la-configuracion-que-solo-usa-main-queda-fuera-de-los-contratos.md) |
| 27 | 2026-11-02 | [No se usan clases ni interfaces contenedoras](027-2026-11-02-no-se-usan-clases-ni-interfaces-contenedoras.md) |
| 26 | 2026-11-02 | [Un archivo por contrato, con sufijo Contract y sin interfaz contenedora](026-2026-11-02-un-archivo-por-contrato-con-sufijo-contract-y-sin-interfaz-contenedora.md) |
| 25 | 2026-11-02 | [El contrato expone una interfaz Sprite](025-2026-11-02-el-contrato-expone-una-interfaz-sprite.md) |
| 24 | 2026-11-02 | [La vista extiende JFrame](024-2026-11-02-la-vista-extiende-jframe.md) |
| 23 | 2026-11-02 | [Paquetes main, model, view, presenter y contract](023-2026-11-02-paquetes-main-model-view-presenter-y-contract.md) |
| 22 | 2026-11-02 | [Licencias, GitHub Pages y correo de los commits al publicar](022-2026-11-02-licencias-github-pages-y-correo-de-los-commits-al-publicar.md) |
| 21 | 2026-11-02 | [Publicar el repositorio ya, con un README](021-2026-11-02-publicar-el-repositorio-ya-con-un-readme.md) |
| 20 | 2026-11-02 | [El repositorio se hace público cuando tenga el código de la demo](020-2026-11-02-el-repositorio-se-hace-publico-cuando-tenga-el-codigo-de-la-demo.md) |
| 19 | 2026-11-02 | [Las interfaces de la demo, en un contrato](019-2026-11-02-las-interfaces-de-la-demo-en-un-contrato.md) |
| 18 | 2026-11-02 | [Sufijo Interface en las interfaces de la demo](018-2026-11-02-sufijo-interface-en-las-interfaces-de-la-demo.md) |
| 17 | 2026-11-02 | [El presentador recibe el modelo y la vista por sus interfaces](017-2026-11-02-el-presentador-recibe-el-modelo-y-la-vista-por-sus-interfaces.md) |
| 16 | 2026-11-02 | [El constructor del presentador conecta y start() solo arranca](016-2026-11-02-el-constructor-del-presentador-conecta-y-start-solo-arranca.md) |
| 15 | 2026-11-02 | [main pide los sprites al modelo, no el presentador](015-2026-11-02-main-pide-los-sprites-al-modelo-no-el-presentador.md) |
| 14 | 2026-11-02 | [Los sprites se crean en el modelo y el presentador arranca la aplicación](014-2026-11-02-los-sprites-se-crean-en-el-modelo-y-el-presentador-arranca-la-aplicacion.md) |
| 13 | 2026-11-02 | [Viñetas para mostrar el montaje paso a paso](013-2026-11-02-vinetas-para-mostrar-el-montaje-paso-a-paso.md) |
| 12 | 2026-10-31 | [Interfaces y constructores de la demo](012-2026-10-31-interfaces-y-constructores-de-la-demo.md) |
| 11 | 2026-10-30 | [Para segundo de DAM: videojuegos y Servicios y Procesos](011-2026-10-30-para-segundo-de-dam-videojuegos-y-servicios-y-procesos.md) |
| 10 | 2026-10-30 | [Dos hojas de sprites: pato y explosión](010-2026-10-30-dos-hojas-de-sprites-pato-y-explosion.md) |
| 9 | 2026-10-30 | [Clases de la demo con Viewer](009-2026-10-30-clases-de-la-demo-con-viewer.md) |
| 8 | 2026-10-30 | [Arrastrar los sprites con el ratón](008-2026-10-30-arrastrar-los-sprites-con-el-raton.md) |
| 7 | 2026-10-30 | [HUD de velocidades ajustable con la rueda del ratón](007-2026-10-30-hud-de-velocidades-ajustable-con-la-rueda-del-raton.md) |
| 6 | 2026-10-30 | [Velocidad de renderización frente a velocidad de animación](006-2026-10-30-velocidad-de-renderizacion-frente-a-velocidad-de-animacion.md) |
| 5 | 2026-10-30 | [Clases de la demo e interfaces MVP](005-2026-10-30-clases-de-la-demo-e-interfaces-mvp.md) (sustituida) |
| 4 | 2026-10-30 | [Ejemplo mínimo de clases y código](004-2026-10-30-ejemplo-minimo-de-clases-y-codigo.md) |
| 3 | 2026-10-30 | [Un hilo por sprite y un hilo de pintado](003-2026-10-30-un-hilo-por-sprite-y-un-hilo-de-pintado.md) |
| 2 | 2026-10-30 | [Swing y Java2D como librería gráfica](002-2026-10-30-swing-y-java2d-como-libreria-grafica.md) |
| 1 | 2026-10-30 | [Material de consulta sobre sprites y MVP para FP](001-2026-10-30-material-de-consulta-sobre-sprites-y-mvp-para-fp.md) (sustituida) |
