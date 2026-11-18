---
tags: [PMDM, UT2, formularios, validacion, TextInput]
bloque: 1
---

# 03 · Formularios y validación

← [[2. MyPlaces]]

## Entrada de datos = `TextInput` + estado
Input **controlado**: el valor vive en el estado y el input solo lo refleja.

```jsx
const [nom, setNom] = useState('');

<TextInput
  value={nom}
  onChangeText={setNom}
  placeholder='Nom del lloc'
/>
```

## Validar antes de guardar
> El usuario también se puede equivocar.

| Caso | Qué hacer |
|---|---|
| Nombre vacío | mostrar un mensaje |
| Espacios al principio/final | `nom.trim()` |
| Información incorrecta | **no** se guarda |
| Confirmación | `Alert.alert(...)` cuando tenga sentido |

```jsx
import { Alert } from 'react-native';

Alert.alert('Títol', 'Missatge');
// Con botones:
Alert.alert('Eliminar', 'Segur?', [
  { text: 'Cancel·la', style: 'cancel' },
  { text: 'Elimina', style: 'destructive', onPress: () => {/* ... */} },
]);
```

> [!warning] `trim()` y la validación
> `'   '` no está vacío, pero `'   '.trim()` sí → valida **siempre sobre el valor recortado**.

## Microejercicio 2 · Formulario (30–40 min)
- Pantalla «Afegir lloc» con nombre y descripción.
- Impedir guardar si el nombre está vacío.
- Aviso + volver a la lista tras añadir.
- *Extensión*: categoría seleccionable.

> [!note] Problema que deja abierto el ME2
> Los lugares añadidos viven en `useState` → **al cerrar la app desaparecen**. Eso es justo lo que resuelve el [[06 Preparación Microejercicio 3|ME3]].

Relacionado: [[04 Persistencia con AsyncStorage]]
