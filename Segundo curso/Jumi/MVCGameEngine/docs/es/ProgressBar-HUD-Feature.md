# Características de Barra de Progreso y Cuenta Atrás - Clase Hud

## Descripción General
La clase `Hud` ha sido extendida con dos nuevos métodos:
1. **`drawProgressBar`** - Muestra barras de progreso visuales
2. **`drawCountdown`** - Muestra temporizadores de cuenta atrás con representación de texto o gráfica

---

## Método de Barra de Progreso

### Firma del Método

```java
public void drawProgressBar(Graphics2D g, int row, String label, double progress, int barWidth)
```

### Parámetros

- **`g`** (Graphics2D): El contexto gráfico donde dibujar
- **`row`** (int): El número de fila donde se debe dibujar la barra de progreso (sigue el mismo sistema de filas que las líneas de texto)
- **`label`** (String): La etiqueta de texto a mostrar antes de la barra de progreso
- **`progress`** (double): El valor del progreso, entre 0.0 (0%) y 1.0 (100%)
- **`barWidth`** (int): El ancho de la barra de progreso en píxeles (ej., 200)

### Características

1. **Progreso Codificado por Color**: La barra cambia automáticamente de color según el progreso:
   - **Rojo**: progreso < 33%
   - **Amarillo**: 33% ≤ progreso < 66%
   - **Verde**: progreso ≥ 66%

2. **Visualización de Porcentaje**: Muestra el porcentaje de progreso como texto junto a la barra

3. **Limitación Automática**: Los valores de progreso se limitan automáticamente al rango válido [0.0, 1.0]

4. **Estilo Consistente**: Se integra perfectamente con el sistema HUD existente (fuente, posicionamiento, colores)

---

## Método de Cuenta Atrás

### Firma del Método

```java
public void drawCountdown(Graphics2D g, int row, String label, double remainingSeconds, double totalSeconds, boolean graphical)
```

### Parámetros

- **`g`** (Graphics2D): El contexto gráfico donde dibujar
- **`row`** (int): El número de fila donde se debe dibujar la cuenta atrás
- **`label`** (String): La etiqueta de texto a mostrar antes de la cuenta atrás
- **`remainingSeconds`** (double): El tiempo restante en segundos
- **`totalSeconds`** (double): La duración total de la cuenta atrás en segundos (usado para representación gráfica)
- **`graphical`** (boolean): Si es true, muestra una barra visual; si es false, muestra solo texto

### Características

1. **Formato de Tiempo Flexible**:
   - Tiempos ≥ 60 segundos: Muestra como "M:SS" (ej., "2:30")
   - Tiempos < 60 segundos: Muestra como "SS.S" (ej., "45.3s")

2. **Modo Texto**: Visualización simple de texto del tiempo restante

3. **Modo Gráfico**: Barra de cuenta atrás visual con codificación de color:
   - **Verde**: Más del 66% del tiempo restante
   - **Amarillo**: 33% a 66% del tiempo restante
   - **Rojo**: Menos del 33% del tiempo restante (¡urgente!)

4. **Limitación Automática**: Los segundos restantes se limitan para prevenir valores negativos

5. **Perfecto para Recargas de Armas**: Ideal para mostrar temporizadores de recarga de munición

---

## Ejemplos de Uso

### Barra de Progreso - Uso Básico

```java
// Crear una instancia de HUD
Hud hud = new Hud(Color.GRAY, 10, 12, 35);
hud.maxLenLabel = 7; // Establecer ancho de etiqueta para alineación correcta

// En tu método de renderizado
public void render(Graphics2D g) {
    // Dibujar una barra de salud al 75% (verde)
    hud.drawProgressBar(g, 1, "Salud", 0.75, 200);
    
    // Dibujar una barra de energía al 50% (amarillo)
    hud.drawProgressBar(g, 2, "Energía", 0.50, 200);
    
    // Dibujar una barra de escudo al 25% (rojo)
    hud.drawProgressBar(g, 3, "Escudo", 0.25, 200);
}
```

