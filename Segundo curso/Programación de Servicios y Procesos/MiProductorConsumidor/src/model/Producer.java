package model;

import config.EstadoHilo;

import java.util.List;
import java.util.Random;

/**
 * Hilo productor. Es la TAREA (Runnable); el Thread que la ejecuta lo crea Model.
 * En cada vuelta: espera si está en pausa, "fabrica" (duerme un tiempo
 * aleatorio) y deja la unidad en uno de sus recursos.
 */
public class Producer implements Runnable{
    // Recursos con los que puede trabajar: uno solo en modo fijo, todos en modo variable.
    private final List<Resource> resources;
    private final int tiempoMin;
    private final int tiempoMax;
    private final Random random;
    // volatile: estas variables las cambia un hilo y las lee otro
    // (el propio productor, Model al pausar, la Vista al refrescar).
    private volatile int contador;
    private volatile boolean pausado;
    // Unidades que debe producir. Integer.MAX_VALUE significa ilimitado.
    private final int objetivo;
    private volatile EstadoHilo estadoHilo;
    private final String nombre;


    public Producer(List<Resource> resources,int tiempoMin, int tiempoMax, int objetivo, String nombre) {
        this.resources = resources;
        random = new Random();
        this.tiempoMin = tiempoMin;
        this.tiempoMax = tiempoMax;
        this.contador=0;
        this.pausado = false;
        this.objetivo =objetivo;
        this.estadoHilo = EstadoHilo.PAUSA;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try{
            // Bucle de trabajo: dura hasta alcanzar el objetivo o hasta que lo interrumpan.
            while (contador < objetivo){
                // Bucle de pausa: espera activa mientras el Model lo tenga en pausa.
                // Se mira al principio de cada vuelta, antes de empezar la siguiente unidad.
                while (pausado) {
                    this.estadoHilo = EstadoHilo.PAUSA;
                    Thread.sleep(50);

                }
                // Simula el tiempo que tarda en fabricar la unidad.
                this.estadoHilo = EstadoHilo.TRABAJANDO;
                Thread.sleep(random.nextInt(tiempoMin,tiempoMax+1));
                // Puede quedarse esperando dentro de producir() si el recurso está lleno.
                this.estadoHilo = EstadoHilo.ESPERANDO;

                // Se elige el recurso en CADA producción: en modo fijo la lista
                // tiene un solo elemento y siempre sale el mismo.
                Resource elegido = resources.get(random.nextInt(resources.size()));
                elegido.producir();
                this.contador++;

            }
            // Ha llegado a su objetivo: termina por sí solo.
            this.estadoHilo = EstadoHilo.ACABADO;
        }catch (InterruptedException e){
            // interrupt() (botón Detener) despierta al hilo si está en sleep() o wait()
            // y lanza esta excepción: no es un error, es la señal para terminar.
            this.estadoHilo=EstadoHilo.ACABADO;
        }
    }
    public int getContador(){
        return contador;
    }

    /** Lo llama Model desde otro hilo; el productor lo verá en su bucle de pausa. */
    public void pausar(){
        this.pausado = true;
    }
    public void reanudar(){
        this.pausado = false;
    }
    public EstadoHilo getEstadoHilo(){
        return estadoHilo;
    }
    public String getNombre(){
        return this.nombre;
    }

    /** Devuelve -1 si es ilimitado, para que la Vista no muestre Integer.MAX_VALUE. */
    public int getObjetivo() {
        return objetivo == Integer.MAX_VALUE ? -1 : objetivo ;
    }
}
