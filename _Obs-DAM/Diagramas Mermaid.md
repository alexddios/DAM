---
tags:
  - recursos
  - DAM2
unidad: 1
tema: UML en Mermaid (Obsidian)
---
# UML en Mermaid (Obsidian)

## 1. Clases

### Clase completa
```mermaid
classDiagram
class Persona {
  <<abstract>>
  +String nombre
  -int edad
  #String dni
  ~String interno
  +int contador$
  +getNombre() String
  +hablar()* void
  +crear()$ Persona
  -setEdad(int e) void
}
```

### Estereotipos
```mermaid
classDiagram
class Volador { 
<<interface>> 
+volar() void 
}
class Color { 
<<enumeration>> ROJO VERDE AZUL }
class Repo { <<service>> }
class Figura { <<abstract>> }
```

### Genéricos
```mermaid
classDiagram
class Lista~T~ {
  +List~T~ items
  +add(T item) void
  +get(int i) T
}
```

### Todas las relaciones
```mermaid
classDiagram
A <|-- B : herencia
C *-- D : composición
E o-- F : agregación
G --> H : asociación
I -- J : enlace
K ..> L : dependencia
M ..|> N : realización
O .. P : enlace punteado
Q <|--|> R : bidireccional
```

### Cardinalidad / multiplicidad
```mermaid
classDiagram
Cliente "1" --> "*" Pedido : realiza
Pedido "1" *-- "1..*" Linea : contiene
Profesor "0..1" -- "0..n" Curso : imparte
```

### Interfaz lollipop
```mermaid
classDiagram
Clase --() Interfaz
```

### Namespace (paquete)
```mermaid
classDiagram
namespace Dominio {
  class Usuario
  class Rol
}
namespace Infra {
  class UsuarioRepo
}
UsuarioRepo ..> Usuario
```

### Notas y dirección
```mermaid
classDiagram
direction LR
note "Nota general"
class Coche
note for Coche "Nota de clase"
Coche --> Motor
```

### Etiqueta de clase
```mermaid
classDiagram
class Srv["Servicio Principal"]
```

## 2. Objetos
```mermaid
classDiagram
class juan["juan : Persona"] {
  nombre = "Juan"
  edad = 30
}
class p1["p1 : Pedido"] {
  id = 101
}
juan --> p1
```

## 3. Secuencia

### Participantes y alias
```mermaid
sequenceDiagram
actor U as Usuario
participant F as Frontend
participant B as Backend
participant D as DB
```

### Tipos de flecha
```mermaid
sequenceDiagram
A->B: sólida sin punta
A-->B: punteada sin punta
A->>B: sólida con punta
A-->>B: punteada con punta
A-xB: sólida con X
A--xB: punteada con X
A-)B: asíncrona
A--)B: asíncrona punteada
A->>A: auto-mensaje
```

### Activación
```mermaid
sequenceDiagram
A->>+B: petición
B->>+C: consulta
C-->>-B: datos
B-->>-A: respuesta
activate A
A->>A: procesar
deactivate A
```

### Notas
```mermaid
sequenceDiagram
Note left of A: izquierda
Note right of A: derecha
Note over A,B: sobre ambos
A->>B: msg
```

### Fragmentos combinados
```mermaid
sequenceDiagram
autonumber
loop Cada 5s
  A->>B: ping
end
alt ok
  B-->>A: 200
else error
  B-->>A: 500
end
opt opcional
  A->>B: extra
end
par paralelo
  A->>B: tarea 1
and
  A->>C: tarea 2
end
critical conexión
  A->>DB: conectar
option timeout
  A->>A: reintentar
end
break fallo
  A->>A: abortar
end
rect rgb(220,240,255)
  A->>B: resaltado
end
```

### Agrupar (box)
```mermaid
sequenceDiagram
box Cliente
  participant A
end
box rgb(230,230,230) Servidor
  participant B
  participant C
end
A->>B: x
B->>C: y
```

### Crear / destruir
```mermaid
sequenceDiagram
A->>B: hola
create participant C
B->>C: new
destroy C
B-xC: delete
```

## 4. Estados

### Básico
```mermaid
stateDiagram-v2
direction LR
[*] --> Inactivo
Inactivo --> Activo : iniciar
Activo --> Inactivo : parar
Activo --> [*]
state "Estado con descripción" as S1
S1 : texto extra
```

