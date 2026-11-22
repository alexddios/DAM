---
tags: [dam, acceso-a-datos, examen, excepciones]
---
# 07 · Gestión de excepciones
← [[06 Serialización]] · Siguiente: [[08 Preguntas de repaso]]

## Qué es una excepción
Evento (normalmente un cambio a **estado de error**) que ocurre durante la ejecución y la **interrumpe**.
- Si el programa no la captura, la captura el **gestor de excepciones por defecto**: muestra el error y **detiene el programa**.
- Ejemplo clásico: dividir entre 0 (`10 / 0` → `ArithmeticException`).

## Cómo funciona (`Throwable`)
- Cuando hay un error dentro de un método, **no se trata ahí**: se **lanza** una excepción (objeto) que se maneja **fuera**, en el sistema de ejecución.
- Quien **llamó** al método decide: **gestionarla** o **propagarla** (volver a lanzarla).

## Jerarquía
`Throwable` → `Exception` → (tus excepciones). Las excepciones de Java son objetos de clases derivadas de `Exception`, que deriva de `Throwable`.
- Para capturar **cualquier** excepción se usa `Exception`.
- Se recomienda ponerla en el **último** `catch`, para que las específicas se gestionen antes.

### Métodos de `Throwable`
| Método | Devuelve / hace |
|---|---|
| `getMessage()` | Mensaje descriptivo |
| `toString()` | Nombre de la excepción + mensaje |
| `printStackTrace()` | Traza de ejecución: dónde se produjo |
| `getCause()` | Excepción original que provocó la actual (si existe) |

## Capturar: `try` / `catch` / `finally`
```java
try {
    // código que puede lanzar excepción (normalmente una llamada a un método con throws)
} catch (Excepcion1 e1) {
    // gestión de e1
} catch (Excepcion2 e2) {
    // gestión de e2
} finally {
    // se ejecuta SIEMPRE (haya o no excepción): cerrar ficheros, liberar recursos
}
```
- `try`: el código que puede fallar.
- `catch`: uno por tipo de excepción, con el código para gestionarla.
- `finally`: **opcional**, va al final, se ejecuta siempre.
- Varias excepciones en un catch: `catch (IOException | ClassNotFoundException e)` (sale en [[06 Serialización]]).

## Tratar o propagar
Un método puede:
- **Tratar**: capturar y gestionar con `try-catch`.
- **Propagar**: pasarla con `throws` a quien lo llamó.

> [!warning] ⚠️ Regla
> Un método **NO debe propagar** las excepciones que **trata**, y viceversa (o la tratas, o la propagas).

### `throws` (en la cabecera)
- Lista los tipos de excepción que el método **puede lanzar** y **no va a tratar**.
- El código que lo llama puede tratarla o **seguir propagándola** con otro `throws`.
- Hay excepciones de **errores fundamentales** que **no** hace falta declarar.

## `throw` vs `throws`
| | `throw` | `throws` |
|---|---|---|
| Dónde | **Dentro** del método | En la **cabecera** (declaración) |
| Qué hace | **Lanza** una excepción concreta en ejecución | **Declara** que el método puede lanzar esas excepciones |
| Regla mnemotécnica | "El código tira **esto ahora**" | "El método tira **estas cosas**" |

## Excepciones personalizadas
| Se extiende | Tipo | Cuándo | ¿`throws` obligatorio? |
|---|---|---|---|
| `Exception` | **Checked** (comprobada) | Errores que se prevén antes de ejecutar | **Sí**, y hay que tratarlas con `try-catch` |
| `RuntimeException` | **Unchecked** (no comprobada) | Errores de programación; aparecen en ejecución | **No** |

```java
// CHECKED
class MiExcepcion1 extends Exception {
    public MiExcepcion1(String mensaje) { super(mensaje); }
}
// UNCHECKED
class MiExcepcion2 extends RuntimeException {
    public MiExcepcion2(String mensaje) { super(mensaje); }
}
```
`super(mensaje)` llama al constructor de la clase padre y guarda el mensaje, que luego se obtiene con `getMessage()`.

### Uso
```java
// Checked: throw dentro + throws en la cabecera
void comprobarCodigo(int cod) throws MiExcepcion1 {
    if (cod <= 0) throw new MiExcepcion1("El código debe ser mayor que 0");
    System.out.println("Código válido");
}
// Unchecked: throw dentro, SIN throws
void comprobarDivision(int a, int b) {
    if (b == 0) throw new MiExcepcion2("No se puede dividir por 0");
    System.out.println("Resultado: " + a / b);
}

// Ejecución
try { comprobarCodigo(-5); }
catch (MiExcepcion1 e) { System.out.println(e.getMessage()); }   // obligatorio tratarla

comprobarDivision(4, 0);   // compila sin try-catch; falla en ejecución si no se trata
```

## ¿Dónde gestionarlas?
En el punto donde se pueda **tomar una decisión adecuada** sobre cómo actuar:
- **Métodos intermedios**: normalmente **propagan** (`throws`).
- **Capa superior** (p. ej. `main`): último nivel, captura las que no se hayan gestionado.
- **Capas especializadas** (funcionalidad concreta): gestionan las excepciones relacionadas con ellas.

> [!tip] Si no sabes qué hacer con una excepción
> **Propágala** hasta quien pueda gestionarla.

## En este tema
Las excepciones aparecen constantemente en E/S: `IOException` (ficheros), `FileNotFoundException`, `JDOMException` (XML con JDOM2), `ClassNotFoundException` (deserializar), `StreamReadException` / `DatabindException` (Jackson). En los ejemplos del profesor muchos `main` declaran `throws Exception` o `throws IOException` para simplificar.
