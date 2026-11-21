# INFORME DE DIAGNÓSTICO
## Vibración (Jitter) ligada a cámara suavizada en MVCGameEngine

---

## 1. Estado del problema

**Decisión actual**  
Se descarta el suavizado de cámara en producción debido a vibraciones perceptibles a altas velocidades.

**Objetivo de este informe**  
Conservar todo el conocimiento adquirido para poder retomar el problema en el futuro sin pérdida de contexto.

---

## 2. Hechos objetivos confirmados (constraints)

Estos hechos están empíricamente demostrados mediante logs e instrumentación:

| ID | Hecho | Estado |
|----|------|--------|
| F1 | Sin cámara no hay jitter | Confirmado |
| F2 | Sin suavizado de cámara no hay jitter | Confirmado |
| F3 | El jitter aumenta con la velocidad del player | Confirmado |
| F4 | Aumentar la frecuencia de actualización del mundo reduce el jitter | Confirmado |
| F5 | Render y simulación no están acoplados | Arquitectura |
| F6 | El jitter se manifiesta como saltos y reversas discretas en píxeles | Logs |
| F7 | No depende de BufferStrategy ni de sync | Confirmado |

---

## 3. Tipos generales de problemas que generan jitter (visión conceptual)

| Problema | Cuándo ocurre | Causa típica |
|--------|---------------|--------------|
| Aliasing temporal | Simulación a menor Hz que el render | Sample-and-hold de posiciones |
| Filtro con memoria | Cámara suavizada persiguiendo señal discreta | Overshoot y correcciones |
| Cuantización a píxel | Movimiento sub-píxel acumulado | round / floor |
| Feedback discreto | Estado interno + cuantización | Stick–slip visual |
| dt no invariante | dt variable sin normalización | Ganancia dependiente del frame |
| Desfase sim/render | Lectura de estados inconsistentes | Falta de interpolación |

---

## 4. Problemas analizados en el código

### Evaluación final basada en logs

| Hipótesis | Estado | Motivo |
|----------|--------|--------|
| Tearing / BufferStrategy | Descartado | No depende de smoothing ni velocidad |
| Render duplicado | Descartado | No hay entidades duplicadas |
| Limpieza de frame | Descartado | Fondo limpio, problema persiste |
| Error puntual de dt | Descartado | Clamp no elimina jitter |
| Redondeo a píxel | Contribuyente | Amplifica pero no origina |
| Cámara suavizada | Necesaria | Sin ella no hay jitter |
| Desacople sim/render | Muy probable | Mejora al subir Hz del mundo |
| Sample-and-hold del target | Confirmado indirectamente | Desired cambia a escalones |
| Filtro persiguiendo escalones | Confirmado | Overshoot + reversas |
| Overshoot de cámara | Confirmado | dC > dW repetidamente |

---

## 5. Modelo mental consolidado

Pipeline efectivo:

```
World (ticks discretos)
   ↓
Sample-and-hold por frame
   ↓
Desired (escalonado)
   ↓
Cámara suavizada (filtro con memoria)
   ↓
Cuantización a píxel
   ↓
Reversas visibles (jitter)
```

La cámara no crea el problema, lo hace visible.

---

## 6. Instrumentación de depuración utilizada

### Métricas de movimiento

| Métrica | Cálculo | Propósito |
|-------|---------|-----------|
| dW | worldY - lastWorldY | Avance real del mundo |
| dC | cameraY - lastCameraY | Avance de la cámara |
| dS | (worldY - cameraY) - lastScreenY | Movimiento en pantalla |
| overshoot | dC - dW | Adelanto de cámara |
| ratio | dC / dW | Ganancia efectiva |

### Cuantización y reversas

| Métrica | Cálculo | Detecta |
|--------|---------|---------|
| screenRound | round(screenY) | Posición entera |
| dSRound | Δ round(screenY) | Saltos por píxel |
| screenFloor | floor(screenY) | Comparativa |
| pixelReverseCount | dW>0 && dSRound<0 | Reversa visual |
| pixelReverseMagnitude | abs(dSRound) | Severidad |

### Jerk (cambio brusco)

| Métrica | Cálculo | Uso |
|------|---------|-----|
| jerk | abs(dSRound - lastDSRound) | Cambio abrupto |
| jerkCount | jerk >= threshold | Frecuencia |
| jerkMagnitude | acumulado | Impacto |

### Estadísticas temporales

| Métrica | Cálculo | Uso |
|-------|---------|-----|
| frameDtNs | now - lastFrame | Variabilidad |
| dtMin / dtMax | por periodo | Correlación |
| Ventana 0.5s | reset contadores | Tendencias |

---

## 7. Qué NO es el problema

- No es un bug puntual
- No es tearing
- No es un problema de render
- No es solo un round incorrecto
- No es ruido aleatorio

Es un problema estructural clásico:

**Simulación discreta + cámara con memoria + render continuo**

---

## 8. Estado final

- El motor es estable sin jitter sin suavizado.
- El diagnóstico es completo y reproducible.
- El problema queda documentado para futura reintroducción del suavizado con otro enfoque.

