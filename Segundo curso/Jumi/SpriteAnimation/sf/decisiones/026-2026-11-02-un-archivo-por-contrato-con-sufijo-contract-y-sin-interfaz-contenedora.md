# Un archivo por contrato, con sufijo Contract y sin interfaz contenedora

Cada contrato de MVP va en su propio archivo del paquete contract, con el sufijo Contract: ModelContract, SpriteContract, ViewContract y PresenterContract. Desaparece la interfaz contenedora AnimationContract. El record SpriteState va en su propio archivo, sin sufijo, porque no es un contrato.

Por qué: un archivo por tipo es más fácil de encontrar y leer para los alumnos, y el sufijo Contract deja claro qué es un contrato y evita choques de nombre con las clases que lo implementan (model.Model, view.View, presenter.Presenter).
