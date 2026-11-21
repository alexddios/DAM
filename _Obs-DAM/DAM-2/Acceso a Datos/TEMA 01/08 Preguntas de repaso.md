---
tags: [dam, acceso-a-datos, examen, repaso]
---
# 08 · Preguntas de repaso
← [[07 Gestión de excepciones]] · [[00 Índice]]

> [!info] Cómo usarlo
> Responde **en voz alta o por escrito antes de abrir** el desplegable (haz clic en la pregunta para ver la respuesta). Apunta las que falles y vuelve a la nota indicada.

## Ficheros y persistencia → [[01 Ficheros y persistencia]]

> [!question]- 1. ¿Por qué se llaman "datos persistentes" y dónde se guardan?
> Porque persisten más allá de la ejecución de la aplicación. Se guardan en ficheros en memoria secundaria (disco duro, pendrive...), no en la RAM.

> [!question]- 2. Diferencia entre fichero de texto y binario. ¿Un .docx con una línea de texto cuál es?
> Texto: solo caracteres, legible con un editor. Binario: no es solo texto y no se puede leer entero. Un .docx es binario, porque el procesador añade información binaria.

> [!question]- 3. ¿Cuándo se usan ficheros (p. ej. XML) en lugar de bases de datos?
> Cuando hay que intercambiar información entre plataformas, software o aplicaciones distintas (a veces se exporta una BBDD a XML para llevarla a otra).

> [!question]- 4. Acceso secuencial vs aleatorio: una ventaja y una desventaja de cada uno.
> Secuencial: simple y eficiente para procesar todo en orden (backups); lento y con bajas/modificaciones que obligan a rehacer el fichero. Aleatorio: rápido con registros concretos y modifica sin procesar todo; más complejo y puede dejar huecos.

> [!question]- 5. En acceso secuencial, ¿cómo se hace una baja o una modificación?
> Se copian todos los registros (sin el eliminado, o con los datos nuevos) y se regenera el fichero, sobrescribiendo el original.

> [!question]- 6. Baja lógica vs baja física.
> Lógica: se cambia el estado del registro a "no válido" (el dato sigue en el fichero). Física: el registro se elimina por completo.

> [!question]- 7. ¿Qué es la zona de excedentes?
> En acceso aleatorio, el área con espacios libres o no catalogados donde se insertan registros cuando su posición está ocupada y donde puede ir el espacio de las bajas.

## java.io / java.nio → [[02 java.io y java.nio]]

> [!question]- 8. ¿Qué diferencia hay entre java.io y java.nio?
> io: bloqueante, basado en streams. nio: no bloqueante, con buffers, channels y selectores (varios canales con un solo hilo).

> [!question]- 9. ¿Qué significa "buffer" en java.io y en java.nio?
> io: agrupación de operaciones de lectura/escritura para mejorar rendimiento (BufferedReader/Writer). nio: objeto explícito (ByteBuffer, CharBuffer) donde se guardan datos temporalmente, usado con un Channel.

> [!question]- 10. ¿Un objeto `File` es el fichero? ¿Qué pasa con `new File("datos.txt")` si no existe?
> No: representa un nombre/ruta, no el archivo. Si no existe, no se crea nada. `File` no escribe contenido.

> [!question]- 11. `mkdir()` vs `mkdirs()`; `list()` vs `listFiles()`.
> `mkdirs()` crea también los directorios superiores que falten. `list()` devuelve `String[]` con nombres; `listFiles()` devuelve `File[]`.

> [!question]- 12. Flujos de bytes vs de caracteres: clases base y para qué.
> Bytes (8 bits): `InputStream`/`OutputStream`, datos binarios. Caracteres (16 bits, Unicode): `Reader`/`Writer`, texto.

> [!question]- 13. ¿Cómo detectas el final del fichero con `FileReader` y con `BufferedReader`?
> `FileReader.read()` devuelve -1. `BufferedReader.readLine()` devuelve null.

> [!question]- 14. ¿Cómo evitas que `FileWriter` sobrescriba el fichero?
> Con el segundo parámetro `true` (append): `new FileWriter("fichero.txt", true)`.

