---
tags: [PMDM, UT2, expo-router, navegacion]
bloque: 1
---

# 01 · Expo Router y navegación

← [[2. MyPlaces]]

## Por qué
Una app no es una única pantalla: **lista de lugares → detalle de un lugar**.
Para eso hacen falta:
- **navegación** entre pantallas
- un **identificador** (`id`) para saber qué lugar mostrar
- que cada pantalla tenga **una responsabilidad clara**

## Rutas basadas en ficheros
En Expo Router, **la carpeta `app/` define las pantallas**: cada fichero es una ruta.

```text
app/
  _layout.jsx        // layout común (Stack, cabecera…)
  index.jsx          // "/"            → lista
  detall/
    [id].jsx         // "/detall/3"    → detalle (segmento dinámico)
```

| Fichero | Ruta | Notas |
|---|---|---|
| `index.jsx` | `/` | pantalla inicial |
| `detall/[id].jsx` | `/detall/:id` | los corchetes = **parámetro dinámico** |
| `_layout.jsx` | — | envuelve las pantallas hermanas; no es una ruta |

## Navegar con parámetros
```jsx
import { Link } from 'expo-router';

<Link href={{ pathname: '/detall/[id]', params: { id: '3' } }}>
  <Text>Veure lloc</Text>
</Link>
```

Para **leer** el parámetro en la pantalla de detalle:
```jsx
import { useLocalSearchParams } from 'expo-router';

const { id } = useLocalSearchParams();   // llega como string
```

> [!warning] Errores típicos
> - La ruta del `href` **tiene que coincidir con un fichero que exista**.
> - Los parámetros llegan como **texto**: si tus `id` son números, ojo al comparar (`'3' !== 3`).

## Volver atrás / navegar por código
```jsx
import { router } from 'expo-router';

router.back();            // volver
router.push('/');         // ir a una ruta
```

## Microejercicio 1 · Navegación (20–30 min)
- Lista + detalle; cada tarjeta abre su detalle.
- Botón para volver atrás.
- *Extensión*: nombre del lugar en la cabecera.

#expo-router #navegacion
