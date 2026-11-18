# Sufijo Interface en las interfaces de la demo

- **Origen:** SpriteAnimation, 2026-11-02, al revisar el documento explicativo de MVP (tarea 6). El propietario: «añade un sufijo Interface a las interfaces, es aclarativo».
- **Contexto:** las interfaces se llamaban `AnimationModel`, `AnimationView` y `AnimationPresenter`, y en el código no se distinguía a simple vista una interfaz de una clase.
- **Decisión:** las tres interfaces pasan a llamarse `AnimationModelInterface`, `AnimationViewInterface` y `AnimationPresenterInterface`. Las clases (`Model`, `View`, `Presenter`) no cambian.
- **Consecuencias:** se aparta de la convención habitual de Java, que no marca las interfaces en el nombre, a cambio de que el alumno de 2º de DAM vea en cada declaración si el tipo es una interfaz. Cambia los nombres de «Interfaces y constructores de la demo» y de «El presentador recibe el modelo y la vista por sus interfaces». El documento explicativo de MVP se adapta en todo su código y texto.
- **Estado:** sustituida por «Las interfaces de la demo, en un contrato» antes de aplicarse.
