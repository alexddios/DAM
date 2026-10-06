# Modelo Vista Presentador (MVP)
El Modelo-Vista-Presentador (MVP) es un patrón de diseño de software que separa la interfaz gráfica de usuario de la lógica de la aplicación y de los datos.
## Componentes Fundamentales
- **Modelo(Model):**  Maneja los datos, las reglas de negocio y el acceso a la base de datos. Funciona de forma totalmente independiente y no conece a la vista ni al presentador.
- **Vista (View):**  Muestra la interfaz gráfica y captura las acciones del usuario (como clics o textos escritos). Envía los eventos al presentador y pinta los datos que este le indica.
- **Presentador(Presenter):**  Actúa como el intermediario (o puente) entre el modelo y la vista. Recibe los eventos de la vista, pide los datos necesarios al modelo y ordena a la vista que debe mostrar.
## Ventajas principales
- **Desacoplamiento total:**  La vista y el modelo no se conocen entre sí; toda la comunicación pasa por el presentador.
- **Facilidad de pruebas (Testing):** Permite probar la lógica de la presentación de forma aislada sin necesidad de levantar la interfaz gráfica.
- **Mantenimiento sencillo:** Al dividir responsabilidades en tres capas claras, el código es mas limpio y fácil de escalar o corregir.

## Ejemplo práctico: Una pantalla de inicio de sesión en una app móvil

- **Modelo:** El código que verifica en el servidor si el usuario y la contraseña son correctos. 

- **Vista:** La pantalla del móvil con el botón "Entrar" y dos casillas de texto. Al hacer clic, la vista solo dice: _"Usuario, presionaron el botón"_.

- **Presentador:** Recibe el aviso, llama al Modelo para verificar la contraseña y, si es correcta, le ordena a la Vista: _"Muestra el mensaje de bienvenida"_. Si falla, le dice: _"Muestra error"_.
# Modelo Vista Controlador(MVC)
El Modelo-Vista-Controlador (MVC) es un patrón de arquitectura de software que separa una aplicación en tres componentes principales para dividir las responsabilidades y ordenar el código.
## Componentes del MVC
- **Modelo:** Gestiona los datos, las reglas del negocio y la comunicación directa con la base de datos.
- **Vista:** Muestra la interfaz gráfica y los elementos visuales al usuario para presentar la información.
- **Controlador:** Actúa como el intermediario que recibe las solicitudes del usuario, consulta el modelo y selecciona la vista adecuada para responder.
## Cómo funciona el flujo
1. El usuario realiza una acción o petición (como entrar a una URL o enviar un formulario).
2. El controlador recibe esa petición y pide los datos necesarios al modelo.
3. El modelo procesa la información y se la devuelve al controlador.
4. El controlador envía esos datos a la vista, la cual genera el formato final (como HTML) para mostrarlo en pantalla al usuario.
## Ventajas principales
- **Organización:** Permite mantener el código ordenado al separar la parte visual de los datos.
- **Mantenimiento:** Facilita corregir errores o actualizar funciones en una capa sin romper las demás.
- **Trabajo en equipo:** Permite que varios programadores trabajen de manera simultánea en el diseño visual y en la lógica interna.

## Ejemplo práctico: Una app web de una tienda

- **Modelo:** La base de datos con los precios y nombres de los productos (ej. zapato, precio: $50).

- **Controlador:** Recibe el clic del usuario en "Comprar producto", calcula el total y pide los datos al Modelo.

- **Vista:** La página HTML que ve el usuario en su navegador con el botón de compra y el precio ya impreso.