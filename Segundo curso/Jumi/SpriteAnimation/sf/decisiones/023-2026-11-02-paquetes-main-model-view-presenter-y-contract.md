# Paquetes main, model, view, presenter y contract

- **Origen:** SpriteAnimation, 2026-11-02, al repasar el código de la demo para los alumnos. El código estaba sin paquete.
- **Contexto:** el propietario pidió paquetes para main, view, model y presenter. Faltaba decidir dónde va el contrato `AnimationContract`, con las interfaces `Model`, `View` y `Presenter`, que usan las tres partes.
- **Opciones:** para el contrato, A, paquete propio `contract`; B, en `presenter`; C, en `main`.
- **Decisión:** cinco paquetes. `main` con `SpriteAnimation`; `model` con `Model`, `Sprite` y `SpriteSheet`; `view` con `View` y `Viewer`; `presenter` con `Presenter`; y `contract` con `AnimationContract`, porque lo comparten las tres partes y ninguna es su dueña. Se hace en una tarea aparte, después de probar y cerrar el repaso del código para los alumnos.
- **Consecuencias:** el código del documento del MVP lleva `package` e `import` donde haga falta, y los scripts, las tareas de VS Code y las pruebas se ajustan a los paquetes.
