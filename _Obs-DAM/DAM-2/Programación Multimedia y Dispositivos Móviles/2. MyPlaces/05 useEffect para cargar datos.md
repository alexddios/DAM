---
tags: [PMDM, UT2, useEffect, hooks, async]
bloque: 2
---

# 05 · `useEffect` para cargar datos

← [[2. MyPlaces]] · anterior: [[04 Persistencia con AsyncStorage]]

## Idea
Un **efecto** es código que se ejecuta **después de renderizar**. Sirve para cosas «de fuera» de React: leer almacenamiento, peticiones, sensores…

```jsx
useEffect(() => {
  // qué hacer
}, [dependencias]);
```

| Dependencias | Cuándo se ejecuta |
|---|---|
| `[]` | **una vez**, al montar la pantalla |
| `[llocs]` | al montar **y** cada vez que cambie `llocs` |
| *(sin array)* | después de **cada** render (casi nunca lo quieres) |

## Cargar al montar (de la diapositiva)
```jsx
useEffect(() => {
  async function carregar() {
    const text = await AsyncStorage.getItem('llocs');
    setLlocs(text ? JSON.parse(text) : []);
  }
  carregar();
}, []);
```

> [!danger] No pongas `async` directamente en el callback
> ```jsx
> useEffect(async () => { ... }, []);   // ❌
> ```
> Una función `async` siempre devuelve una **Promise**, y `useEffect` espera que devuelvas *nada* o una **función de limpieza**.
> ✅ Solución: define una función `async` **dentro** y llámala.

## Función de limpieza (adelanto del bloque de sensores)
```jsx
useEffect(() => {
  const sub = algo.subscribe();
  return () => sub.remove();   // se ejecuta al desmontar
}, []);
```

## Asincronía y estado
- Mientras `carregar()` no termina, la pantalla ya se ha pintado con el **estado inicial** (p. ej. `[]`).
- ¿Quieres mostrar «Carregant…» mientras tanto? → otro estado (`carregant`, `true/false`).
- `setLlocs(...)` **no cambia `llocs` al instante**: el nuevo valor se ve en el siguiente render.

> [!question] Para pensar antes del ME3
> Si usas un `useEffect` con `[llocs]` para guardar automáticamente cada vez que cambia la lista…
> **¿qué valor tiene `llocs` en el primer render, antes de que termine la carga?** ¿Qué podría acabar guardándose?

Siguiente: [[06 Preparación Microejercicio 3]]
