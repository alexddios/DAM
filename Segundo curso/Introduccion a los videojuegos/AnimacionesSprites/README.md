# AnimacionesSprites

Animación de varias hojas de sprites en Java (Swing), con arquitectura **MVP**.
Práctica del Tema 2 de *Introducción a los videojuegos* (2º DAM).

Cada animación tiene su propio hilo y avanza de frame a su ritmo; la vista repinta unas 60 veces por segundo en una rejilla de 5 × 2.

## Requisitos

- **JDK 26** (el proyecto está configurado con `openjdk-26`).
- IntelliJ IDEA.

## Abrirlo y ejecutarlo

1. Clona el repositorio:
   ```bash
   git clone https://github.com/alexddios/AnimacionesSprites.git
   ```
2. En IntelliJ: **File → Open** y elige la carpeta `AnimacionesSprites`.
3. Si pide un SDK, selecciona tu JDK 26 en **File → Project Structure → Project → SDK**.
4. Ejecuta `src/Main.java`.

Las imágenes se cargan con rutas relativas (`res/...`), así que el programa debe ejecutarse **desde la carpeta del proyecto**. Es lo que hace IntelliJ por defecto.

## Estructura

La idea principal: **el modelo solo maneja números; las imágenes solo existen en la vista.**

| Paquete | Qué contiene |
|---|---|
| `assets` | `Catalogo` (id → datos de cada hoja) y `HojaInfo` (ruta, columnas, filas y pasos). Solo datos, sin imágenes |
| `model` | `Modelo` y `SpriteAnimation`: cada animación guarda el id de su hoja, sus pasos y su frame actual, y tiene un hilo que avanza de frame |
| `presenter` | `Presenter`: crea las animaciones, conecta vista y modelo y le pasa a la vista un `AnimacionDTO` (hoja y frame) por animación |
| `view` | `View` (ventana, hilo de repintado y botones), `SpriteSheet` (carga y recorta cada hoja), `ViewPanel` (lienzo con doble buffer) y `ControlPanel` (botones Iniciar / Parar) |
| `res` | Las hojas de sprites en PNG |

`Main` crea el `Catalogo`, registra las hojas y se lo pasa al modelo y a la vista (inyección de dependencias).
