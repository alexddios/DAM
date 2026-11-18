<!-- destino: proyecto -->
<!-- herramientas: buscador lectura -->
<!-- repositorio: https://github.com/jumibot/SpriteAnimation (el generador no lo pinta: tras regenerar, se añade a mano en la cabecera, en .institution-repo) -->

# SpriteAnimation: animar sprites con MVP e hilos en Java

**Estado:** 2 de noviembre de 2026, versión 0.2, al día con el código de la demo.

<div class="caso"><p class="caso-etiqueta">El caso · Andreu, profesor de videojuegos en segundo de DAM</p><p>Andreu tiene treinta prácticas sobre la mesa, y casi todas son la misma: un personaje animado en una ventana de Java. En casi todas, un solo método carga la imagen, cuenta el tiempo y pinta. En el portátil de Laia, el pato aletea como un colibrí; en el ordenador del aula, como si estuviera cansado. El lunes empieza la unidad de animación del módulo optativo de videojuegos, y Andreu se hace la pregunta de cada curso: <strong>¿qué ejemplo les doy para que entiendan cómo se organiza una animación?</strong></p></div>

## Resumen

SpriteAnimation es la respuesta de Andreu: el ejemplo más pequeño posible. Es una demo en Java, con Swing y Java2D. Sus alumnos de segundo de DAM la abren, la ejecutan y la consultan cuando la necesitan. No es un curso que se sigue de principio a fin, sino un material de consulta.

En la ventana hay dos animaciones sacadas de dos hojas de sprites: el pato volando y una explosión. Arriba, un pequeño HUD (las cifras que flotan sobre la escena) muestra tres velocidades. Una es cuántas veces por segundo se pinta; las otras, cuántos fotogramas por segundo avanza cada animación. Con la rueda del ratón sobre una cifra, esa velocidad cambia mientras la demo funciona. Con el ratón, además, los sprites se arrastran sin que dejen de animarse.

<figure class="vertical">
<svg viewBox="0 0 560 330" role="img" aria-label="Boceto de la ventana de SpriteAnimation: HUD con tres velocidades, el pato y la explosión" style="font-family:var(--fuente-texto);display:block">
<rect x="20" y="10" width="520" height="310" rx="10" fill="none" stroke="#b2deda" stroke-width="1.5"/>
<line x1="20" y1="42" x2="540" y2="42" stroke="#b2deda"/>
<text x="40" y="31" font-size="14" font-weight="700" fill="#004d47">SpriteAnimation</text>
<rect x="40" y="58" width="270" height="78" rx="6" fill="none" stroke="#00726b"/>
<text x="54" y="80" font-size="13" fill="#1a1a1a"><tspan font-weight="700" fill="#00726b">Pintado</tspan> · 60 veces por segundo</text>
<text x="54" y="102" font-size="13" fill="#1a1a1a"><tspan font-weight="700" fill="#00726b">Pato</tspan> · 8 fotogramas por segundo</text>
<text x="54" y="124" font-size="13" fill="#1a1a1a"><tspan font-weight="700" fill="#00726b">Explosión</tspan> · 12 fotogramas por segundo</text>
<text x="326" y="92" font-size="12" fill="#555555">⇕ rueda del ratón</text>
<text x="326" y="108" font-size="12" fill="#555555">sobre cada cifra</text>
<svg x="110" y="196" width="72" height="72" viewBox="0 0 16 16"><image href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAABACAYAAAATffeWAAABmklEQVRYR2NkoBAwUqifgeoG/EdyEcxwZDGYNNxiFEXxG3rg+hcGlIDZcZ7yGL5ctP0h3CCQAf9fHHcGC5S/9GQAaQQZtO7IbYbAqzswNL989hUsJi7FzQAyCMUA1bUqKBpgBqgqKoDFb99/AKZhhuy8+AYeiNj8idX5MANAmhkYGBgJxcJ/UBiguwAaBmC9hAwAqQEbggxINqA5yx4eBsiayXIBWQbAXFA77SCGpcSEATgcoGGAoZ5YA3DmuVEDiEuJeAst9EAcrgUKciECKolIKlCQizD00oioAgVWsCLbipwviE3K2GIHbA6xBoxmJjy5YQQFIkUJ6b+7vgg4GH++/cFw4MkXlARITCDCDQDphNXKsIghqkQCuQBkOwwgu4JgE8dBhgcjFUANgLcP8DZxHl1+i9UAUJVPdBMH2RA5XeHREgktSIlJSCRVbXgVY5OkmwsoaiP9hyVn9JxIbL1AUm7EFk5gA2C5keLyAL1MIBQLKM7H5gpCBQqK8wemQAHZilwesAtzgB1CUp9ptImDP4cTSkgEywcABLsuSPGSfPgAAAAASUVORK5CYIIA" width="16" height="64" style="image-rendering:pixelated"/></svg>
<path d="M176 262 l0 22 l6 -6 l5 10 l4 -2 l-5 -10 l8 0 z" fill="#ffffff" stroke="#1a1a1a" stroke-width="1.2"/>
<text x="200" y="296" font-size="12" fill="#555555">arrastrar el sprite</text>
<rect x="336" y="164" width="128" height="128" rx="8" fill="#2a2a2a"/><svg x="340" y="168" width="120" height="120" viewBox="96 0 48 48"><image href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAYAAAAAwCAYAAAAYeq1+AAAACXBIWXMAAAsTAAALEwEAmpwYAAAF8WlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDggNzkuMTY0MDM2LCAyMDE5LzA4LzEzLTAxOjA2OjU3ICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdEV2dD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlRXZlbnQjIiB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgMjEuMCAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIxLTExLTI2VDIxOjIxOjI2KzAzOjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDIxLTExLTI2VDIxOjIxOjI2KzAzOjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMS0xMS0yNlQyMToyMToyNiswMzowMCIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo0YjM1YTA2Mi1mYTU0LTMzNGItOGEzNy04MjgxNGNkYzcwY2QiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDpkMjAzOTdlNi05MTViLWNiNGUtOGQ0Yi0wYTgzMjE4YTdmNWIiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo1YjdlMWI1OS1jOGYyLTZlNGItOWUxMC00MmE3MjVmMmY3ZWQiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjViN2UxYjU5LWM4ZjItNmU0Yi05ZTEwLTQyYTcyNWYyZjdlZCIgc3RFdnQ6d2hlbj0iMjAyMS0xMS0yNlQyMToyMToyNiswMzowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIDIxLjAgKFdpbmRvd3MpIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo0YjM1YTA2Mi1mYTU0LTMzNGItOGEzNy04MjgxNGNkYzcwY2QiIHN0RXZ0OndoZW49IjIwMjEtMTEtMjZUMjE6MjE6MjYrMDM6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCAyMS4wIChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7BrRXcAAAKmUlEQVR42u2dy5EkNRCGxxYOXDnAjSsnvMAJDMIATMAaItYEbhQ0UIQmOx9/plIqqSo7QtHLTHU9mt3/U74/juP4yFi//fhNrVq1Bq1fvv72aNfoz9W656K6/VEAqFULE09uXXkfyGf++PmrT6tAcN3fowJArVo3Ev9ZQPBeSxL/qyDQc70VLJg73H8BoNYS6yVA53v75xVF/89fvzt+//6HtxUFQVQEIgCQxP8EwOquq6jlUwAoC6DWRgB4CSr9nfQZ67jXas/nXa24n2L/gkC7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" width="384" height="48" style="image-rendering:pixelated"/></svg>
</svg>
<figcaption>Lo que ven Andreu y sus alumnos al ejecutar la demo, con las cifras del HUD al arrancar.</figcaption>
</figure>

