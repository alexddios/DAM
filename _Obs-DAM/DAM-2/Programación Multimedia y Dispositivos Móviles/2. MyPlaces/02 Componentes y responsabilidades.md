---
tags: [PMDM, UT2, componentes, arquitectura]
bloque: 1
---

# 02 · Componentes y responsabilidades

← [[2. MyPlaces]]

> [!quote] Objetivo
> Evitar un `App.jsx` de 600 líneas.

| Pieza | Responsabilidad | Ejemplo en MyPlaces |
|---|---|---|
| **Pantalla** | coordina **datos y navegación** | `app/index.jsx` (lista) |
| **Componente** | muestra una **pieza reutilizable** | `TargetaLloc.jsx` |
| **Función auxiliar** | **transforma o valida** datos | `validarLloc()` |

- **No se duplican** tarjetas ni formularios: si lo usas dos veces, es un componente.
- Los datos fluyen **de la pantalla hacia los componentes por props**.
- Las acciones (pulsar, borrar…) suben **con funciones pasadas por props** (`onPress`, `onEliminar`…).

```jsx
// La pantalla tiene el estado; el componente solo pinta y avisa
<TargetaLloc lloc={lloc} onEliminar={() => /* la pantalla decide */} />
```

> [!tip] Pensando en el ME3
> Si cada tarjeta necesita un botón «eliminar», **¿quién debería saber cómo se borra y se guarda?** ¿La tarjeta o la pantalla que tiene la lista?

Relacionado: [[03 Formularios y validación]] · [[04 Persistencia con AsyncStorage]]
