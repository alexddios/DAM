# Explicación del proyecto `ProgramaDosHilos`  
  
El proyecto **ProgramaDosHilos** es una aplicación Java de escritorio desarrollada con **Swing**. Su objetivo es demostrar el funcionamiento de dos hilos concurrentes mediante el patrón **Productor-Consumidor**.  
  
Además, el proyecto utiliza una arquitectura **MVP**:  
  
- **Model**: contiene la lógica de la aplicación.  
- **View**: contiene la interfaz gráfica.  
- **Presenter**: conecta el modelo con la vista.  
  
## 1. Estructura del proyecto  
  
```text  
src  
├── Main.java  
├── diagrama.py  
├── listeners  
│   └── AccionBoton.java  
├── model  
│   ├── Consumer.java  
│   ├── IModel.java  
│   ├── MyModel.java  
│   ├── Producer.java  
│   └── Resource.java  
├── presenter  
│   ├── IPresenter.java  
│   └── MyPresenter.java  
└── view  
    ├── IView.java    └── MyView.java  
```  
  
## 2. Inicio de la aplicación  
  
El archivo `Main.java` es el punto de entrada del programa. La aplicación inicia la interfaz gráfica dentro del hilo de eventos de Swing usando `SwingUtilities.invokeLater()`.  
  
```java  
SwingUtilities.invokeLater(new Runnable() {  
    @Override  
    public void run() {  
        // Instanciamos el Modelo y tu nueva Vista  
        IView view = new MyView();  
        IModel model = new MyModel();  
  
        // Conectamos todo a través del Presentador  
        IPresenter presenter = new MyPresenter(view, model);  
  
        // Mostramos la ventana  
        ((MyView) view).setVisible(true);  
    }  
});
```  
  
Se crean tres elementos principales:  
  
- `MyView`: representa la ventana.  
- `MyModel`: contiene la lógica de la simulación.  
- `MyPresenter`: conecta la vista con el modelo.  
  
## 3. Arquitectura MVP  
  
El proyecto utiliza el patrón **Model-View-Presenter**.  
  
### Modelo  
  
El modelo contiene la lógica de la aplicación y controla la simulación.  
  
Clases principales:  
  
- `IModel`  
- `MyModel`  
- `Resource`  
- `Producer`  
- `Consumer`  
  
### Vista  
  
La vista se encarga de mostrar la interfaz gráfica.  
  
Clases principales:  
  
- `IView`  
- `MyView`  
  
### Presentador  
  
El presentador sirve de intermediario entre la vista y el modelo.  
  
Clases principales:  
  
- `IPresenter`  
- `MyPresenter`  
  
La vista no llama directamente al modelo. En su lugar, utiliza el presentador.  
  
## 4. La vista gráfica  
  
La clase `MyView` hereda de `JFrame` e implementa la interfaz `IView`:  
  
```java  
public class MyView extends JFrame implements IView  
```  
  
La ventana utiliza `GridBagLayout` y contiene varios paneles:  
  
- Un panel `CP` con el botón de inicio.  
- Un panel `DP` con una tabla.  
- Un `viewerPanel` que muestra el resultado de la simulación.  
  
El botón principal se crea así:  
  
```java  
startButton = new JButton("Iniciar Simulación");  
```  
  
También se crea una etiqueta para mostrar el resultado:  
  
```java  
resultLabel = new JLabel("Resultado: Esperando...");  
```  
  
Cuando se configura el presentador, se añade el listener al botón:  
  
```java  
startButton.addActionListener(  
    new AccionBoton(this.presenter, this.resultLabel));  
```  
  
## 5. Funcionamiento del botón  
  
La clase `AccionBoton` implementa `ActionListener`:  
  
```java  
public class AccionBoton implements ActionListener  
```  
  
Cuando el usuario pulsa el botón, se ejecuta el método `actionPerformed()`:  
  
```java  
@Override  
public void actionPerformed(ActionEvent e) {  
    // Obtenemos el "comando" del botón que ha sido pulsado  
    String comando = e.getActionCommand();  
  
    // Evaluamos qué botón se pulsó  
    switch (comando) {  
        case "Simulando":  
            if (presenter != null) {  
                resultLabel.setText("Simulando...");  
                presenter.onSimulationStarted();  
            }  
            break;  
  
        default:  
            System.out.println("Botón no reconocido");  
            break;  
    }  
}
```  
  
