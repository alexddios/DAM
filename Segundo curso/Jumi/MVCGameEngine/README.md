# MVCGameEngine

**English** | **[Español](README_ES.md)**

An educational Java project that demonstrates a modular 2D game engine architecture with real-time physics simulation, designed for creating arcade-style games like Asteroids, space shooters, or other physics-based games.

This project serves as a comprehensive learning platform for understanding software architecture patterns, concurrent programming, game engine fundamentals, and object-oriented design principles.

## What the Engine Does

**MVCGameEngine** is a flexible 2D game engine featuring real-time physics simulation, entity management, and rendering capabilities. While the included example implementation demonstrates a space shooter scenario, the engine is designed to support various arcade-style games:

- **Flexible Game Development**: Build different types of 2D arcade games (space shooters like Asteroids, billiards, or other physics-based games)
- **Example Implementation**: Includes a space shooter example with asteroids, spaceships, and projectiles
- **Dynamic Bodies**: Entities move, rotate, and collide according to physics rules governed by interchangeable physics engines
- **Player Interaction**: Users can control player entities with thrust, rotation, and firing capabilities using keyboard inputs
- **Multiple Physics Engines**: Choose from different physics implementations including basic physics and null physics for varied simulation behaviors
- **Scene Generation**: Define worlds with customizable backgrounds, static bodies, and decorative elements
- **Weapon Systems**: Configurable weapon framework supporting different projectile types and behaviors
- **Life Generation**: Automated entity spawning system for maintaining game activity
- **Visual Rendering**: Real-time graphics rendering using Java Swing with asset management for sprites and visual effects

The engine runs continuously with a multithreaded architecture, separating rendering (View), game logic (Model), and user input handling (Controller) for optimal performance and maintainability.

## Educational Value for Learning Programming

This project serves as an excellent educational resource for learning fundamental and advanced programming concepts:

### Core Programming Concepts

1. **Object-Oriented Programming (OOP)**
   - Inheritance hierarchies (AbstractBody, DynamicBody, StaticBody, PlayerBody)
   - Polymorphism through interface implementations (PhysicsEngine, ActionExecutor)
   - Encapsulation of entity state and behavior
   - Abstract classes and concrete implementations

2. **Design Patterns**
   - **Model-View-Controller (MVC)**: Clean separation of concerns with Model handling simulation, View managing rendering, and Controller coordinating communication
   - **Factory Pattern**: WorldDefinitionProvider for creating different world configurations
   - **Strategy Pattern**: Interchangeable PhysicsEngine implementations
   - **Data Transfer Objects (DTOs)**: ActionDTO, EventDTO, EntityInfoDTO for safe data transfer between layers

3. **Concurrent Programming**
   - Multithreading for parallel execution of rendering and simulation loops
   - Thread-safe data structures (ConcurrentHashMap for entity management)
   - Volatile variables for thread visibility (ModelState, EngineState)
   - Synchronization between rendering thread and simulation thread

4. **Software Architecture**
   - UML-driven design with class diagrams defining structure before implementation
   - Dependency injection pattern (Controller, Model, View wiring)
   - Clear separation of responsibilities across packages (model, view, controller, assets, generators, world)
   - Event-driven architecture for entity behaviors

5. **Data Structures & Algorithms**
   - Efficient entity management using hash maps for O(1) lookups
   - Priority-based action execution system
   - Collision detection algorithms
   - Spatial calculations for physics simulation

6. **Code Organization**
   - Package structure reflecting architectural layers
   - Clear naming conventions and documentation
   - Modular design allowing feature addition without core changes

## Educational Value for Learning Game Engines

This project provides hands-on experience with fundamental game engine concepts:

### Game Engine Architecture

1. **Game Loop Implementation**
   - Fixed timestep vs. variable timestep considerations
   - Separation of update logic from rendering
   - Frame rate management and performance optimization

2. **Entity Component System**
   - Entity state management (BodyState enum)
   - Component-based entity attributes (position, velocity, rotation, mass)
   - Entity lifecycle management (creation, updates, destruction)

3. **Physics Simulation**
   - **Multiple Physics Engines**: Demonstrates how different physics implementations can be swapped
   - **Physics Values**: Centralized physics constants (friction, elasticity, gravity)
   - **Kinematics**: Position, velocity, acceleration calculations
   - **Rotation Dynamics**: Angular velocity and spin calculations
   - **Boundary Handling**: World edge collision detection and response

4. **Rendering Pipeline**
   - Separation of game state from visual representation
   - Sprite-based rendering with asset management
   - Layered rendering (backgrounds, entities, decorators, UI)
   - Screen space transformations
   - Double buffering for smooth animation

5. **Input Handling**
   - Keyboard event processing (KeyListener implementation)
   - Command pattern for player actions (thrust, rotate, fire)
   - Input state management for continuous vs. discrete actions

6. **Asset Management**
   - Asset loading and caching system
   - Asset catalog organization
   - Resource lifecycle management
   - Support for different asset types (images, sprites)

7. **World Building**
   - Procedural world generation
   - Static and dynamic entity placement
   - Background and decorator systems
   - Configurable world definitions

8. **Game Mechanics**
   - Weapon systems with projectile physics
   - Entity spawning and lifecycle (LifeGenerator)
   - Event-driven behavior system
   - Rule-based game logic (boundary rules, collision rules)

### Advanced Concepts