### Cuenta Atrás - Ejemplo de Recarga de Armas

```java
// Crear una instancia de HUD
Hud hud = new Hud(Color.GRAY, 10, 12, 35);
hud.maxLenLabel = 10; // Establecer ancho de etiqueta para nombres de armas

// En tu método de renderizado
public void render(Graphics2D g) {
    double recargaArma1 = 5.3;  // 5.3 segundos restantes
    double totalArma1 = 10.0;   // 10 segundos de tiempo total de recarga
    
    double recargaArma2 = 125.0; // 2 minutos 5 segundos restantes
    double totalArma2 = 180.0;   // 3 minutos de tiempo total de recarga
    
    // Dibujar barras de cuenta atrás gráficas
    hud.drawCountdown(g, 1, "Rifle", recargaArma1, totalArma1, true);
    hud.drawCountdown(g, 2, "Lanzador", recargaArma2, totalArma2, true);
    
    // O dibujar cuenta atrás solo texto
    hud.drawCountdown(g, 3, "Pistola", 3.5, 0, false);
}
```

### Usando las Clases de Ejemplo

Las clases `ProgressBarHud` y `CountdownHud` proporcionan ejemplos listos para usar:

```java
// Barras de progreso
ProgressBarHud progressHud = new ProgressBarHud();
progressHud.drawWithProgressBars(g, 0.85, 0.40, 0.95);

// Temporizadores de recarga de armas (gráfico)
CountdownHud countdownHud = new CountdownHud();
countdownHud.drawWeaponReloads(g, 5.3, 10.0, 125.0, 180.0);

// Temporizadores de recarga de armas (solo texto)
countdownHud.drawWeaponReloadsTextOnly(g, 5.3, 125.0);
```

### HUD Personalizado con Características Mixtas

Puedes combinar ambas características en una clase HUD personalizada:

```java
public class MiHudJuego extends Hud {
    public MiHudJuego() {
        super(Color.CYAN, 10, 12, 35);
        this.maxLenLabel = 12;
    }
    
    public void dibujarEstadisticasJuego(Graphics2D g, EstadisticasJugador stats, EstadisticasArma arma) {
        // Mostrar estadísticas del jugador como barras de progreso
        drawProgressBar(g, 1, "Salud", stats.getRatioSalud(), 200);
        drawProgressBar(g, 2, "Escudo", stats.getRatioEscudo(), 200);
        
        // Mostrar recarga de arma como cuenta atrás
        if (arma.estaRecargando()) {
            drawCountdown(g, 3, "Recargando", 
                         arma.getTiempoRecargaRestante(), 
                         arma.getTiempoTotalRecarga(), 
                         true);
        }
    }
}
```

---

## Apariencia Visual

### Barra de Progreso
```
Salud    [████████████░░░░░░░░] 60%
Energía  [██████░░░░░░░░░░░░░░] 30%
Escudo   [████████████████████] 100%
```

### Cuenta Atrás (Gráfico)
```
Rifle      [████████░░░░░░░░░░] 5.3s
Lanzador   [██████████████░░░░] 2:05
```

### Cuenta Atrás (Solo Texto)
```
Pistola     3.5s
Francotirador    1:45
```

---

## Notas

- Ambos métodos calculan automáticamente las dimensiones según el tamaño de la fuente
- Ambos métodos se alinean verticalmente con la línea base del texto
- El estado gráfico (colores) se preserva y restaura automáticamente después de dibujar
- Los métodos funcionan con cualquier configuración de posicionamiento HUD existente
- **Barras de cuenta atrás** muestran el tiempo restante (la barra comienza llena y disminuye a medida que el tiempo transcurre)
  - Barra llena (verde) = mucho tiempo restante
  - Barra parcial (amarillo) = tiempo bajo
  - Casi vacía (rojo) = urgente, casi sin tiempo
- **Barras de progreso** muestran completitud (la barra comienza vacía y aumenta a medida que el progreso avanza)
  - Barra vacía (rojo) = recién comenzado
  - Barra parcial (amarillo) = progresando
  - Barra llena (verde) = casi completo
