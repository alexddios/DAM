---
tags: [dam, acceso-a-datos, examen, ficheros]
---
# 01 · Ficheros y persistencia
← [[00 Índice]] · Siguiente: [[02 java.io y java.nio]]

## Persistencia de datos
- La **RAM** (memoria principal, de ejecución) se pierde al apagar.
- Los **ficheros** se guardan en memoria **secundaria** (disco duro, DVD, pendrive): no ejecutan ni procesan, **guardan**.
- **Datos persistentes** = los que persisten más allá de la ejecución de la aplicación.

## Tipos de ficheros
| Tipo | Qué es | Extensiones típicas |
|---|---|---|
| **Texto** | Solo caracteres de texto; se lee con un editor | `.txt` `.xml` `.json` `.props` `.conf` `.sql` `.srt` |
| **Binario** | No es solo texto, no se puede leer entero con un editor | `.bin` `.dat` `.pdf` `.jpg` `.doc/.docx` `.avi` `.ppt/.pptx` |

- `.bin` / `.dat`: formato binario poco difundido; solo la aplicación que lo creó sabe leerlo/escribirlo.
- Un `.docx` con solo un poco de texto sigue siendo **binario** (el procesador añade información binaria).

## Uso actual: ficheros estructurados
- Hoy no se guarda en texto plano "a pelo", sino **estructurado** o con **lenguaje de marcas**. El más extendido: **XML**.
- Se usan ficheros en vez de BBDD para **intercambiar información entre plataformas/aplicaciones** (a veces se exporta de una BBDD a XML para llevarlo a otra).

### XML
- **eXtensible Markup Language**, especificación del **W3C**.
- Estructura **jerárquica**; cada elemento tiene etiqueta de apertura y cierre; puede contener otros elementos o texto.

```xml
<student>
  <id>123</id>
  <name>John</name>
  <email>johndoe@dominio.com</email>
</student>
```

### JSON
- **JavaScript Object Notation**. Formato **ligero** de intercambio de datos (apps y servicios web).
- Objetos `{ }` con pares **clave-valor**; arrays `[ ]`; se pueden anidar.
- Valores posibles: string, número, booleano (`true/false`), array, `null`, otro objeto.

```json
{ "student": { "id": 123, "name": "John", "subjects": ["Database", "Programming"] } }
```

## Forma de acceso
| | **Secuencial** | **Directo / aleatorio** |
|---|---|---|
| Lectura | En orden; para llegar a un dato hay que leer todos los anteriores | Salto directo a cualquier registro |
| Escritura | Solo a partir del último dato (no se inserta en medio) | Registros de **tamaño conocido**; se mueve entre ellos |
| Ventaja | Simple y eficiente para procesar **todo** en orden (p. ej. **backups**) | Rápido con registros concretos; modifica sin procesar todo el fichero |
| Desventaja | Lento; bajas y modificaciones obligan a **rehacer el fichero** | Más complejo; pueden quedar **huecos** |

> [!note] Matiz de las diapositivas
> Para el programa el acceso es "directo a la información deseada", pero para el dispositivo de memoria es "aleatorio" o impredecible.

## Operaciones básicas
**Sobre el fichero:** creación (una sola vez) · apertura · cierre (normalmente la última instrucción) · lectura (fichero → memoria principal, a variables) · escritura (programa → fichero).

**Sobre los datos = CRUD** (igual que en BBDD):

| Operación | Significado |
|---|---|
| **Altas** (Create) | Añadir registro |
| **Consultas** (Read) | Buscar un registro |
| **Modificaciones** (Update) | Cambiar datos de un registro |
| **Bajas** (Delete) | Eliminar: **lógica** (cambia el estado del registro) o **física** (se borra del todo) |

### CRUD según el tipo de acceso
**Secuencial**
- Altas: solo **al final**, tras el último registro.
- Bajas: se copian todos los registros que se quedan y se **regenera el fichero**, sobrescribiendo el original.
- Modificaciones: igual que bajas (se copia todo con los nuevos datos).
- Consultas: lectura secuencial desde el principio hasta encontrarlo.

**Aleatorio** (direcciones relativas; hace falta la **clave** o una **función de conversión** para calcular la posición)
- Altas: si la posición está ocupada, va a la **zona de excedentes** (huecos libres o no catalogados).
- Bajas: normalmente **lógicas** (estado a no válido, como "descatalogado"); su espacio puede pasar a la zona de excedentes.
- Modificaciones: se localiza y se cambian solo los campos necesarios.
- Consultas: se calcula la dirección con la clave/función.

> [!warning] ⚠️ Pregunta típica
> "¿Por qué en acceso secuencial una baja obliga a reescribir el fichero?" → Porque no se pueden dejar huecos ni insertar/borrar en medio: se copian los registros que se quedan a un fichero nuevo que sobrescribe el original.

## Entrada/Salida (E/S, I/O)
Operaciones que forman un flujo de información entre el programa y el exterior. En Java las da el paquete `java.io` (y `java.nio`), con clases para:
- Leer entradas desde un flujo de datos
- Escribir a un flujo de datos
- Operar con ficheros del sistema local (crear, abrir, leer, escribir, copiar, mover, eliminar archivos y directorios)
- Gestionar la **serialización** de objetos → [[06 Serialización]]
