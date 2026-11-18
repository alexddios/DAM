# Ctrl+clic: la vista pide un pato al presentador y este al modelo

Con Ctrl+clic en el lienzo, la vista le pide al presentador que cree un pato en ese punto, y el presentador se lo pide al modelo. Cada capa hace solo lo suyo: la vista recoge el gesto, el presentador decide si se puede (máximo de sprites) y el modelo crea el pato con lo que ya tiene cargado.

- Máximo de 12 sprites por defecto, configurable desde main. Al llegar al máximo, Ctrl+clic no hace nada.
- Ctrl+clic encima de un sprite también crea un pato.
- Los patos nuevos se llaman «Pato» en el HUD, como el original.
- El punto pulsado es el centro del pato nuevo, que arranca su animación al crearse.

Por qué: es el viaje completo de una orden en MVP, vista → presentador → modelo, sin que la vista ni el presentador sepan cómo se fabrica un pato.
