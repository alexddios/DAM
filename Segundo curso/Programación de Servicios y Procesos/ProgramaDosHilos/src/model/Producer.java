package model;

public class Producer implements Runnable {
    private Resource resource;
    private IModel model;

    public Producer(Resource resource, IModel model) {
        this.resource = resource;
        this.model = model;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            resource.add();
            // Le decimos al modelo que hemos actualizado el contador
            // Nota: En una versión avanzada, aquí iría código para notificar al presentador,
            // pero como no están sincronizados, el resultado será caótico (que es lo que queremos ver).
            try {
                Thread.sleep((long) (Math.random() * 2)); // Simula un retraso de 0 a 1 milisegundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
