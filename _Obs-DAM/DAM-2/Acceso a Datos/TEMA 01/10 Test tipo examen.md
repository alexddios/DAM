---
tags: [dam, acceso-a-datos, examen, test]
---
# 10 · Test tipo examen
← [[09 Plan de examen]] · [[00 Índice]]

> [!info] Cómo usarlo
> 53 preguntas con 4 opciones. Anota tus respuestas en papel y **luego** abre cada desplegable para corregir. Las que falles, vuelve a la nota indicada. Las preguntas son mías, basadas en las diapositivas: sirven para practicar el estilo, no son las del examen.

## Teoría: persistencia, ficheros y acceso → [[01 Ficheros y persistencia]]

> [!question] 1. ¿Qué son los datos persistentes?
> - a) Los que se guardan en la memoria RAM
> - b) Los que persisten más allá de la ejecución de la aplicación que los trata
> - c) Solo los que están en una base de datos
> - d) Los que se guardan en la caché del procesador
>
> > [!success]- Respuesta
> > **b**. Se guardan en ficheros en memoria secundaria; la RAM se pierde al apagar.

> [!question] 2. ¿Cuál de estos es un fichero binario?
> - a) `.json`
> - b) `.sql`
> - c) `.docx`
> - d) `.xml`
>
> > [!success]- Respuesta
> > **c**. Los demás son de texto. Un `.docx` es binario aunque solo tenga un poco de texto.

> [!question] 3. Una extensión `.dat` o `.bin` indica normalmente...
> - a) Un fichero de texto plano estándar
> - b) Un fichero XML
> - c) Información binaria en un formato poco difundido que solo una aplicación concreta sabe leer
> - d) Un fichero de configuración legible
>
> > [!success]- Respuesta
> > **c**.

> [!question] 4. Sobre el acceso secuencial, es cierto que...
> - a) Permite saltar directamente a cualquier registro
> - b) Para leer un dato del medio hay que leer antes los anteriores
> - c) Permite insertar datos entre los ya escritos
> - d) Los registros tienen tamaño conocido para calcular su posición
>
> > [!success]- Respuesta
> > **b**. (c) y (d) son del acceso aleatorio/directo o falsas.

> [!question] 5. ¿Cómo se realiza una baja en un fichero de acceso secuencial?
> - a) Se cambia el estado del registro a "no válido" y ya está
> - b) Se borra el registro en su posición, dejando un hueco
> - c) Se copian los registros que se mantienen y se regenera el fichero sobrescribiendo el original
> - d) Se mueve el registro a la zona de excedentes
>
> > [!success]- Respuesta
> > **c**. (a) es una baja lógica (típica del acceso aleatorio); (d) es del aleatorio.

> [!question] 6. La "zona de excedentes" aparece en...
> - a) El acceso secuencial
> - b) El acceso aleatorio, para insertar cuando la posición está ocupada o reutilizar espacios libres
> - c) XML con DOM
> - d) La serialización de objetos
>
> > [!success]- Respuesta
> > **b**.

> [!question] 7. Una baja lógica consiste en...
> - a) Eliminar el registro por completo del fichero
> - b) Cambiar el estado del registro a no válido, sin borrarlo
> - c) Borrar el fichero entero
> - d) Reescribir todos los registros
>
> > [!success]- Respuesta
> > **b**. La baja física es la que lo elimina del todo.

> [!question] 8. ¿Qué ventaja tiene el acceso secuencial?
> - a) Es muy rápido para consultar un registro concreto
> - b) Permite modificar sin procesar todo el fichero
> - c) Es sencillo y eficiente cuando se procesan todos los datos en orden, como en un backup
> - d) Nunca deja huecos entre registros y no requiere reescritura
>
> > [!success]- Respuesta
> > **c**. (a) y (b) son ventajas del acceso aleatorio; (d) es falso: las bajas obligan a reescribir.

## java.io / java.nio, File y streams → [[02 java.io y java.nio]]

> [!question] 9. ¿Qué afirmación sobre java.io y java.nio es correcta?
> - a) java.io es no bloqueante y java.nio es bloqueante
> - b) java.nio usa buffers y channels y permite E/S no bloqueante
> - c) java.io usa selectores para manejar varios canales con un hilo
> - d) Ambos usan exactamente el mismo modelo
>
> > [!success]- Respuesta
> > **b**. java.io es bloqueante y trabaja con streams; los selectores son de nio.

