package model;

public class Consumer implements Runnable {
    private Resource resource;
    private IModel model;

    public Consumer(Resource resource, IModel model) {
        this.resource = resource;
        this.model = model;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            resource.remove();
            try {
                Thread.sleep((long) (Math.random() * 2)); // Simula un retraso
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
