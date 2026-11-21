---
tags: [dam, acceso-a-datos, examen, indice]
---
# Acceso a Datos · Examen

Apuntes reordenados a partir de las diapositivas del profesor (Acceso a ficheros, Serializable y Gestión de excepciones).

## Mapa de temas
1. [[01 Ficheros y persistencia]]: qué es un fichero, tipos, acceso secuencial/aleatorio, CRUD
2. [[02 java.io y java.nio]]: File, streams, Readers/Writers, Buffered, Scanner, Path/Files
3. [[03 XML con JDOM2]]: estructura XML, DOM vs SAX, JDOM2 (crear, leer, modificar, eliminar)
4. [[04 XSLT y XPath]]: transformar XML y buscar con XPath (Jaxen)
5. [[05 JSON con Jackson]]: ObjectMapper, POJO, TypeReference, JsonNode, XmlMapper, anotaciones
6. [[06 Serialización]]: Serializable, ObjectOutputStream / ObjectInputStream
7. [[07 Gestión de excepciones]]: try/catch/finally, throw vs throws, checked vs unchecked
8. [[08 Preguntas de repaso]]: autoevaluación con respuestas ocultas
9. [[09 Plan de examen]]: lista oficial del profesor, teoría/práctica y qué priorizar (**empieza aquí**)
10. [[10 Test tipo examen]]: 53 preguntas tipo test (el examen es test)

## Leyenda
- ⚠️ **Trampa**: cosa que suele caer o error que hay en las diapositivas
- 🆕 **Extra**: no sale en las diapositivas; lo he añadido yo (compruébalo con lo que dé el profesor)

## Orden de estudio sugerido
Las notas 01 → 02 → 06 → 07 son la base de E/S y excepciones. Después 03 → 04 (XML) y 05 (JSON), que comparten la idea de **serializar/deserializar**. Termina con la 08 sin mirar las notas.

> [!tip] Idea central de todo el tema
> Todo es **persistencia**: sacar datos de la RAM a un fichero (escribir/serializar) y traerlos de vuelta (leer/deserializar). Lo que cambia es el **formato** (texto plano, XML, JSON, bytes de objeto) y la **librería** (java.io, JDOM2, Jackson).