> [!question] 10. En java.io, `BufferedReader` y `BufferedWriter` sirven para...
> - a) Crear canales no bloqueantes
> - b) Agrupar operaciones de lectura/escritura y mejorar el rendimiento
> - c) Representar rutas del sistema
> - d) Serializar objetos
>
> > [!success]- Respuesta
> > **b**. El `ByteBuffer` explícito es de java.nio.

> [!question] 11. ¿A qué paquete pertenecen `ByteBuffer` y `Path`?
> - a) java.io
> - b) java.util
> - c) java.nio
> - d) org.jdom2
>
> > [!success]- Respuesta
> > **c**.

> [!question] 12. ¿Qué ocurre al ejecutar `File f = new File("datos.txt");` si el fichero no existe?
> - a) Se crea el fichero vacío
> - b) Se lanza `FileNotFoundException`
> - c) No se crea nada: el objeto solo representa la ruta/nombre
> - d) Se escribe una línea en blanco
>
> > [!success]- Respuesta
> > **c**. Para crearlo hay que usar algo que escriba (`PrintWriter`, `FileWriter`...) o `createNewFile()`.

> [!question] 13. ¿Qué devuelve `listFiles()` de la clase `File`?
> - a) Un `String[]` con los nombres
> - b) Un vector de objetos `File`
> - c) Un `Path`
> - d) Un booleano
>
> > [!success]- Respuesta
> > **b**. `list()` es la que devuelve `String[]`.

> [!question] 14. `mkdirs()` se diferencia de `mkdir()` en que...
> - a) Borra directorios
> - b) Lista directorios
> - c) También crea los directorios superiores que no existan
> - d) Solo funciona en Windows
>
> > [!success]- Respuesta
> > **c**.

> [!question] 15. Las clases base de los flujos de caracteres son...
> - a) `InputStream` y `OutputStream`
> - b) `Reader` y `Writer`
> - c) `File` y `Path`
> - d) `Channel` y `Buffer`
>
> > [!success]- Respuesta
> > **b**. `InputStream`/`OutputStream` son de bytes.

> [!question] 16. ¿Cómo se detecta el final del fichero al leer con `FileReader.read()`?
> - a) Devuelve `null`
> - b) Devuelve `0`
> - c) Devuelve `-1`
> - d) Lanza `EOFException` siempre
>
> > [!success]- Respuesta
> > **c**. `BufferedReader.readLine()` es la que devuelve `null`.

> [!question] 17. ¿Qué línea abre `fichero.txt` para AÑADIR texto al final sin borrarlo?
> - a) `new FileWriter("fichero.txt")`
> - b) `new FileWriter("fichero.txt", false)`
> - c) `new FileWriter("fichero.txt", true)`
> - d) `new FileWriter("fichero.txt").append()`
>
> > [!success]- Respuesta
> > **c**. El `true` activa el modo append.

> [!question] 18. ¿Cuál de estas líneas compila?
> - a) `new BufferedWriter("datos.txt")`
> - b) `new BufferedWriter(new File("datos.txt"))`
> - c) `new BufferedWriter(new FileWriter("datos.txt"))`
> - d) `new BufferedWriter(new Scanner("datos.txt"))`
>
> > [!success]- Respuesta
> > **c**. `BufferedWriter` envuelve un `Writer`.

> [!question] 19. ¿Para qué sirve `newLine()` de `BufferedWriter`?
> - a) Crea una nueva línea siempre con `\n`
> - b) Escribe el separador de línea adecuado al sistema operativo
> - c) Vacía el búfer
> - d) Cierra el fichero
>
> > [!success]- Respuesta
> > **b**. Es el salto de línea portable.