### Compuesto (anidado)
```mermaid
stateDiagram-v2
[*] --> Encendido
state Encendido {
  [*] --> Esperando
  Esperando --> Procesando
  Procesando --> Esperando
}
Encendido --> [*]
```

### Choice
```mermaid
stateDiagram-v2
state decision <<choice>>
[*] --> Validar
Validar --> decision
decision --> Aprobado : ok
decision --> Rechazado : error
```

### Fork / Join
```mermaid
stateDiagram-v2
state f <<fork>>
state j <<join>>
[*] --> f
f --> A
f --> B
A --> j
B --> j
j --> [*]
```

### Concurrencia
```mermaid
stateDiagram-v2
[*] --> Activo
state Activo {
  [*] --> TecladoOn
  TecladoOn --> TecladoOff
  --
  [*] --> RatonOn
  RatonOn --> RatonOff
}
```

### Notas
```mermaid
stateDiagram-v2
A --> B
note right of A
  Nota multilínea
end note
note left of B : Nota corta
```

## 5. Actividad
```mermaid
flowchart TD
I((●)) --> A1([Recibir pedido])
A1 --> D{¿Stock?}
D -- Sí --> F1[" "]:::barra
D -- No --> A2([Notificar])
F1 --> A3([Empaquetar])
F1 --> A4([Facturar])
A3 --> J1[" "]:::barra
A4 --> J1
J1 --> M{ }
A2 --> M
M --> FIN(((◉)))
classDef barra fill:#000,stroke:#000
```

### Con carriles (swimlanes)
```mermaid
flowchart LR
subgraph Cliente
  C1([Pedir])
end
subgraph Tienda
  T1([Preparar]) --> T2([Enviar])
end
C1 --> T1
```

## 6. Casos de uso
```mermaid
flowchart LR
U["👤 Cliente"]
Adm["👤 Admin"]
subgraph Sistema
  UC1((Iniciar sesión))
  UC2((Comprar))
  UC3((Validar pago))
  UC4((Aplicar cupón))
  UC5((Gestionar))
end
U --- UC1
U --- UC2
Adm --- UC5
UC2 -. "«include»" .-> UC3
UC4 -. "«extend»" .-> UC2
Adm -->|generalización| U
```

## 7. Componentes
```mermaid
flowchart LR
subgraph App["«component» App"]
  UI[[UI]]
  API[[API]]
end
DB[("«component» BD")]
Ext[["«component» Pagos"]]
UI -->|usa| API
API -->|«interface» IRepo| DB
API -.->|REST| Ext
```

## 8. Despliegue
```mermaid
flowchart TB
subgraph N1["«device» Servidor Web"]
  subgraph E1["«execution env» Nginx"]
    W[["app.war"]]
  end
end
subgraph N2["«device» Servidor BD"]
  DB[("PostgreSQL")]
end
PC["«device» PC Cliente"]
PC -->|HTTPS| N1
N1 -->|TCP 5432| N2
```

## 9. Paquetes
```mermaid
flowchart TB
subgraph P1["📁 presentacion"]
  C1[Controlador]
end
subgraph P2["📁 negocio"]
  S1[Servicio]
end
subgraph P3["📁 datos"]
  R1[Repositorio]
end
P1 -. "«import»" .-> P2
P2 -. "«access»" .-> P3
```

## 10. Comunicación
```mermaid
flowchart LR
A[":Cliente"] -- "1: pedir()" --> B[":Tienda"]
B -- "2: comprobar()" --> C[":Almacén"]
B -- "3: cobrar()" --> D[":Pago"]
C -- "2.1: reservar()" --> C
```

## 11. Tiempo (aprox. con gantt)
```mermaid
gantt
dateFormat ss
axisFormat %S
section Semáforo
Rojo     :a1, 00, 10s
Verde    :a2, after a1, 8s
Ámbar    :a3, after a2, 3s
section Peatón
Espera   :b1, 00, 10s
Cruza    :b2, after b1, 8s
```

## 12. Estilos útiles
```mermaid
classDiagram
class A:::rojo
class B
A --> B
classDef rojo fill:#f99,stroke:#900
style B fill:#9cf
```