El flujo es el siguiente:  
  
1. Se pulsa el botón.  
2. Se cambia el texto a `Simulando...`.  
3. Se llama al presentador.  
4. El presentador inicia la simulación en el modelo.  
  
## 6. El presentador  
  
La clase `MyPresenter` implementa `IPresenter`:  
  
```java  
public class MyPresenter implements IPresenter  
```  
  
Su constructor conecta la vista y el modelo:  
  
```java  
public MyPresenter(IView view, IModel model) {  
    this.view = view;  
    this.model = model;  
  
    // Conectamos bidireccionalmente  
    this.view.setPresenter(this);  
    this.model.setPresenter(this);  
}
```  
  
Cuando la vista solicita iniciar la simulación:  
  
```java  
@Override  
public void onSimulationStarted() {  
    model.startSimulation();  
}  
```  
  
Cuando el modelo termina, el presentador actualiza la vista:  
  
```java  
@Override  
public void updateCounter(int value) {  
    view.updateCount(value);  
}  
```  
  
El presentador evita que la vista y el modelo tengan que comunicarse directamente.  
  
## 7. El modelo  
  
La clase `MyModel` implementa `IModel` y contiene el recurso compartido:  
  
```java  
private Resource resource;  
```  
  
El recurso se inicializa en el constructor:  
  
```java  
public MyModel() {  
    this.resource = new Resource();
}  
```  
  
La simulación se inicia en el método `startSimulation()`:  
  
```java  
@Override  
public void startSimulation() {  
    Producer p = new Producer(resource, this);  
    Consumer c = new Consumer(resource, this);  
  
    Thread threadP = new Thread(p);  
    Thread threadC = new Thread(c);  
  
    threadP.start();  
    threadC.start();  
}  
```  
  
Se crean dos objetos:  
  
- `Producer`: productor.  
- `Consumer`: consumidor.  
  
Después, cada uno se ejecuta en su propio hilo.  
  
## 8. El recurso compartido  
  
La clase `Resource` contiene un contador:  
  
```java  
public class Resource {  
    private int count = 0;
}  
```  
  
Tiene tres métodos:  
  
```java  
public int getCount() {  
    return count;
}  
  
public void add() {  
    count++;
}  
  
public void remove() {  
    count--;
}  
```  
  
Este contador es utilizado simultáneamente por el productor y el consumidor.  
  
## 9. El productor  
  
La clase `Producer` implementa `Runnable`:  
  
```java  
public class Producer implements Runnable  
```  
  
Su método `run()` se ejecuta cuando comienza su hilo:  
  
```java  
@Override  
public void run() {  
    for (int i = 0; i < 10000; i++) {  
        resource.add();  
  
        try {  
            Thread.sleep((long) (Math.random() * 2));  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}  
```  
  
El productor realiza las siguientes acciones:  
  
1. Repite el proceso 10.000 veces.  
2. Incrementa el contador.  
3. Duerme entre 0 y 1 milisegundos.  
4. Vuelve a repetir el proceso.  
  
## 10. El consumidor  
  
La clase `Consumer` también implementa `Runnable`:  
  
```java  
public class Consumer implements Runnable  
```  
  
Su funcionamiento es similar al del productor:  
  
```java  
@Override  
public void run() {  
    for (int i = 0; i < 10000; i++) {  
        resource.remove();  
  
        try {  
            Thread.sleep((long) (Math.random() * 2));  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}  
```  
  
El consumidor:  
  
1. Repite el proceso 10.000 veces.  
2. Decrementa el contador.  
3. Duerme entre 0 y 1 milisegundos.  
4. Continúa hasta terminar las 10.000 iteraciones.  
  
## 11. Uso de varios hilos  
  
El programa utiliza dos hilos principales:  
  
```java  
Thread threadP = new Thread(p);  
Thread threadC = new Thread(c);  
```  
  
Después se inician:  
  
