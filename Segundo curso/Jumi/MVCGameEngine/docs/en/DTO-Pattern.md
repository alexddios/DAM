# DTO Pattern (Data Transfer Object)

## Overview

The DTO (Data Transfer Object) pattern is a design pattern used to transfer data between software application subsystems or layers. DTOs are simple objects that contain no business logic, only data fields and their accessors.

## What It Provides

### Safe Data Transfer
- Immutable snapshots of data prevent unintended modifications
- Decouples data representation from business objects
- Protects internal object state from external access

### Performance Optimization
- Reduces number of method calls by bundling data
- Minimizes network/layer boundary crossings
- Allows efficient serialization for remote calls

### Layer Separation
- Provides clean interface between architectural layers
- Hides internal implementation details
- Enables independent evolution of layers

## How It's Implemented

### Basic DTO

```java
// Simple DTO with public fields or getters
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
    
    // Only getters, no setters (immutable)
    public String getEntityId() { return entityId; }
    public String getAssetId() { return assetId; }
    public int getSize() { return size; }
    public double getPosX() { return posX; }
    public double getPosY() { return posY; }
    public double getAngle() { return angle; }
}
```

### DTOs in Balls Project

**Location**: `src/view/renderables/EntityInfoDTO.java`, `src/view/renderables/DBodyInfoDTO.java`

The project uses DTOs to transfer entity data from Model to View:

```java
// Static entity DTO
public class EntityInfoDTO {
    private final String entityId;
    private final String assetId;
    private final int size;
    private final double posX, posY;
    private final double angle;
    
    // Constructor and getters only (immutable)
}

// Dynamic entity DTO extends EntityInfoDTO
public class DBodyInfoDTO extends EntityInfoDTO {
    private final long timeStamp;
    private final double speedX, speedY;
    private final double accX, accY;
    
    // Additional physics data for dynamic entities
}

// Model creates DTOs
public class DynamicBody {
    public DBodyInfoDTO getInfo() {
        return new DBodyInfoDTO(
            this.entityId,
            this.assetId,
            this.size,
            physicsEngine.getValues().getPosX(),
            physicsEngine.getValues().getPosY(),
            // ... more fields
        );
    }
}

// View consumes DTOs
public class Renderer {
    public void render() {
        List<DBodyInfoDTO> entities = controller.getDBodyInfo();
        for (DBodyInfoDTO dto : entities) {
            drawEntity(dto.getPosX(), dto.getPosY(), dto.getAngle());
        }
    }
}
```

## Benefits

1. **Thread Safety**: Immutable DTOs can be safely shared between threads
2. **Encapsulation**: Internal model structure is hidden from consumers
3. **Versioning**: DTOs can evolve independently from domain objects
4. **Testability**: Easy to create test DTOs with specific data
5. **Clear Contracts**: Explicit data transfer interface between layers

## Variants

### Immutable DTO (Recommended)
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

### Mutable DTO (Use with caution)
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

### DTO with Builder
```java
public class ComplexDTO {
    private final String field1;
    private final String field2;
    // ... many fields
    
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

### Record-based DTO (Java 14+)
```java
public record EntityInfoDTO(
    String entityId,
    String assetId,
    int size,
    double posX,
    double posY,
    double angle
) {
    // Automatically immutable with getters
    // Compact and clear
}
```

## Common Implementation Mistakes

### 1. DTOs with Business Logic

**Wrong**:
```java
public class OrderDTO {
    private double price;
    private int quantity;
    
    // ❌ Business logic in DTO
    public double calculateTotal() {
        return price * quantity * 1.20; // Including tax
    }
    
    // ❌ Validation in DTO
    public boolean isValid() {
        return price > 0 && quantity > 0;
    }
}
```

**Correct**:
```java
public class OrderDTO {
    private final double price;
    private final int quantity;
    
    // ✓ Only data and accessors
    public OrderDTO(double price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }
    
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}

// Business logic in service layer
public class OrderService {
    public double calculateTotal(OrderDTO order) {
        return order.getPrice() * order.getQuantity() * 1.20;
    }
}
```

### 2. Mutable DTOs Causing Issues

**Wrong**:
```java
public class EntityDTO {
    public double x, y; // ❌ Public mutable fields
    
