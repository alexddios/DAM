---
tags: [dam, acceso-a-datos, examen, xml, jdom2]
---
# 03 · XML con JDOM2
← [[02 java.io y java.nio]] · Siguiente: [[04 XSLT y XPath]]

## Partes de un XML
```xml
<students>                                   <!-- elemento raíz -->
  <student id="123" active="true">           <!-- elemento con ATRIBUTOS id, active -->
    <name>John</name>                        <!-- elemento con VALOR/TEXTO "John" -->
    <course code="DAM01" year="1">
      <name>Acceso a Datos</name>
      <grade>8.5</grade>
    </course>
  </student>
</students>
```
**Elementos** (etiquetas) · **Atributos** (`id`, `active`, `code`, `year`) · **Valores/texto** (`John`, `8.5`...).

## Parsers: DOM vs SAX
(Independientes del lenguaje de programación.)
| | **DOM** (Document Object Model) | **SAX** (Simple API for XML) |
|---|---|---|
| Cómo lee | Carga **todo en memoria** como **árbol** (nodos padre, hijos y finales/hoja) | Lectura **secuencial** por **eventos** (inicio/fin de documento, inicio/fin de etiqueta...) |
| Cada evento | — | Invoca un método definido por el programador |
| Memoria | Más | **Menos** |
| Visión global | Sí | **No** |

## Paquetes XML en Java
| Paquete | Qué es | Uso |
|---|---|---|
| `org.jdom2` | Biblioteca **externa** | Leer, crear, modificar y recorrer XML con objetos Java |
| `javax.xml` | API estándar | Parsear, transformar (XSLT), validar, consultar |
| `org.w3c.dom` | API estándar DOM | XML como árbol de nodos en memoria |
| `org.xml.sax` | API estándar SAX | Lectura secuencial por eventos |

## JDOM y JDOM2
- JDOM: librería **externa** (hay que descargarla y añadirla al proyecto) para leer/crear/modificar XML con objetos Java.
- **JDOM2** = segunda versión. Mejoras: mejor integración con Java, API más clara y consistente, **mejor soporte de namespaces**, mejor gestión de tipos y seguridad con **genéricos**, separación más clara de funcionalidades internas.

### Subpaquetes de `org.jdom2`
| Paquete | Para qué | Contiene |
|---|---|---|
| `org.jdom2` | Documento XML y componentes | `Attribute`, `Document`, `Element`, `Namespace`... |
| `org.jdom2.input` | **Parsear** XML → objeto JDOM | `SAXBuilder`, ... |
| `org.jdom2.output` | JDOM → texto XML (`OutputStream` o `Writer`) | `XMLOutputter`, `Format` (indentación y saltos de línea) |
| `org.jdom2.transform` | Transformar XML (XSLT) | `JDOMSource`... |
| `org.jdom2.xpath` | Buscar con XPath | `XPathFactory`, `XPathExpression` |

### Clases principales
| Clase | Representa |
|---|---|
| `Document` | Todo el documento XML |
| `Element` | Una etiqueta |
| `Attribute` | Un atributo de una etiqueta |
| `Text` | El contenido textual de un elemento |
| `SAXBuilder` | **Lee** un XML y crea un `Document` |
| `XMLOutputter` | Convierte un `Document` en XML y lo **escribe** formateado |

### Métodos de `Element`
| Método | Hace |
|---|---|
| `getChild()` | Un hijo |
| `getChildren()` | Los hijos (lista) |
| `getChildText("x")` | Texto del hijo `x` |
| `getText()` / `setText()` | Leer / fijar texto |
| `getAttributeValue()` | Valor de un atributo |
| `setAttribute()` | Crear/modificar atributo |
| `addContent()` | Añadir hijos |
| `removeContent()` | Quitar contenido |

> [!tip] Flujo mental
> **Leer**: `SAXBuilder.build(File)` → `Document` → `getRootElement()` → `getChildren(...)`.
> **Escribir**: construir `Element`s → `new Document(root)` → `XMLOutputter(Format.getPrettyFormat()).output(doc, new FileWriter(...))`.

## CRUD con JDOM2

### Crear
```java
Element root = new Element("personas");
Element p1 = new Element("persona");
p1.setAttribute("id", "1");
p1.addContent(new Element("nombre").setText("Juan"));
p1.addContent(new Element("edad").setText("30"));
root.addContent(p1);                       // colgar p1 de la raíz

Document document = new Document(root);
XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
out.output(document, new FileWriter("personas.xml"));   // a fichero
String s = out.outputString(document);                  // a String
```

### Leer
```java
SAXBuilder saxBuilder = new SAXBuilder();
Document document = saxBuilder.build(new File("personas.xml"));
Element raiz = document.getRootElement();
for (Element persona : raiz.getChildren("persona")) {
    String id     = persona.getAttributeValue("id");
    String nombre = persona.getChildText("nombre");
    String edad   = persona.getChildText("edad");
}
```

### Modificar
```java
// Cambiar un campo de un registro concreto
for (Element persona : raiz.getChildren("persona")) {
    if (persona.getAttributeValue("id").equals("1")) {
        persona.getChild("nombre").setText("Juanito");
    }
}
// Añadir un nodo a uno existente
if (persona.getChildText("nombre").equals("Ana"))
    persona.addContent(new Element("oficio").setText("Directora"));
// Guardar los cambios (mismo XMLOutputter que al crear)
```

### Eliminar
```java
// a) por id: removeContent + break
for (Element persona : raiz.getChildren("persona")) {
    if (persona.getAttributeValue("id").equals("1")) {
        raiz.removeContent(persona);
        break;
    }
}
// b) varios (edad = 25): Iterator + iterator.remove()
Iterator<Element> it = raiz.getChildren("persona").iterator();
while (it.hasNext()) {
    Element persona = it.next();
    if (persona.getChildText("edad").equals("25")) it.remove();
}
// Guardar cambios
```
> [!note] 🆕 Por qué `break` o `Iterator`
> Si quitas elementos de una lista mientras la recorres con un `for-each` y sigues iterando, Java lanza `ConcurrentModificationException`. Por eso el primer ejemplo sale del bucle con `break` y el segundo usa `iterator.remove()`.

> [!warning] ⚠️ Patrón común en modificar y eliminar
> **Leer fichero → cambiar en memoria → volver a escribir el fichero entero** con `XMLOutputter`. Si no guardas, el fichero no cambia.

## `org.w3c.dom` vs JDOM2 (¡no confundir!)
| Característica | `org.w3c.dom` | `org.jdom2` |
|---|---|---|
| Clases principales | `Document`, `Element`, `Node` | `Document`, `Element`, `Content` |
| Construcción | `DocumentBuilder` | `SAXBuilder` |
| Navegación | `getElementsByTagName()` | `getChild()` y `getChildren()` |
| Modificación del XML | Sí | Sí |

## Serialización con XML
`Document` (JDOM2) → XML = **serialización** · XML → `Document` = **deserialización**. Ver [[06 Serialización]].
