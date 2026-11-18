---
antetitulo: SpriteAnimation · Material para segundo de DAM
lema: Cada pieza en su sitio, // y un solo sitio que las une.
subtitulo: Cómo se organiza un programa en Java, explicado con la demo de animación de sprites.
tipo: Documento explicativo
version: Versión 0.1 · 31/10/2026
estado: Borrador · Material didáctico
portada: oscura
pie: Material de consulta de SpriteAnimation para segundo de DAM.
destino: proyecto
herramientas: buscador lectura
---
# MVP, interfaces e inyección de dependencias

Código de la demo: [repositorio en GitHub](https://github.com/jumibot/SpriteAnimation)

## El ejemplo | Lo que vamos a construir {#ejemplo}

Todo este documento gira alrededor de un solo programa: SpriteAnimation, una ventana con dos animaciones.

Al ejecutarlo, ves un pato que vuela y una explosión que estalla una y otra vez. Cada una va a su ritmo: el pato, a 8 fotogramas por segundo, y la explosión, a 12. Arriba, un pequeño HUD (las cifras que flotan sobre la escena) muestra tres velocidades: cuántas veces por segundo se pinta la ventana y a qué ritmo avanza cada animación.

Se maneja con el ratón. Con la rueda sobre una cifra del HUD, la subes o la bajas; sobre un sprite, lo agrandas o lo encoges. La línea del HUD que está bajo el ratón se ilumina en amarillo, para que sepas cuál vas a cambiar. Y puedes arrastrar el pato o la explosión a otro sitio: mientras lo llevas, sigue animándose. Con Ctrl+clic aparece un pato nuevo donde pulsas, hasta 12 sprites.

<figure class="vertical">
<svg viewBox="0 0 560 350" role="img" style="font-family:var(--fuente-texto);display:block">
<title>La ventana de SpriteAnimation: arriba, el HUD con la velocidad de pintado y la de cada sprite, que se cambian con la rueda del ratón; abajo, el pato volando, que se puede arrastrar sin que deje de aletear, y la explosión</title>
<style>
.ej-pato{animation:ej-pato .5s steps(4) infinite}
.ej-expl{animation:ej-expl .667s steps(8) infinite}
.ej-lleva{animation:ej-lleva 7s ease-in-out infinite}
.ej-rueda{animation:ej-rueda 1.4s ease-in-out infinite}
@keyframes ej-pato{from{transform:translateY(0)}to{transform:translateY(-64px)}}
@keyframes ej-expl{from{transform:translateX(0)}to{transform:translateX(-384px)}}
@keyframes ej-lleva{0%,15%{transform:translate(0,0)}45%,60%{transform:translate(110px,-18px)}90%,100%{transform:translate(0,0)}}
@keyframes ej-rueda{0%,100%{transform:translateY(-3px)}50%{transform:translateY(3px)}}
@media (prefers-reduced-motion:reduce){.ej-pato,.ej-expl,.ej-lleva,.ej-rueda{animation:none}}
@media print{.ej-pato,.ej-expl,.ej-lleva,.ej-rueda{animation:none}}
html[data-modo="oscuro"] .ej-px{filter:url(#ej-inv)}
</style>
<defs>
<filter id="ej-inv" color-interpolation-filters="sRGB"><feComponentTransfer><feFuncR type="table" tableValues="1 0"/><feFuncG type="table" tableValues="1 0"/><feFuncB type="table" tableValues="1 0"/></feComponentTransfer><feColorMatrix type="hueRotate" values="180"/></filter>
<marker id="ej-f" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--texto-suave)"/></marker>
</defs>
<rect x="20" y="10" width="520" height="330" rx="10" style="fill:#fff;stroke:var(--oscuro)" stroke-width="2"/>
<line x1="20" y1="42" x2="540" y2="42" style="stroke:var(--borde)" stroke-width="1.5"/>
<text x="40" y="32" font-size="15" font-weight="700" style="fill:var(--oscuro)">SpriteAnimation</text>
<g style="fill:var(--tenue)"><circle cx="490" cy="26" r="5"/><circle cx="506" cy="26" r="5"/><circle cx="522" cy="26" r="5"/></g>
<rect x="40" y="58" width="300" height="84" style="fill:#0a0a14;stroke:#ff3df2" stroke-width="3"/>
<rect x="45" y="63" width="290" height="74" fill="none" style="stroke:#00e5ff" stroke-width="1"/>
<rect x="48" y="90" width="284" height="22" style="fill:#ffe600"/>
<path d="M53 95 l0 11 l7 -5.5 z" style="fill:#000"/>
<g font-size="14" font-weight="700" font-family="monospace">
<text x="66" y="82" style="fill:#ffe600">PINTADO<tspan x="160" style="fill:#fff">60 fps</tspan></text>
<text x="66" y="106" style="fill:#000">PATO<tspan x="168">8 fps</tspan></text>
<text x="66" y="130" style="fill:#ffe600">EXPLOSIÓN<tspan x="160" style="fill:#fff">12 fps</tspan></text>
</g>
<rect x="362" y="66" width="26" height="40" rx="13" style="fill:#fff;stroke:var(--oscuro)" stroke-width="2"/>
<line x1="375" y1="66" x2="375" y2="82" style="stroke:var(--oscuro)" stroke-width="1.5"/>
<rect class="ej-rueda" x="372" y="71" width="6" height="10" rx="3" style="fill:var(--acento)"/>
<text x="398" y="84" font-size="14" style="fill:var(--texto-suave)">rueda sobre una cifra:</text>
<text x="398" y="102" font-size="14" style="fill:var(--texto-suave)">la sube o la baja</text>
<path d="M150 262 Q 205 228 262 244" fill="none" style="stroke:var(--texto-suave)" stroke-width="1.5" stroke-dasharray="5 4" marker-end="url(#ej-f)"/>
<text x="60" y="318" font-size="14" style="fill:var(--texto-suave)">arrástralo: sigue aleteando</text>
<g class="ej-lleva">
<svg class="ej-px" x="86" y="190" width="72" height="72" viewBox="0 0 16 16" style="image-rendering:pixelated"><image class="ej-pato" width="16" height="64" href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAABACAYAAAATffeWAAABmklEQVRYR2NkoBAwUqifgeoG/EdyEcxwZDGYNNxiFEXxG3rg+hcGlIDZcZ7yGL5ctP0h3CCQAf9fHHcGC5S/9GQAaQQZtO7IbYbAqzswNL989hUsJi7FzQAyCMUA1bUqKBpgBqgqKoDFb99/AKZhhuy8+AYeiNj8idX5MANAmhkYGBgJxcJ/UBiguwAaBmC9hAwAqQEbggxINqA5yx4eBsiayXIBWQbAXFA77SCGpcSEATgcoGGAoZ5YA3DmuVEDiEuJeAst9EAcrgUKciECKolIKlCQizD00oioAgVWsCLbipwviE3K2GIHbA6xBoxmJjy5YQQFIkUJ6b+7vgg4GH++/cFw4MkXlARITCDCDQDphNXKsIghqkQCuQBkOwwgu4JgE8dBhgcjFUANgLcP8DZxHl1+i9UAUJVPdBMH2RA5XeHREgktSIlJSCRVbXgVY5OkmwsoaiP9hyVn9JxIbL1AUm7EFk5gA2C5keLyAL1MIBQLKM7H5gpCBQqK8wemQAHZilwesAtzgB1CUp9ptImDP4cTSkgEywcABLsuSPGSfPgAAAAASUVORK5CYIIA"/></svg>
<path d="M140 238 l0 24 l6 -6 l5 11 l4 -2 l-5 -11 l8 0 z" style="fill:#fff;stroke:var(--oscuro)" stroke-width="1.5" stroke-linejoin="round"/>
</g>
<g class="ej-px">
<rect x="350" y="170" width="140" height="140" rx="8" style="fill:#1e2a30"/>
<svg x="360" y="180" width="120" height="120" viewBox="0 0 48 48" style="image-rendering:pixelated"><image class="ej-expl" width="384" height="48" transform="translate(-144 0)" href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAYAAAAAwCAYAAAAYeq1+AAAACXBIWXMAAAsTAAALEwEAmpwYAAAF8WlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDggNzkuMTY0MDM2LCAyMDE5LzA4LzEzLTAxOjA2OjU3ICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdEV2dD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlRXZlbnQjIiB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgMjEuMCAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIxLTExLTI2VDIxOjIxOjI2KzAzOjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDIxLTExLTI2VDIxOjIxOjI2KzAzOjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMS0xMS0yNlQyMToyMToyNiswMzowMCIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo0YjM1YTA2Mi1mYTU0LTMzNGItOGEzNy04MjgxNGNkYzcwY2QiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDpkMjAzOTdlNi05MTViLWNiNGUtOGQ0Yi0wYTgzMjE4YTdmNWIiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo1YjdlMWI1OS1jOGYyLTZlNGItOWUxMC00MmE3MjVmMmY3ZWQiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjViN2UxYjU5LWM4ZjItNmU0Yi05ZTEwLTQyYTcyNWYyZjdlZCIgc3RFdnQ6d2hlbj0iMjAyMS0xMS0yNlQyMToyMToyNiswMzowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIDIxLjAgKFdpbmRvd3MpIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo0YjM1YTA2Mi1mYTU0LTMzNGItOGEzNy04MjgxNGNkYzcwY2QiIHN0RXZ0OndoZW49IjIwMjEtMTEtMjZUMjE6MjE6MjYrMDM6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCAyMS4wIChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7BrRXcAAAKmUlEQVR42u2dy5EkNRCGxxYOXDnAjSsnvMAJDMIATMAaItYEbhQ0UIQmOx9/plIqqSo7QtHLTHU9mt3/U74/juP4yFi//fhNrVq1Bq1fvv72aNfoz9W656K6/VEAqFULE09uXXkfyGf++PmrT6tAcN3fowJArVo3Ev9ZQPBeSxL/qyDQc70VLJg73H8BoNYS6yVA53v75xVF/89fvzt+//6HtxUFQVQEIgCQxP8EwOquq6jlUwAoC6DWRgB4CSr9nfQZ67jXas/nXa24n2L/gkC7eiBg/Vz6rBcymvi3EJC+x0zRugMAygVUAKjVIfjoegnr+U6FnDu+Ffz2+PbzHiC0IsOJPwcCBAY9LiavkKIAkFxB1H2EihcCsBmxD+56FfMoANRaXPwlCJz/bR1Pf0YXsvu3RF+DAAoC+rMoQKL+fwrQ3sDx6GBzTwDccw8rWhnR//cFgDHkXGYXQXe4PW6P2eKPCjs9Pno+T4zBC4FofKAV/vNdsyqigmm5fzgLwxs3GB1spuAc4Xq5g6sJvfcCQED4T/FfAQKc6ER2vNb5Mnz8rShb92uJOz1WOze646X/eCJWQMQlJF3v/HMmBFqxl8QfFXPrGtkB557AKwqAFdJ8CwCLiLz3NUPsuV1/KxLabtQCRBZEuF0/58pBFgoMz7lQACC+fwsC6M6/3fVrQMko/mpFHwlSSzt5TlgjcYbV3Cd3AEC5gCaL/ywIUNdEK3AUBBmiScWz1/XDwQp1p2Q/i5X10iP+KARa1w/6nWRm0HhrCJBA8YoAGP09VRB4cwAcia8R7iFJ5Nn15ad/FxEPTlypq8ESYg8IJDfO/9c675OuIAi4Z5XOgQQ/ewHQPqvl9ml9/ygAejJpPDtk1FJAILCykCIB+wLADQFwJL9GA4CK/ev1STwZIVXFvz0W+BwKAc5//+ka9N4BCKjPQz/fnAeJC2TGACJuIDSuMAIAWcKJiP9OFsBqefsFgMXFfwQIRAEkonmK6RsQ/lv//JyKPCO60uc9AJB2/hLAzhd3P5AIS9aEAwJUCHoB4AkCI9fJAsBI8WwDy7uJPweBu9cNPBoAx4TXsN1/I/raiwr7GwQakWwBwkHAYwWwAPj7XO1O3bxvwSJ4s3I08e8AQCvMERB4MoGs62SkhI6GwZX9kWoVAJYEQAYI3gAAiv8nwW8AILpdmHNKIuoBAHf/FDYuCHjuHYhnaLEALlaSbQlogXyrwKw3LTIbBiX+BYACQCIAWPePFwDkeHaXL5yvFd+oBfDm/49YAJx/XwEAElBGK2FpcRaayRSFgJTJlSWoVkro1U3fntZddoZrTjr/IwFwXPTKBIDnxYmkJrAWAJCKWqmAywsA836F78PjArL+8dBUTS5/H4lXICmh9BraZ7J251KGzqq7+DvAZeYzVCHYnQDg3P3TXb+aeWPsoj3FVBELAP0dGxeQIJeQ0tru0rhdutQq2gMAKvrZPYGsIC1XGLayK8fbC2i17p0eC2Dkdz4NAG1WzJUtFI4LX1cCgNtZm8FTxf+P3LcFAOle3C6hQD1BtLBNCtxKcPC4gNBmcD3VwGiappTauQIEvO22e90so57X05pi1Pc4BQBXVcyuBoDIc2cDwCWqQi59FwCQrB0FDmZMQKmORgFgFUtZ3TulIK6nD1DvrIFoV1C0n0+ks+bsIPNo62U0CGdYXcMBsELrhDsCIAMC6O6Zij8KAdoDCM7bJ/fBuqSUzyDiLz2DV1A8Vb5e8ae/s66bCQDkOGRXnS1i0XkJI8R0lv9+WwCsJv47AuCEwFvV7gwroBHraFO4tyAwAgGr0KstamPO4+kw2gMAKsLe7p9WEZjW6mIEALhuoair6MrBME/o6rldGij1+a8Cgl0B8JaLPsMKIG6V17u3l77Wswet4BWPVQAhWQBIEBsVf8Rlg8wHsDKHPKMoeyDgaengcQUh32c2BKoeYQEArNRBcwXxz6gHkEQxmhXEQUBLo6Rze70QUFMlrUwerSiM6WPU2xZaC/4ibZu5nbtVBIaIv3a+aM8etJ9/xAq4wr9dANgkBvC0OEDG0BdvRWy3dRDoBeS2BBgAsH2L6PHBSV29AEAKvjgLQUsH1Tq2ZgDA065BA0F2QdrV2UIFgItiAE9z//QAwISBYRFwOf7cu+Qe6kmhhKwAR23AGwC02AEjvnSCmFdEpN06BYC3GEwqOMtyAUVAYLl+etpIV9HYwy2AJ9YB9Dz3KVYR8UdSKz0A8AaDEXC5q5g5a8DIYvJYAEjWjhYHQNtCWEVgGUHgTCBY2Tfn39Wn78B3eNZpdQCc+D6tGjhzwLqnP5AGAMlCoJ1DPW0grEygEACsnb6VReQsBkPn93LtG07x5yAgCfx5vBQ0RoLJs7p5WgNTENfRCimWBZdqBbEdBKx4ALKL1txBHCQyR0N6rBdE1Nndv6OmARFCz1QyJLdfO6fV/XNWcVO0EhmNH6wSH/AMk9+971AB4Aa7f1FMAQCgvYKsDKDIgPi2OhjJAtIqfbl0WHUojtDQ7nwOaRYAIv50oLs27Ytz9aDjLzWXTLaA9or/DmMhowDYGQIFgAshkH3/aJM1T3aQ1Qco4vvvhoCS/2/Bz+pnxD2LJv6aFWIFdj0D4FEIjAx8Rq0OT8HZTt06dy8kKwBcCIER905FxBTxCAAYUcqwXEx3kKcATBkyY4HMqgRGxV8DQM/4x+g8gCyx8kLgDoPhPUPid9v1c/ddANgYAHTKl+U7j1oANAaQ5bpyDVWxYgFWlpNQy4C4fyK1BlarZ/ScFmQyXByeoL4FAKuNxA4AkFw/d8xkejwAZgyGH2oBGKMQqZtIFX+gt04PALTCMLT4SRVJRzvrbABYKZq94h9xBWkzAVqhbtM2e/3nVkO5nZqxFQBuDoCR4j/6vumULat6V4OA2ldHmAaWaQG4qoWtdgnAQPjRAEA7fkaGy2fk/2u79CzRzGgjfVWHzBnivwpIHg+AbBj0FnuF00ENtw0ikprLpScFVAMAEvyM7Jil81qipxV8oUCKBpJHA0AbBZm5Qx+ZthrJzMkMhl9RnbxlN9CNTaItLADOCqCCRfvxoyLpzZuPBIGta2ntI7Re/9bytoT2Aonr99MLAGRAjFd4PO6ZnmZvWg+hWTvz1dw2K7mTCgAJELjiHllhUnrhIwKPCOcVAOB2+dKAF6/4RwCAQMBq59BjBcyYCNazW9XmCowYar+jX75cQJvBQAoeX3VfUkolJ3ie3bEkrBn+YQsC6H1pbh2P8CN9gCIxCquNQ7YLaGUAZMYCqsVzAeCyLKFV71EStVYQrbbMbX4/PYe3708GvLhnkoBgCVpE/FvBiQLA60byxEVmAiCyW/UMny8AXNsPqABwo8VBIOtco6GlibQEhJHij840kMSc6+5ptXqwUmN7hG/kzhwJNGfFATLFvyyHAsDtxH/Ejn2m9aIJtXQMtWQyxV+DgNWqGbEEtLRRZPB8NAsoQ/yRUY9chlF2IHh3P3wBoFZZLYBIU4uAc3VFzusRiujc396V0QI62uQtUhE8s4NprQJArVpTU/YivfItgc9oxzzLj14AKAAUAGo9FgDRgqdIM7cRAjrah17iXwCoVetWEPDsqke2I7gLQGvdGAC1atWqVWuv9ReW53luNA8dbAAAAABJRU5ErkJggg=="/></svg>
</g>
<text x="420" y="328" font-size="14" text-anchor="middle" style="fill:var(--texto-suave)">la explosión, a su ritmo</text>
</svg>
<figcaption>Lo que ves al ejecutar la demo. Las cifras del HUD son de ejemplo: la rueda las cambia mientras todo se mueve.</figcaption>
</figure>

Con este ejemplo vas a aprender cuatro cosas:

- **Separar el programa en piezas.** Quién guarda el pato, quién lo pinta y quién decide qué pasa al girar la rueda: es [MVP](#mvp).
- **Las promesas entre piezas.** Qué puede pedirle la ventana a quien la gobierna, y al revés: es [el contrato](#interfaces).
- **Quién crea a quién.** Cómo se juntan, al arrancar, la ventana, el estado y las reglas: es [la inyección de dependencias](#inyeccion).
- **Cada animación en su hilo.** Por qué el pato y la explosión no se esperan, y la ventana pinta a su propio ritmo: es [MVP con hilos](#hilos).

Todos los apartados vuelven a esta ventana: cuando leas «la demo», es ella. Y no se queda en el papel: puedes descargar el programa y ejecutarlo, desde su repositorio [[R6]](#src-R6 "SpriteAnimation, Código de la demo"){: .ref}.

## Por qué | Por qué hace falta una estructura {#estructura}

Ya sabes crear clases, y no escribirías un programa entero en una sola. Pero cada programa nuevo te obliga a decidir qué clases hacen falta, qué hace cada una y cómo se hablan entre ellas. Ese diseño lo haces desde cero cada vez: gasta tiempo y casi nunca sale tan bien como algo ya pensado y probado.

La demo del apartado anterior es pequeña, pero tiene todo lo que complica un programa de verdad: estado que cambia, dibujo, ratón y varios hilos.

Sin un patrón, cada programa es una carretera de curvas. En cada curva tienes que pararte a decidir algo, y la respuesta es distinta cada vez:

<figure class="vertical">
<svg viewBox="0 0 560 520" role="img" style="font-family:var(--fuente-texto);display:block">
<title>Sin un patrón, el diseño es una carretera de curvas: en cada curva hay que decidir qué clases hacen falta, quién pinta y quién guarda el estado, cómo se avisan y quién crea a quién</title>
<text x="300" y="24" font-size="15" style="fill:var(--texto-suave)">programa nuevo</text>
<path d="M280 20 C 80 60, 80 120, 280 140 S 480 220, 280 260 S 80 340, 280 380 S 480 440, 280 470" fill="none" style="stroke:var(--texto-suave)" stroke-width="10" stroke-linecap="round" opacity=".35"/>
<path d="M280 20 C 80 60, 80 120, 280 140 S 480 220, 280 260 S 80 340, 280 380 S 480 440, 280 470" fill="none" style="stroke:var(--texto-suave)" stroke-width="2"/>
<g style="fill:var(--oscuro)"><circle cx="130" cy="88" r="7"/><circle cx="430" cy="193" r="7"/><circle cx="130" cy="320" r="7"/><circle cx="430" cy="429" r="7"/></g>
<g font-size="15" style="fill:var(--oscuro)">
<text x="152" y="93">¿Qué clases hacen falta?</text>
<text x="410" y="198" text-anchor="end">¿Quién pinta? ¿Quién guarda el estado?</text>
<text x="152" y="325">¿Cómo se avisan entre ellas?</text>
<text x="410" y="434" text-anchor="end">¿Quién crea a quién?</text>
</g>
<text x="280" y="505" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">una estructura nueva, sin probar</text>
</svg>
<figcaption>Las mismas cuatro preguntas en cada programa, y cada vez una respuesta que nadie ha probado antes.</figcaption>
</figure>

Un patrón de arquitectura como MVP es una autopista: esas preguntas ya tienen respuesta, pensada y probada en muchísimos programas antes que el tuyo. Tú solo decides lo propio de tu programa, como qué es un sprite o cómo se recorta una hoja. Así quedan las respuestas en la demo:

<figure class="vertical">
<svg viewBox="0 0 560 470" role="img" style="font-family:var(--fuente-texto);display:block">
<title>Con MVP el diseño es una autopista recta: cada pregunta ya tiene respuesta, y en la demo da estas clases</title>
<rect x="40" y="10" width="52" height="450" rx="6" style="fill:var(--primario)" opacity=".15"/>
<line x1="66" y1="20" x2="66" y2="450" style="stroke:var(--primario)" stroke-width="3" stroke-dasharray="14 10"/>
<g style="stroke:var(--primario)" stroke-width="2"><line x1="92" y1="62" x2="130" y2="62"/><line x1="92" y1="172" x2="130" y2="172"/><line x1="92" y1="282" x2="130" y2="282"/><line x1="92" y1="392" x2="130" y2="392"/></g>
<g fill="none" style="stroke:var(--primario)" stroke-width="2"><rect x="130" y="20" width="390" height="86" rx="10"/><rect x="130" y="130" width="390" height="86" rx="10"/><rect x="130" y="240" width="390" height="86" rx="10"/><rect x="130" y="350" width="390" height="86" rx="10"/></g>
<g font-size="15" style="fill:var(--texto-suave)">
<text x="148" y="44">¿Qué clases hacen falta?</text>
<text x="148" y="154">¿Quién pinta? ¿Quién guarda el estado?</text>
<text x="148" y="264">¿Cómo se avisan entre ellas?</text>
<text x="148" y="374">¿Quién crea a quién?</text>
</g>
<g font-size="18" font-weight="700" style="fill:var(--primario)">
<text x="148" y="70">Model, View y Presenter</text>
<text x="148" y="180">View pinta y Model guarda</text>
<text x="148" y="290">Por un contrato con tres interfaces</text>
<text x="148" y="400">main crea, Presenter conecta</text>
</g>
<g font-size="15" style="fill:var(--texto-suave)">
<text x="148" y="94">y las propias, como Sprite, SpriteSheet o Viewer</text>
<text x="148" y="204">Presenter aplica las reglas entre los dos</text>
<text x="148" y="314">ModelContract, ViewContract y PresenterContract</text>
<text x="148" y="424">y Presenter.start() lo pone en marcha</text>
</g>
</svg>
<figcaption>Con MVP no te paras en ninguna curva: cada pregunta trae su respuesta, y en la demo da estas clases.</figcaption>
</figure>

La estructura, además, se nota después. Otra hoja de sprites solo afecta a `SpriteSheet`, y otra ventana solo a la vista. También se puede probar el presentador sin abrir ninguna ventana, como verás en «Para practicar».

> **Para quedarse**
> Un patrón de arquitectura ya responde a las preguntas de siempre: qué clases hay, qué hace cada una y cómo se hablan. Tú solo decides lo propio de tu programa.

## MVP | Las tres piezas de MVP {#mvp}

MVP significa modelo, vista y presentador (en inglés, *Model-View-Presenter*) [[R1]](#src-R1 "Wikipedia, Modelo–vista–presentador"){: .ref}. Cada pieza tiene un papel, y lo que define el patrón es tanto lo que cada una sabe como lo que no sabe.

El **modelo** es el estado del programa: en la demo, los sprites con su fotograma actual, su velocidad y su posición. No sabe que existe una ventana. La **vista** es lo que el usuario ve y toca: la ventana, el pintado y el ratón. No toma decisiones; solo pinta lo que le dan y avisa de lo que hace el usuario. El **presentador** está en medio: traduce el modelo a lo que la vista necesita y convierte los gestos del usuario en cambios del modelo.

::: tarjetas 3

### Model

Sabe
:   El estado: qué fotograma toca, a qué velocidad y dónde está cada sprite. También crea sus sprites a partir de cada hoja.

No sabe
:   Que existe una vista. Al presentador, como mucho, lo conoce por su interfaz, y en la demo ni eso.

### View

Sabe
:   Pintar y escuchar el ratón.

No sabe
:   Qué es un `Sprite`, ni qué reglas tiene el programa (por ejemplo, la velocidad máxima).

### Presenter

Sabe
:   Qué datos necesita la vista, qué reglas se aplican a cada gesto y cómo se pone en marcha la aplicación.

No sabe
:   Pintar. No usa `Graphics` ni nada de Swing.

:::

En la demo, la vista toma la iniciativa para pintar: su hilo le pide al presentador, en cada vuelta, lo que tiene que dibujar. El presentador lo lee del modelo y se lo devuelve como una lista de `SpriteView`: de cada sprite, solo lo que hay que enseñar. El sprite no sale del modelo. Así se ve una vuelta de pintado:

<figure class="vertical">
<svg viewBox="0 0 560 440" role="img" style="font-family:var(--fuente-texto);display:block">
<title>Animación: la vista pide qué pintar, el presentador pide al modelo los datos de cada sprite y devuelve una lista de SpriteView, y la vista pinta</title>
<style>
.a1-ida{animation:a1-ida 6s ease-in-out infinite}
.a1-vuelta{animation:a1-vuelta 6s ease-in-out infinite}
.a1-pinta{animation:a1-pinta 6s ease-in-out infinite}
@keyframes a1-ida{0%{transform:translateY(0);opacity:1}30%{transform:translateY(250px);opacity:1}34%,100%{transform:translateY(250px);opacity:0}}
@keyframes a1-vuelta{0%,38%{transform:translateY(0);opacity:0}40%{transform:translateY(0);opacity:1}74%{transform:translateY(-250px);opacity:1}78%,100%{transform:translateY(-250px);opacity:0}}
@keyframes a1-pinta{0%,76%{opacity:0}80%,94%{opacity:1}100%{opacity:0}}
@media (prefers-reduced-motion:reduce){.a1-ida,.a1-vuelta,.a1-pinta{animation:none}}
@media print{.a1-ida,.a1-vuelta,.a1-pinta{animation:none}}
</style>
<rect x="40" y="20" width="480" height="70" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/>
<text x="60" y="50" font-size="18" font-weight="700" style="fill:var(--primario)">View</text>
<text x="60" y="74" font-size="15" style="fill:var(--texto-suave)">su hilo pide qué pintar</text>
<text class="a1-pinta" x="500" y="62" font-size="18" font-weight="700" text-anchor="end" style="fill:var(--primario)">pinta</text>
<rect x="40" y="185" width="480" height="70" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/>
<text x="60" y="215" font-size="18" font-weight="700" style="fill:var(--primario)">Presenter</text>
<text x="60" y="239" font-size="15" style="fill:var(--texto-suave)">monta un SpriteView por sprite</text>
<rect x="40" y="350" width="480" height="70" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/>
<text x="60" y="380" font-size="18" font-weight="700" style="fill:var(--primario)">Model</text>
<text x="60" y="404" font-size="15" style="fill:var(--texto-suave)">Sprite pato · Sprite explosión</text>
<g style="stroke:var(--linea)" stroke-width="2"><line x1="110" y1="92" x2="110" y2="183"/><line x1="110" y1="257" x2="110" y2="348"/><line x1="450" y1="92" x2="450" y2="183"/><line x1="450" y1="257" x2="450" y2="348"/></g>
<text x="122" y="142" font-size="15" style="fill:var(--texto-suave)">spriteViews()</text>
<text x="122" y="307" font-size="15" style="fill:var(--texto-suave)">frame(i), x(i)…</text>
<text x="438" y="142" font-size="15" text-anchor="end" style="fill:var(--texto-suave)">List&lt;SpriteView&gt;</text>
<text x="438" y="307" font-size="15" text-anchor="end" style="fill:var(--texto-suave)">copias y números</text>
<circle class="a1-ida" cx="110" cy="96" r="9" style="fill:var(--primario)"/>
<rect class="a1-vuelta" x="438" y="330" width="24" height="18" rx="3" opacity="0" style="fill:var(--oscuro)"/>
</svg>
<figcaption>Una vuelta del hilo de pintado: baja la petición por la izquierda y sube la respuesta por la derecha. La vista nunca toca el modelo.</figcaption>
</figure>

Fíjate en que la flecha que baja la mueve siempre la vista. Nadie empuja datos hacia ella: los pide cuando le toca pintar. Esa decisión simplifica el resto, porque el modelo puede cambiar tantas veces como quiera sin avisar a nadie.

El presentador hace de intermediario: la vista y el modelo no se hablan nunca, y todo lo que uno necesita del otro pasa por él. Es la idea del patrón Mediator [[R2]](#src-R2 "Refactoring.Guru, Mediator"){: .ref}, que quita dependencias entre objetos obligándolos a colaborar a través de uno solo.

> **Para quedarse**
> El modelo guarda el estado, la vista pinta y escucha, y el presentador decide. La vista y el modelo no se hablan nunca: todo pasa por el presentador.

## Interfaces | El contrato: las interfaces {#interfaces}

En MVP, las piezas no se conocen por su clase, sino por una interfaz: una promesa de qué se pueden pedir, sin decir cómo se hace. Si una pieza conociera la clase de otra, dependería de cómo está hecha por dentro, y cada cambio en esa clase la obligaría a cambiar también. Con interfaces, cada pieza se puede cambiar o sustituir sin tocar las demás, se puede programar por separado, y el compilador no deja pedir nada que no esté prometido.

Hay tres promesas: lo que la vista puede pedirle al presentador, y lo que el presentador puede pedirles a la vista y al modelo. Si hace falta, el modelo también puede pedirle algo al presentador, como avisarle de que sus datos han cambiado: para eso recibe la interfaz del presentador, igual que la vista.

Las tres promesas juntas forman el **contrato** de la aplicación:

<figure class="vertical">
<svg viewBox="0 0 560 480" role="img" style="font-family:var(--fuente-texto);display:block">
<title>El contrato de MVP: la vista usa la interfaz del presentador, y el presentador usa las interfaces de la vista y del modelo; si hace falta, el modelo usa la del presentador para avisar de cambios</title>
<defs><marker id="ctr-f" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--primario)"/></marker></defs>
<rect x="40" y="20" width="480" height="60" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/><text x="60" y="47" font-size="18" font-weight="700" style="fill:var(--primario)">Vista</text><text x="60" y="69" font-size="15" style="fill:var(--texto-suave)">pinta y escucha al usuario</text>
<rect x="40" y="210" width="480" height="60" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/><text x="60" y="237" font-size="18" font-weight="700" style="fill:var(--primario)">Presentador</text><text x="60" y="259" font-size="15" style="fill:var(--texto-suave)">aplica las reglas y traduce los datos</text>
<rect x="40" y="400" width="480" height="60" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/><text x="60" y="427" font-size="18" font-weight="700" style="fill:var(--primario)">Modelo</text><text x="60" y="449" font-size="15" style="fill:var(--texto-suave)">guarda el estado</text>
<line x1="110" y1="82" x2="110" y2="206" style="stroke:var(--primario)" stroke-width="2.2" marker-end="url(#ctr-f)"/>
<rect x="44.0" y="117" width="132" height="30" rx="7" style="fill:var(--fondo);stroke:var(--oscuro)" stroke-width="2.5"/><text x="110" y="137" font-size="15" font-weight="700" text-anchor="middle" style="fill:var(--oscuro)">«Presentador»</text>
<text x="186" y="128" font-size="15" text-anchor="start" style="fill:var(--texto-suave)">qué pintar, gestos del usuario</text>
<line x1="450" y1="208" x2="450" y2="84" style="stroke:var(--primario)" stroke-width="2.2" marker-end="url(#ctr-f)"/>
<rect x="410.0" y="143" width="80" height="30" rx="7" style="fill:var(--fondo);stroke:var(--oscuro)" stroke-width="2.5"/><text x="450" y="163" font-size="15" font-weight="700" text-anchor="middle" style="fill:var(--oscuro)">«Vista»</text>
<text x="400" y="164" font-size="15" text-anchor="end" style="fill:var(--texto-suave)">arrancar, ritmo de pintado</text>
<line x1="110" y1="272" x2="110" y2="396" style="stroke:var(--primario)" stroke-width="2.2" marker-end="url(#ctr-f)"/>
<rect x="62.0" y="299" width="96" height="30" rx="7" style="fill:var(--fondo);stroke:var(--oscuro)" stroke-width="2.5"/><text x="110" y="319" font-size="15" font-weight="700" text-anchor="middle" style="fill:var(--oscuro)">«Modelo»</text>
<text x="170" y="319" font-size="15" text-anchor="start" style="fill:var(--texto-suave)">arrancar, dar sus datos</text>
<line x1="450" y1="398" x2="450" y2="274" style="stroke:var(--primario)" stroke-width="2.2" marker-end="url(#ctr-f)" stroke-dasharray="6 5"/>
<rect x="384.0" y="345" width="132" height="30" rx="7" style="fill:var(--fondo);stroke:var(--oscuro)" stroke-width="2.5" stroke-dasharray="5 4"/><text x="450" y="365" font-size="15" font-weight="700" text-anchor="middle" style="fill:var(--oscuro)">«Presentador»</text>
<text x="376" y="365" font-size="15" text-anchor="end" style="fill:var(--texto-suave)">si hace falta: avisar de cambios</text>
</svg>
<figcaption>Cada flecha va de quien pide a quien responde, y pasa por la interfaz que lo permite. En discontinuo, lo opcional: el modelo solo le pide algo al presentador si tiene que avisarle de cambios. Las tres interfaces, juntas, son el contrato.</figcaption>
</figure>

En la demo, el modelo no pide nada: la vista pregunta al presentador cada vez que pinta, así que nadie tiene que avisarla. Por eso `ModelContract` no recibe al presentador. El contrato está en el paquete `contract`, un archivo por interfaz: `ModelContract`, `ViewContract` y `PresenterContract`. Los sprites no salen del modelo: el presentador pide sus datos por su número. Son cortas a propósito: cada método es algo que la otra parte necesita de verdad.

::: tarjetas 3

### `ModelContract`: lo que el presentador puede pedirle al modelo

- `start()`: poner en marcha las animaciones.
- `spriteCount()`: decir cuántos sprites hay.
- `frame(index)`, `x(index)`, `y(index)`, `name(index)`, `fps(index)` y `scale(index)`: dar un dato de un sprite, por su número. El fotograma va en una copia, ya ampliada a su escala.
- `setFps(index, fps)`: cambiar la velocidad de un sprite.
- `setScale(index, scale)`: cambiar su tamaño, sin mover su centro.
- `moveTo(index, x, y)`: llevar un sprite a otro sitio.
- `createDuck(centerX, centerY)`: crear un pato con su centro en ese punto, ya animándose.

### `ViewContract`: lo que el presentador puede pedirle a la vista

- `setPresenter(presenter)`: guardar a quién pedirle qué pintar.
- `start()`: empezar a pintar.
- `renderFps()`: decir cuántas veces por segundo pinta.
- `setRenderFps(fps)`: cambiar ese ritmo.

### `PresenterContract`: lo que la vista puede pedirle al presentador

- `spriteViews()`: dar lo que hay que enseñar de cada sprite, para pintarlo.
- `spriteCount()`: decir cuántos sprites hay, para contar las líneas del HUD.
- `changeRenderSpeed(delta)`: subir o bajar el ritmo de pintado, sin salirse de los límites.
- `changeSpriteSpeed(index, delta)`: lo mismo con la velocidad de un sprite.
- `changeSpriteScale(index, delta)`: agrandar o encoger un sprite, también entre unos límites.
- `moveSprite(index, x, y)`: llevar un sprite a otro sitio.
- `createDuck(x, y)`: crear un pato en ese punto, si aún cabe otro sprite.
- `start()`: poner en marcha la aplicación. No lo pide la vista: lo llama `main`.

Lo que se enseña de cada sprite es un `SpriteView`: una copia de su fotograma, su posición, su nombre y su velocidad. Va en su propio archivo, también en `contract`, pero no es un contrato: son datos. Entre capas solo viajan datos que nadie puede cambiar (records, números y textos), y lo que sí se podría cambiar, como una imagen, viaja en copia. Así ninguna capa tiene en sus manos un objeto de otra.

:::

> **Para quedarse**
> Cada pieza conoce a las demás por una interfaz, no por su clase. Las tres interfaces juntas son el contrato, y entre capas solo viajan datos que nadie puede cambiar.

## En Java | El contrato en Java {#contrato-java}

Las promesas del apartado anterior se escriben en Java como interfaces, y cada clase cumple la suya.

El contrato se escribe así, una interfaz en cada archivo, y cada clase implementa la suya:

```java
public interface ModelContract { … }
public interface ViewContract { … }
public interface PresenterContract { … }
public record SpriteView(BufferedImage image, int x, int y, String name, int fps) {}

public class Model implements ModelContract { … }
public class View extends JFrame implements ViewContract { … }
public class Presenter implements PresenterContract { … }
```

Cada pieza va en su paquete: el contrato en `contract`; `Model`, `View` y `Presenter` en `model`, `view` y `presenter`; y la clase con `main`, en `main`.

Lo puedes comprobar mirando los `import`. Los archivos de `view` solo importan de `contract` `PresenterContract`, `ViewContract` y `SpriteView`; los de `model`, solo `ModelContract`. Ninguno de los dos importa nada del otro. En definitiva, la vista no necesita nada del modelo, ni el modelo de la vista.

A `ModelContract` le falta algo a propósito: `addSprite`. Decidir qué animaciones hay es cosa de `main`, así que ese método es de la clase `Model`, pero no del contrato. El presentador sí puede pedir un pato con `createDuck`, pero no inventarse una animación: cómo es un pato lo sabe el modelo. Sacar a una interfaz solo la parte de una clase que otra necesita es una técnica de refactorización con nombre propio: *extraer interfaz* [[R3]](#src-R3 "Refactoring.Guru, Extract Interface"){: .ref}.

Gracias al contrato, el presentador funciona igual con cualquier vista que lo cumpla: la de Swing, una de texto o una falsa para probar sin ventana.

<figure class="vertical">
<svg viewBox="0 0 560 400" role="img" style="font-family:var(--fuente-texto);display:block">
<title>El presentador usa la interfaz ViewContract, que pueden implementar la vista de Swing, una vista de texto o una vista falsa para pruebas</title>
<defs><marker id="m3" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--texto-suave)"/></marker></defs>
<rect x="160" y="16" width="240" height="60" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/>
<text x="280" y="44" font-size="18" font-weight="700" text-anchor="middle" style="fill:var(--primario)">Presenter</text>
<text x="280" y="66" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">solo conoce el contrato</text>
<line x1="280" y1="78" x2="280" y2="134" style="stroke:var(--texto-suave)" stroke-width="2" marker-end="url(#m3)"/>
<text x="292" y="112" font-size="15" style="fill:var(--texto-suave)">usa</text>
<rect x="130" y="138" width="300" height="66" rx="10" fill="none" style="stroke:var(--oscuro)" stroke-width="3"/>
<text x="280" y="164" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">«interface»</text>
<text x="280" y="190" font-size="18" font-weight="700" text-anchor="middle" style="fill:var(--oscuro)">ViewContract</text>
<g style="stroke:var(--texto-suave)" stroke-width="2"><line x1="110" y1="290" x2="200" y2="208" marker-end="url(#m3)"/><line x1="280" y1="290" x2="280" y2="208" marker-end="url(#m3)"/><line x1="450" y1="290" x2="360" y2="208" marker-end="url(#m3)"/></g>
<text x="300" y="256" font-size="15" style="fill:var(--texto-suave)">implementan</text>
<rect x="20" y="292" width="170" height="80" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/>
<text x="105" y="322" font-size="18" font-weight="700" text-anchor="middle" style="fill:var(--primario)">View</text>
<text x="105" y="346" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">Swing y Canvas</text>
<text x="105" y="364" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">la de la demo</text>
<rect x="195" y="292" width="170" height="80" rx="10" fill="none" style="stroke:var(--texto-suave)" stroke-width="2" stroke-dasharray="6 4"/>
<text x="280" y="322" font-size="18" font-weight="700" text-anchor="middle" style="fill:var(--texto-suave)">TextView</text>
<text x="280" y="346" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">escribe en consola</text>
<text x="280" y="364" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">ejercicio</text>
<rect x="370" y="292" width="170" height="80" rx="10" fill="none" style="stroke:var(--texto-suave)" stroke-width="2" stroke-dasharray="6 4"/>
<text x="455" y="322" font-size="18" font-weight="700" text-anchor="middle" style="fill:var(--texto-suave)">FakeView</text>
<text x="455" y="346" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">para probar</text>
<text x="455" y="364" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">ejercicio</text>
</svg>
<figcaption>En trazo continuo, la vista que trae la demo; en discontinuo, las que puedes escribir tú sin tocar el presentador.</figcaption>
</figure>

> **Para quedarse**
> Cada interfaz en su archivo y cada clase cumple la suya. El contrato solo lleva lo que la otra pieza necesita, y la vista y el modelo no se importan nunca entre sí.

## Inyección | Quién crea a quién: la inyección de dependencias {#inyeccion}

Una clase *depende* de otra cuando la necesita para trabajar. Puede conseguirla de dos formas: crearla ella misma con `new`, o recibirla de fuera. La **inyección de dependencias** es la segunda [[R4]](#src-R4 "Wikipedia, Inyección de dependencias"){: .ref}: cada clase recibe por su constructor lo que necesita, y no lo crea ella. Así no queda atada a una clase concreta, y se le puede pasar cualquier otra que cumpla la misma interfaz, por ejemplo una falsa para probar.

<figure class="vertical">
<svg viewBox="0 0 560 370" role="img" style="font-family:var(--fuente-texto);display:block">
<title>Dos formas de conseguir una dependencia. Crear: el presentador hace new VistaSwing dentro y queda atado a ella, sin poder usar una vista falsa. Recibir: main le pasa por el constructor cualquier clase que cumpla la interfaz Vista, la real o una falsa.</title>
<style>
.di-real{animation:di-real 8s ease-in-out infinite}
.di-falsa{animation:di-falsa 8s ease-in-out infinite}
@keyframes di-real{0%{transform:translate(0,0);opacity:0}5%{opacity:1}24%,40%{transform:translate(56px,-100px);opacity:1}44%,100%{transform:translate(56px,-100px);opacity:0}}
@keyframes di-falsa{0%,50%{transform:translate(0,0);opacity:0}55%{opacity:1}74%,90%{transform:translate(-56px,-100px);opacity:1}94%,100%{transform:translate(-56px,-100px);opacity:0}}
@media (prefers-reduced-motion:reduce){.di-real,.di-falsa{animation:none}}
@media print{.di-real,.di-falsa{animation:none}}
</style>
<defs><marker id="di-f" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--primario)"/></marker></defs>
<line x1="280" y1="14" x2="280" y2="356" style="stroke:var(--tenue)" stroke-width="1.5" stroke-dasharray="6 5"/>
<text x="20" y="32" font-size="18" font-weight="700" style="fill:var(--primario)">Crear</text>
<text x="20" y="54" font-size="15" style="fill:var(--texto-suave)">la clase hace new dentro</text>
<rect x="20" y="72" width="240" height="150" rx="10" style="fill:var(--fondo-suave);stroke:var(--primario)" stroke-width="2"/>
<text x="36" y="98" font-size="16" font-weight="700" style="fill:var(--primario)">Presentador</text>
<text x="36" y="126" font-size="14" style="fill:var(--oscuro);font-family:ui-monospace,Consolas,monospace">new VistaSwing()</text>
<rect x="36" y="138" width="208" height="66" rx="8" style="fill:#fff;stroke:var(--oscuro)" stroke-width="2.5"/>
<text x="140" y="166" font-size="16" font-weight="700" text-anchor="middle" style="fill:var(--oscuro)">VistaSwing</text>
<text x="140" y="190" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">atada para siempre</text>
<line x1="140" y1="252" x2="140" y2="226" style="stroke:var(--tenue)" stroke-width="1.5" stroke-dasharray="4 3"/>
<text x="140" y="244" font-size="16" font-weight="700" text-anchor="middle" style="fill:var(--texto-suave);paint-order:stroke;stroke:var(--pagina);stroke-width:5px">✕</text>
<rect x="60" y="254" width="160" height="40" rx="8" fill="none" style="stroke:var(--tenue)" stroke-width="1.5" stroke-dasharray="5 4"/>
<text x="140" y="279" font-size="15" text-anchor="middle" style="fill:var(--texto-tenue)">VistaFalsa</text>
<text x="140" y="318" font-size="15" text-anchor="middle" style="fill:var(--texto-suave)">no hay forma de cambiarla</text>
<text x="300" y="32" font-size="18" font-weight="700" style="fill:var(--primario)">Recibir</text>
<text x="300" y="54" font-size="15" style="fill:var(--texto-suave)">se la dan por el constructor</text>
<rect x="300" y="72" width="240" height="110" rx="10" style="fill:var(--fondo-suave);stroke:var(--primario)" stroke-width="2"/>
<text x="316" y="98" font-size="16" font-weight="700" style="fill:var(--primario)">Presentador</text>
<text x="316" y="126" font-size="14" style="fill:var(--oscuro);font-family:ui-monospace,Consolas,monospace">Presentador(Vista v)</text>
<rect x="372" y="166" width="96" height="32" rx="7" style="fill:var(--fondo);stroke:var(--oscuro)" stroke-width="2.5"/>
<text x="420" y="187" font-size="15" font-weight="700" text-anchor="middle" style="fill:var(--oscuro)">«Vista»</text>
<g style="stroke:var(--primario)" stroke-width="2" marker-end="url(#di-f)"><line x1="364" y1="260" x2="404" y2="204"/><line x1="476" y1="260" x2="436" y2="204"/></g>
<rect x="300" y="248" width="240" height="106" rx="10" fill="none" style="stroke:var(--oscuro)" stroke-width="2"/>
<text x="420" y="338" font-size="15" text-anchor="middle" style="fill:var(--oscuro)"><tspan font-weight="700">main</tspan> elige una y se la pasa</text>
<rect x="314" y="264" width="100" height="40" rx="8" style="fill:#fff;stroke:var(--primario)" stroke-width="2"/>
<text x="364" y="289" font-size="15" text-anchor="middle" style="fill:var(--texto)">VistaSwing</text>
<rect x="426" y="264" width="100" height="40" rx="8" style="fill:#fff;stroke:var(--primario)" stroke-width="2" stroke-dasharray="5 4"/>
<text x="476" y="289" font-size="15" text-anchor="middle" style="fill:var(--texto)">VistaFalsa</text>
<g class="di-real" opacity="0"><rect x="314" y="264" width="100" height="40" rx="8" style="fill:var(--acento-fondo);stroke:var(--acento)" stroke-width="2.5"/><text x="364" y="289" font-size="15" font-weight="700" text-anchor="middle" style="fill:var(--acento)">VistaSwing</text></g>
<g class="di-falsa" opacity="0"><rect x="426" y="264" width="100" height="40" rx="8" style="fill:var(--acento-fondo);stroke:var(--acento)" stroke-width="2.5"/><text x="476" y="289" font-size="15" font-weight="700" text-anchor="middle" style="fill:var(--acento)">VistaFalsa</text></g>
</svg>
<figcaption>A la izquierda, la clase crea lo que necesita y queda atada a esa clase concreta. A la derecha, lo recibe por el constructor a través de una interfaz, y <code>main</code> puede pasarle la vista real o una falsa para probar.</figcaption>
</figure>

En la demo, si el presentador hiciera `new View()` dentro de su constructor, quedaría atado para siempre a esa vista, y no se podría probar con una vista falsa. Por eso recibe el modelo y la vista:

```java
public class Presenter implements PresenterContract {

    private final ModelContract model;
    private final ViewContract view;

    public Presenter(ModelContract model, ViewContract view) {   // las recibe, no las crea
        this.model = model;
        this.view = view;

        view.setPresenter(this);   // se conecta con la vista: lo verás en «Conectar antes de arrancar»
    }
    // …
}
```

Fíjate en el tipo de los dos parámetros: son interfaces, no clases. Eso se llama **programar contra interfaces**. El presentador solo ve los métodos del contrato: aunque reciba un `Model` de verdad, no puede llamar a `addSprite`, porque el compilador no le deja. Así, que solo `main` decida qué animaciones hay no es una norma que haya que recordar: lo garantiza el compilador.

No todo se inyecta. Lo que es parte interna de una pieza lo crea ella: la vista crea su lienzo (`Viewer`), su HUD (`Hud`) y la clase que escucha el ratón (`MouseHandler`); y el modelo, sus sprites. Se inyecta lo que conecta capas o lo que querrías sustituir.

> **Para quedarse**
> Cada clase recibe por su constructor lo que necesita, en lugar de hacer `new`. Y lo recibe como interfaz: así vale cualquier pieza que cumpla el contrato, también una falsa para probar.

## Montaje | Cómo se monta la aplicación {#montaje}

Si cada clase recibe lo que necesita y no lo crea, alguien tiene que crear las piezas. En MVP ese sitio es uno solo: el punto de entrada del programa, el único que hace `new` de las capas. La receta es siempre la misma y tiene dos fases: construir y conectar, todo en un solo hilo; y arrancar, cuando nacen los demás hilos. Así se monta, en tres pasos:

<figure class="vertical">
<style>
figure ol.vin{--vin-main:#2c6e9b;--vin-start:#b23a2a;list-style:none;margin:0 auto;padding:0;max-width:560px;display:grid;gap:.75rem}
figure ol.vin>li{margin:0;border:1.5px solid var(--borde);border-radius:10px;padding:.75rem .9rem .8rem}
figure ol.vin>li::before{content:none}
.vin-cab{display:flex;gap:.65rem;align-items:center}
.vin-num{flex:none;width:1.7rem;height:1.7rem;border-radius:50%;background:var(--vin-main);color:#fff;font-weight:700;font-size:.95rem;display:grid;place-items:center}
.vin-fase{display:block;font-size:.78rem;color:var(--vin-main);font-weight:650;text-transform:uppercase;letter-spacing:.04em;line-height:1.3}
.vin-tit{display:block;font-weight:700;line-height:1.3}
.vin-start .vin-num{background:var(--vin-start)}.vin-start .vin-fase{color:var(--vin-start)}
figure ol.vin ol.vin-acc{margin:.55rem 0 .7rem 2.35rem;padding:0;list-style:none;display:grid;gap:.2rem}
figure ol.vin ol.vin-acc>li{margin:0;padding:0 0 0 1.1rem;position:relative;line-height:1.45}
figure ol.vin ol.vin-acc>li::before{content:"→";position:absolute;left:0;top:0;color:var(--vin-main);font-weight:700;background:none;width:auto;height:auto}
.vin-start ol.vin-acc>li::before{color:var(--vin-start)!important}
.vin svg{display:block;width:100%;max-width:500px;height:auto;margin:0}
@media (max-width:480px){figure ol.vin ol.vin-acc{margin-left:0}}
.vin.en-marcha>li{animation:vin-paso 2s ease-in-out both}
.vin.en-marcha>li:nth-child(2){animation-delay:2s}
.vin.en-marcha>li:nth-child(3){animation-name:vin-ultimo;animation-delay:4s}
@keyframes vin-paso{0%,100%{border-color:var(--borde);box-shadow:none}15%,85%{border-color:var(--primario);box-shadow:0 0 0 1.5px var(--primario)}}
@keyframes vin-ultimo{0%{border-color:var(--borde);box-shadow:none}15%,100%{border-color:var(--primario);box-shadow:0 0 0 1.5px var(--primario)}}
@media (prefers-reduced-motion:reduce){.vin.en-marcha>li{animation:none}}
@media print{.vin.en-marcha>li{animation:none}}
</style>
<svg width="0" height="0" style="position:absolute;width:0;height:0;overflow:hidden" aria-hidden="true"><defs><marker id="vin-fv" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--texto-suave)"/></marker><marker id="vin-fn" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--primario)"/></marker></defs></svg>
<ol class="vin">
<li><div class="vin-cab"><span class="vin-num">1</span><div><span class="vin-fase">Construir y conectar</span><span class="vin-tit">Preparar el modelo y la vista</span></div></div>
<ol class="vin-acc"><li>Se crea el modelo, todavía vacío.</li><li>Después se configura: se le dan sus datos (aquí, las animaciones).</li><li>Luego se crea la vista y se configura (aquí, el tamaño del lienzo y la velocidad de pintado). Aún no tiene presentador: no sabe a quién pedir qué pintar.</li></ol>
<svg viewBox="0 0 500 42" role="img" style="font-family:var(--fuente-texto)"><title>Nuevos: el modelo con sus datos y la vista, todavía sin presentador</title><rect x="2" y="4" width="162" height="34" rx="6" style="fill:var(--fondo-medio);stroke:var(--primario)" stroke-width="2.5"/><text x="83.0" y="26" font-size="15" text-anchor="middle" font-weight="700" style="fill:var(--primario)">datos del modelo</text><rect x="190" y="4" width="86" height="34" rx="6" style="fill:var(--fondo-medio);stroke:var(--primario)" stroke-width="2.5"/><text x="233.0" y="26" font-size="15" text-anchor="middle" font-weight="700" style="fill:var(--primario)">Modelo</text><rect x="302" y="4" width="110" height="34" rx="6" fill="none" style="stroke:var(--tenue)" stroke-width="1.2" stroke-dasharray="4 3"/><text x="357.0" y="26" font-size="15" text-anchor="middle" style="fill:var(--texto-tenue)">Presentador</text><rect x="436" y="4" width="62" height="34" rx="6" style="fill:var(--fondo-medio);stroke:var(--primario)" stroke-width="2.5"/><text x="467.0" y="26" font-size="15" text-anchor="middle" font-weight="700" style="fill:var(--primario)">Vista</text><line x1="188" y1="21" x2="166" y2="21" style="stroke:var(--primario)" stroke-width="2.2" marker-end="url(#vin-fn)"/></svg></li>
<li><div class="vin-cab"><span class="vin-num">2</span><div><span class="vin-fase">Construir y conectar</span><span class="vin-tit">Construir el presentador</span></div></div>
<ol class="vin-acc"><li>Se crea el presentador, que recibe el modelo y la vista.</li><li>Los guarda para usarlos después.</li><li>Se presenta a la vista, que lo guarda: se cierra el ciclo.</li><li>Por último, se configura (aquí, los límites de velocidad).</li></ol>
<svg viewBox="0 0 500 42" role="img" style="font-family:var(--fuente-texto)"><title>Nuevo: el presentador, que apunta al modelo y a la vista, y la vista, que apunta al presentador</title><rect x="2" y="4" width="162" height="34" rx="6" fill="none" style="stroke:var(--texto-suave)" stroke-width="1.5"/><text x="83.0" y="26" font-size="15" text-anchor="middle" style="fill:var(--texto)">datos del modelo</text><rect x="190" y="4" width="86" height="34" rx="6" fill="none" style="stroke:var(--texto-suave)" stroke-width="1.5"/><text x="233.0" y="26" font-size="15" text-anchor="middle" style="fill:var(--texto)">Modelo</text><rect x="302" y="4" width="110" height="34" rx="6" style="fill:var(--fondo-medio);stroke:var(--primario)" stroke-width="2.5"/><text x="357.0" y="26" font-size="15" text-anchor="middle" font-weight="700" style="fill:var(--primario)">Presentador</text><rect x="436" y="4" width="62" height="34" rx="6" fill="none" style="stroke:var(--texto-suave)" stroke-width="1.5"/><text x="467.0" y="26" font-size="15" text-anchor="middle" style="fill:var(--texto)">Vista</text><line x1="188" y1="21" x2="166" y2="21" style="stroke:var(--texto-suave)" stroke-width="1.5" marker-end="url(#vin-fv)"/><line x1="300" y1="21" x2="278" y2="21" style="stroke:var(--primario)" stroke-width="2.2" marker-end="url(#vin-fn)"/><line x1="413" y1="15" x2="434" y2="15" style="stroke:var(--primario)" stroke-width="2.2" marker-end="url(#vin-fn)"/><line x1="434" y1="27" x2="413" y2="27" style="stroke:var(--primario)" stroke-width="2.2" marker-end="url(#vin-fn)"/></svg></li>
<li class="vin-start"><div class="vin-cab"><span class="vin-num">3</span><div><span class="vin-fase">Arrancar</span><span class="vin-tit">Arrancar todo</span></div></div>
<ol class="vin-acc"><li>Se pone en marcha el modelo (aquí, un hilo por animación).</li><li>Se pone en marcha la vista, con su hilo de pintado.</li><li>El programa principal termina; la aplicación sigue en los hilos nuevos.</li></ol>
<svg viewBox="0 0 500 42" role="img" style="font-family:var(--fuente-texto)"><title>Los datos del modelo y la vista, con sus hilos en marcha</title><rect x="2" y="4" width="162" height="34" rx="6" fill="none" style="stroke:var(--acento)" stroke-width="2.5"/><text x="83.0" y="26" font-size="14" text-anchor="middle" font-weight="700" style="fill:var(--acento)">▶ datos del modelo</text><rect x="190" y="4" width="86" height="34" rx="6" fill="none" style="stroke:var(--texto-suave)" stroke-width="1.5"/><text x="233.0" y="26" font-size="15" text-anchor="middle" style="fill:var(--texto)">Modelo</text><rect x="302" y="4" width="110" height="34" rx="6" fill="none" style="stroke:var(--texto-suave)" stroke-width="1.5"/><text x="357.0" y="26" font-size="15" text-anchor="middle" style="fill:var(--texto)">Presentador</text><rect x="436" y="4" width="62" height="34" rx="6" fill="none" style="stroke:var(--acento)" stroke-width="2.5"/><text x="467.0" y="26" font-size="14" text-anchor="middle" font-weight="700" style="fill:var(--acento)">▶ Vista</text><line x1="188" y1="21" x2="166" y2="21" style="stroke:var(--texto-suave)" stroke-width="1.5" marker-end="url(#vin-fv)"/><line x1="300" y1="21" x2="278" y2="21" style="stroke:var(--texto-suave)" stroke-width="1.5" marker-end="url(#vin-fv)"/><line x1="413" y1="15" x2="434" y2="15" style="stroke:var(--texto-suave)" stroke-width="1.5" marker-end="url(#vin-fv)"/><line x1="434" y1="27" x2="413" y2="27" style="stroke:var(--texto-suave)" stroke-width="1.5" marker-end="url(#vin-fv)"/></svg></li>
</ol>
<script>(function(){var l=document.querySelector('.vin');if(!l)return;function mira(){var r=l.getBoundingClientRect();if(r.height>0&&r.top<innerHeight*.7&&r.bottom>0){l.classList.add('en-marcha');removeEventListener('scroll',mira);removeEventListener('resize',mira);document.removeEventListener('toggle',mira,true);}}addEventListener('scroll',mira,{passive:true});addEventListener('resize',mira);document.addEventListener('toggle',mira,true);mira();})();</script>
<figcaption>La receta de montaje de cualquier MVP, en el orden en que se ejecuta. Debajo de cada paso, las piezas tal como quedan al terminarlo. El color del número marca la fase: en azul, construir y conectar (1 y 2); en terracota, arrancar (3). Hasta el paso 3 solo trabaja un hilo, el del programa principal. Lo nuevo de cada paso va resaltado y lo discontinuo aún no existe. Las flechas son lo que cada pieza guarda; la doble entre el presentador y la vista es el ciclo que cierra el paso 2. En dorado, lo que tiene un hilo en marcha.</figcaption>
</figure>

En la demo, el punto de entrada es el método `main` de `SpriteAnimation`, y cada línea es una acción de las viñetas: crea el modelo y lo configura con sus animaciones, crea y configura la vista y el presentador, y lo arranca todo.

```java
public static void main(String[] args) {
    Model model = new Model();
    // nombre, hoja, ancho y alto del fotograma, escala, fotogramas por segundo, x, y
    model.addSprite("Pato", "/sprites/pato.png", 16, 16, 4, 8, 120, 220);
    model.addSprite("Explosión", "/sprites/explosion.png", 48, 48, 3, 12, 360, 160);

    View view = new View();
    view.setCanvasSize(640, 400);         // ancho y alto del lienzo, en píxeles
    view.setRenderFps(60);                // al empezar, pinta 60 veces por segundo

    Presenter presenter = new Presenter(model, view);
    presenter.setFpsLimits(1, 120);       // la rueda no baja de 1 ni pasa de 120 por segundo
    presenter.setScaleLimits(1, 8);       // ni encoge un sprite por debajo de 1 ni lo amplía más de 8 veces
    presenter.setMaxSprites(12);          // Ctrl+clic crea patos hasta que haya 12 sprites

    presenter.start();
}
```

`main` declara cada pieza con su clase, `Model`, `View` y `Presenter`, porque las configura con métodos que no son del contrato: `addSprite`, que recorta la hoja con `SpriteSheet` y crea su `Sprite`, `setCanvasSize`, `setFpsLimits`, `setScaleLimits` y `setMaxSprites`. Así la configuración queda a la vista, en un solo sitio. El presentador recibe el modelo y la vista como `ModelContract` y `ViewContract`, sin esos métodos. `setRenderFps` sí es del contrato, porque el presentador lo usa cuando giras la rueda.

Fíjate en que el presentador no sabe qué animaciones hay: trabaja con los sprites que tenga el modelo. Por eso sirve igual para dos sprites que para veinte, y cambiar las animaciones no lo toca.

> **Para quedarse**
> Solo `main` crea y configura las capas, en un único sitio. La receta es siempre la misma: construir, conectar y, al final, arrancar.

## Conectar | Conectar antes de arrancar {#conectar}

Las viñetas del apartado anterior esconden dos detalles: cómo se conectan el presentador y la vista, y por qué arrancar va al final.

Empieza por el paso 2. El presentador necesita la vista y la vista necesita el presentador, así que no pueden recibirse los dos por constructor: uno de los dos tiene que existir primero. La demo crea la vista, se la pasa al presentador, y el constructor del presentador, en su última línea, se presenta a la vista con `view.setPresenter(this)`. Es la única dependencia que no va por constructor.

Pasar `this` desde un constructor se llama *dejar escapar* `this`, y suele ser mala práctica: otro hilo podría usar el objeto antes de que esté construido. Aquí no hay problema: es la última línea del constructor y todavía solo existe el hilo de `main`.

El presentador hace de intermediario entre las capas [[R2]](#src-R2 "Refactoring.Guru, Mediator"){: .ref}, y en el montaje separa las dos fases. Su constructor, que ya has visto, guarda el modelo y la vista y se presenta a la vista: los conecta. Su método `start()` solo arranca:

```java
@Override
public void start() {          // solo arranca
    this.model.start();
    this.view.start();
}
```

Por eso arrancar va aparte, en el paso 3. Los hilos solo nacen cuando todo está construido y conectado: si el hilo de pintado empezara antes del paso 2, le pediría qué pintar a un presentador que la vista aún no tiene.

> **Para quedarse**
> La vista nace primero y el presentador se le presenta con `setPresenter(this)`. Los hilos arrancan solo cuando todo está construido y conectado.

## Hilos | MVP con hilos {#hilos}

Una aplicación con ventana suele tener varios hilos a la vez: uno atiende al usuario, otro pinta y otros trabajan de fondo. Si dos hilos tocan los mismos datos, pueden pisarse. MVP ayuda porque cada hilo vive en una capa. El hilo de pintado está en la vista y solo habla con el presentador; los hilos de fondo están en el modelo y no conocen la vista. Cada frontera entre capas es también una frontera entre hilos.

En la demo trabajan tres clases de hilos, y cada uno sabe qué puede tocar y qué solo puede leer:

<figure class="vertical">
<svg viewBox="0 0 560 480" role="img" style="font-family:var(--fuente-texto);display:block">
<title>Las tres capas como zonas. En la vista viven el hilo de pintado y el de eventos de Swing; el presentador no tiene hilo propio; en el modelo vive un hilo por sprite. El pintado baja a pedir una foto y la sube; el gesto baja hasta el modelo; los hilos de los sprites nunca salen del modelo.</title>
<style>
.hz-gira{transform-box:fill-box;transform-origin:center;animation:hz-gira 2s linear infinite}
.hz-gira.lento{animation-duration:3.2s}.hz-gira.rapido{animation-duration:1.3s}
@keyframes hz-gira{to{transform:rotate(360deg)}}
.hz-film{animation:hz-film4 2s steps(4) infinite}
.hz-film.ocho{animation:hz-film8 2s steps(8) infinite}
@keyframes hz-film4{from{transform:translateX(0)}to{transform:translateX(88px)}}
@keyframes hz-film8{from{transform:translateX(0)}to{transform:translateX(176px)}}
.hz-pide{animation:hz-pide 5s ease-in-out infinite}
.hz-foto{animation:hz-foto 5s ease-in-out infinite}
.hz-gesto{animation:hz-gesto 8s ease-in-out infinite}
@keyframes hz-pide{0%{transform:translateY(0);opacity:0}4%{opacity:1}40%{transform:translateY(208px);opacity:1}44%,100%{transform:translateY(208px);opacity:0}}
@keyframes hz-foto{0%,46%{transform:translateY(0);opacity:0}50%{opacity:1}86%{transform:translateY(-100px);opacity:1}90%,100%{transform:translateY(-100px);opacity:0}}
@keyframes hz-gesto{0%,30%{transform:translateY(0);opacity:0}33%{opacity:1}68%{transform:translateY(208px);opacity:1}72%,100%{transform:translateY(208px);opacity:0}}
@media (prefers-reduced-motion:reduce){.hz-gira,.hz-film,.hz-pide,.hz-foto,.hz-gesto{animation:none}}
@media print{.hz-gira,.hz-film,.hz-pide,.hz-foto,.hz-gesto{animation:none}}
</style>
<defs>
<marker id="hz-fp" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--primario)"/></marker>
<marker id="hz-fo" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto"><path d="M0 0 L10 5 L0 10 z" style="fill:var(--oscuro)"/></marker>
<g id="hz-hilo"><path d="M10 0 A10 10 0 1 1 -7.1 -7.1" fill="none" style="stroke:var(--acento)" stroke-width="3" stroke-linecap="round"/><path d="M-12.5 -11 L-6 -5 L-13.5 -2.5 z" style="fill:var(--acento)"/></g>
</defs>
<rect x="4" y="4" width="552" height="136" rx="12" style="fill:var(--fondo-suave);stroke:var(--primario)" stroke-width="2"/>
<rect x="4" y="176" width="552" height="88" rx="12" style="fill:var(--fondo-suave);stroke:var(--primario)" stroke-width="2"/>
<rect x="4" y="300" width="552" height="176" rx="12" style="fill:var(--fondo-suave);stroke:var(--primario)" stroke-width="2"/>
<g font-size="18" font-weight="700" style="fill:var(--primario)"><text x="20" y="30">View</text><text x="20" y="202">Presenter</text><text x="20" y="326">Model</text></g>
<text x="20" y="226" font-size="15" style="fill:var(--texto-suave)">No tiene hilo propio:</text>
<text x="20" y="246" font-size="15" style="fill:var(--texto-suave)">lo recorren los de la vista.</text>
<g style="stroke:var(--tenue)" stroke-width="1.5" stroke-dasharray="6 5"><line x1="20" y1="158" x2="540" y2="158"/><line x1="20" y1="282" x2="540" y2="282"/></g>
<g font-size="13" style="fill:var(--texto-tenue)"><text x="540" y="153" text-anchor="end">frontera</text><text x="540" y="277" text-anchor="end">frontera</text></g>
<rect x="20" y="42" width="250" height="84" rx="10" style="fill:var(--acento-fondo);stroke:var(--acento)" stroke-width="2"/>
<text x="66" y="70" font-size="16" font-weight="700" style="fill:var(--acento)">Hilo de pintado</text>
<text x="66" y="92" font-size="14" style="fill:var(--texto-suave)">pide la foto y la pinta:</text>
<text x="66" y="110" font-size="14" style="fill:var(--texto-suave)">solo lee</text>
<rect x="290" y="42" width="250" height="84" rx="10" style="fill:var(--acento-fondo);stroke:var(--acento)" stroke-width="2"/>
<text x="336" y="70" font-size="16" font-weight="700" style="fill:var(--acento)">Hilo de eventos</text>
<text x="336" y="92" font-size="14" style="fill:var(--texto-suave)">el de Swing: atiende</text>
<text x="336" y="110" font-size="14" style="fill:var(--texto-suave)">la rueda y el arrastre</text>
<rect x="20" y="340" width="250" height="122" rx="10" style="fill:var(--acento-fondo);stroke:var(--acento)" stroke-width="2"/>
<text x="66" y="368" font-size="16" font-weight="700" style="fill:var(--acento)">Hilo del pato</text>
<text x="66" y="390" font-size="14" style="fill:var(--texto-suave)">avanza su fotograma</text>
<g transform="translate(66 408)">
<g style="fill:#fff;stroke:var(--tenue)" stroke-width="1.2"><rect x="0" y="0" width="22" height="22" rx="3"/><rect x="22" y="0" width="22" height="22" rx="3"/><rect x="44" y="0" width="22" height="22" rx="3"/><rect x="66" y="0" width="22" height="22" rx="3"/></g>
<rect class="hz-film" x="0" y="0" width="22" height="22" rx="3" fill="none" style="stroke:var(--acento)" stroke-width="3"/>
</g>
<text x="66" y="450" font-size="13" style="fill:var(--texto-tenue)">8 por segundo</text>
<rect x="290" y="340" width="250" height="122" rx="10" style="fill:var(--acento-fondo);stroke:var(--acento)" stroke-width="2"/>
<text x="336" y="368" font-size="16" font-weight="700" style="fill:var(--acento)">Hilo de la explosión</text>
<text x="336" y="390" font-size="14" style="fill:var(--texto-suave)">avanza su fotograma</text>
<g transform="translate(336 408)">
<g style="fill:#fff;stroke:var(--tenue)" stroke-width="1.2"><rect x="0" y="0" width="22" height="22" rx="3"/><rect x="22" y="0" width="22" height="22" rx="3"/><rect x="44" y="0" width="22" height="22" rx="3"/><rect x="66" y="0" width="22" height="22" rx="3"/><rect x="88" y="0" width="22" height="22" rx="3"/><rect x="110" y="0" width="22" height="22" rx="3"/><rect x="132" y="0" width="22" height="22" rx="3"/><rect x="154" y="0" width="22" height="22" rx="3"/></g>
<rect class="hz-film ocho" x="0" y="0" width="22" height="22" rx="3" fill="none" style="stroke:var(--acento)" stroke-width="3"/>
</g>
<text x="336" y="450" font-size="13" style="fill:var(--texto-tenue)">12 por segundo</text>
<g transform="translate(43 64)"><g class="hz-gira rapido"><use href="#hz-hilo"/></g></g>
<g transform="translate(313 64)"><g class="hz-gira lento"><use href="#hz-hilo"/></g></g>
<g transform="translate(43 362)"><g class="hz-gira"><use href="#hz-hilo"/></g></g>
<g transform="translate(313 362)"><g class="hz-gira rapido"><use href="#hz-hilo"/></g></g>
<line x1="200" y1="128" x2="200" y2="336" style="stroke:var(--primario)" stroke-width="2" stroke-dasharray="5 4" marker-end="url(#hz-fp)"/>
<line x1="240" y1="230" x2="240" y2="130" style="stroke:var(--primario)" stroke-width="2.5" marker-end="url(#hz-fp)"/>
<line x1="470" y1="128" x2="470" y2="336" style="stroke:var(--oscuro)" stroke-width="2.5" marker-end="url(#hz-fo)"/>
<g font-size="13">
<text x="194" y="296" text-anchor="end" style="fill:var(--primario)">lee</text>
<text x="194" y="172" text-anchor="end" style="fill:var(--primario)">pide</text>
<text x="248" y="172" style="fill:var(--primario)">foto</text>
<text x="464" y="172" text-anchor="end" style="fill:var(--oscuro)">gesto</text>
<text x="464" y="296" text-anchor="end" style="fill:var(--oscuro)">cambia fps, x, y</text>
</g>
<circle class="hz-pide" cx="200" cy="128" r="7" opacity="0" style="fill:var(--primario)"/>
<rect class="hz-foto" x="231" y="224" width="18" height="14" rx="2" opacity="0" style="fill:var(--fondo);stroke:var(--primario)" stroke-width="2"/>
<circle class="hz-gesto" cx="470" cy="128" r="7" opacity="0" style="fill:var(--oscuro)"/>
</svg>
<figcaption>Cada hilo vive en su capa. Los de los sprites nunca salen del modelo. El de pintado baja a pedir una foto de los sprites y la sube, sin tocarlos. El de eventos lleva el gesto hasta el modelo.</figcaption>
</figure>

Cada `Sprite` tiene su hilo, que avanza el fotograma según el tiempo que ha pasado. Por eso su ritmo es el mismo en cualquier equipo, y no depende de lo rápido que se pinte.

Los campos que escribe un hilo y lee otro son `volatile`, para que cada hilo vea el último valor escrito. Con la lista de sprites pasa algo parecido: el hilo de pintado la recorre mientras el de eventos le añade patos. Por eso es una `CopyOnWriteArrayList`, que al añadir hace una lista nueva y cambia una por otra. Y la vista no lee los sprites: recibe una lista de `SpriteView` que nadie puede cambiar, con copias de los fotogramas.

Así se cumple la idea del principio: el hilo de pintado nunca toca un `Sprite`, y los hilos de los sprites nunca tocan Swing.

Para cerrar, la demo no para los hilos uno a uno: al pulsar la X, `EXIT_ON_CLOSE` termina el programa y, con él, todos los hilos.

> **Para quedarse**
> Cada hilo vive en una capa: el de pintado, en la vista; los de los sprites, en el modelo. El hilo de pintado nunca toca un `Sprite`, y los de los sprites nunca tocan Swing.

## Un gesto | Un gesto, de principio a fin {#gesto}

En MVP, cualquier gesto del usuario recorre el mismo camino. La vista lo recibe y avisa al presentador. El presentador aplica las reglas y cambia el modelo. La vista se entera del cambio de una de dos formas: lo ve la próxima vez que pide qué pintar, o el modelo avisa al presentador y este, a la vista. En la demo es la primera: nadie avisa a la vista.

En la demo, sigue un gesto: giras la rueda del ratón sobre la cifra del pato en el HUD.

<figure class="vertical">
<svg viewBox="0 0 560 500" role="img" style="font-family:var(--fuente-texto);display:block">
<title>Animación: la rueda del ratón sube la velocidad del pato de 8 a 9; el gesto baja de la vista al presentador y al modelo, y el siguiente pintado muestra 9</title>
<style>
.a3-gesto{animation:a3-gesto 9s ease-in-out infinite}
.a3-vuelta{animation:a3-vuelta 9s ease-in-out infinite}
.a3-ocho{animation:a3-ocho 9s ease-in-out infinite}
.a3-nueve{animation:a3-nueve 9s ease-in-out infinite}
.a3-m8{animation:a3-m8 9s ease-in-out infinite}
.a3-m9{animation:a3-m9 9s ease-in-out infinite}
@keyframes a3-gesto{0%,4%{transform:translateY(0);opacity:0}6%{opacity:1}40%{transform:translateY(300px);opacity:1}44%,100%{transform:translateY(300px);opacity:0}}
@keyframes a3-m8{0%,40%{opacity:1}44%,100%{opacity:0}}
@keyframes a3-m9{0%,40%{opacity:0}44%,96%{opacity:1}100%{opacity:0}}
@keyframes a3-vuelta{0%,52%{transform:translateY(0);opacity:0}54%{opacity:1}82%{transform:translateY(-300px);opacity:1}85%,100%{transform:translateY(-300px);opacity:0}}
@keyframes a3-ocho{0%,84%{opacity:1}86%,100%{opacity:0}}
@keyframes a3-nueve{0%,84%{opacity:0}86%,96%{opacity:1}100%{opacity:0}}
@media (prefers-reduced-motion:reduce){[class^="a3-"]{animation:none}}
@media print{[class^="a3-"]{animation:none}}
</style>
<rect x="40" y="14" width="480" height="56" rx="10" fill="none" style="stroke:var(--oscuro)" stroke-width="2"/>
<text x="60" y="48" font-size="18" style="fill:var(--oscuro)"><tspan font-weight="700">HUD</tspan> · Pato ·</text>
<text class="a3-ocho" x="176" y="48" font-size="18" font-weight="700" opacity="0" style="fill:var(--oscuro)">8</text>
<text class="a3-nueve" x="176" y="48" font-size="18" font-weight="700" style="fill:var(--primario)">9</text>
<text x="194" y="48" font-size="18" style="fill:var(--oscuro)">fps</text>
<rect x="40" y="110" width="480" height="64" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/>
<text x="60" y="138" font-size="18" font-weight="700" style="fill:var(--primario)">View</text>
<text x="60" y="162" font-size="15" style="fill:var(--texto-suave)">mouseWheelMoved: ¿sobre qué línea del HUD?</text>
<rect x="40" y="250" width="480" height="64" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/>
<text x="60" y="278" font-size="18" font-weight="700" style="fill:var(--primario)">Presenter</text>
<text x="60" y="302" font-size="15" style="fill:var(--texto-suave)">changeSpriteSpeed(0, +1): aplica el límite</text>
<rect x="40" y="390" width="480" height="64" rx="10" fill="none" style="stroke:var(--primario)" stroke-width="2"/>
<text x="60" y="418" font-size="18" font-weight="700" style="fill:var(--primario)">Model</text>
<text class="a3-m8" x="60" y="442" font-size="15" opacity="0" style="fill:var(--texto-suave)">Sprite pato · fps = 8</text>
<text class="a3-m9" x="60" y="442" font-size="15" style="fill:var(--texto-suave)">Sprite pato · setFps(9)</text>
<g style="stroke:var(--linea)" stroke-width="2"><line x1="150" y1="72" x2="150" y2="108"/><line x1="150" y1="176" x2="150" y2="248"/><line x1="150" y1="316" x2="150" y2="388"/><line x1="410" y1="72" x2="410" y2="108"/><line x1="410" y1="176" x2="410" y2="248"/><line x1="410" y1="316" x2="410" y2="388"/></g>
<text x="162" y="218" font-size="15" style="fill:var(--texto-suave)">el gesto</text>
<text x="398" y="218" font-size="15" text-anchor="end" style="fill:var(--texto-suave)">siguiente pintado</text>
<circle class="a3-gesto" cx="150" cy="80" r="9" opacity="0" style="fill:var(--oscuro)"/>
<rect class="a3-vuelta" x="398" y="380" width="24" height="18" rx="3" opacity="0" style="fill:var(--primario)"/>
</svg>
<figcaption>El gesto baja por la izquierda hasta el modelo. La vista no cambia la cifra: la ve cambiada cuando vuelve a pedir qué pintar.</figcaption>
</figure>

En código, el recorrido es corto. La vista solo averigua sobre qué línea del HUD está el ratón y avisa (lo hace `MouseHandler`, la parte de la vista que escucha el ratón); el presentador aplica la regla del límite y se lo pasa al modelo con `setFps`.

```java
// MouseHandler, de la vista: el evento llega por el hilo de eventos de Swing
public void mouseWheelMoved(MouseWheelEvent e) {
    int delta = -e.getWheelRotation();                      // rueda hacia arriba: sube
    int lines = 1 + this.presenter.spriteCount();           // la de pintado y una por sprite
    int line = this.hud.lineAt(e.getX(), e.getY(), lines);  // 0: pintado; 1, 2…: sprites
    if (line < 0) {
        int sprite = this.spriteAt(this.presenter.spriteViews(), e.getX(), e.getY());
        if (sprite >= 0) {
            this.presenter.changeSpriteScale(sprite, delta);   // sobre un sprite: lo agranda o lo encoge
        }
        return;                                              // fuera del HUD y de los sprites, no hace nada
    }

    if (line == 0) {
        this.presenter.changeRenderSpeed(delta);
    } else {
        this.presenter.changeSpriteSpeed(line - 1, delta);
    }
}

// Presenter: aplica la regla
public void changeSpriteSpeed(int index, int delta) {
    this.model.setFps(index, this.limit(this.model.fps(index) + delta, this.minFps, this.maxFps));
}
```

Arrastrar un sprite sigue el mismo camino. Qué sprite hay bajo el puntero lo averigua la vista: al pulsar, le pide al presentador los `SpriteView` de ese momento y busca el que queda encima. No guarda los `SpriteView`: los pide cuando los necesita, los usa y los suelta. Mientras arrastras, le pide al presentador que mueva el sprite (`moveSprite`). El pato sigue aleteando todo el rato, porque su hilo no sabe nada del ratón. Con la rueda sobre un sprite pasa lo mismo: la vista busca el sprite y avisa con `changeSpriteScale`, el presentador lo deja entre 1 y 8 y el modelo, con `setScale`, lo agranda sin mover su centro. Con Ctrl+clic, el viaje es el más corto: la vista pide un pato (`createDuck`), el presentador mira si cabe otro sprite y el modelo lo crea con los fotogramas que ya tiene y arranca su hilo. La diana roja que aparece sobre el sprite bajo el puntero sale de la misma búsqueda: la vista solo recuerda dónde está el ratón y, al pintar, mira qué sprite hay ahí.

Para enterarse de la rueda, la vista usa el patrón Observer [[R5]](#src-R5 "Refactoring.Guru, Observer"){: .ref}: se suscribe a los eventos del ratón de Swing, y Swing la avisa llamando a `mouseWheelMoved`. Entre el modelo y la vista, en cambio, la demo no lo usa: el modelo no avisa a nadie, y la vista pide los datos cuando le toca pintar. En otros MVP, el modelo sí avisa de sus cambios al presentador, y también suele hacerlo con Observer.

> **Para quedarse**
> Todo gesto sigue el mismo camino: la vista lo recibe y avisa, el presentador aplica la regla y cambia el modelo, y la vista ve el cambio la próxima vez que pinta.

## Balance | Qué aporta MVP y qué cuesta {#balance}

MVP no sale gratis. Ordena el programa, pero a cambio pide más código. Antes de usarlo, conviene saber qué ganas y qué pagas.

::: tarjetas 2

### A favor

- **Cada pieza, una tarea.** El modelo guarda, la vista pinta y el presentador decide. Cada cambio tiene su sitio.
- **La vista se cambia sola.** Otra ventana, o una de texto, no toca el presentador ni el modelo.
- **Se prueba sin ventana.** Con una vista falsa, el presentador se prueba solo.
- **Cada uno, en su pieza.** Con el contrato acordado, una persona puede hacer la vista y otra el presentador, a la vez.

### En contra

- **Más código y más archivos.** Interfaces, contrato y montaje en `main`, para hacer lo mismo.
- **Más piezas que entender.** Al principio cuesta seguir un gesto que pasa por tres clases.
- **Puede sobrar.** En un programa muy pequeño, el patrón pesa más que lo que ordena.

:::

En la demo, el precio se puede contar. El contrato declara 24 métodos: 12 del modelo, 4 de la vista y 8 del presentador. Cada uno se escribe dos veces, en la interfaz y en la clase que lo cumple. Sin MVP, el pato y la explosión cabrían en una sola clase de ventana que guarda los sprites, los pinta y escucha el ratón. Haría lo mismo con menos código, pero no podrías cambiar ni probar una parte sin las demás.

> **Cuándo compensa**
> Cuando el programa va a crecer, va a cambiar de interfaz o necesita pruebas. Para un ejercicio de una tarde, una sola clase basta.

## Prohibido | Prohibido en MVP {#prohibido}

Estos atajos son los que más se repiten al empezar, y en MVP están prohibidos. Todos funcionan el primer día, y todos complican el segundo.

::: tarjetas 2

### La vista no lee el modelo

Cómo se ve
:   `View` recibe el `Model` y recorre sus `Sprite` para pintarlos.

Por qué es un problema
:   La vista queda atada a las clases del modelo, y el hilo de pintado toca objetos que otros hilos están cambiando.

Cómo se arregla
:   La vista solo conoce `PresenterContract` y pinta `SpriteView`: datos preparados para enseñar, nunca un objeto del modelo, ni suelto ni dentro de una lista o un record.

### El presentador no pinta

Cómo se ve
:   `Presenter` recibe un `Graphics2D` y dibuja.

Por qué es un problema
:   El presentador deja de poder probarse sin ventana, y la vista se queda sin trabajo.

Cómo se arregla
:   El presentador devuelve datos y no guarda ninguno: cada vez se los pide al modelo, los prepara y los entrega. Pintar es cosa de la vista.

### Ningún `new` escondido

Cómo se ve
:   `Presenter` hace `new View()` en su constructor.

Por qué es un problema
:   No se puede cambiar la vista por otra, ni probar con una `FakeView`.

Cómo se arregla
:   Inyección por constructor: `new Presenter(model, view)` en `main`.

### Nunca la clase en lugar de la interfaz

Cómo se ve
:   `Presenter(Model model, View view)`: los parámetros son las clases, no sus interfaces.

Por qué es un problema
:   El presentador puede llamar a cualquier método, también a `addSprite`, y ya no acepta una `FakeView` ni un modelo falso.

Cómo se arregla
:   Programar contra interfaces: `Presenter(ModelContract model, ViewContract view)`.

### Ninguna interfaz que lo exponga todo

Cómo se ve
:   `PresenterContract` tiene veinte métodos, uno por cada campo del modelo.

Por qué es un problema
:   El contrato deja de proteger nada: la vista vuelve a depender de cómo es el modelo.

Cómo se arregla
:   Un método por cada cosa que la otra parte necesita de verdad, no por cada dato.

### El modelo no avisa a la vista

Cómo se ve
:   `Sprite` llama a `repaint()` cada vez que cambia de fotograma.

Por qué es un problema
:   El modelo depende de Swing, y el ritmo de pintado vuelve a atarse al de la animación.

Cómo se arregla
:   El modelo no conoce la vista. Si tiene que avisar, avisa al presentador por su interfaz; en la demo ni eso: la vista pide los datos cuando le toca pintar.


:::

## Practicar | Para practicar {#practicar}

Cada ejercicio cambia una sola cosa de la demo. Antes de empezar, intenta adivinar qué clases tendrás que tocar: si la estructura está bien hecha, son muy pocas.

El código completo de la demo está en su repositorio [[R6]](#src-R6 "SpriteAnimation, Código de la demo"){: .ref}, con cómo ejecutarlo y sus pruebas. Parte de él para cada ejercicio.

::: tarjetas 2

### Una tercera animación

Qué tienes que hacer
:   Busca una hoja de sprites y añade un tercer sprite a la demo.

Qué clases cambian
:   Solo `SpriteAnimation`: una llamada más a `model.addSprite` en `main`. El presentador y la vista no cambian, y el HUD muestra la nueva línea solo.

Qué aprendes
:   Que si cada pieza tiene su papel, añadir algo es cambiar una línea en el sitio que toca.

### Una vista de texto

Qué tienes que hacer
:   Escribe una `TextView` que implemente `ViewContract` y escriba en consola, una vez por segundo, el nombre, la velocidad y la posición de cada sprite: lo que le da el presentador con `spriteViews()`.

Qué clases cambian
:   Ninguna: añades `TextView` y cambias una línea de `main`.

Qué aprendes
:   Para qué sirve el contrato: el presentador no nota la diferencia.

### Una prueba sin ventana

Qué tienes que hacer
:   Escribe una `FakeView` que implemente `ViewContract` sin abrir ninguna ventana, y un `FakeModel` con un sprite fijo. Con ellos, comprueba que la velocidad no baja de 1 ni sube de 120, para el pintado y para cada sprite.

Qué clases cambian
:   Ninguna: la prueba, la `FakeView` y el `FakeModel` son clases aparte. La demo trae las suyas en `src/test/java/presenter`: míralas cuando termines las tuyas.

Qué aprendes
:   Que inyectar por interfaces hace que el presentador se pueda probar solo, sin ventana y sin hojas de sprites.

### Pausar un sprite

Qué tienes que hacer
:   Con un doble clic sobre un sprite, su animación se para o vuelve a empezar.

Qué clases cambian
:   `MouseHandler` (el gesto), `PresenterContract` y `Presenter` (el método nuevo), `ModelContract` y `Model` (lo que el presentador le pide) y `Sprite` (la pausa). En las pruebas, también `FakeModel`, que cumple `ModelContract`.

Qué aprendes
:   Cómo atraviesa una funcionalidad nueva las tres capas, y cómo se para y se reanuda un hilo.

:::

## Referencias | Para saber más {#referencias}

Para ampliar lo que cuenta este documento: la Wikipedia en español para las ideas generales, y Refactoring.Guru para los patrones de diseño y las técnicas de refactorización, con dibujos y ejemplos en Java. Los enlaces se revisaron el 2 de noviembre de 2026.

::: fuentes

**[R1] Wikipedia.** «Modelo–vista–presentador». Qué es MVP y qué papel tiene cada una de sus tres piezas. [Enlace](https://es.wikipedia.org/wiki/Modelo%E2%80%93vista%E2%80%93presentador)
{: #src-R1}

**[R2] Refactoring.Guru.** «Mediator». En la demo, el presentador es el intermediario entre la vista y el modelo, que nunca se hablan directamente. [Enlace](https://refactoring.guru/es/design-patterns/mediator)
{: #src-R2}

**[R3] Refactoring.Guru.** «Extract Interface» (en inglés). Cómo se saca a una interfaz la parte de una clase que otra necesita, como `ModelContract`. [Enlace](https://refactoring.guru/es/extract-interface)
{: #src-R3}

**[R4] Wikipedia.** «Inyección de dependencias». Por qué una clase recibe lo que necesita en lugar de crearlo ella. [Enlace](https://es.wikipedia.org/wiki/Inyecci%C3%B3n_de_dependencias)
{: #src-R4}

**[R5] Refactoring.Guru.** «Observer». En la demo, la vista se suscribe a los eventos del ratón de Swing; el modelo, en cambio, no avisa a nadie. [Enlace](https://refactoring.guru/es/design-patterns/observer)
{: #src-R5}

**[R6] SpriteAnimation.** «Código de la demo». El programa completo de este documento, con cómo ejecutarlo y sus pruebas. [Enlace](https://github.com/jumibot/SpriteAnimation)
{: #src-R6}

:::
