---
tags: [dam, acceso-a-datos, examen, xslt, xpath]
---
# 04 · XSLT y XPath
← [[03 XML con JDOM2]] · Siguiente: [[05 JSON con Jackson]]

## XSLT
**eXtensible Stylesheet Language Transformations**: lenguaje para **transformar un XML en otro formato o estructura** (HTML, otro XML, texto...). Forma parte de la familia **XSL** y está muy relacionado con **XPath**.

```
Documento XML (students.xml) + hoja de estilo XSLT (students.xsl)
                 ↓
       Procesador XSLT (transform)
                 ↓
      HTML / otro XML / texto  (students.html)
```

Ejemplo mínimo:
```xml
<!-- students.xsl -->
<xsl:template match="student">
  <h1><xsl:value-of select="name"/></h1>
</xsl:template>
```
`<student><name>John</name></student>` → `<h1>John</h1>`

### Piezas del `.xsl` del ejemplo
- `<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">`: raíz
- `<xsl:output method="html" encoding="UTF-8" indent="yes"/>`: formato de salida
- `<xsl:template match="/">`: plantilla principal (empieza en la raíz)
- `<xsl:for-each select="students/student">`: bucle sobre nodos
- `<xsl:value-of select="name"/>` y `select="@id"`: valor de un elemento / de un atributo

### XSLT desde Java (JDOM2 + javax.xml.transform)
```java
// 1. Cargar el XML con JDOM2
Document docXml = new SAXBuilder().build(new File("students.xml"));
// 2. Fuente de origen y hoja XSLT
JDOMSource source = new JDOMSource(docXml);
StreamSource xsltSource = new StreamSource(new File("students.xsl"));
// 3. Salida
FileWriter writer = new FileWriter("students.html");
StreamResult result = new StreamResult(writer);
// 4. Transformer y ejecución
Transformer transformer = TransformerFactory.newInstance().newTransformer(xsltSource);
transformer.setOutputProperty(OutputKeys.METHOD, "html");   // opcional pero recomendado
transformer.transform(source, result);
// 5. Cerrar
writer.close();
```
Clases: `JDOMSource` (org.jdom2.transform) · `StreamSource` / `StreamResult` / `Transformer` / `TransformerFactory` / `OutputKeys` (javax.xml.transform).

## XPath
Lenguaje para **seleccionar nodos** dentro de un XML.

| Expresión | Significado |
|---|---|
| `etiqueta` | Nodos de nombre "etiqueta" |
| `/` | Desde la raíz, o separador de nodos |
| `//` | Nodos que coincidan **sin importar dónde estén** |
| `.` | Nodo actual |
| `..` | Padre del nodo actual |
| `@atributo` | Selecciona un atributo |
| `*` / `@*` | Cualquier etiqueta / cualquier atributo |

- **Ruta absoluta**: desde la raíz → `/etiquetas/etiqueta`
- **Ruta relativa**: desde el nodo actual → `etiquetas/etiqueta`
- **Filtro por atributo**: `//etiqueta[@Atributo='valor']`

### Ejemplos (de la tienda de libros)
| XPath | Selecciona |
|---|---|
| `bookstore` | Todos los nodos llamados bookstore |
| `/bookstore` | El elemento raíz bookstore |
| `bookstore//book` | Todos los `book` descendientes de bookstore, a cualquier nivel |
| `/bookstore/book[1]` | El **primer** `book` hijo de bookstore |
| `/bookstore/book[price>35.00]` | Los `book` cuyo `price` es mayor que 35 |
| `/bookstore/book[price>35.00]/title` | Los `title` de esos libros |
| `//title[@lang='en']` | Los `title` con atributo `lang="en"` |

> [!warning] ⚠️ `[1]` en XPath
> En XPath el primer elemento es `[1]`, no `[0]`.

## XPath en JDOM2 con Jaxen
**Jaxen** = biblioteca externa que evalúa XPath, incluidos `Document` de JDOM2.

| Clase / método | Hace |
|---|---|
| `XPathFactory.instance()` | Instancia de la fábrica de expresiones |
| `XPathFactory.compile("expr", Filters.x())` | **Compila**: comprueba, prepara y permite reutilizar la expresión, e indica el tipo devuelto |
| `XPathExpression<T>` | Expresión XPath compilada |
| `XPathExpression.evaluate(document)` | **Ejecuta** → devuelve `List<T>` |
| `Filters.element()` / `Filters.attribute()` / `Filters.fstring()` | Tipo de resultado |

```java
XPathExpression<Element> expr =
    XPathFactory.instance().compile("//personas/persona[nombre='Juan']", Filters.element());
List<Element> results = expr.evaluate(document);
for (Element ele : results) {
    System.out.printf("Id: %s, Nombre: %s, Edad: %s%n",
        ele.getAttribute("id").getValue(), ele.getChildText("nombre"), ele.getChildText("edad"));
}
```
Búsqueda por valor de hijo: `persona[nombre='Juan']` · por atributo: `persona[@id='2']`.

> [!tip] Resumen
> **Compilar** (preparar la expresión + tipo de salida) → **evaluar** sobre el `Document` → recorrer la lista de resultados.
