---
tags: [dam, acceso-a-datos, examen, plan]
---
# 09 · Plan de examen (lista oficial del profesor)
← [[00 Índice]]

Lista de temas enviada por el profesor, con el tipo de pregunta y dónde estudiarlo.

| # | Tema | Tipo | Nota |
|---|---|---|---|
| 1 | Persistencia de datos | 📖 Teoría | [[01 Ficheros y persistencia]] |
| 2 | Ficheros de texto y binarios | 📖 Teoría | [[01 Ficheros y persistencia]] |
| 3 | Forma de acceso (secuencial y directo/aleatorio) | 📖 Teoría | [[01 Ficheros y persistencia]] |
| 4 | Operaciones con ficheros (y su contenido, con CRUD) | 📖 Teoría | [[01 Ficheros y persistencia]] |
| 5 | Librerías java.io y java.nio | 📖 Teoría + 💻 Práctica | [[02 java.io y java.nio]] |
| 6 | Clase File | 📖 Teoría + 💻 Práctica | [[02 java.io y java.nio]] |
| 7 | Streams | 📖 Teoría + 💻 Práctica | [[02 java.io y java.nio]] |
| 8 | XML: DOM y SAX | 📖 Teoría | [[03 XML con JDOM2]] (sección DOM vs SAX) |
| 9 | XML: JDOM2 | 💻 Práctica | [[03 XML con JDOM2]] |
| 10 | XPath | 📖 Teoría + 💻 Práctica | [[04 XSLT y XPath]] (sección XPath) |
| 11 | Jaxen (compile, evaluate) | 💻 Práctica | [[04 XSLT y XPath]] (sección Jaxen) |
| 12 | Serialización y deserialización (presentación "vuelta a java.io") | 📖 Teoría + 💻 Práctica | [[06 Serialización]] |
| 13 | JSON: Jackson | 💻 Práctica | [[05 JSON con Jackson]] |
| 14 | Gestión de excepciones | 📖 Teoría + 💻 Práctica | [[07 Gestión de excepciones]] |

## Qué priorizar
> [!warning] Fuera de la lista
> **XSLT** no aparece en la lista del profesor. Déjalo para el final, solo si te sobra tiempo. (La nota 04 incluye XSLT, pero céntrate en las secciones **XPath** y **Jaxen**.)
>
> De Jackson, la lista solo dice "Práctica": céntrate en `ObjectMapper`, `readValue`, `writeValue`, `TypeReference`, el CRUD leer → cambiar → guardar y `JsonNode`/`ObjectNode`/`ArrayNode`. `XmlMapper` y las anotaciones tienen menos prioridad.

### Solo teoría (se estudia leyendo y contestando preguntas)
Temas 1 a 4 y 8. Memoriza definiciones, tablas comparativas (texto vs binario, secuencial vs aleatorio, DOM vs SAX) y el CRUD según el tipo de acceso.

### Con práctica (examen TIPO TEST)
> [!info] El examen es tipo test
> "Práctica" = **reconocer y entender código**, no escribirlo. Te preguntarán cosas como: qué clase/método hace X, cuál es el orden correcto de pasos, qué imprime o devuelve un fragmento, qué línea no compila, qué falta para que el cambio llegue al fichero, o qué expresión XPath selecciona qué.

Temas 5 a 7, 9 a 14. Qué debes saber **reconocer**:
- `BufferedReader`/`BufferedWriter`/`FileWriter` (¿`readLine()` devuelve `null`? ¿`read()` devuelve -1? ¿append con `true`?) y qué envuelve a qué
- JDOM2: qué hace cada clase (`SAXBuilder`, `XMLOutputter`, `Element`...) y cada método (`getChild`, `getChildText`, `setAttribute`, `addContent`, `removeContent`)
- XPath: leer una expresión y saber qué selecciona; Jaxen: `compile` → `evaluate` y el filtro
- Serialización: `writeObject`/`readObject`, el cast y las dos excepciones
- Jackson: `readValue`, `writeValue`, `TypeReference`, `readTree`, `JsonNode`/`ObjectNode`/`ArrayNode`, pretty printer, y que **sin guardar no cambia el fichero**
- Excepciones: `throw` vs `throws`, checked vs unchecked, orden de `catch`, qué se ejecuta en `finally`
- **Las trampas ⚠️** de las notas son justo el tipo de cosa que se usa para las opciones falsas del test

Practica con [[10 Test tipo examen]].

## Orden de estudio recomendado
1. Teoría pura (temas 1 a 4, 8) → [[01 Ficheros y persistencia]]
2. [[02 java.io y java.nio]] → [[06 Serialización]] → [[07 Gestión de excepciones]]
3. [[03 XML con JDOM2]] → XPath y Jaxen de [[04 XSLT y XPath]]
4. [[05 JSON con Jackson]]
5. [[10 Test tipo examen]] (cronométrate) y después [[08 Preguntas de repaso]] sin mirar las notas