> [!question]- 15. ¿Por qué usar BufferedWriter y qué aporta `newLine()`?
> Agrupa varias escrituras en una sola operación física (más rápido). `newLine()` escribe el separador de línea correcto para el SO.

> [!question]- 16. ¿Qué sustituye `Path` y qué hace `Files`?
> `Path` sustituye a `File` (ubicación de un fichero/directorio). `Files` crea, lee, escribe, copia, mueve y elimina ficheros y directorios.

## XML / JDOM2 → [[03 XML con JDOM2]]

> [!question]- 17. DOM vs SAX.
> DOM carga todo en memoria como árbol (visión global, más memoria). SAX lee secuencialmente por eventos (menos memoria, sin visión global).

> [!question]- 18. ¿Qué hacen `SAXBuilder` y `XMLOutputter`?
> `SAXBuilder` lee un XML y crea un `Document`. `XMLOutputter` convierte un `Document` en XML y lo escribe (con `Format.getPrettyFormat()` queda indentado).

> [!question]- 19. Pasos para crear un XML con JDOM2.
> Crear `Element` raíz → crear hijos con `setText`/`setAttribute`/`addContent` → `new Document(root)` → `XMLOutputter.output(document, new FileWriter(...))`.

> [!question]- 20. ¿Cómo modificas un valor de un XML existente y qué NO puedes olvidar?
> Leer con `SAXBuilder`, localizar el `Element`, usar `setText`/`setAttribute`, y guardar de nuevo con `XMLOutputter`. Sin guardar, el fichero no cambia.

> [!question]- 21. Dos formas de eliminar nodos en JDOM2 y por qué se usan.
> `raiz.removeContent(elemento)` seguido de `break`, o `Iterator` con `iterator.remove()`. Evitan modificar la lista mientras se recorre con for-each.

> [!question]- 22. Diferencias `org.w3c.dom` vs `org.jdom2` (construcción y navegación).
> w3c: `DocumentBuilder` y `getElementsByTagName()`. jdom2: `SAXBuilder` y `getChild()`/`getChildren()`.

## XSLT y XPath → [[04 XSLT y XPath]]

> [!question]- 23. ¿Qué entra y qué sale en una transformación XSLT?
> Entran un XML y una hoja de estilo XSLT. El procesador genera HTML, otro XML, texto, etc.

> [!question]- 24. ¿Qué selecciona `//title[@lang='en']`? ¿Y `/bookstore/book[1]`?
> Los `title` con atributo `lang="en"` en cualquier parte del documento. El primer `book` hijo de `bookstore`.

> [!question]- 25. Diferencia entre `/` y `//` en XPath.
> `/` va desde la raíz o separa nodos (camino directo). `//` selecciona nodos que coincidan sin importar dónde estén.

> [!question]- 26. Pasos para ejecutar XPath en JDOM2.
> `XPathFactory.instance().compile("expr", Filters.element())` (compilar) → `expr.evaluate(document)` (ejecutar, devuelve lista) → recorrer resultados. Necesita Jaxen.

## JSON / Jackson → [[05 JSON con Jackson]]

> [!question]- 27. ¿Qué hace `ObjectMapper` y qué es `XmlMapper`?
> `ObjectMapper` convierte objetos Java ↔ JSON. `XmlMapper` hereda de él pero trabaja con XML.

> [!question]- 28. ¿Para qué sirve `TypeReference`? ¿Por qué no basta `List.class`?
> Para indicar tipos genéricos completos (`List<Empleado>`). `List.class` solo dice que es una lista, sin saber que cada elemento debe ser un `Empleado`.

> [!question]- 29. Cómo obtienes JSON legible (indentado).
> `mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, objeto)`.

> [!question]- 30. `readValue` vs `readTree`.
> `readValue` deserializa a un tipo Java (Map, POJO, lista). `readTree` devuelve un `JsonNode` (árbol) sin necesidad de POJO.

> [!question]- 31. `get()` vs `path()` en `JsonNode`.
> `get` devuelve null si no existe el campo; `path` devuelve un `MissingNode`.

> [!question]- 32. ¿Cuándo `ObjectNode` y cuándo `ArrayNode`?
> `ObjectNode` si el JSON es un objeto `{ }`; `ArrayNode` si es un array `[ ]`.

