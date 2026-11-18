---
tags: [PMDM, UT2, persistencia, AsyncStorage, JSON]
bloque: 2
criterio_RA2: f
---

# 04 · Persistencia con AsyncStorage

← [[2. MyPlaces]]

## Datos en memoria ≠ persistencia
- `useState` mantiene los datos **mientras vive el componente**.
- Si la app se reinicia → **se pierden**.
- Hace falta un **almacén persistente** en el dispositivo.

## ¿Qué es AsyncStorage?
Almacén **clave → valor** donde **todo se guarda como texto**.

| ✅ Sirve para | ❌ No es |
|---|---|
| datos pequeños | una base de datos relacional |
| datos **no sensibles** | un almacén seguro de secretos (tokens, contraseñas) |
| listas, preferencias, favoritos | algo para consultas complejas |

### Instalación
```bash
npx expo install @react-native-async-storage/async-storage
```
```js
import AsyncStorage from '@react-native-async-storage/async-storage';
```
> La importación depende del paquete instalado (lo dice la diapositiva): comprueba el `package.json` del proyecto base.

## Objeto/array ⇄ texto: JSON
AsyncStorage solo entiende **strings**, así que:

| Dirección | Función | Ejemplo |
|---|---|---|
| guardar | `JSON.stringify(valor)` | `[{id:1}]` → `'[{"id":1}]'` |
| recuperar | `JSON.parse(texto)` | `'[{"id":1}]'` → `[{id:1}]` |

## Las operaciones (todas son **asíncronas** → devuelven Promise)
| Método | Qué hace | Devuelve |
|---|---|---|
| `setItem(clau, text)` | guarda / sobrescribe | — |
| `getItem(clau)` | lee | el texto **o `null` si no existe** |
| `removeItem(clau)` | borra esa clave | — |
| `clear()` | borra **todo** el almacén de la app | — |

```js
// Guardar
await AsyncStorage.setItem('llocs', JSON.stringify(llocs));

// Recuperar
const text = await AsyncStorage.getItem('llocs');
const llocsDesats = text ? JSON.parse(text) : [];
```

> [!important] `text ? JSON.parse(text) : []`
> La primera vez que se abre la app **no hay nada guardado** → `getItem` devuelve `null`.
> Sin ese ternario, la lista sería `null` y un `.map()` haría petar la pantalla.

## Errores: `try / catch`
Leer/escribir puede fallar (almacén lleno, JSON corrupto…).
```js
try {
  // operación con await
} catch (error) {
  console.log('Error de persistència', error);
  // ¿mostrar un Alert al usuario?
}
```

> [!warning] Trampas habituales
> - Olvidar el `await` → trabajas con una **Promise**, no con el dato.
> - Guardar el array sin `stringify` → se guarda `"[object Object]"`.
> - `setItem` **sobrescribe** el valor entero de la clave: guardas **la lista completa**, no «un elemento más».
> - Usar claves distintas para guardar y leer (`'llocs'` vs `'lloc'`).

Siguiente: [[05 useEffect para cargar datos]]
