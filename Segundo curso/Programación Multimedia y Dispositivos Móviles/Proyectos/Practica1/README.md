# Práctica 1: Análisis y adaptación de una aplicación móvil

**Módulo:** 0489 Programació Multimèdia i Dispositius Mòbils  
**Alumno:** Álex De Dios Pallicer  
**Fecha:** 09/10/2026  

## Descripción del Proyecto

Este proyecto es una adaptación de la plantilla predeterminada de Expo y React Native. El objetivo principal de la práctica es demostrar la comprensión de la estructura de una aplicación móvil, la configuración del entorno de desarrollo y la capacidad para modificar componentes de forma razonada.

## Modificaciones Realizadas

Se han completado todos los requisitos exigidos en el apartado 4 de la práctica:

1. **Componente Reutilizable:** Se ha modificado el componente `HintRow` para que reciba datos dinámicos mediante *props* (como el número de paso), utilizándose múltiples veces en la pantalla principal sin duplicar código.
2. **Nuevo Contenido:** Se ha añadido una nueva instancia del componente que incluye información textual nueva y renderiza de forma condicional una imagen (el icono del proyecto).
3. **Apariencia:** Se ha personalizado el diseño global modificando propiedades en `theme.ts` y en los estilos locales:
   - Alineación vertical del contenedor principal (`justifyContent: 'flex-start'`).
   - Color de fondo de los elementos seleccionados (`backgroundSelected`).
   - Ajuste en los márgenes internos (`Spacing`).
4. **Interacción:** Se ha integrado un componente `Counter` que utiliza el *hook* `useState` de React para mantener y actualizar el estado de un contador de clics interactivo.

## Tecnologías Utilizadas

- **Lenguaje:** JavaScript / TypeScript
- **Framework:** React Native
- **Herramienta de desarrollo:** Expo

## Instrucciones de Ejecución

Para ejecutar este proyecto en tu entorno local, sigue estos pasos:

1. Asegúrate de tener instalado [Node.js](https://nodejs.org/).
2. Clona o extrae los archivos de este proyecto.
3. Abre una terminal en la raíz del proyecto y ejecuta el siguiente comando para instalar las dependencias:
   ```bash
   npm install
   ```
4. Inicia el servidor de desarrollo de Expo:
   ```bash
   npx expo start
   ```
5. Escanea el código QR con la aplicación **Expo Go** (Android/iOS) o presiona `a` en la terminal para abrirlo en un emulador de Android (se requiere Android Studio).