> [!question]- 33. Para qué sirven `@JsonIgnore`, `@JsonProperty` y `@JsonInclude(NON_NULL)`.
> Excluir una propiedad; fijar el nombre en el JSON; omitir propiedades con valor null.

> [!question]- 34. Ventajas de Jackson frente a parsear el JSON a mano con `substring()`/`indexOf()`.
> Más simple y legible, más fiable (anidados, arrays, caracteres especiales), más mantenible y detecta errores de sintaxis.

## Serialización → [[06 Serialización]]

> [!question]- 35. ¿Qué es serializar? ¿Qué método tiene `Serializable`?
> Convertir un objeto en una secuencia de bytes para guardarlo o transmitirlo. `Serializable` es una interfaz sin métodos.

> [!question]- 36. ¿Qué clases se usan para guardar y para cargar un objeto?
> Guardar: `FileOutputStream` + `ObjectOutputStream` (`writeObject`). Cargar: `FileInputStream` + `ObjectInputStream` (`readObject`, con cast).

> [!question]- 37. ¿Qué excepciones captura la deserialización y por qué?
> `IOException` (E/S) y `ClassNotFoundException` (`readObject` puede no encontrar la clase del objeto).

> [!question]- 38. ¿Por qué suele serializarse una colección en lugar de objetos sueltos?
> Cada objeto se serializa a un fichero; metiendo todos en una estructura (ArrayList, Set, HashMap) se guarda y carga toda la aplicación de una vez.

## Excepciones → [[07 Gestión de excepciones]]

> [!question]- 39. ¿Qué pasa con una excepción que nadie captura?
> La captura el gestor por defecto, que muestra el error y detiene el programa.

> [!question]- 40. `throw` vs `throws`.
> `throw` lanza una excepción concreta dentro del método. `throws` declara en la cabecera qué excepciones puede lanzar el método.

> [!question]- 41. Checked vs unchecked: de qué extienden, y si obligan a `throws`/`try-catch`.
> Checked: `extends Exception`, Java obliga a declararlas con `throws` y tratarlas. Unchecked: `extends RuntimeException`, no hace falta declararlas.

> [!question]- 42. ¿Cuándo se ejecuta `finally` y para qué se usa?
> Siempre, haya excepción o no. Para cerrar ficheros y liberar recursos.

> [!question]- 43. ¿Por qué `catch (Exception e)` va el último?
> Porque captura cualquier excepción; las específicas deben gestionarse antes.

> [!question]- 44. Un método ¿puede tratar y propagar la misma excepción?
> No: no debe propagar las que trata y viceversa.

> [!question]- 45. ¿Dónde se debe gestionar una excepción?
> Donde se pueda tomar una decisión adecuada. Métodos intermedios propagan, `main` captura lo que quede, las capas especializadas gestionan las suyas. Si dudas, propágala.

## Encuentra el error
> [!bug]- A. ¿Por qué no compila?
> ```java
> BufferedWriter writer = new BufferedWriter("fichero.txt");
> ```
> El constructor necesita un `Writer`: `new BufferedWriter(new FileWriter("fichero.txt"))`.

> [!bug]- B. ¿Qué falla?
> ```java
> cadena = reader.readline();
> ```
> El método es `readLine()` (L mayúscula).

> [!bug]- C. ¿Qué falla?
> ```java
> void comprobarCodigo(int cod) {
>     if (cod <= 0) throw new MiExcepcion1("Código inválido");   // MiExcepcion1 extends Exception
> }
> ```
> Es checked: falta `throws MiExcepcion1` en la cabecera (o un try-catch dentro).

> [!bug]- D. ¿Qué pasa con el fichero?
> ```java
> List<Empleado> lista = mapper.readValue(f, ref);
> lista.add(new Empleado(60, "LOPEZ", 40, 1800.0));
> ```
> Nada: falta guardar. Hay que llamar a `mapper.writerWithDefaultPrettyPrinter().writeValue(f, lista)`.

> [!bug]- E. ¿Qué falla al leer el objeto?
> ```java
> Empleado e = entrada.readObject();
> ```
> `readObject()` devuelve `Object`: falta el cast `(Empleado)`.
