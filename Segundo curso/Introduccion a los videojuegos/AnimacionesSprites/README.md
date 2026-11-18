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
   git clone <url-del-repositorio>
   ```
2. En IntelliJ: **File → Open** y elige la carpeta `AnimacionesSprites`.
3. Si pide un SDK, selecciona tu JDK 26 en **File → Project Structure → Project → SDK**.
4. Ejecuta `src/Main.java`.

Las imágenes se cargan con rutas relativas (`res/...`), así que el programa debe ejecutarse **desde la carpeta del proyecto**. Es lo que hace IntelliJ por defecto.

## Estructura

| Paquete | Qué contiene |
|---|---|
| `model` | `Modelo`, `SpriteSheet` (carga y recorta la hoja) y `SpriteAnimation` (hilo que avanza de frame) |
| `presenter` | `Presenter`: carga las hojas, crea las animaciones y conecta vista y modelo |
| `view` | `View` (ventana con su hilo de repintado), `ViewPanel` (lienzo con doble buffer) y `ControlPanel` (botones Iniciar / Parar) |
| `res` | Las hojas de sprites en PNG |