> [!question] 20. En java.nio, `Path.getParent()` devuelve...
> - a) El nombre del fichero
> - b) La ruta del directorio padre
> - c) La raíz del sistema
> - d) El separador de rutas
>
> > [!success]- Respuesta
> > **b**. `getFileName()` da el nombre; `FileSystem.getSeparator()` da `/` o `\`.

## XML: DOM, SAX y JDOM2 → [[03 XML con JDOM2]]

> [!question] 21. ¿Qué afirmación sobre DOM y SAX es correcta?
> - a) DOM lee por eventos y consume poca memoria
> - b) SAX construye el árbol completo en memoria
> - c) DOM carga el documento como árbol en memoria; SAX lo lee secuencialmente por eventos
> - d) Ambos dan visión global del documento
>
> > [!success]- Respuesta
> > **c**. SAX consume menos memoria pero no da visión global.

> [!question] 22. ¿Qué clase de JDOM2 lee un fichero XML y crea un `Document`?
> - a) `XMLOutputter`
> - b) `SAXBuilder`
> - c) `DocumentBuilder`
> - d) `Transformer`
>
> > [!success]- Respuesta
> > **b**. `DocumentBuilder` es de org.w3c.dom.

> [!question] 23. ¿Qué clase convierte un `Document` en XML y lo escribe formateado?
> - a) `SAXBuilder`
> - b) `XMLOutputter` (con `Format.getPrettyFormat()`)
> - c) `Element`
> - d) `JDOMSource`
>
> > [!success]- Respuesta
> > **b**.

> [!question] 24. ¿Qué devuelve `persona.getChildText("nombre")`?
> - a) El `Element` hijo llamado "nombre"
> - b) El texto contenido en el hijo "nombre", como `String`
> - c) El atributo "nombre"
> - d) Una lista de elementos
>
> > [!success]- Respuesta
> > **b**. `getChild("nombre")` devolvería el `Element`.

> [!question] 25. Para obtener el valor del atributo `id` de un `Element` se usa...
> - a) `getChild("id")`
> - b) `getText()`
> - c) `getAttributeValue("id")`
> - d) `getChildText("id")`
>
> > [!success]- Respuesta
> > **c**.

> [!question] 26. Modificas un nombre con `setText("Juanito")` sobre el `Document` en memoria, pero no llamas a `XMLOutputter`. ¿Qué pasa con el fichero?
> - a) Se actualiza automáticamente
> - b) Se actualiza al cerrar el programa
> - c) No cambia; hay que escribir el `Document` de nuevo con `XMLOutputter`
> - d) Se borra
>
> > [!success]- Respuesta
> > **c**.

> [!question] 27. En el ejemplo de eliminar nodos, ¿por qué se usa `break` tras `removeContent` o se emplea un `Iterator`?
> - a) Porque JDOM2 no permite eliminar
> - b) Para no modificar la lista mientras se recorre con for-each y evitar errores
> - c) Para acelerar la escritura
> - d) Porque `removeContent` solo funciona con atributos
>
> > [!success]- Respuesta
> > **b**. (Evita `ConcurrentModificationException`.)

> [!question] 28. Diferencia en la construcción del documento entre `org.w3c.dom` y `org.jdom2`:
> - a) w3c usa `SAXBuilder` y jdom2 `DocumentBuilder`
> - b) w3c usa `DocumentBuilder` y jdom2 `SAXBuilder`
> - c) Ambos usan `XMLOutputter`
> - d) No hay diferencia
>
> > [!success]- Respuesta
> > **b**. Además w3c navega con `getElementsByTagName()` y jdom2 con `getChild()`/`getChildren()`.

## XPath y Jaxen → [[04 XSLT y XPath]]

> [!question] 29. ¿Qué selecciona `//title[@lang='en']`?
> - a) El primer `title` del documento
> - b) Todos los `title` con atributo `lang` igual a `en`, estén donde estén
> - c) Solo el `title` hijo de la raíz
> - d) Todos los atributos `lang`
>
> > [!success]- Respuesta
> > **b**.

> [!question] 30. ¿Qué selecciona `/bookstore/book[1]`?
> - a) Todos los `book`
> - b) El primer `book` hijo de `bookstore`
> - c) El `book` con índice 0
> - d) El `book` con precio 1
>
> > [!success]- Respuesta
> > **b**. En XPath se empieza en 1.

> [!question] 31. En XPath, `..` representa...
> - a) El nodo actual
> - b) La raíz
> - c) El padre del nodo actual
> - d) Todos los descendientes
>
> > [!success]- Respuesta
> > **c**. `.` es el nodo actual.

> [!question] 32. ¿Qué selecciona `/bookstore/book[price>35.00]/title`?
> - a) Los `book` con precio mayor que 35
> - b) Los `title` de los `book` de `bookstore` cuyo `price` es mayor que 35
> - c) Todos los `title`
> - d) Los `price` de los `title`
>
> > [!success]- Respuesta
> > **b**.

