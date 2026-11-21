# Patrón DTO (Objeto de Transferencia de Datos)

## Visión General

El patrón DTO (Data Transfer Object - Objeto de Transferencia de Datos) es un patrón de diseño usado para transferir datos entre subsistemas o capas de una aplicación de software. Los DTOs son objetos simples que no contienen lógica de negocio, solo campos de datos y sus accesores.

## Qué Aporta

### Transferencia Segura de Datos
- Las instantáneas inmutables de datos previenen modificaciones no deseadas
- Desacopla la representación de datos de los objetos de negocio
- Protege el estado interno del objeto del acceso externo

### Optimización de Rendimiento
- Reduce el número de llamadas a métodos agrupando datos
- Minimiza cruces de límites de red/capa
- Permite serialización eficiente para llamadas remotas

### Separación de Capas
- Proporciona interfaz limpia entre capas arquitectónicas
- Oculta detalles de implementación interna
- Permite evolución independiente de capas

## Cómo se Implementa

### DTO Básico

```java
// DTO simple con campos públicos o getters
public class EntityInfoDTO {
    private final String entityId;
    private final String assetId;
    private final int size;
    private final double posX;
    private final double posY;
    private final double angle;
    
    public EntityInfoDTO(String entityId, String assetId, int size,
                         double posX, double posY, double angle) {
        this.entityId = entityId;
        this.assetId = assetId;
        this.size = size;
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
    }
    
    // Solo getters, sin setters (inmutable)
    public String getEntityId() { return entityId; }
    public String getAssetId() { return assetId; }
    public int getSize() { return size; }
    public double getPosX() { return posX; }
    public double getPosY() { return posY; }
    public double getAngle() { return angle; }
}
```

### DTOs en el Proyecto Balls

**Ubicación**: `src/view/renderables/EntityInfoDTO.java`, `src/view/renderables/DBodyInfoDTO.java`

El proyecto usa DTOs para transferir datos de entidad del Modelo a la Vista:

```java
// DTO de entidad estática
public class EntityInfoDTO {
    private final String entityId;
    private final String assetId;
    private final int size;
    private final double posX, posY;
    private final double angle;
    
    // Solo constructor y getters (inmutable)
}

// DTO de entidad dinámica extiende EntityInfoDTO
public class DBodyInfoDTO extends EntityInfoDTO {
    private final long timeStamp;
    private final double speedX, speedY;
    private final double accX, accY;
    
    // Datos físicos adicionales para entidades dinámicas
}

// Modelo crea DTOs
public class DynamicBody {
    public DBodyInfoDTO getInfo() {
        return new DBodyInfoDTO(
            this.entityId,
            this.assetId,
            this.size,
            physicsEngine.getValues().getPosX(),
            physicsEngine.getValues().getPosY(),
            // ... más campos
        );
    }
}

// Vista consume DTOs
public class Renderer {
    public void render() {
        List<DBodyInfoDTO> entities = controller.getDBodyInfo();
        for (DBodyInfoDTO dto : entities) {
            drawEntity(dto.getPosX(), dto.getPosY(), dto.getAngle());
        }
    }
}
```

## Beneficios

1. **Seguridad de Hilos**: Los DTOs inmutables pueden compartirse de forma segura entre hilos
2. **Encapsulación**: La estructura interna del modelo está oculta de los consumidores
3. **Versionado**: Los DTOs pueden evolucionar independientemente de los objetos de dominio
4. **Testabilidad**: Fácil crear DTOs de prueba con datos específicos
5. **Contratos Claros**: Interfaz explícita de transferencia de datos entre capas

## Variantes

### DTO Inmutable (Recomendado)
```java
public final class UserDTO {
    private final String username;
    private final String email;
    
    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
```

### DTO Mutable (Usar con precaución)
```java
public class UserDTO {
    private String username;
    private String email;
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

### DTO con Builder
```java
public class ComplexDTO {
    private final String field1;
    private final String field2;
    // ... muchos campos
    
    private ComplexDTO(Builder builder) {
        this.field1 = builder.field1;
        this.field2 = builder.field2;
    }
    
    public static class Builder {
        private String field1;
        private String field2;
        
        public Builder field1(String field1) {
            this.field1 = field1;
            return this;
        }
        
        public Builder field2(String field2) {
            this.field2 = field2;
            return this;
        }
        
        public ComplexDTO build() {
            return new ComplexDTO(this);
        }
    }
}
```

### DTO basado en Record (Java 14+)
```java
public record EntityInfoDTO(
    String entityId,
    String assetId,
    int size,
    double posX,
    double posY,
    double angle
) {
    // Automáticamente inmutable con getters
    // Compacto y claro
}
```

## Errores Comunes de Implementación

### 1. DTOs con Lógica de Negocio

**Incorrecto**:
```java
public class OrderDTO {
    private double price;
    private int quantity;
    