Detrás de esa ventana hay nueve clases, tres interfaces y un record, repartidos según la arquitectura MVP: modelo, vista y presentador. Cada sprite avanza en su propio hilo y otro hilo pinta. Así Andreu enseña a la vez las tres ideas que sus alumnos necesitan: el recorte de la hoja, la separación entre estado y dibujo, y los dos ritmos. Las secciones siguientes las recorren una a una.

## Qué le cuesta a quien empieza

<div class="caso"><p class="caso-etiqueta">El caso · capítulo 1 · el método que lo hace todo</p><p>Andreu abre la práctica de Laia: un método de ochenta líneas. Carga la hoja cada vez que pinta, suma uno al fotograma en cada repintado y dibuja. Funciona, pero Laia no sabe explicar por qué el pato cambia de ritmo. ¿Dónde se tuercen todos, curso tras curso?</p></div>

Quien empieza tropieza casi siempre en tres sitios. El primero es atar la animación al repintado. Si el fotograma avanza cada vez que se pinta, un equipo rápido anima más deprisa que uno lento. Es justo lo que le pasa a Laia.

El segundo es mezclarlo todo en una clase. La carga de la imagen, el estado del personaje y el dibujo acaban juntos, y cualquier cambio rompe otra cosa. El código funciona, pero no se puede leer ni ampliar.

El tercero es aprender la arquitectura sin un caso que la justifique. MVP suele explicarse con un formulario, donde separar capas parece un trámite. En una animación, en cambio, dos hilos tocan el mismo estado. Ahí separar deja de ser una cuestión de orden y pasa a ser una necesidad.

## De la hoja al fotograma

<div class="caso"><p class="caso-etiqueta">El caso · capítulo 2 · un pato en columna y una explosión en fila</p><p>Andreu busca hojas de sprites para el ejemplo. Elige un pato con sus cuatro fotogramas uno encima de otro y una explosión con ocho fases de izquierda a derecha. No es casualidad: quiere que sus alumnos se pregunten cómo sabe el programa dónde empieza cada fotograma.</p></div>

Una hoja de sprites es una sola imagen con todos los fotogramas de una animación, del mismo tamaño y alineados. La clase `SpriteSheet` la carga una vez y la recorta en fotogramas sueltos, que se guardan listos para pintar. Recortar es solo calcular un rectángulo por fotograma.

La demo usa dos hojas a propósito, porque se recortan en sentidos distintos:

