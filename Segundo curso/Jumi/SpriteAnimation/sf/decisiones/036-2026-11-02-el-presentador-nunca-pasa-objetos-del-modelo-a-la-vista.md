# El presentador nunca pasa objetos del modelo a la vista

El presentador nunca le pasa a la vista un objeto del modelo, ni directamente ni dentro de otro (una lista, un record). Le pasa solo datos preparados para visualizar, como los records Picture.

Por qué: la vista no debe conocer el modelo ni poder tocarlo. Concreta, para el presentador, la regla de que los datos entre vista y modelo son inmutables y no comparten referencias (035).
