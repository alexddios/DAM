---
tags: [dam, acceso-a-datos, examen, json, jackson]
---
# 05 · JSON con Jackson
← [[04 XSLT y XPath]] · Siguiente: [[06 Serialización]]

## Librerías JSON en Java
| Paquete | Qué es | Uso |
|---|---|---|
| **JSON-P** (`jakarta.json`) | API estándar Jakarta | Leer y generar JSON |
| **JSON-B** (`jakarta.json.bind`) | API estándar Jakarta | Serializar/deserializar objetos |
| **Gson** | Librería de Google | Serializar/deserializar |
| **Jackson** | Completa, rápida, configurable | Muy extendida en apps y servicios web |
| **org.json** | API sencilla | Lectura y creación manual |
| **JSON.simple** | Ligera, API básica | Ejemplos y apps sencillas |

## Jackson (FasterXML)
Biblioteca **externa**. Muy usada en aplicaciones y **servicios REST** (REST = servicios web que intercambian información por HTTP).
- **Data binding**: convierte automáticamente objetos Java ↔ JSON con `ObjectMapper`.
- **Anotaciones** para personalizar la conversión.
- **Multiformato**: también XML, YAML y CSV con módulos adicionales.
- `jackson-databind` **no viene en `jackson-core`**: hay que añadirla explícitamente.

### Clases clave
| Clase | Para qué |
|---|---|
| `ObjectMapper` | Convierte objetos Java ↔ JSON |
| `JsonNode` | Nodo JSON; permite consultar datos |
| `ObjectNode` | Crear y modificar **objetos** JSON `{ }` |
| `ArrayNode` | Crear y modificar **arrays** JSON `[ ]` |
| `JsonParser` | Lee JSON de forma secuencial |
| `JsonGenerator` | Genera JSON de forma secuencial |

### Ventajas del data binding (vs. parsear a mano con `substring()` / `indexOf()`)
Simplicidad y legibilidad · **Fiabilidad** (objetos anidados, arrays, caracteres especiales) · Mantenibilidad · **Gestión de errores** (detecta errores de sintaxis).

> [!note] ObjectMapper vs SAXBuilder
> Ambos facilitan trabajar con ficheros estructurados con POO, pero:
> - `ObjectMapper` (Jackson): objetos Java ↔ **JSON** (serializa / deserializa).
> - `SAXBuilder` (JDOM2): **analiza XML** y construye un árbol de objetos.

## Leer JSON
```java
ObjectMapper mapper = new ObjectMapper();

// a) A un Map (clave-valor)
Map<String, Object> datos = mapper.readValue(new File("unempleado.json"), Map.class);

// b) A un POJO
Empleado e = mapper.readValue(new File("unempleado.json"), Empleado.class);

// c) A una colección → TypeReference
TypeReference<List<Empleado>> ref = new TypeReference<List<Empleado>>() { };
List<Empleado> empleados = mapper.readValue(new File("empleados.json"), ref);
```

### `TypeReference`
- Sirve para indicar **tipos genéricos** (`List<Empleado>`, `Map<String,Object>`).
- `Map.class` / `List.class` solo dicen "es un mapa / una lista", **sin el tipo de los elementos**.
- `TypeReference<List<Empleado>>` le dice a Jackson que **cada elemento** debe convertirse en `Empleado`.

> [!warning] 🆕 Constructor vacío
> En las diapositivas `Empleado` solo tiene constructor con parámetros. Jackson, para **deserializar a un POJO**, normalmente necesita un **constructor sin argumentos** (o `@JsonCreator`). Si el profesor te pide una clase POJO para Jackson, añádelo.

## Escribir JSON (serialización)
```java
ObjectMapper mapper = new ObjectMapper();

// Desde un Map
Map<String, Object> empleado = new HashMap<>();
empleado.put("id", 33); empleado.put("apellido", "FRAILE");
mapper.writeValue(new File("salida.json"), empleado);

// Desde un POJO (no hay que definir estructura, solo instanciar)
Empleado e = new Empleado(33, "FRAILE", 30, 3000.34);
mapper.writeValue(new File("salida.json"), e);

// Formato legible (indentado)
mapper.writerWithDefaultPrettyPrinter().writeValue(new File("salida.json"), e);
```
Por defecto el JSON sale **compactado en una sola línea** (bueno para guardar/intercambiar, malo para leer).

