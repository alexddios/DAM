---
tags: [dam, acceso-a-datos, examen, java-io, java-nio]
---
# 02 · java.io y java.nio
← [[01 Ficheros y persistencia]] · Siguiente: [[03 XML con JDOM2]]

## java.io vs java.nio
| | `java.io` (tradicional) | `java.nio` (New I/O) |
|---|---|---|
| E/S | **Bloqueante**: el hilo espera hasta que acaba | **No bloqueante**: los datos se procesan cuando están listos |
| Modelo | **Streams** (flujos), datos de uno en uno, secuencial | **Buffers** + **Channels** (canales) |
| Extra | No eficiente con muchas conexiones/datos | **Selectores**: varios canales con un solo hilo (rendimiento y escalabilidad) |

> [!warning] ⚠️ No confundir los "buffers"
> - **java.io**: buffer = mejora de rendimiento que **agrupa** operaciones de lectura/escritura → `BufferedReader` / `BufferedWriter`.
> - **java.nio**: `Buffer` = **objeto explícito** donde se guardan datos temporalmente → `ByteBuffer`, `CharBuffer`, usados con un `Channel`.

## Clase `File` (java.io)
- Representación **abstracta** de ficheros y directorios. Una instancia de `File` es un **nombre/ruta**, **no el archivo en sí**.
- El archivo puede **no existir** → hay que controlar excepciones (`IOException`).
- Independiente de la plataforma. `File.separator` evita distinguir entre SO.
- **No escribe contenido** (para eso, `PrintWriter`, `FileWriter`...).

| Método | Hace |
|---|---|
| `renameTo()` | Renombra |
| `delete()` / `deleteOnExit()` | Borra / borra al terminar la JVM |
| `createTempFile()` (estático) | Crea fichero temporal con nombre único, devuelve `File` |
| `setLastModified()` | Cambia fecha/hora de modificación |
| `mkdir()` / `mkdirs()` | Crea directorio / crea también los **superiores** si no existen |
| `list()` | Contenido del directorio como `String[]` (nombres) |
| `listFiles()` | Contenido como `File[]` |
| `listRoots()` (estático) | Raíces del sistema de ficheros |

```java
File f = new File("datos.txt");          // solo representa la ruta; NO crea nada
PrintWriter pw = new PrintWriter("datos.txt"); // esto SÍ crea y escribe
pw.println("Hola");
pw.close();
```

## Streams (flujos)
Abstracción para el traspaso de información entre un **origen** y un **destino**.
| Tipo | Unidad | Clases base | Para |
|---|---|---|---|
| **Bytes** | 8 bits | `InputStream` / `OutputStream` | Datos binarios |
| **Caracteres** | 16 bits (Unicode, por internacionalización) | `Reader` / `Writer` | Texto |

### Clases de caracteres relevantes
| Clase | Para qué |
|---|---|
| `FileReader` / `FileWriter` | Leer/escribir caracteres en ficheros |
| `PrintWriter` | Escritura de texto formateado (`print()`, `println()`) |
| `CharArrayReader` / `CharArrayWriter` | Flujo de caracteres en un **array en memoria** (no fichero) |
| `BufferedReader` / `BufferedWriter` | Búfer intermedio para no acceder al fichero en cada operación |

## Leer texto plano (4 formas)
```java
// 1) Scanner (java.util) - analizar información brevemente
Scanner lector = new Scanner(new File("fichero.txt"));
while (lector.hasNextLine()) System.out.println(lector.nextLine());
lector.close();

// 2) FileReader - carácter a carácter; read() devuelve -1 al final
FileReader reader = new FileReader(new File("datos.txt"));
int c;
while ((c = reader.read()) != -1) System.out.print((char) c);
reader.close();

// 3) BufferedReader - eficiente, línea a línea; readLine() devuelve null al final
BufferedReader br = new BufferedReader(new FileReader("datos.txt"));
String linea;
while ((linea = br.readLine()) != null) System.out.println(linea);
br.close();

// 4) BufferedReader con try-with-resources (cierra solo)
try (BufferedReader br2 = new BufferedReader(new FileReader("archivo.txt"))) { ... }
```
> [!warning] ⚠️ Fin de lectura
> `FileReader.read()` → **-1** · `BufferedReader.readLine()` → **null**.

## Escribir texto plano
```java
// FileWriter: sobrescribe por defecto
FileWriter w = new FileWriter("fichero.txt");
w.write("Texto \n");
w.close();

// true = append (añade al final en vez de sobrescribir)
FileWriter w2 = new FileWriter("fichero.txt", true);

// BufferedWriter: más eficiente + salto de línea portable
BufferedWriter bw = new BufferedWriter(new FileWriter("datos.txt"));
bw.write("Hola");
bw.newLine();          // separador correcto según el SO
bw.close();            // cierra, libera recursos y vacía el búfer
```

### ¿Por qué Buffered?
- **BufferedWriter**: sin búfer, cada `write()` es una operación física de E/S (lenta). Con búfer se agrupan en una sola operación en bloque. Incluye `newLine()` (portable). Se **envuelve** sobre `FileWriter` / `OutputStreamWriter`.
- **BufferedReader**: lee **bloques** de caracteres a memoria en vez de uno a uno. Incluye `readLine()` (hasta `\n` o `\r\n`; `null` al final). Se envuelve sobre `FileReader` / `InputStreamReader`.

> [!warning] ⚠️ Errores en las diapositivas (no los copies en el examen)
> En una diapositiva aparece `new BufferedWriter("fichero.txt")` y `new BufferedReader("fichero.txt")`: **no compila**, el constructor recibe un `Writer`/`Reader`, hay que **envolver** `new FileWriter(...)` / `new FileReader(...)`. También sale `reader.readline()`: el método es `readLine()` (con L mayúscula).

## java.nio: `Path`, `Files`, `FileSystem`
| Clase | Qué es | Métodos |
|---|---|---|
| `Path` | Ubicación de un fichero/directorio. **Sustituye a `File`** de java.io | `getFileName()` · `getParent()` · `resolve(Path other)` (combina rutas) |
| `Files` | Operaciones: crear, leer, escribir, copiar, mover, eliminar | `exists()` · `createFile()` · `createDirectory()` |
| `FileSystem` | El sistema de ficheros al que accede la app | `getPath()` · `getSeparator()` (`/` Unix, `\` Windows) · `getRootDirectories()` |