> [!question] 33. Con JDOM2 y Jaxen, el orden correcto es...
> - a) `evaluate` → `compile`
> - b) `compile` la expresión con `XPathFactory` y después `evaluate` sobre el `Document`
> - c) `build` → `transform`
> - d) `readValue` → `writeValue`
>
> > [!success]- Respuesta
> > **b**.

> [!question] 34. `XPathFactory.instance().compile("//persona", Filters.element())` devuelve...
> - a) Una lista de `Element`
> - b) Un `Document`
> - c) Un `XPathExpression<Element>` (la expresión preparada)
> - d) Un `String`
>
> > [!success]- Respuesta
> > **c**. La lista de resultados la da `evaluate(document)`.

## Serialización → [[06 Serialización]]

> [!question] 35. La interfaz `Serializable`...
> - a) Obliga a implementar `serializar()` y `deserializar()`
> - b) No contiene métodos que implementar
> - c) Es una clase abstracta
> - d) Solo sirve para XML
>
> > [!success]- Respuesta
> > **b**.

> [!question] 36. Para guardar un objeto serializado en un fichero se usan...
> - a) `FileReader` y `BufferedReader`
> - b) `FileOutputStream` y `ObjectOutputStream` con `writeObject()`
> - c) `FileInputStream` y `ObjectInputStream` con `writeObject()`
> - d) `PrintWriter` y `println()`
>
> > [!success]- Respuesta
> > **b**.

> [!question] 37. Al deserializar con `readObject()` hay que capturar...
> - a) Solo `IOException`
> - b) `IOException` y `ClassNotFoundException`
> - c) Solo `ClassNotFoundException`
> - d) `JDOMException`
>
> > [!success]- Respuesta
> > **b**. Y el resultado es `Object`, por lo que hay que hacer cast.

> [!question] 38. ¿Por qué se suele serializar una colección (ArrayList, HashMap...) en lugar de objetos sueltos?
> - a) Porque Java no permite serializar objetos individuales
> - b) Porque así se guarda y carga todo el conjunto de objetos de la aplicación de una vez
> - c) Porque es obligatorio por el compilador
> - d) Porque ocupa más
>
> > [!success]- Respuesta
> > **b**.

## Jackson (JSON) → [[05 JSON con Jackson]]

> [!question] 39. ¿Qué clase de Jackson convierte objetos Java a JSON y viceversa?
> - a) `JsonNode`
> - b) `ObjectMapper`
> - c) `SAXBuilder`
> - d) `XMLOutputter`
>
> > [!success]- Respuesta
> > **b**. `SAXBuilder` es de JDOM2 (XML).

> [!question] 40. Para leer `empleados.json` (un array de empleados) como `List<Empleado>` se usa...
> - a) `mapper.readValue(f, List.class)`
> - b) `mapper.readValue(f, new TypeReference<List<Empleado>>() { })`
> - c) `mapper.readTree(f, Empleado.class)`
> - d) `mapper.writeValue(f, Empleado.class)`
>
> > [!success]- Respuesta
> > **b**. `List.class` no indica el tipo de los elementos.

> [!question] 41. ¿Cómo se genera un JSON indentado y legible?
> - a) `mapper.writeValue(f, obj)`
> - b) `mapper.writerWithDefaultPrettyPrinter().writeValue(f, obj)`
> - c) `mapper.readTree(f)`
> - d) `mapper.pretty(f)`
>
> > [!success]- Respuesta
> > **b**. Sin eso, el JSON sale en una sola línea.

> [!question] 42. `mapper.readTree(file)` devuelve...
> - a) Un `Map`
> - b) Un `JsonNode` (árbol de nodos), sin necesidad de clase POJO
> - c) Un `Document`
> - d) Un `List<Empleado>`
>
> > [!success]- Respuesta
> > **b**.

> [!question] 43. Si el JSON raíz es un array `[ ... ]`, el `JsonNode` raíz se convierte a...
> - a) `ObjectNode`
> - b) `ArrayNode`
> - c) `XmlMapper`
> - d) `JsonParser`
>
> > [!success]- Respuesta
> > **b**. `ObjectNode` es para objetos `{ }`.

> [!question] 44. Añades un empleado a la lista leída con Jackson (`empleados.add(nuevo)`) y no llamas a `writeValue`. ¿Qué ocurre con el fichero JSON?
> - a) Se actualiza solo
> - b) No cambia hasta que guardes los cambios con `writeValue`
> - c) Se borra
> - d) Lanza excepción
>
> > [!success]- Respuesta
> > **b**.