## CRUD: leer → cambiar → guardar
Para añadir, modificar o eliminar: **se lee el fichero** (deserializar), se trabaja con objetos Java (POJO) **o** con el árbol (`JsonNode`) y **se guarda de nuevo**.

```java
List<Empleado> empleados = mapper.readValue(jsonFile, ref);

// MODIFICAR (id 4)
for (Empleado e : empleados)
    if (e.getId() == 4) { e.setSalario(e.getSalario() + 500); e.setApellido("REYES"); }

// AÑADIR
empleados.add(new Empleado(60, "LOPEZ", 40, 1800.0));

// ELIMINAR (id 3)
Iterator<Empleado> it = empleados.iterator();
while (it.hasNext()) if (it.next().getId() == 3) it.remove();

// GUARDAR (¡sin esto no cambia el fichero!)
mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, empleados);
```

## Árbol de nodos: `JsonNode`, `ObjectNode`, `ArrayNode`
`mapper.readTree(fichero)` → `JsonNode` (árbol, **sin POJO**). Luego se convierte según el JSON:
- `{ }` → `ObjectNode` · `[ ]` → `ArrayNode`.

```java
JsonNode rootNode = mapper.readTree(jsonFile);
ArrayNode arrayNode = (ArrayNode) rootNode;          // el fichero es un array
for (JsonNode node : arrayNode) {
    ObjectNode empleado = (ObjectNode) node;          // cada elemento es un objeto
    double actual = empleado.get("salario").asDouble();
    empleado.put("salario", actual + 100);
}
mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, arrayNode);
```

### Métodos
**`JsonNode`**
| Método | Hace |
|---|---|
| `get("campo")` | Nodo por nombre |
| `path("campo")` | Igual, pero devuelve `MissingNode` si no existe (no `null`) |
| `has("campo")` | ¿Existe el campo? |
| `asText()` / `asInt()` | Valor como String / int (`asDouble()` en el ejemplo) |
| `at("/campo/subcampo")` | Acceso con **JSON Pointer** |
| `isObject()` / `isArray()` | Comprobar tipo |

**`ArrayNode`**: `add(value)` (`add(JsonNode)`, `addArray()`, `addObject()`) · `insert(index, value)` · `set(index, node)` · `get(index)` · `remove(index)`.

**`ObjectNode`**: `put("campo", value)` (primitivo) · `set("campo", node)` · `putObject("campo")` · `putArray("campo")` · `putPOJO("campo", obj)` · `get` · `has` · `fields()` · `fieldNames()` · `remove("campo")`.

> [!warning] ⚠️ `get` vs `path`
> `get("x")` devuelve `null` si no existe; `path("x")` devuelve un `MissingNode` (evita `NullPointerException`).

## JSON ↔ XML con `XmlMapper`
- `XmlMapper` (Jackson DataFormat) **hereda de `ObjectMapper`** pero trabaja con **XML**.
- Proceso: `readValue()` convierte XML → objeto Java; luego ese objeto → JSON.

```java
// XML → JSON
XmlMapper xmlMapper = new XmlMapper();
Map<String,Object> map = xmlMapper.readValue(new File("empleados.xml"), Map.class);
new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("empleados.json"), map);

// JSON → XML
JsonNode jsonNode = new ObjectMapper().readTree(new File("empleados.json"));
new XmlMapper().writerWithDefaultPrettyPrinter().writeValue(new File("empleados.xml"), jsonNode);
```

## Anotaciones (sobre clases POJO)
Controlan cómo se serializa/deserializa. **No son obligatorias** si usas los nombres y estructuras por defecto.
| Anotación | Efecto |
|---|---|
| `@JsonProperty("nombre")` | Nombre de la propiedad JSON |
| `@JsonIgnore` | Excluye la propiedad |
| `@JsonInclude(NON_NULL)` | Omite propiedades `null` |
| `@JsonAlias("nombre")` | Acepta nombres alternativos al deserializar |
| `@JsonPropertyOrder` | Orden de las propiedades |
| `@JsonFormat` | Formato (p. ej. fechas) |
| `@JacksonXmlProperty` | Nombre y si es **atributo o elemento** XML |
| `@JacksonXmlRootElement` | Elemento raíz XML |
| `@JacksonXmlElementWrapper` | Si una colección XML lleva elemento contenedor |

## Serialización con JSON
`Jackson → JSON` = **serialización** · `JSON → Jackson/objeto` = **deserialización**. Ver [[06 Serialización]].
