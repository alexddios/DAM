# La configuración que solo usa main queda fuera de los contratos

Los métodos de configuración que solo llama main al montar la aplicación (setCanvasSize de la vista, setFpsLimits del presentador, como addSprite del modelo) no van en los contratos. main declara la vista, el presentador y el modelo con su clase concreta para poder llamarlos.

La velocidad de pintado inicial también la fija main, con view.setRenderFps(60). El título y el cierre de la ventana se quedan dentro de View.

Por qué: el contrato recoge lo que las piezas se piden entre sí. Lo que solo hace main al montar es configuración, y así se ve toda junta en main.