```java  
threadP.start();  
threadC.start();  
```  
  
Ambos hilos modifican al mismo tiempo el objeto `Resource`.  
  
También se utiliza un tercer hilo monitor:  
  
```java  
Thread monitorThread = new Thread(new Runnable() {  
    @Override  
    public void run() {  
        try {  
            threadP.join();  
            threadC.join();  
  
            if (presenter != null) {  
                presenter.updateCounter(resource.getCount());  
            }  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
});  
```  
  
Las instrucciones `join()` hacen que el hilo monitor espere a que terminen el productor y el consumidor:  
  
```java  
threadP.join();  
threadC.join();  
```  
  
Cuando ambos terminan, se obtiene el valor final del contador y se envía a la vista.  
  
## 12. Condición de carrera  
  
El contador `count` es compartido por dos hilos, pero sus métodos no están sincronizados:  
  
```java  
public void add() {  
    count++;
}  
  
public void remove() {  
    count--;
}  
```  
  
Esto puede producir una **condición de carrera**.  
  
Aunque el productor suma 10.000 veces y el consumidor resta 10.000 veces, el resultado no tiene por qué ser siempre exactamente `0`.  
  
Esto ocurre porque operaciones como:  
  
```java  
count++;  
```  
  
realmente implican varios pasos:  
  
1. Leer el valor actual.  
2. Incrementarlo.  
3. Guardar el nuevo valor.  
  
Si dos hilos acceden al contador simultáneamente, pueden sobrescribirse los valores y perderse actualizaciones.  
  
Por este motivo, el resultado final puede ser diferente en cada ejecución.  
  
## 13. Actualización de la interfaz  
  
Cuando termina la simulación, el modelo llama al presentador:  
  
```java  
presenter.updateCounter(resource.getCount());  
```  
  
El presentador envía el valor a la vista:  
  
```java  
view.updateCount(value);  
```  
  
La vista actualiza la etiqueta usando `SwingUtilities.invokeLater()`:  
  
```java  
@Override  
public void updateCount(int currentCount) {  
    SwingUtilities.invokeLater(new Runnable() {  
        @Override  
        public void run() {  
            resultLabel.setText("Resultado final: " + currentCount);  
        }  
    });  
}  
```  
  
Esto garantiza que la interfaz gráfica se modifique desde el hilo de eventos de Swing.  
  
## 14. Flujo completo de ejecución  
  
```text  
El usuario pulsa "Iniciar Simulación"  
                ↓AccionBoton recibe el evento  
                ↓MyPresenter.onSimulationStarted()  
                ↓MyModel.startSimulation()  
                ↓Se crean Producer y Consumer  
                ↓Se inician dos hilos  
                ↓Producer incrementa Resource  
Consumer decrementa Resource  
                ↓El hilo monitor espera con join()  
                ↓Se obtiene el valor final del contador  
                ↓MyPresenter.updateCounter()  
                ↓MyView.updateCount()  
                ↓Se muestra el resultado en la ventana  
```  
  
## 15. Diagrama de clases  
  
El archivo `diagrama.py` utiliza la biblioteca `graphviz` para crear un diagrama de clases del proyecto.  
  
Representa:  
  
- Las interfaces `IView`, `IModel` e `IPresenter`.  
- Las clases que implementan dichas interfaces.  
- Las relaciones entre `MyView`, `MyModel` y `MyPresenter`.  
- La relación entre `Producer`, `Consumer` y `Resource`.  
- La implementación de `Runnable`.  
  
## 16. Conceptos que se practican  
  
Este proyecto permite practicar los siguientes conceptos:  
  
- Programación orientada a objetos.  
- Interfaces en Java.  
- Patrón arquitectónico MVP.  
- Creación y ejecución de hilos.  
- Uso de `Runnable`.  
- Uso de `Thread`.  
- Sincronización mediante `join()`.  
- Recursos compartidos.  
- Condiciones de carrera.  
- Programación concurrente.  
- Interfaces gráficas con Swing.  
- Gestión de eventos con `ActionListener`.  
- Uso de `GridBagLayout`.  
- Comunicación entre modelo, vista y presentador.