    // ❌ Lógica de negocio en DTO
    public double calculateTotal() {
        return price * quantity * 1.20; // Incluyendo impuestos
    }
    
    // ❌ Validación en DTO
    public boolean isValid() {
        return price > 0 && quantity > 0;
    }
}
```

**Correcto**:
```java
public class OrderDTO {
    private final double price;
    private final int quantity;
    
    // ✓ Solo datos y accesores
    public OrderDTO(double price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }
    
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}

// Lógica de negocio en capa de servicio
public class OrderService {
    public double calculateTotal(OrderDTO order) {
        return order.getPrice() * order.getQuantity() * 1.20;
    }
}
```

### 2. DTOs Mutables Causando Problemas

**Incorrecto**:
```java
public class EntityDTO {
    public double x, y; // ❌ Campos públicos mutables
    
    public EntityDTO(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

// Hilo 1 (Modelo)
EntityDTO dto = new EntityDTO(10, 20);
sendToView(dto);
dto.x = 50; // ❌ Modifica después de enviar

// Hilo 2 (Vista)
void render(EntityDTO dto) {
    // Puede ver valor antiguo o nuevo - condición de carrera!
    drawAt(dto.x, dto.y);
}
```

**Correcto**:
```java
public final class EntityDTO {
    private final double x;
    private final double y;
    
    // ✓ Inmutable
    public EntityDTO(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
}

// Hilo 1 (Modelo)
EntityDTO dto = new EntityDTO(10, 20);
sendToView(dto);
// No se puede modificar - seguro para hilos

// Hilo 2 (Vista)
void render(EntityDTO dto) {
    // Siempre ve estado consistente
    drawAt(dto.getX(), dto.getY());
}
```

### 3. Demasiados Datos en DTO

**Incorrecto**:
```java
public class UserDTO {
    // ❌ Exponiendo demasiados datos
    private String username;
    private String hashedPassword;
    private String creditCardNumber;
    private String ssn;
    private byte[] privateKey;
    private List<String> securityQuestions;
    // ... 50 campos más
}
```

**Correcto**:
```java
// ✓ DTOs separados para diferentes propósitos
public class UserProfileDTO {
    private final String username;
    private final String displayName;
    private final String avatarUrl;
}

public class UserCredentialsDTO {
    private final String username;
    private final String hashedPassword;
}

public class UserDetailsDTO {
    private final String username;
    private final String email;
    private final LocalDate registeredDate;
}
```

### 4. Referencias Circulares

**Incorrecto**:
```java
public class DepartmentDTO {
    private String name;
    private List<EmployeeDTO> employees; // ❌ Contiene empleados
}

public class EmployeeDTO {
    private String name;
    private DepartmentDTO department; // ❌ Contiene departamento
}

// Causa bucle infinito durante serialización/transferencia
```

**Correcto**:
```java
public class DepartmentDTO {
    private final String departmentId;
    private final String name;
    // ✓ Solo referencia por ID
}

public class EmployeeDTO {
    private final String employeeId;
    private final String name;
    private final String departmentId; // ✓ Referencia, no objeto completo
}

// O usar DTOs separados
public class DepartmentWithEmployeesDTO {
    private final String departmentId;
    private final String name;
    private final List<String> employeeIds; // ✓ Solo IDs
}
```

### 5. Usar Objetos de Dominio como DTOs

**Incorrecto**:
```java
// Objeto de dominio
public class User {
    private Long id;
    private String username;
    private String hashedPassword;
    // ... métodos de lógica de negocio
    
    public void updatePassword(String newPassword) { ... }
    public boolean authenticate(String password) { ... }
}

// ❌ Exponiendo directamente objeto de dominio
public class UserController {
    public User getUser(Long id) {
        return userRepository.findById(id); // Expone objeto de dominio
    }
}
```

**Correcto**:
```java
// Objeto de dominio (interno)
public class User {
    private Long id;
    private String username;
    private String hashedPassword;
    
    public UserDTO toDTO() {
        return new UserDTO(id, username);
    }
}

// DTO para transferencia
public class UserDTO {
    private final Long id;
    private final String username;
    
    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }
    
    public Long getId() { return id; }
    public String getUsername() { return username; }
}

// ✓ Controlador usa DTO
public class UserController {
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id);
        return user.toDTO();
    }
}
```

### 6. No Validar Entrada de DTO

**Incorrecto**:
```java
public class CreateUserDTO {
    private String username;
    private String email;
    
    // ❌ Sin validación
}

public class UserService {
    public User create(CreateUserDTO dto) {
        // ❌ Asume datos válidos
        return new User(dto.getUsername(), dto.getEmail());
    }
}
```

**Correcto**:
```java
public class CreateUserDTO {
    private final String username;
    private final String email;
    