<figure class="vertical">
<svg viewBox="0 0 560 440" role="img" aria-label="Las dos hojas de sprites de la demo partidas en sus fotogramas" style="font-family:var(--fuente-texto);display:block">
<text x="20" y="24" font-size="14" font-weight="700" fill="#00726b">pato.png</text>
<text x="96" y="24" font-size="13" fill="#555555">16 × 64 · 4 fotogramas en columna</text>
<rect x="54" y="38" width="60" height="204" rx="4" fill="#2a2a2a"/><svg x="60" y="44" width="48" height="192" viewBox="0 0 16 64"><image href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAABACAYAAAATffeWAAABmklEQVRYR2NkoBAwUqifgeoG/EdyEcxwZDGYNNxiFEXxG3rg+hcGlIDZcZ7yGL5ctP0h3CCQAf9fHHcGC5S/9GQAaQQZtO7IbYbAqzswNL989hUsJi7FzQAyCMUA1bUqKBpgBqgqKoDFb99/AKZhhuy8+AYeiNj8idX5MANAmhkYGBgJxcJ/UBiguwAaBmC9hAwAqQEbggxINqA5yx4eBsiayXIBWQbAXFA77SCGpcSEATgcoGGAoZ5YA3DmuVEDiEuJeAst9EAcrgUKciECKolIKlCQizD00oioAgVWsCLbipwviE3K2GIHbA6xBoxmJjy5YQQFIkUJ6b+7vgg4GH++/cFw4MkXlARITCDCDQDphNXKsIghqkQCuQBkOwwgu4JgE8dBhgcjFUANgLcP8DZxHl1+i9UAUJVPdBMH2RA5XeHREgktSIlJSCRVbXgVY5OkmwsoaiP9hyVn9JxIbL1AUm7EFk5gA2C5keLyAL1MIBQLKM7H5gpCBQqK8wemQAHZilwesAtzgB1CUp9ptImDP4cTSkgEywcABLsuSPGSfPgAAAAASUVORK5CYIIA" width="16" height="64" style="image-rendering:pixelated"/></svg>
<rect x="60" y="44" width="48" height="192" fill="none" stroke="#b2deda"/>
<line x1="60" y1="92" x2="108" y2="92" stroke="#00726b" stroke-dasharray="3 3"/>
<line x1="60" y1="140" x2="108" y2="140" stroke="#00726b" stroke-dasharray="3 3"/>
<line x1="60" y1="188" x2="108" y2="188" stroke="#00726b" stroke-dasharray="3 3"/>
<text x="120" y="72" font-size="12" fill="#555555">fotograma 0</text>
<text x="120" y="120" font-size="12" fill="#555555">fotograma 1</text>
<text x="120" y="168" font-size="12" fill="#555555">fotograma 2</text>
<text x="120" y="216" font-size="12" fill="#555555">fotograma 3</text>
<text x="250" y="128" font-size="13" fill="#1a1a1a">Se recorta hacia abajo:</text>
<text x="250" y="150" font-size="13" fill="#004d47" font-weight="700">x = 0 · y = fotograma × 16</text>
<text x="20" y="290" font-size="14" font-weight="700" fill="#00726b">explosion.png</text>
<text x="124" y="290" font-size="13" fill="#555555">384 × 48 · 8 fotogramas en fila</text>
<rect x="14" y="298" width="532" height="77" rx="4" fill="#2a2a2a"/><svg x="20" y="304" width="520" height="65" viewBox="0 0 384 48"><image href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAYAAAAAwCAYAAAAYeq1+AAAACXBIWXMAAAsTAAALEwEAmpwYAAAF8WlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDggNzkuMTY0MDM2LCAyMDE5LzA4LzEzLTAxOjA2OjU3ICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdEV2dD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlRXZlbnQjIiB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgMjEuMCAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIxLTExLTI2VDIxOjIxOjI2KzAzOjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDIxLTExLTI2VDIxOjIxOjI2KzAzOjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMS0xMS0yNlQyMToyMToyNiswMzowMCIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo0YjM1YTA2Mi1mYTU0LTMzNGItOGEzNy04MjgxNGNkYzcwY2QiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDpkMjAzOTdlNi05MTViLWNiNGUtOGQ0Yi0wYTgzMjE4YTdmNWIiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo1YjdlMWI1OS1jOGYyLTZlNGItOWUxMC00MmE3MjVmMmY3ZWQiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjViN2UxYjU5LWM4ZjItNmU0Yi05ZTEwLTQyYTcyNWYyZjdlZCIgc3RFdnQ6d2hlbj0iMjAyMS0xMS0yNlQyMToyMToyNiswMzowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIDIxLjAgKFdpbmRvd3MpIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo0YjM1YTA2Mi1mYTU0LTMzNGItOGEzNy04MjgxNGNkYzcwY2QiIHN0RXZ0OndoZW49IjIwMjEtMTEtMjZUMjE6MjE6MjYrMDM6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCAyMS4wIChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7BrRXcAAAKmUlEQVR42u2dy5EkNRCGxxYOXDnAjSsnvMAJDMIATMAaItYEbhQ0UIQmOx9/plIqqSo7QtHLTHU9mt3/U74/juP4yFi//fhNrVq1Bq1fvv72aNfoz9W656K6/VEAqFULE09uXXkfyGf++PmrT6tAcN3fowJArVo3Ev9ZQPBeSxL/qyDQc70VLJg73H8BoNYS6yVA53v75xVF/89fvzt+//6HtxUFQVQEIgCQxP8EwOquq6jlUwAoC6DWRgB4CSr9nfQZ67jXas/nXa24n2L/gkC7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" width="384" height="48" style="image-rendering:pixelated"/></svg>
<rect x="20" y="304" width="520" height="65" fill="none" stroke="#b2deda"/>
<g stroke="#00726b" stroke-dasharray="3 3"><line x1="85" y1="304" x2="85" y2="369"/><line x1="150" y1="304" x2="150" y2="369"/><line x1="215" y1="304" x2="215" y2="369"/><line x1="280" y1="304" x2="280" y2="369"/><line x1="345" y1="304" x2="345" y2="369"/><line x1="410" y1="304" x2="410" y2="369"/><line x1="475" y1="304" x2="475" y2="369"/></g>
<g font-size="12" fill="#555555" text-anchor="middle"><text x="52" y="386">0</text><text x="117" y="386">1</text><text x="182" y="386">2</text><text x="247" y="386">3</text><text x="312" y="386">4</text><text x="377" y="386">5</text><text x="442" y="386">6</text><text x="507" y="386">7</text></g>
<text x="20" y="420" font-size="13" fill="#1a1a1a">Se recorta hacia la derecha:</text>
<text x="210" y="420" font-size="13" fill="#004d47" font-weight="700">x = fotograma × 48 · y = 0</text>
</svg>
<figcaption>Las dos hojas reales de la demo, ampliadas y sobre fondo oscuro para que se vea el destello blanco con que empieza la explosión. Las líneas discontinuas marcan dónde corta <code>SpriteSheet</code>.</figcaption>
</figure>

