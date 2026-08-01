# 🔴⚪ Pokémon Team Builder & Pokédex

Una aplicación web interactiva desarrollada con Vanilla JavaScript, HTML5 y CSS3 que permite explorar un catálogo de 80 Pokémon clásicos y crear tu equipo ideal de 6 integrantes.

---

## 📋 Documentación del Proyecto

### **1. Idea general del proyecto**
El proyecto es una aplicación web interactiva de una sola página (Single Page Application) que funciona como una Pokédex y un creador de equipos (*Team Builder*). Permite al usuario explorar un catálogo de 80 Pokémon clásicos (evoluciones finales o únicas de la primera generación), buscar por nombre, filtrar por tipo y seleccionar hasta 6 Pokémon para formar un equipo. Los cambios, como la actualización del equipo o los resultados de búsqueda, se reflejan en tiempo real en la interfaz.

### **2. Estructura básica del trabajo**
El proyecto se ha construido utilizando únicamente tecnologías web nativas (Vanilla), estructuradas en tres capas:
* **HTML5:** Define la estructura semántica de la página. Se divide en un `<header>` principal y un `.contenedor-app` que alberga dos paneles laterales: una `<section>` para el catálogo (Pokédex) y un `<aside>` para el equipo.
* **CSS3:** Se encarga del diseño visual y la disposición. Se ha prescindido de *frameworks* externos, utilizando variables CSS (`:root`) para la paleta de colores y el sistema de diseño.
* **JavaScript:** La lógica está separada en dos archivos para mayor limpieza: `data.js` (almacenamiento de datos estáticos) y `app.js` (manipulación del DOM y gestión de eventos).

### **3. Datos mockeados utilizados**
Los datos están estructurados en el archivo `data.js` mediante un *array* de objetos (`pokemonData`). Se han mockeado 80 Pokémon, cada uno conteniendo: un identificador numérico (`id`), el nombre (`name`), un *array* con sus tipos (`types`), sus estadísticas de combate (`stats`) y una URL (`image`). Para darle un aspecto moderno y dinámico, las URLs de las imágenes apuntan a GIFs animados de los modelos 3D alojados en el repositorio oficial de *Showdown* a través de la PokéAPI.

### **4. Principales decisiones de diseño y organización**
* **Layout Adaptativo:** Se ha utilizado **CSS Flexbox** para la estructura principal (`.contenedor-app`), permitiendo que el panel de la Pokédex (`flex: 2`) tenga más anchura que el panel lateral del equipo (`flex: 1`).
* **Grid Responsivo:** El catálogo de Pokémon utiliza **CSS Grid** con la regla `grid-template-columns: repeat(auto-fill, minmax(150px, 1fr))`, lo que garantiza que las tarjetas se adapten automáticamente a cualquier tamaño de pantalla.
* **Navegación Fluida:** El panel lateral del equipo utiliza `position: sticky; top: 20px;`, lo que permite que el equipo seleccionado acompañe visualmente al usuario mientras hace *scroll*.
* **Experiencia de Usuario (UX):** Se han incluido enlaces dinámicos en las imágenes y nombres que abren la *WikiDex* en una pestaña nueva (`target="_blank"`), evitando que se pierda el progreso. Los tipos de Pokémon están representados con *badges* coloreados para una rápida identificación visual.

### **5. Explicación general del código y funcionalidades**
El archivo `app.js` gestiona el estado y la interactividad mediante las siguientes técnicas:
* **Renderizado Dinámico:** La función `renderListaPokemon` vacía el contenedor HTML y lo reconstruye iterando sobre el *array* de datos utilizando *Template Literals* (`` ` ``) para inyectar el código HTML.
* **Gestión de Estado Centralizada:** Se utilizan dos variables globales (`textoActualSeleccionado` y `filtroTipoSeleccionado`). La función `aplicarFiltro()` lee ambas variables y encadena el método `.filter()` sobre el *array* original para mostrar siempre el resultado correcto combinando ambos filtros.
* **Delegación de Eventos:** Para mejorar el rendimiento, los *Event Listeners* se asignan a los contenedores padre (`#lista-pokemon`, `#filtros`, `#slots-equipo`). Mediante `event.target.classList.contains()` se intercepta el clic exacto, extrayendo los datos necesarios con atributos personalizados (`data-id`, `data-type`, `data-index`).

### **6. Uso de la IA**
La inteligencia artificial se ha utilizado como tutor y asistente de programación durante el desarrollo del proyecto.
* **¿Para qué se ha utilizado?** Para generar el extenso *array* de datos de prueba (`data.js`), estructurar la lógica inicial de JavaScript, y como herramienta de depuración para identificar y corregir errores de sintaxis o variables mal escritas.
* **¿En qué parte ha influido?** Principalmente en la implementación de técnicas avanzadas como la delegación de eventos en JS, el uso de CSS Grid/Flexbox y la resolución de conflictos de estado entre los filtros de búsqueda y los botones.
* **¿Qué se ha adaptado?** El código se ha adaptado al idioma español para mejorar su legibilidad (`miEquipo`, `renderListaPokemon`). Además, se ajustaron las propiedades CSS para asegurar que los elementos visuales se enlazaran correctamente con las interacciones del DOM.

---

## ✨ Características de Uso

* **Búsqueda en tiempo real:** Encuentra rápidamente cualquier Pokémon tecleando su nombre.
* **Filtrado combinado:** Organiza el catálogo pulsando sobre los botones de tipo. Los filtros de texto y tipo se combinan de forma natural sin solaparse.
* **Gestión de Equipo Avanzada:** Añade y elimina Pokémon haciendo clic. El sistema alerta y bloquea la adición si se intenta superar el límite de 6 miembros o si se intenta añadir un Pokémon que ya está en la plantilla.

## 📁 Estructura de Archivos

El proyecto no requiere dependencias externas, librerías adicionales ni procesos de compilación (*build tools*):

```text
/
├── index.html        # Estructura principal de la aplicación
├── css/
│   └── style.css     # Estilos visuales, maquetación y variables de color
└── js/
    ├── data.js       # Base de datos mockeada con la información de los 80 Pokémon
    └── app.js        # Lógica de la aplicación, filtros y manipulación del DOM
```
## 🚀 Instalación y Uso

1. Descarga o clona este repositorio en tu ordenador.
    
2. Asegúrate de mantener la estructura de carpetas intacta.
    
3. Abre el archivo `index.html` en cualquier navegador web moderno (Chrome, Firefox, Safari, Edge).
    
4. ¡Empieza a buscar y a construir tu equipo Pokémon!
    

## 👨‍💻 Autor

Proyecto desarrollado como práctica de programación Front-End, centrándose en el uso de JavaScript nativo, la correcta gestión del DOM y la estructuración de interfaces de usuario modernas.
