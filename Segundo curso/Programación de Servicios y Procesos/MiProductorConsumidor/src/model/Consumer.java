package model;

import config.Estado;
import config.EstadoHilo;

import java.util.List;
import java.util.Random;

/**
 * Hilo consumidor. Funciona igual que Producer, pero retira unidades del recurso.
 * Es la TAREA (Runnable); el Thread que la ejecuta lo crea Model.
 */
public class Consumer implements Runnable{
    // Recursos con los que puede trabajar: uno solo en modo fijo, todos en modo variable.
    private final List<Resource> resources;
    private final int tiempoMin;
    private final int tiempoMax;
    // Unidades que debe consumir. Integer.MAX_VALUE significa ilimitado.
    private final int objetivo;
    private final Random random;

    // volatile: las cambia un hilo y las lee otro (Model al pausar, la Vista al refrescar).
    private volatile int contador;
    private volatile boolean pausado;
    private volatile EstadoHilo estadoHilo;
    private final String nombre;



    public Consumer(List<Resource> resources, int tiempoMin, int tiempoMax, int objetivo, String nombre) {
        this.resources = resources;
        random = new Random();
        this.tiempoMin = tiempoMin;
        this.tiempoMax = tiempoMax;
        this.contador =0;
        this.pausado = false;
        this.objetivo = objetivo;
        this.estadoHilo = EstadoHilo.PAUSA;
        this.nombre=nombre;
    }
    @Override
    public void run(){
        try{
            // Bucle de trabajo: dura hasta alcanzar el objetivo o hasta que lo interrumpan.
            while (contador < objetivo){
                // Bucle de pausa: espera activa mientras el Model lo tenga en pausa.
                while (pausado) {
                    this.estadoHilo = EstadoHilo.PAUSA;
                    Thread.sleep(50);
                }
                // Simula el tiempo que tarda en consumir la unidad.
                this.estadoHilo = EstadoHilo.TRABAJANDO;
                Thread.sleep(random.nextInt(tiempoMin,tiempoMax+1));
                // Puede quedarse esperando dentro de consumir() si el recurso está vacío.
                this.estadoHilo = EstadoHilo.ESPERANDO;
                // Se elige el recurso en cada consumición (en modo fijo siempre el mismo).
                Resource elegido = resources.get(random.nextInt(resources.size()));
                elegido.consumir();
                this.contador++;
//                System.out.printf("%s -> %s: %d %n",Thread.currentThread().getName(),elegido.getNombre(),elegido.getStock());
            }
            // Ha llegado a su objetivo: termina por sí solo.
            this.estadoHilo = EstadoHilo.ACABADO;

        }catch (InterruptedException e){
            // Detener: interrupt() lo saca de sleep()/wait() con esta excepción.
            // No es un error, es la señal para terminar.
            this.estadoHilo = EstadoHilo.ACABADO;
        }
    }
    public int getContador(){
        return contador;
    }

    /** Lo llama Model desde otro hilo; el consumidor lo verá en su bucle de pausa. */
    public void pausar(){
        this.pausado = true;
    }
    public void reanudar(){
        this.pausado = false;
    }
    public EstadoHilo getEstadoHilo(){
        return this.estadoHilo;
    }
    public String getNombre(){
        return this.nombre;
    }

    public int getObjetivo() {
        return objetivo == Integer.MAX_VALUE ? -1 : objetivo ;
    }
}