Con las dos hojas, los alumnos ven que el recorte no depende del dibujo, sino de cómo está colocada la hoja. Basta saber el tamaño del fotograma y en qué sentido avanzan. Ese conocimiento vive en `SpriteSheet` y en ningún otro sitio.

#### Las hojas y su licencia

Las dos hojas son de dominio público (CC0). El pato es «[16x16 Duck](https://opengameart.org/content/16x16-duck)», de ARoachIFoundOnMyPillow, y la explosión es «[Explosion](https://opengameart.org/node/132621)», de Pavel Kutejnikov, ambas en OpenGameArt. Están en `src/main/resources/sprites/`, con su origen en `ORIGEN.md`.

## Cada pieza en su sitio

<div class="caso"><p class="caso-etiqueta">El caso · capítulo 3 · ¿quién manda aquí?</p><p>Hasta ahora, Andreu explicaba MVP con un formulario de altas y bajas, y la clase se le dormía. Esta vez tiene fotogramas que alguien tiene que elegir, alguien tiene que pintar y un ratón que cambia cosas. ¿Quién hace cada cosa?</p></div>

La demo reparte el trabajo según MVP, con el mínimo de clases con que la separación se ve. Son nueve clases, tres interfaces y un record, cada uno en su archivo. Las interfaces son el contrato entre las capas, una por capa: qué puede pedir cada una a las otras. El record, `SpriteView`, lleva a la vista lo que tiene que pintar de cada sprite, porque el sprite no sale del modelo.

<figure class="vertical">
<svg viewBox="0 0 560 520" role="img" aria-label="Las clases de la demo según MVP y los dos recorridos: pintar y ratón" style="font-family:var(--fuente-texto);display:block">
<rect x="60" y="14" width="440" height="58" rx="10" fill="none" stroke="#b2deda"/>
<text x="80" y="38" font-size="14" font-weight="700" fill="#00726b">Viewer</text>
<text x="80" y="58" font-size="12" fill="#555555">el lienzo: un Canvas sobre el que se pinta</text>
<rect x="60" y="112" width="440" height="78" rx="10" fill="none" stroke="#b2deda"/>
<text x="80" y="136" font-size="14" font-weight="700" fill="#00726b">View</text>
<text x="128" y="136" font-size="12" fill="#888888">· implementa ViewContract</text>
<text x="80" y="157" font-size="12" fill="#555555">crea la ventana y el HUD · tiene el hilo de pintado</text>
<text x="80" y="176" font-size="12" fill="#555555">su MouseHandler escucha el ratón sobre el Viewer</text>
<rect x="60" y="252" width="440" height="66" rx="10" fill="none" stroke="#b2deda"/>
<text x="80" y="277" font-size="14" font-weight="700" fill="#00726b">Presenter</text>
<text x="160" y="277" font-size="12" fill="#888888">· implementa PresenterContract</text>
<text x="80" y="300" font-size="12" fill="#555555">une el modelo y la vista; no pinta ni guarda estado propio</text>
<rect x="60" y="380" width="440" height="126" rx="10" fill="none" stroke="#b2deda"/>
<text x="80" y="404" font-size="14" font-weight="700" fill="#00726b">Model</text>
<text x="132" y="404" font-size="12" fill="#555555">· implementa ModelContract · los sprites no salen de él</text>
<text x="96" y="434" font-size="13" font-weight="700" fill="#004d47">Sprite pato</text>
<text x="96" y="452" font-size="12" fill="#555555">4 fotogramas · su hilo · su velocidad · su posición</text>
<text x="96" y="478" font-size="13" font-weight="700" fill="#004d47">Sprite explosión</text>
<text x="96" y="496" font-size="12" fill="#555555">8 fotogramas · su hilo · su velocidad · su posición</text>
<defs><marker id="fv" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" fill="#00726b"/></marker><marker id="fa" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" fill="#2c6e9b"/></marker></defs>
<line x1="140" y1="110" x2="140" y2="76" stroke="#00726b" stroke-width="1.6" marker-end="url(#fv)"/>
<text x="150" y="98" font-size="12" fill="#00726b">pinta en cada vuelta</text>
<line x1="140" y1="192" x2="140" y2="248" stroke="#00726b" stroke-width="1.6" marker-end="url(#fv)"/>
<text x="150" y="216" font-size="12" fill="#00726b">pide qué dibujar</text>
<text x="150" y="232" font-size="12" fill="#00726b">y recibe un SpriteView por sprite</text>
<line x1="140" y1="320" x2="140" y2="376" stroke="#00726b" stroke-width="1.6" marker-end="url(#fv)"/>
<text x="150" y="346" font-size="12" fill="#00726b">pide una copia del fotograma</text>
<text x="150" y="362" font-size="12" fill="#00726b">y posición</text>
<line x1="420" y1="192" x2="420" y2="248" stroke="#2c6e9b" stroke-width="1.6" marker-end="url(#fa)"/>
<text x="410" y="216" font-size="12" fill="#2c6e9b" text-anchor="end">rueda o arrastre</text>
<line x1="420" y1="320" x2="420" y2="376" stroke="#2c6e9b" stroke-width="1.6" marker-end="url(#fa)"/>
<text x="410" y="346" font-size="12" fill="#2c6e9b" text-anchor="end">cambia velocidad</text>
<text x="410" y="362" font-size="12" fill="#2c6e9b" text-anchor="end">o posición</text>
</svg>
<figcaption>Las flechas de la izquierda son el recorrido de pintar; las de la derecha, el del ratón. <code>SpriteAnimation</code>, la clase principal, crea las piezas y las conecta; <code>SpriteSheet</code> prepara los fotogramas.</figcaption>
</figure>

Para pintar, la iniciativa es de la vista. En cada vuelta, el hilo de pintado de `View` le pide al presentador qué tiene que dibujar. El presentador se lo pide al modelo y se lo devuelve como una lista de `SpriteView`, con una copia de cada fotograma, y `View` lo pinta sobre el `Viewer`. Nadie empuja nada hacia la vista: ella pide cuando le toca.

Para el ratón, el recorrido va al revés. `MouseHandler`, la parte de la vista que escucha el ratón, recoge el gesto sobre el lienzo y avisa al presentador, que cambia el modelo: la velocidad o la posición de un sprite. La vista no mueve nada por su cuenta. En la vuelta siguiente, el sprite aparece donde diga el modelo.

Así cada clase tiene una sola razón para cambiar. Si un alumno quiere otro personaje, añade una línea en `main`; si quiere otra ventana, solo toca la vista.

## Dos ritmos que no dependen uno del otro

<div class="caso"><p class="caso-etiqueta">El caso · capítulo 4 · el pato que aleteaba demasiado</p><p>Es lunes. Andreu proyecta la demo en el aula y pregunta por qué el pato de Laia iba rápido en su portátil y lento aquí. Nadie lo sabe todavía. Si pintar y animar fueran cosas distintas, ¿qué fotograma se pintaría en cada momento?</p></div>

La demo separa dos velocidades. La **velocidad de renderización** es cuántas veces por segundo pinta el hilo de la vista. La velocidad de cada animación es cuántos fotogramas por segundo avanza el hilo de su sprite. Son independientes: el hilo de pintado dibuja el fotograma que toque en ese instante, cualquiera que sea.

<figure class="vertical">
<svg viewBox="0 0 560 300" role="img" aria-label="Un segundo de la demo: 60 repintados frente a 8 fotogramas del pato y 12 de la explosión" style="font-family:var(--fuente-texto);display:block">
<text x="40" y="30" font-size="13" fill="#1a1a1a"><tspan font-weight="700" fill="#00726b">Pintado</tspan> · 60 veces en un segundo</text>
<g stroke="#00726b" stroke-width="1">
<line x1="40" y1="42" x2="40" y2="66"/><line x1="48" y1="42" x2="48" y2="66"/><line x1="56" y1="42" x2="56" y2="66"/><line x1="64" y1="42" x2="64" y2="66"/><line x1="72" y1="42" x2="72" y2="66"/><line x1="80" y1="42" x2="80" y2="66"/><line x1="88" y1="42" x2="88" y2="66"/><line x1="96" y1="42" x2="96" y2="66"/><line x1="104" y1="42" x2="104" y2="66"/><line x1="112" y1="42" x2="112" y2="66"/><line x1="120" y1="42" x2="120" y2="66"/><line x1="128" y1="42" x2="128" y2="66"/><line x1="136" y1="42" x2="136" y2="66"/><line x1="144" y1="42" x2="144" y2="66"/><line x1="152" y1="42" x2="152" y2="66"/><line x1="160" y1="42" x2="160" y2="66"/><line x1="168" y1="42" x2="168" y2="66"/><line x1="176" y1="42" x2="176" y2="66"/><line x1="184" y1="42" x2="184" y2="66"/><line x1="192" y1="42" x2="192" y2="66"/><line x1="200" y1="42" x2="200" y2="66"/><line x1="208" y1="42" x2="208" y2="66"/><line x1="216" y1="42" x2="216" y2="66"/><line x1="224" y1="42" x2="224" y2="66"/><line x1="232" y1="42" x2="232" y2="66"/><line x1="240" y1="42" x2="240" y2="66"/><line x1="248" y1="42" x2="248" y2="66"/><line x1="256" y1="42" x2="256" y2="66"/><line x1="264" y1="42" x2="264" y2="66"/><line x1="272" y1="42" x2="272" y2="66"/><line x1="280" y1="42" x2="280" y2="66"/><line x1="288" y1="42" x2="288" y2="66"/><line x1="296" y1="42" x2="296" y2="66"/><line x1="304" y1="42" x2="304" y2="66"/><line x1="312" y1="42" x2="312" y2="66"/><line x1="320" y1="42" x2="320" y2="66"/><line x1="328" y1="42" x2="328" y2="66"/><line x1="336" y1="42" x2="336" y2="66"/><line x1="344" y1="42" x2="344" y2="66"/><line x1="352" y1="42" x2="352" y2="66"/><line x1="360" y1="42" x2="360" y2="66"/><line x1="368" y1="42" x2="368" y2="66"/><line x1="376" y1="42" x2="376" y2="66"/><line x1="384" y1="42" x2="384" y2="66"/><line x1="392" y1="42" x2="392" y2="66"/><line x1="400" y1="42" x2="400" y2="66"/><line x1="408" y1="42" x2="408" y2="66"/><line x1="416" y1="42" x2="416" y2="66"/><line x1="424" y1="42" x2="424" y2="66"/><line x1="432" y1="42" x2="432" y2="66"/><line x1="440" y1="42" x2="440" y2="66"/><line x1="448" y1="42" x2="448" y2="66"/><line x1="456" y1="42" x2="456" y2="66"/><line x1="464" y1="42" x2="464" y2="66"/><line x1="472" y1="42" x2="472" y2="66"/><line x1="480" y1="42" x2="480" y2="66"/><line x1="488" y1="42" x2="488" y2="66"/><line x1="496" y1="42" x2="496" y2="66"/><line x1="504" y1="42" x2="504" y2="66"/><line x1="512" y1="42" x2="512" y2="66"/>
</g>
<text x="40" y="104" font-size="13" fill="#1a1a1a"><tspan font-weight="700" fill="#00726b">Pato</tspan> · 8 fotogramas en un segundo (dos vueltas de 4)</text>
<g fill="none" stroke="#b2deda"><rect x="40" y="114" width="60" height="30"/><rect x="100" y="114" width="60" height="30"/><rect x="160" y="114" width="60" height="30"/><rect x="220" y="114" width="60" height="30"/><rect x="280" y="114" width="60" height="30"/><rect x="340" y="114" width="60" height="30"/><rect x="400" y="114" width="60" height="30"/><rect x="460" y="114" width="60" height="30"/></g>
<g font-size="12" fill="#555555" text-anchor="middle"><text x="70" y="134">0</text><text x="130" y="134">1</text><text x="190" y="134">2</text><text x="250" y="134">3</text><text x="310" y="134">0</text><text x="370" y="134">1</text><text x="430" y="134">2</text><text x="490" y="134">3</text></g>
<text x="40" y="182" font-size="13" fill="#1a1a1a"><tspan font-weight="700" fill="#00726b">Explosión</tspan> · 12 fotogramas en un segundo</text>
<g fill="none" stroke="#b2deda"><rect x="40" y="192" width="40" height="30"/><rect x="80" y="192" width="40" height="30"/><rect x="120" y="192" width="40" height="30"/><rect x="160" y="192" width="40" height="30"/><rect x="200" y="192" width="40" height="30"/><rect x="240" y="192" width="40" height="30"/><rect x="280" y="192" width="40" height="30"/><rect x="320" y="192" width="40" height="30"/><rect x="360" y="192" width="40" height="30"/><rect x="400" y="192" width="40" height="30"/><rect x="440" y="192" width="40" height="30"/><rect x="480" y="192" width="40" height="30"/></g>
<g font-size="12" fill="#555555" text-anchor="middle"><text x="60" y="212">0</text><text x="100" y="212">1</text><text x="140" y="212">2</text><text x="180" y="212">3</text><text x="220" y="212">4</text><text x="260" y="212">5</text><text x="300" y="212">6</text><text x="340" y="212">7</text><text x="380" y="212">0</text><text x="420" y="212">1</text><text x="460" y="212">2</text><text x="500" y="212">3</text></g>
<g stroke="#b36e1d" stroke-width="1.8" stroke-dasharray="5 3"><line x1="184" y1="38" x2="184" y2="70"/><line x1="184" y1="110" x2="184" y2="148"/><line x1="184" y1="188" x2="184" y2="236"/></g>
<text x="194" y="250" font-size="12" fill="#b36e1d">un repintado a los 0,3 s: pinta el pato 2 y la explosión 3</text>
<line x1="40" y1="268" x2="520" y2="268" stroke="#888888"/>
<g font-size="12" fill="#888888"><text x="40" y="288">0 s</text><text x="268" y="288">0,5 s</text><text x="508" y="288">1 s</text></g>
</svg>
<figcaption>Un segundo de la demo con las cifras del HUD al arrancar. Cada repintado dibuja el fotograma que toca en ese momento.</figcaption>
</figure>

Si el pintado va más rápido que la animación, dibuja el mismo fotograma varias veces. Si va más despacio, se salta alguno, pero la animación no se ralentiza. Para que el ritmo sea constante, cada hilo **mide el tiempo** en lugar de contar vueltas. Es la idea que los programadores de juegos conocen como «[Fix Your Timestep](https://gafferongames.com/post/fix_your_timestep/)», reducida a lo mínimo.

Los hilos son aquí un tema secundario, pero son la pieza que hace visible esta diferencia. Hay uno por sprite, en el modelo, y uno de pintado, en la vista. La demo enseña a crearlos y a mantener su velocidad. No los para uno a uno: al cerrar la ventana, `EXIT_ON_CLOSE` termina el programa y, con él, todos los hilos. Los gestos del ratón llegan por un tercer hilo, el de eventos de Swing. El código resuelve con lo mínimo el estado que comparte con el de pintado.

<div class="escena" role="note" aria-label="Escena ilustrativa">
<p class="escena-etiqueta">Escena ilustrativa · el aula, el lunes</p>
<p class="escena-contexto">Andreu proyecta SpriteAnimation en el aula. Laia tiene el ratón.</p>
<div class="turno"><p class="ponente">Andreu<small>profesor</small></p><p>Laia, pon la rueda sobre «Pintado» y bájalo a diez.</p></div>
<div class="turno"><p class="ponente">Laia<small>alumna</small></p><p>Va a trompicones, pero el pato aletea al mismo ritmo. Solo se ven menos dibujos.</p></div>
<div class="turno"><p class="ponente">Andreu<small>profesor</small></p><p>Ahora sube el pato a dieciséis y deja la explosión como está.</p></div>
<div class="turno"><p class="ponente">Laia<small>alumna</small></p><p>El pato va el doble de rápido y la explosión ni se entera. Cada uno tiene su reloj.</p></div>
<div class="turno"><p class="ponente">Andreu<small>profesor</small></p><p>¿Y cuántos relojes había en tu práctica?</p></div>
<div class="turno"><p class="ponente">Laia<small>alumna</small></p><p>Uno. Y era el repintado.</p></div>
<p class="escena-pie">Diálogo imaginado: no es una transcripción ni un resultado validado.</p>
</div>

La escena enseña lo que ningún párrafo consigue tan rápido: tocar una velocidad y ver que la otra no cambia. Por eso las tres cifras del HUD se pueden cambiar en vivo y no son constantes escondidas en el código.

## Tocar para entender

<div class="caso"><p class="caso-etiqueta">El caso · capítulo 5 · coger el pato en pleno vuelo</p><p>Laia arrastra el pato hasta la explosión y lo suelta, y la clase se ríe: el pato no ha dejado de aletear. Andreu lo aprovecha para preguntar: ¿quién ha movido el pato, la ventana o el modelo?</p></div>

La demo admite dos gestos con el ratón. La rueda sobre una cifra del HUD sube o baja esa velocidad. Arrastrar un sprite lo cambia de sitio; si dos se solapan, se coge el de arriba.

Los dos gestos recorren MVP de vuelta, como en el gráfico de «Cada pieza en su sitio». `MouseHandler`, de la vista, los recoge sobre el `Viewer` y avisa al presentador, que cambia el modelo. Por eso el pato sigue aleteando mientras se arrastra. Su hilo no sabe nada del ratón: la posición y la animación son datos distintos del mismo sprite.

Con muy poco código, el ratón le da a Andreu lo que le faltaba para explicar MVP: que se vea que la vista no decide nada. Solo recoge el gesto y pinta lo que el modelo diga.

## Lo que la demo no hace

<div class="caso"><p class="caso-etiqueta">El caso · capítulo 6 · ¿y si el pato choca?</p><p>Pau, desde la última fila, pregunta si el pato puede chocar con la explosión, o moverse con las flechas. Lo busca en el código y no lo encuentra. ¿Es un olvido?</p></div>

No es un olvido. SpriteAnimation es un ejemplo mínimo, no un juego: no tiene teclas de movimiento, ni física, ni colisiones. Cada cosa que no está la deja fuera para que lo que sí está se vea sin ruido.

La misma regla vale para el código. Tiene el mínimo de clases y de líneas con que se ven los tres temas: las hojas de sprites, MVP y los hilos. Cada clase se gana su sitio por lo que enseña, y no hay utilidades, capas ni bibliotecas de concurrencia que no hagan falta. Las tres interfaces están porque enseñan el contrato entre las capas.

Para Andreu, esa ausencia es un ejercicio: añadir las colisiones es fácil cuando el alumno sabe en qué clase va cada cosa.

## En qué se diferencia de lo que ya existe

<div class="caso"><p class="caso-etiqueta">El caso · capítulo 7 · lo que Andreu ya había probado</p><p>Antes de preparar SpriteAnimation, Andreu había buscado. Encontró tutoriales, un libro gordísimo y un motor de juegos. ¿Por qué no le bastó ninguno?</p></div>

Lo que hay se agrupa en tres familias, y cada una resuelve algo que SpriteAnimation no pretende resolver.

Los **tutoriales**, empezando por el de Java de Oracle, avanzan el fotograma con un temporizador de Swing y piden repintar ([«Performing Animation»](https://www.comp.nus.edu.sg/~cs3283/ftp/Java/JavaTutorial/uiswing/painting/animation.html)). Son breves y correctos, pero atan el ritmo de la animación al temporizador del repintado y no separan capas.

Los **libros** van mucho más lejos. «[Killer Game Programming in Java](https://www.oreilly.com/library/view/killer-game-programming/0596007302/)», de Andrew Davison (O'Reilly, 2005), explica el pintado activo con todo detalle, en casi mil páginas. Es una obra de referencia, pero no un material de consulta para quien empieza.

Los **motores**, como [libGDX](https://libgdx.com/wiki/graphics/2d/2d-animation), lo resuelven bien: su clase `Animation` recorta la hoja y elige el fotograma según el tiempo transcurrido. Son más completos y maduros que cualquier demo, pero esconden justo lo que Andreu quiere que sus alumnos vean.

SpriteAnimation no compite con ellos. Su diferencia es estar a medio camino. Es pequeña como un tutorial, deja a la vista lo que un motor esconde y su arquitectura se lee en una tarde. Además, encaja con el curso: los mismos alumnos estudian hilos en Programación de Servicios y Procesos, también de segundo de DAM ([programación del IES Historiador Chabás](https://portal.edu.gva.es/ieschabas/wp-content/uploads/sites/1291/2023/12/2DAM_M0490_PROGRAMACIO_DE_SERVICIS_23_24.pdf)).

## De vuelta al caso

<div class="caso"><p class="caso-etiqueta">El caso · Andreu, dos semanas después</p><p>Llegan otra vez las prácticas. La de Laia tiene un <code>SpriteSheet</code> que recorta la hoja una sola vez, un modelo con el pato y su hilo, y una vista que pide qué pintar. En su portátil y en el aula, el pato aletea igual. Marta, que da Programación de Servicios y Procesos al mismo grupo, le ha pedido la demo para sus clases de hilos. Lo que Andreu no sabe todavía es cuántos alumnos aceptarán escribir tres clases donde antes escribían una.</p></div>

SpriteAnimation no les ha hecho la práctica a los alumnos de Andreu. Les ha dado un ejemplo pequeño al que volver cada vez que dudaban: cómo se recorta una hoja, quién guarda el estado y por qué pintar y animar son dos ritmos. A Andreu le ha dado algo que no tenía: un caso en el que la arquitectura se justifica sola.

## Anexo: el caso y las fuentes

#### El caso de Andreu

Andreu, sus alumnos Laia y Pau, y su compañera Marta son ficticios. El caso se ha construido para ilustrar los tres tropiezos de «Qué le cuesta a quien empieza». Los momentos de la historia (las prácticas sobre la mesa, la clase del lunes, la entrega dos semanas después) son ilustrativos.

Las cifras del HUD (60 repintados, 8 fotogramas del pato y 12 de la explosión por segundo) son las de la demo al arrancar; con la rueda cambian. Con ellas, el pato da una vuelta completa a su animación cada medio segundo y la explosión, cada 0,67 segundos. A los 0,3 segundos, el pato está en su fotograma 2 (0,3 × 8 = 2,4) y la explosión, en el 3 (0,3 × 12 = 3,6).

#### Las hojas de sprites

- **`pato.png`:** «[16x16 Duck](https://opengameart.org/content/16x16-duck)», de ARoachIFoundOnMyPillow, CC0. Son 4 fotogramas de 16×16 en columna.
- **`explosion.png`:** «[Explosion](https://opengameart.org/node/132621)», de Pavel Kutejnikov, CC0. Son 8 fotogramas de 48×48 en fila.

#### Fuentes

- Tutorial de Java de Oracle, «[Performing Animation](https://www.comp.nus.edu.sg/~cs3283/ftp/Java/JavaTutorial/uiswing/painting/animation.html)» (copia en la Universidad Nacional de Singapur).
- Andrew Davison, «[Killer Game Programming in Java](https://www.oreilly.com/library/view/killer-game-programming/0596007302/)», O'Reilly, 2005.
- libGDX, «[2D Animation](https://libgdx.com/wiki/graphics/2d/2d-animation)».
- Glenn Fiedler, «[Fix Your Timestep!](https://gafferongames.com/post/fix_your_timestep/)».
- IES Historiador Chabás, [programación del módulo 0490, Programación de Servicios y Procesos, de 2.º de DAM](https://portal.edu.gva.es/ieschabas/wp-content/uploads/sites/1291/2023/12/2DAM_M0490_PROGRAMACIO_DE_SERVICIS_23_24.pdf), curso 2023-24. El título de DAM lo establece el [Real Decreto 450/2010](https://www.boe.es/eli/es/rd/2010/04/16).

Comparación con lo que ya existe consultada el 30 de octubre de 2026. Las decisiones que sostienen este documento están en [sf/decisiones](../../../sf/decisiones/README.md).
