---
tags: [PMDM, UT2, microejercicio, persistencia]
microejercicio: 3
tiempo: 35–45 min
estado: pendiente
---

# 06 · Preparación Microejercicio 3 · Persistencia

← [[2. MyPlaces]]

> [!example] Enunciado
> 1. Haz que los lugares **se conserven** después de cerrar y volver a abrir la app.
> 2. Añade una acción para **eliminar** un lugar.
> 3. Comprueba **qué pasa si todavía no hay datos guardados**.

> [!info] Esta nota no tiene la solución
> Son preguntas guía para que lo montes tú. Repasa primero [[04 Persistencia con AsyncStorage]] y [[05 useEffect para cargar datos]].

## Antes de tocar código
- [ ] ¿Está instalado AsyncStorage en el proyecto? (`package.json`)
- [ ] ¿**Dónde** vive ahora mismo el array de lugares? ¿En qué pantalla / en qué estado?
- [ ] ¿La pantalla «Afegir lloc» y la lista **comparten** ese estado o cada una tiene el suyo?
- [ ] ¿Qué **clave** vas a usar? Escríbela aquí: `________`

## Parte 1 · Guardar y recuperar
**Preguntas guía**
1. ¿**En qué momento** hay que *leer* del almacén? ¿Una vez o muchas?
2. ¿**En qué momentos** cambia la lista? (añadir, eliminar…) → en cada uno, ¿qué tiene que pasar con el almacén?
3. `setItem` sobrescribe: ¿guardas el lugar nuevo **o la lista entera ya actualizada**?
4. Si guardas justo después de `setLlocs(nova)`, ¿qué variable tiene el valor bueno: `llocs` o `nova`? (pista: [[05 useEffect para cargar datos#Asincronía y estado]])
5. ¿Dónde pondrías el `try/catch`?

## Parte 2 · Eliminar
**Preguntas guía**
1. ¿Cómo identificas **cuál** lugar borrar? (¿tienen todos un `id` único? ¿cómo lo generas al añadir?)
2. ¿Qué método de array te da **una lista nueva sin un elemento**, sin mutar la original?
3. Después de quitarlo del estado, ¿qué falta para que el borrado **sobreviva a un reinicio**?
4. ¿El botón va en la tarjeta, en el detalle o en ambos? ¿Quién ejecuta la lógica? (ver [[02 Componentes y responsabilidades]])
5. ¿Tiene sentido un `Alert.alert` de confirmación aquí? (ver [[03 Formularios y validación]])

## Parte 3 · Sin datos guardados
**Preguntas guía**
1. ¿Qué devuelve `getItem` la primera vez?
2. ¿Qué pasaría si haces `JSON.parse` de eso, o un `.map()` sobre el resultado?
3. ¿Qué ve el usuario con la lista vacía? ¿Una pantalla en blanco o un mensaje tipo «Encara no hi ha llocs»? (`FlatList` tiene una prop para esto: búscala 👀)

## Cómo comprobar que funciona
| # | Caso | Esperado | Obtenido |
|---|---|---|---|
| 1 | Primera ejecución (sin datos) | lista vacía + mensaje, sin error | |
| 2 | Añado 2 lugares, cierro y reabro | los 2 siguen | |
| 3 | Elimino 1, cierro y reabro | solo queda 1 | |
| 4 | Elimino todos, reabro | lista vacía, sin error | |

> [!tip] Cerrar la app «de verdad»
> Un *reload* de Expo no siempre es lo mismo que cerrar la app. Prueba a **matar la app** desde el multitarea del móvil/emulador y abrirla otra vez.
> Para empezar de cero durante las pruebas puedes borrar la clave (`removeItem`) temporalmente.

## Notas mientras lo hago
- 
