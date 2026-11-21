# Balls

**[English](../README.md)** | **Español**

Un proyecto educativo en Java que demuestra simulación de física 2D en tiempo real a través de un entorno dinámico de bolas rebotantes con elementos de juego interactivos.

Este proyecto sirve como una plataforma de aprendizaje integral para comprender patrones de arquitectura de software, programación concurrente, fundamentos de motores de juego y principios de diseño orientado a objetos.

## Qué Hace el Programa

**Balls** es una simulación de física 2D en tiempo real que presenta entidades dinámicas (bolas/asteroides) que interactúan dentro de un espacio mundial configurable. El programa crea un entorno animado donde:

- **Cuerpos Dinámicos**: Las entidades se mueven, rotan y colisionan según las reglas de física gobernadas por motores de física intercambiables
- **Interacción del Jugador**: Los usuarios pueden controlar una entidad jugador con capacidades de empuje, rotación y disparo usando entradas de teclado
- **Múltiples Motores de Física**: Elige entre diferentes implementaciones de física incluyendo física básica, física de giro y física nula para comportamientos de simulación variados
- **Generación de Mundos**: Mundos generados proceduralmente con fondos personalizables, cuerpos estáticos y elementos decorativos
- **Sistemas de Armas**: Dispara proyectiles con propiedades y comportamientos configurables
- **Generación de Vida**: Sistema automático de generación de entidades que mantiene la actividad en la simulación
- **Renderizado Visual**: Renderizado de gráficos en tiempo real usando Java Swing con gestión de assets para sprites y efectos visuales

La simulación se ejecuta continuamente con una arquitectura multihilo, separando el renderizado (Vista), la lógica del juego (Modelo) y el manejo de entrada del usuario (Controlador) para un rendimiento y mantenibilidad óptimos.