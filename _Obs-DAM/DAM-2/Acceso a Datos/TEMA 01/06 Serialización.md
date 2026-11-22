---
tags: [dam, acceso-a-datos, examen, serializacion]
---
# 06 · Serialización
← [[05 JSON con Jackson]] · Siguiente: [[07 Gestión de excepciones]]

## Concepto
- **Serializar**: pasar un objeto de la memoria primaria a una estructura que se pueda **almacenar en un fichero** (persistencia de objetos) o **transmitir**: una secuencia de **bytes**.
- **Deserializar**: el proceso contrario, recuperar el objeto.
- Cada objeto se serializa a un fichero → lo habitual es **serializar una agrupación de objetos** (`ArrayList`, `Set`, `HashMap`) en lugar de objetos sueltos.

> [!tip] Mismo concepto, distintos formatos
> | Formato | Serializar | Deserializar |
> |---|---|---|
> | XML (JDOM2) | `Document` → XML | XML → `Document` |
> | JSON (Jackson) | objeto → JSON | JSON → objeto |
> | Bytes (`java.io`) | objeto → `.dat` con `writeObject` | `.dat` → objeto con `readObject` |

## La interfaz `Serializable` (`java.io`)
- Permite que los objetos de una clase se conviertan en **secuencia de bytes** para almacenarlos o transmitirlos, y recuperarlos después.
- **Es una interfaz sin métodos** que implementar.

```java
import java.io.Serializable;

public class Empleado implements Serializable {
    private String nombre;
    private int edad;
    // Constructor, getters y setters
}
```

### Relación con los POJO
**POJO** (Plain Old Java Object): clase Java sencilla con atributos, constructores, getters y setters. Se les pone `Serializable` porque sus datos suelen necesitar:
- Actualizar o mantener el **estado** de un objeto
- **Guardarse en archivos**
- **Enviarse** entre aplicaciones o procesos
- Almacenarse temporalmente en **sesiones**

## Clases que intervienen
| Clase | Papel |
|---|---|
| `Serializable` | Permite serializar el objeto |
| `File` | Representa el archivo donde se guardan los datos |
| `FileOutputStream` + `ObjectOutputStream` | **Escriben** el objeto en el archivo |
| `FileInputStream` + `ObjectInputStream` | **Leen y reconstruyen** el objeto |

## Guardar (serializar)
```java
Empleado empleado = new Empleado("Carlos", 30);
File archivo = new File("empleado.dat");
try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
    salida.writeObject(empleado);
    System.out.println("Empleado guardado correctamente.");
} catch (IOException e) {
    e.printStackTrace();
}
```

## Cargar (deserializar)
```java
File archivo = new File("empleado.dat");
try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
    Empleado empleado = (Empleado) entrada.readObject();     // ← cast obligatorio
    System.out.println("Nombre: " + empleado.getNombre());
} catch (IOException | ClassNotFoundException e) {
    e.printStackTrace();
}
```

> [!warning] ⚠️ Detalles que caen
> - Escribir → `writeObject()`; leer → `readObject()` (devuelve `Object`, hay que hacer **cast**).
> - Al **leer** se capturan **dos** excepciones: `IOException` **y** `ClassNotFoundException`. Al escribir basta `IOException`.
> - Se usa **try-with-resources**: el stream se cierra solo.
> - Extensión habitual del fichero: `.dat` (binario).

## 🆕 Extras que suelen preguntarse (no están en las diapositivas)
- Si la clase **no** implementa `Serializable`, `writeObject` lanza `NotSerializableException`.
- `transient` en un atributo → **no se serializa**.
- `serialVersionUID`: identificador de versión de la clase; si cambia la clase y no coincide, falla la deserialización (`InvalidClassException`).
- Los atributos que sean objetos también deben ser `Serializable`.