    public EntityDTO(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

// Thread 1 (Model)
EntityDTO dto = new EntityDTO(10, 20);
sendToView(dto);
dto.x = 50; // ❌ Modifies after sending

// Thread 2 (View)
void render(EntityDTO dto) {
    // May see old or new value - race condition!
    drawAt(dto.x, dto.y);
}
```

**Correct**:
```java
public final class EntityDTO {
    private final double x;
    private final double y;
    
    // ✓ Immutable
    public EntityDTO(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
}

// Thread 1 (Model)
EntityDTO dto = new EntityDTO(10, 20);
sendToView(dto);
// Cannot modify - thread-safe

// Thread 2 (View)
void render(EntityDTO dto) {
    // Always sees consistent state
    drawAt(dto.getX(), dto.getY());
}
```

### 3. Too Much Data in DTO

**Wrong**:
```java
public class UserDTO {
    // ❌ Exposing too much data
    private String username;
    private String hashedPassword;
    private String creditCardNumber;
    private String ssn;
    private byte[] privateKey;
    private List<String> securityQuestions;
    // ... 50 more fields
}
```

**Correct**:
```java
// ✓ Separate DTOs for different purposes
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

### 4. Circular References

**Wrong**:
```java
public class DepartmentDTO {
    private String name;
    private List<EmployeeDTO> employees; // ❌ Contains employees
}

public class EmployeeDTO {
    private String name;
    private DepartmentDTO department; // ❌ Contains department
}

// Causes infinite loop during serialization/transfer
```

**Correct**:
```java
public class DepartmentDTO {
    private final String departmentId;
    private final String name;
    // ✓ Just reference by ID
}

public class EmployeeDTO {
    private final String employeeId;
    private final String name;
    private final String departmentId; // ✓ Reference, not full object
}

// Or use separate DTOs
public class DepartmentWithEmployeesDTO {
    private final String departmentId;
    private final String name;
    private final List<String> employeeIds; // ✓ Just IDs
}
```

### 5. Using Domain Objects as DTOs

**Wrong**:
```java
// Domain object
public class User {
    private Long id;
    private String username;
    private String hashedPassword;
    // ... business logic methods
    
    public void updatePassword(String newPassword) { ... }
    public boolean authenticate(String password) { ... }
}

// ❌ Directly exposing domain object
public class UserController {
    public User getUser(Long id) {
        return userRepository.findById(id); // Exposes domain object
    }
}
```

**Correct**:
```java
// Domain object (internal)
public class User {
    private Long id;
    private String username;
    private String hashedPassword;
    
    public UserDTO toDTO() {
        return new UserDTO(id, username);
    }
}

// DTO for transfer
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

// ✓ Controller uses DTO
public class UserController {
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id);
        return user.toDTO();
    }
}
```

### 6. Not Validating DTO Input

**Wrong**:
```java
public class CreateUserDTO {
    private String username;
    private String email;
    
    // ❌ No validation
}

public class UserService {
    public User create(CreateUserDTO dto) {
        // ❌ Assumes valid data
        return new User(dto.getUsername(), dto.getEmail());
    }
}
```

**Correct**:
```java
public class CreateUserDTO {
    private final String username;
    private final String email;
    
    // ✓ Validate in constructor
    public CreateUserDTO(String username, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username required");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email required");
        }
        this.username = username;
        this.email = email;
    }
    
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}

// Or use Bean Validation
public class CreateUserDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private final String username;
    
    @NotNull
    @Email
    private final String email;
}
```

## Best Practices

1. **Make DTOs Immutable**: Use `final` fields and no setters
2. **Keep DTOs Simple**: No business logic, only data
3. **Validate Early**: Validate data when creating DTOs
4. **Separate DTOs by Purpose**: Different DTOs for different use cases
5. **Avoid Deep Nesting**: Flatten structures when possible
6. **Use Meaningful Names**: DTO names should indicate purpose (e.g., `CreateUserDTO`, `UserResponseDTO`)
7. **Document Fields**: Explain what each field represents
8. **Consider Records**: Use Java Records for simple DTOs (Java 14+)

## When to Use

Use DTOs when:
- Transferring data between layers (Model → View, Client → Server)
- Creating snapshots of mutable objects
- Reducing coupling between layers
- Working with multithreaded code
- Serializing data for network transmission
- Creating API responses

## When Not to Use

Avoid DTOs when:
- Transferring data within the same layer
- The overhead isn't justified (simple single-layer apps)
- Domain objects are already immutable and suitable

## DTO Mapping

### Manual Mapping
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

### Automated Mapping (with libraries)
```java
// Using ModelMapper or MapStruct
ModelMapper mapper = new ModelMapper();
UserDTO dto = mapper.map(user, UserDTO.class);
```

## Testing DTOs

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
    
    // Verify no setters exist
    // Verify fields are final (via reflection if needed)
}

@Test
public void testDTOThreadSafety() throws InterruptedException {
    EntityInfoDTO dto = new EntityInfoDTO("id", "asset", 10, 50, 75, 90);
    
    // Share DTO between threads
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
    
    // No race conditions with immutable DTO
}
```

## Real-World Examples

### REST API Response
```java
public class ProductDTO {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    
    // Used in API responses
}
```

### Database to UI Transfer
```java
// Database entity
@Entity
public class Order {
    @Id private Long id;
    private LocalDateTime createdAt;
    @ManyToOne private Customer customer;
    // ... many fields
}

// DTO for UI
public class OrderSummaryDTO {
    private final Long orderId;
    private final String customerName;
    private final BigDecimal total;
}
```

### Multi-tier Applications
```java
// Service layer returns DTO
public class UserService {
    public UserDTO getUserProfile(Long userId) {
        User user = repository.findById(userId);
        return new UserDTO(user.getId(), user.getUsername());
    }
}

// Controller uses DTO
public class UserController {
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserProfile(id));
    }
}
```

## Related Patterns

- [MVC Pattern](MVC-Pattern.md) - DTOs facilitate data transfer between MVC layers
- **Value Object Pattern** - Similar concept, but for domain objects
- **Builder Pattern** - Useful for creating complex DTOs
- **Mapper Pattern** - Converts between domain objects and DTOs

## References

- Source: `src/view/renderables/EntityInfoDTO.java`
- Source: `src/view/renderables/DBodyInfoDTO.java`
- Source: `src/model/EventDTO.java`
- Source: `src/model/ActionDTO.java`
- Martin Fowler - Patterns of Enterprise Application Architecture
- [Refactoring Guru - DTO Pattern](https://refactoring.guru/design-patterns/dto)
