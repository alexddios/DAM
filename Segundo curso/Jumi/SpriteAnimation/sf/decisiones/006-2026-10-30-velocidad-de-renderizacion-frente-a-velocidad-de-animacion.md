# Velocidad de renderización frente a velocidad de animación

- **Origen:** SpriteAnimation, 2026-10-30, al preparar el documento de visión. El propietario: «tiene que explicar la diferencia de velocidad renderización de la velocidad de las animaciones».
- **Contexto:** quien empieza suele atar el avance de la animación a cada repintado, y la animación cambia de velocidad según el equipo.
- **Decisión:** la demo explica que la velocidad de renderización (cuántas veces por segundo pinta el hilo de la vista) y la velocidad de cada animación (cuántos fotogramas por segundo avanza el hilo de cada sprite) son independientes.
- **Consecuencias:** los dos sprites animan a ritmos distintos, y ninguno depende del ritmo de pintado. Los hilos, que siguen siendo un tema secundario, son la pieza que hace visible esta diferencia (ver «Un hilo por sprite y un hilo de pintado»).