- **State Machines**: ModelState and EngineState for lifecycle management
- **Camera/Viewport**: World dimension vs. screen dimension concepts
- **Performance Optimization**: Efficient entity lookups, minimal object allocation in hot paths
- **Extensibility**: Abstract base classes allowing new entity types, physics engines, and weapons without modifying existing code

## Technical Highlights

- **Language**: Java (leveraging strong OOP features)
- **GUI Framework**: Java Swing for cross-platform rendering
- **Architecture**: Strict MVC separation
- **Concurrency**: Multithreaded architecture with thread-safe collections
- **Design**: UML class diagrams guide implementation
- **Scalability**: Designed to support 1000+ dynamic entities with configurable limits

## Project Structure

The codebase is organized into well-defined packages, each with clear responsibilities:

### Core MVC Packages

- **`main`**: Application entry point that bootstraps the engine, wires dependencies, and starts the simulation
- **`model`**: Game state and simulation logic (entities, physics, weapons, events, actions)
  - **`model.entities`**: Entity implementations (DynamicBody, StaticBody, PlayerBody, AbstractEntity, DecoEntity)
  - **`model.physics`**: Physics engine implementations (BasicPhysicsEngine, SpinPhysicsEngine, NullPhysicsEngine, AbstractPhysicsEngine)
  - **`model.weapons`**: Weapon system implementations (BasicWeapon, WeaponDto)
- **`view`**: Presentation layer handling rendering and display (View, Renderer, ControlPanel)
  - **`view.renderables`**: Visual representation objects for entities (DBodyRenderable, EntityRenderable, EntityInfoDTO)
- **`controller`**: Mediator coordinating Model and View, processing user input and managing engine state

### Supporting Packages

- **`assets`**: Asset management system for loading and organizing visual resources (Assets, AssetCatalog, AssetInfo, AssetType)
- **`world`**: World definition and configuration (WorldDefinition, BackgroundDef, DynamicBodyDef, StaticBodyDef, DecoratorDef)
  - **`world.providers`**: Factory implementations for generating different world configurations (RandomWorldDefinitionProvider)
- **`generators`**: Procedural content generators (SceneGenerator for static scene setup, LifeGenerator for dynamic entity spawning)
- **`fx`**: Visual effects system for animations and particle effects (Fx, FxImage, Spin)
- **`_helpers`**: Utility classes for common operations (DoubleVector for 2D vector math, RandomArrayList)
- **`_images`**: Image loading and caching infrastructure (Images, ImageCache, ImageDTO, CachedImageKeyDTO)
- **`resources`**: Static resources including sprite images organized by type (backgrounds, gravity_bodies, solid_bodies, space_decors, spaceship, ui_decors, weapons)

This package structure follows a clear architectural separation, making it easy to locate functionality and understand the system's organization.

## Documentation

### Architecture Documentation

For detailed architectural documentation of the main classes, including design patterns, threading models, and implementation guidelines, see:

**[ARCHITECTURE.md](ARCHITECTURE.md)** - Comprehensive documentation extracted from source code headers covering:
- MVC Core Components (Controller, Model, View, Renderer)
- Entity System (DynamicBody, StaticBody)
- Weapon System (AbstractWeapon)

This documentation provides in-depth explanations of concurrency strategies, lifecycle management, and design philosophies used throughout the codebase.

### Design Patterns Documentation

Learn about the architectural patterns and design patterns used in this project:

- **[Introduction to MVC](docs/en/MVC.md)** - General introduction to the Model-View-Controller pattern
- **[MVC Pattern Implementation](docs/en/MVC-Pattern.md)** - How MVC is implemented in this project, what it provides, common mistakes, and best practices
- **[Factory Pattern](docs/en/Factory-Pattern.md)** - How the Factory pattern is used for object creation, benefits, implementation details, and common pitfalls
- **[Strategy Pattern](docs/en/Strategy-Pattern.md)** - How the Strategy pattern enables interchangeable algorithms (physics engines), usage examples, and anti-patterns
- **[DTO Pattern](docs/en/DTO-Pattern.md)** - Data Transfer Objects for safe data transfer between layers, thread safety, and implementation guidelines

Each pattern document explains:
- **What it provides**: The benefits and advantages of using the pattern
- **How it's implemented**: Concrete implementation details from this codebase
- **Common mistakes**: Anti-patterns and errors to avoid when implementing the pattern
- **Best practices**: Guidelines for proper implementation and usage

## Getting Started

To run the simulation:

1. Compile all Java source files in the `src` directory
2. Run the `Main` class located in `src/main/Main.java`
3. Use keyboard controls to interact with the player entity
4. Observe the physics simulation and entity behaviors

## Learning Path

For students and learners, we recommend exploring the codebase in this order:

1. **Start with Architecture**: Examine `Main.java` to understand the bootstrap sequence
2. **Study MVC Components**: Read `Model.java`, `View.java`, and `Controller.java` to understand the architecture
3. **Explore Entities**: Investigate `DynamicBody`, `StaticBody`, and `PlayerBody` classes
4. **Understand Physics**: Compare different `PhysicsEngine` implementations
5. **Analyze Threading**: Trace the execution flow between render loop and simulation loop
6. **Examine Event System**: Study how `EventDTO` and `ActionDTO` enable behavior
7. **Review Asset Management**: Understand the `Assets` and asset loading system

This project demonstrates that game engines are not magic—they're well-structured software systems built on solid programming fundamentals. By studying and modifying this codebase, learners gain practical experience with professional software engineering practices while exploring the exciting domain of game development.

## License

This project is released under the Creative Commons CC0 1.0 Universal license, making it freely available for educational use, modification, and distribution.
