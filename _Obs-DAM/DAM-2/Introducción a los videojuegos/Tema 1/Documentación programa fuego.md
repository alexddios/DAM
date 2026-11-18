# Fuego — Animación de fuego en Java (Swing + MVP)  
  
Programa que simula y dibuja en tiempo real un efecto de **fuego procedural** (el clásico "fire effect" de la demoscene) en una ventana de 320 × 200 píxeles. Está estructurado siguiendo el patrón **Modelo–Vista–Presentador (MVP)** y usa **dos hilos** independientes: uno que calcula el fuego y otro que lo pinta.  
  
---  
  
## Índice  
  
1. [Requisitos y ejecución](#requisitos-y-ejecución)  
2. [Estructura del proyecto](#estructura-del-proyecto)  
3. [Arquitectura MVP](#arquitectura-mvp)  
4. [Flujo de arranque](#flujo-de-arranque)  
5. [El algoritmo del fuego](#el-algoritmo-del-fuego)  
6. [La paleta de colores](#la-paleta-de-colores)  
7. [Hilos y sincronización](#hilos-y-sincronización)  
8. [Referencia de clases](#referencia-de-clases)  
9. [Parámetros configurables](#parámetros-configurables)  
10. [Posibles mejoras](#posibles-mejoras)  
  
---  
  
## Requisitos y ejecución  
  
- **JDK 25** o superior. `Main.java` usa un *main de instancia* en una clase implícita (`void main() { ... }`, JEP 512), que no compila en versiones anteriores.  
- Solo usa la librería estándar (`javax.swing`, `java.awt`).  
  
Desde IntelliJ IDEA basta con ejecutar `Main.java`. Desde la terminal:  
  
```bash  
javac -d out src/Main.java src/model/*.java src/view/*.java src/presenter/*.javajava -cp out Main```  
  
Se abre una ventana titulada **"Fire Animation0"** con el fuego animado. Al cerrarla termina el programa.  
```
---  
## Estructura del proyecto  
src/  
├── Main.java                 Punto de entrada: crea Modelo, Vista y Presentador  
├── model/  
│   ├── IModel.java           Interfaz del modelo  
│   ├── Model.java            Modelo: bucle de simulación en su propio hilo  
│   ├── Fire.java             Matriz de temperaturas y algoritmo del fuego  
│   ├── Render.java           Convierte temperaturas en una imagen ARGB  
│   ├── Palette.java          Tabla temperatura → color (256 entradas)  
│   └── ColorTargetDTO.java   Punto de control de la paleta (temperatura + ARGB)  
├── presenter/  
│   ├── IPresenter.java       Interfaz del presentador  
│   └── Presenter.java        Une Modelo y Vista  
└── view/  
    ├── IView.java            Interfaz de la vista    ├── View.java             Ventana (JFrame) + hilo de refresco    └── Viewer.java           Canvas donde se pinta la imagen```  
  
---  
  
## Arquitectura MVP  
  
```mermaid
classDiagram
    direction LR

    class IModel {
        <<interface>>
        +update()
        +start()
        +getImage() BufferedImage
    }
    class IPresenter {
        <<interface>>
        +start()
        +getImage() BufferedImage
    }
    class IView {
        <<interface>>
        +setPresenter(IPresenter)
        +start()
    }

    class Model
    class Fire
    class Render
    class Palette
    class ColorTargetDTO
    class Presenter
    class View
    class Viewer

    IModel <|.. Model
    IPresenter <|.. Presenter
    IView <|.. View
    Runnable <|.. Model
    Runnable <|.. View
    Canvas <|-- Viewer

    Presenter --> IModel
    Presenter --> IView
    View --> IPresenter
    View *-- Viewer
    Model *-- Fire
    Model *-- Render
    Render --> Fire
    Render --> Palette
    Palette ..> ColorTargetDTO
```
  
| Capa | Responsabilidad |  
|------|-----------------|  
| **Modelo** (`model`) | Simula el fuego (`Fire`) y lo traduce a una imagen (`Render` + `Palette`). No sabe nada de Swing. |  
| **Presentador** (`presenter`) | Arranca el modelo y la vista y hace de intermediario: la vista le pide la imagen y él se la pide al modelo. |  
| **Vista** (`view`) | Muestra la ventana y repinta periódicamente la imagen que obtiene del presentador. No sabe nada del modelo. |  
  
La vista y el modelo **nunca se comunican directamente**: todo pasa por el presentador y por interfaces, lo que permite cambiar una capa sin tocar las demás.  
  
---  
  
## Flujo de arranque  
  
```mermaid  
sequenceDiagram  
    participant EDT as Main (hilo de Swing)    participant P as Presenter    participant M as Model    participant V as View    participant Vw as Viewer  
    EDT->>M: new Model()  (crea Fire, Palette y Render)    EDT->>V: new View()   (crea JFrame y Viewer)    EDT->>P: new Presenter(model, view)    EDT->>P: start()    P->>V: setPresenter(this)    P->>M: start()  → lanza hilo "Model-Fire"    P->>V: start()    V->>P: getImage()    P->>M: getImage()    V->>Vw: setPreferredSize + setImage    V->>V: pack() + setVisible(true)    V->>V: lanza hilo "fire-view"  
```  
  
1. `Main` crea todo dentro de `SwingUtilities.invokeLater`, es decir, en el **Event Dispatch Thread** de Swing.  
2. `Presenter.start()` registra el presentador en la vista, arranca el hilo del modelo y luego la vista.  
3. `View.start()` pide la primera imagen para saber el tamaño del canvas, ajusta la ventana con `pack()`, la muestra y arranca su propio hilo de refresco.  
  
---  
  
## El algoritmo del fuego  
  
El fuego se representa como una matriz `temperatures[height][width]` de enteros entre **0** (frío) y **255** (máximo calor). En cada paso del modelo (`Model.update()`) se ejecutan tres fases:  
  
### 1. Generación de chispas — `sparksGeneration()`  
En la **última fila** (la base del fuego), cada celda tiene un **40 %** de probabilidad de ponerse a 255.  
  
### 2. Generación de puntos fríos — `coolPointsGeneration()`  
En esa misma fila, cada celda tiene un **30 %** de probabilidad de ponerse a 0. Al mezclar chispas y puntos fríos la base queda irregular, y por eso las llamas tienen formas distintas.  
  
### 3. Propagación hacia arriba — `updateFlames()`  
Para cada celda que no esté en la última fila, su nueva temperatura es la media de ella misma y de las tres celdas que tiene debajo, menos un pequeño enfriamiento aleatorio:  
  
```  
            [ actual ]                fila y[ left ]   [ center ]   [ right ]     fila y+1  
```  
  
```java  
nueva = max(0, (actual + left + center + right) / 4 - random(0..1))  
```  
  
- En los bordes laterales se reutiliza la celda del borde (`Math.max(x-1, 0)` y `Math.min(x+1, width-1)`) para no salirse del array.  
- Al restar un valor aleatorio, el calor se pierde poco a poco al subir, así que las llamas se apagan con la altura.  
- Como se toma la media de las celdas vecinas, el calor también se reparte hacia los lados y las llamas se ven suaves.  
  
---  
  
## La paleta de colores  
  
`Palette` precalcula un array de **256 colores ARGB**, uno por temperatura, interpolando linealmente entre varios **puntos de control** (`ColorTargetDTO`):  
  
| Temperatura | Alpha | R | G | B | Color |  
|------------:|------:|----:|----:|----:|-------|  
| 0   | 0   | 0   | 0   | 0   | Transparente / negro |  
| 20  | 255 | 224 | 58  | 0   | Rojo anaranjado |  
| 80  | 255 | 255 | 208 | 32  | Amarillo |  
| 255 | 255 | 255 | 255 | 255 | Blanco |  
  
Entre cada par de puntos consecutivos `from → to`, para cada temperatura `t`:  
  
```  
ratio = (t - from.temperatura) / (to.temperatura - from.temperatura)   // 0..1  
canal = round(from.canal + (to.canal - from.canal) * ratio)  
```  
  
Así, en cada fotograma `Render` solo tiene que consultar `colors[temperatura]`, sin hacer cálculos de color.  
  
Para cambiar el aspecto del fuego (por ejemplo, un fuego azul) basta con modificar los puntos de `Palette.createPalette()`. El primer punto debe estar en la temperatura 0 y el último en la 255 para cubrir todo el array.  
  
---  
  
## Hilos y sincronización  
  
El programa usa tres hilos:  
  
| Hilo | Dónde se crea | Qué hace | Frecuencia |  
|------|---------------|----------|------------|  
| **EDT** (Swing) | `SwingUtilities.invokeLater` | Construye los objetos y ejecuta `Viewer.paint()` | Cuando Swing repinta |  
| **`Model-Fire`** | `Model.start()` | Bucle `update()` → `sleep(33 ms)` | ≈ 30 actualizaciones/s |  
| **`fire-view`** | `View.start()` | Bucle `presenter.getImage()` → `viewer.setImage()` → `sleep(16 ms)` | ≈ 60 refrescos/s |  
  
Los dos hilos propios son **daemon**, así que no impiden que la JVM termine al cerrar la ventana.  
  
Como varios hilos acceden a los mismos datos, se usan dos bloqueos:  
  
- **`synchronized (fire)`**  
  - `Model.update()` lo toma mientras modifica las temperaturas.  
  - `Render.getImage()` lo toma mientras las lee.  
  - Así nunca se convierte en imagen un fuego a medio actualizar.  
- **`synchronized (image)`**  
  - `Render.getImage()` lo toma mientras escribe los píxeles.  
  - `Viewer.paint()` lo toma mientras dibuja la imagen.  
  - Así el canvas nunca pinta una imagen a medio escribir.  
  
`Viewer` sobrescribe `update(Graphics)` para llamar directamente a `paint()`. De esta forma AWT no borra el fondo antes de cada repintado y se evita el parpadeo.  
  
---  
  
## Referencia de clases  
  
### `Main`  
Punto de entrada. Crea `Model`, `View` y `Presenter` en el hilo de Swing y llama a `presenter.start()`.  
  
### Paquete `model`  
  
#### `IModel`  
| Método | Descripción |  
|--------|-------------|  
| `void start()` | Arranca la simulación. |  
| `void update()` | Avanza un paso la simulación. |  
| `BufferedImage getImage()` | Devuelve la imagen actual del fuego. |  
  
#### `Model` (implementa `IModel`, `Runnable`)  
- Crea un `Fire` de **320 × 200** y un `Render` con la paleta por defecto.  
- `start()` lanza el hilo daemon `Model-Fire`.  
- `run()` llama a `update()` cada `frameMS = 33` ms hasta que el hilo se interrumpe.  
- `update()` ejecuta chispas → puntos fríos → propagación, todo dentro de `synchronized (fire)`.  
- `getImage()` delega en `render.getImage()`.  
  
#### `Fire`  
Guarda la matriz de temperaturas e implementa el algoritmo.  
  
| Miembro | Descripción |  
|---------|-------------|  
| `sparksPercentage = 40` | Probabilidad (%) de chispa en cada celda de la base. |  
| `coolPontsPercentage = 30` | Probabilidad (%) de punto frío en cada celda de la base. |  
| `randomizer = 1` | Enfriamiento aleatorio máximo por paso (se resta `0..randomizer`). |  
| `cooling = 1` | Declarado pero **no se usa** actualmente. |  
| `sparksGeneration()` | Pone chispas (255) en la última fila. |  
| `coolPointsGeneration()` | Pone puntos fríos (0) en la última fila. |  
| `updateFlames()` | Propaga el calor hacia arriba (ver algoritmo). |  
| `getTemperatures()`, `getWidth()`, `getHeight()` | Getters. |  
  
#### `Render`  
- Tiene una `BufferedImage` de tipo `TYPE_INT_ARGB` del mismo tamaño que el fuego.  
- `getImage()` recorre la matriz, pone cada píxel a `palette.color(temperatura)` y devuelve la imagen. Siempre devuelve **la misma instancia** y la reescribe en cada llamada.  
  
#### `Palette`  
- `Palette(List<ColorTargetDTO> targets)` construye la tabla de 256 colores interpolando entre los puntos de control.  
- `int color(int temperatura)` devuelve el color ARGB empaquetado en un `int`.  
- `static Palette createPalette()` crea la paleta por defecto (negro → rojo → amarillo → blanco).  
- `mix(v1, v2, ratio)` hace la interpolación lineal de un canal.  
  
#### `ColorTargetDTO`  
Objeto inmutable con `temperatura`, `alpha`, `red`, `green` y `blue`. Representa un punto de control de la paleta.  
  
### Paquete `presenter`  
  
#### `IPresenter`  
| Método | Descripción |  
|--------|-------------|  
| `void start()` | Arranca la aplicación. |  
| `BufferedImage getImage()` | Devuelve la imagen del modelo a la vista. |  
  
Tiene comentados `play()`, `pause()` y `stop()`, pensados para controlar la animación más adelante.  
  
#### `Presenter` (implementa `IPresenter`)  
- `start()`: `view.setPresenter(this)` → `model.start()` → `view.start()`.  
- `getImage()`: delega en `model.getImage()`.  
  
### Paquete `view`  
  
#### `IView`  
| Método | Descripción |  
|--------|-------------|  
| `void setPresenter(IPresenter)` | Inyecta el presentador. |  
| `void start()` | Muestra la ventana y arranca el refresco. |  
  
#### `View` (implementa `IView`, `Runnable`)  
- Crea un `JFrame` ("Fire Animation0", `EXIT_ON_CLOSE`) con un `Viewer` dentro.  
- `start()` usa la primera imagen para dimensionar el canvas, muestra la ventana y lanza el hilo `fire-view`.  
- `run()` pide la imagen al presentador y se la pasa al `Viewer` cada `FRAME_MS = 16` ms.  
  
#### `Viewer` (extiende `Canvas`)  
- `setImage(BufferedImage)` guarda la imagen y llama a `repaint()`.  
- `paint(Graphics)` dibuja la imagen escalada al tamaño del canvas, bloqueando la imagen mientras lo hace.  
- `update(Graphics)` llama directamente a `paint()` para evitar el parpadeo.  
  
---  
  
## Parámetros configurables  
  
| Parámetro | Clase | Valor | Efecto al aumentarlo |  
|-----------|-------|-------|----------------------|  
| `width`, `height` | `Model` | 320 × 200 | Más resolución (y más coste de CPU). |  
| `frameMS` | `Model` | 33 ms | Simulación más lenta. |  
| `FRAME_MS` | `View` | 16 ms | Menos refrescos por segundo. |  
| `sparksPercentage` | `Fire` | 40 % | Fuego más intenso y denso. |  
| `coolPontsPercentage` | `Fire` | 30 % | Base más irregular y fuego más débil. |  
| `randomizer` | `Fire` | 1 | Llamas más bajas (se enfrían antes). |  
| Puntos de control | `Palette.createPalette()` | negro → rojo → amarillo → blanco | Cambia los colores del fuego. |  
  
---  
  
## Posibles mejoras  
  
- Implementar `play()`, `pause()` y `stop()` (ya esbozados en `IPresenter`) con botones o teclas.  
- Usar el atributo `cooling` (ahora sin uso) o eliminarlo.  
- Corregir el nombre `coolPontsPercentage` → `coolPointsPercentage`.  
- Corregir el título de la ventana ("Fire Animation0").  
- Hacer que la ventana se pueda redimensionar manteniendo la proporción, o permitir escalar la imagen (por ejemplo ×2) para verla más grande.  
- Usar doble búfer (`BufferStrategy`) en el `Canvas` para un renderizado más fluido.