    // ✓ Validar en constructor
    public CreateUserDTO(String username, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username requerido");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email válido requerido");
        }
        this.username = username;
        this.email = email;
    }
    
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}

// O usar Bean Validation
public class CreateUserDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private final String username;
    
    @NotNull
    @Email
    private final String email;
}
```

## Mejores Prácticas

1. **Haz los DTOs Inmutables**: Usa campos `final` y sin setters
2. **Mantén los DTOs Simples**: Sin lógica de negocio, solo datos
3. **Valida Temprano**: Valida datos al crear DTOs
4. **Separa DTOs por Propósito**: Diferentes DTOs para diferentes casos de uso
5. **Evita Anidación Profunda**: Aplana estructuras cuando sea posible
6. **Usa Nombres Significativos**: Los nombres de DTO deben indicar propósito (ej: `CreateUserDTO`, `UserResponseDTO`)
7. **Documenta los Campos**: Explica qué representa cada campo
8. **Considera Records**: Usa Java Records para DTOs simples (Java 14+)

## Cuándo Usar

Usa DTOs cuando:
- Transfiriendo datos entre capas (Modelo → Vista, Cliente → Servidor)
- Creando instantáneas de objetos mutables
- Reduciendo acoplamiento entre capas
- Trabajando con código multihilo
- Serializando datos para transmisión de red
- Creando respuestas de API

## Cuándo No Usar

Evita DTOs cuando:
- Transfiriendo datos dentro de la misma capa
- El overhead no está justificado (apps simples de una sola capa)
- Los objetos de dominio ya son inmutables y adecuados

## Mapeo de DTOs

### Mapeo Manual
```java
public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail()
        );
    }
    
    public static User fromDTO(CreateUserDTO dto) {
        return new User(
            dto.getUsername(),
            dto.getEmail()
        );
    }
}
```

### Mapeo Automatizado (con librerías)
```java
// Usando ModelMapper o MapStruct
ModelMapper mapper = new ModelMapper();
UserDTO dto = mapper.map(user, UserDTO.class);
```

## Testing de DTOs

```java
@Test
public void testDTOCreation() {
    EntityInfoDTO dto = new EntityInfoDTO(
        "entity1", "asteroid", 50, 100.0, 200.0, 45.0
    );
    
    assertEquals("entity1", dto.getEntityId());
    assertEquals(100.0, dto.getPosX(), 0.01);
}

@Test
public void testDTOImmutability() {
    EntityInfoDTO dto = new EntityInfoDTO("id", "asset", 10, 0, 0, 0);
    
    // Verificar que no existen setters
    // Verificar que los campos son final (vía reflexión si es necesario)
}

@Test
public void testDTOThreadSafety() throws InterruptedException {
    EntityInfoDTO dto = new EntityInfoDTO("id", "asset", 10, 50, 75, 90);
    
    // Compartir DTO entre hilos
    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 1000; i++) {
            assertEquals(50.0, dto.getPosX(), 0.01);
        }
    });
    
    Thread t2 = new Thread(() -> {
        for (int i = 0; i < 1000; i++) {
            assertEquals(75.0, dto.getPosY(), 0.01);
        }
    });
    
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    
    // Sin condiciones de carrera con DTO inmutable
}
```

## Ejemplos del Mundo Real

### Respuesta de API REST
```java
public class ProductDTO {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    
    // Usado en respuestas de API
}
```

### Transferencia de Base de Datos a UI
```java
// Entidad de base de datos
@Entity
public class Order {
    @Id private Long id;
    private LocalDateTime createdAt;
    @ManyToOne private Customer customer;
    // ... muchos campos
}

// DTO para UI
public class OrderSummaryDTO {
    private final Long orderId;
    private final String customerName;
    private final BigDecimal total;
}
```

### Aplicaciones Multi-capa
```java
// Capa de servicio retorna DTO
public class UserService {
    public UserDTO getUserProfile(Long userId) {
        User user = repository.findById(userId);
        return new UserDTO(user.getId(), user.getUsername());
    }
}

// Controlador usa DTO
public class UserController {
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserProfile(id));
    }
}
```

## Patrones Relacionados

- [Patrón MVC](MVC-Pattern.md) - Los DTOs facilitan la transferencia de datos entre capas MVC
- **Patrón Value Object** - Concepto similar, pero para objetos de dominio
- **Patrón Builder** - Útil para crear DTOs complejos
- **Patrón Mapper** - Convierte entre objetos de dominio y DTOs

## Referencias

- Fuente: `src/view/renderables/EntityInfoDTO.java`
- Fuente: `src/view/renderables/DBodyInfoDTO.java`
- Fuente: `src/model/EventDTO.java`
- Fuente: `src/model/ActionDTO.java`
- Martin Fowler - Patterns of Enterprise Application Architecture
- [Refactoring Guru - Patrón DTO](https://refactoring.guru/es/design-patterns/dto)