## Excepciones → [[07 Gestión de excepciones]]

> [!question] 45. ¿Cuál es la diferencia entre `throw` y `throws`?
> - a) Son sinónimos
> - b) `throw` lanza una excepción concreta dentro del método; `throws` declara en la cabecera las que el método puede lanzar
> - c) `throws` lanza la excepción y `throw` la declara
> - d) `throw` solo existe en excepciones unchecked
>
> > [!success]- Respuesta
> > **b**. "El código tira esto ahora" (throw) vs "el método tira estas cosas" (throws).

> [!question] 46. Una excepción personalizada que extiende `Exception` es...
> - a) Unchecked y no necesita `throws`
> - b) Checked: Java obliga a declararla con `throws` y tratarla con `try-catch`
> - c) Un error de compilación siempre
> - d) Igual que `RuntimeException`
>
> > [!success]- Respuesta
> > **b**. Las que extienden `RuntimeException` son unchecked.

> [!question] 47. ¿Cuándo se ejecuta el bloque `finally`?
> - a) Solo si hay excepción
> - b) Solo si no hay excepción
> - c) Siempre, haya excepción o no
> - d) Nunca si hay `catch`
>
> > [!success]- Respuesta
> > **c**. Se usa para cerrar ficheros y liberar recursos.

> [!question] 48. ¿Qué imprime este código?
> ```java
> try {
>     int[] a = new int[2];
>     a[5] = 1;
> } catch (ArithmeticException e) {
>     System.out.print("A");
> } catch (Exception e) {
>     System.out.print("B");
> } finally {
>     System.out.print("C");
> }
> ```
> - a) AC
> - b) BC
> - c) B
> - d) C
>
> > [!success]- Respuesta
> > **b**. El acceso fuera de rango no es `ArithmeticException`, lo atrapa `Exception` (B) y `finally` siempre ejecuta (C).

> [!question] 49. ¿Por qué el `catch (Exception e)` genérico se coloca en el último bloque catch?
> - a) Es obligatorio por sintaxis
> - b) Captura cualquier excepción, así las específicas se gestionan antes
> - c) Para que `finally` se ejecute
> - d) Para que sea unchecked
>
> > [!success]- Respuesta
> > **b**. Es una recomendación (y si va antes de una subclase, el compilador da error por código inalcanzable).

> [!question] 50. ¿Qué método de `Throwable` muestra dónde se produjo la excepción (la traza de ejecución)?
> - a) `getMessage()`
> - b) `toString()`
> - c) `printStackTrace()`
> - d) `getCause()`
>
> > [!success]- Respuesta
> > **c**. `getMessage()` el mensaje; `toString()` nombre + mensaje; `getCause()` la excepción original.

> [!question] 51. Este método, donde `MiExcepcion1 extends Exception`, ¿compila?
> ```java
> void comprobar(int cod) {
>     if (cod <= 0) throw new MiExcepcion1("Código inválido");
> }
> ```
> - a) Sí
> - b) No: falta `throws MiExcepcion1` en la cabecera (o un `try-catch`)
> - c) No: `throw` no se puede usar en `if`
> - d) Sí, pero solo en Windows
>
> > [!success]- Respuesta
> > **b**. Es checked.

> [!question] 52. Un método que **trata** una excepción con `try-catch`...
> - a) Debe además propagarla con `throws`
> - b) No debería propagarla también (o se trata o se propaga)
> - c) No puede usar `finally`
> - d) Solo puede tratar `RuntimeException`
>
> > [!success]- Respuesta
> > **b**.

> [!question] 53. ¿Dónde conviene gestionar normalmente una excepción?
> - a) Siempre en el método donde ocurre
> - b) Donde se pueda tomar una decisión adecuada: los métodos intermedios suelen propagar y la capa superior (`main`) captura lo que quede
> - c) Nunca; es mejor dejar que el programa se pare
> - d) Solo en `finally`
>
> > [!success]- Respuesta
> > **b**. Si no sabes qué hacer con ella, propágala.

## Mi puntuación
- 0-30 → repasa las notas 01-07 y vuelve a intentarlo
- 31-44 → casi: revisa solo los temas donde fallaste
- 45-53 → listo; haz [[08 Preguntas de repaso]] como último repaso
