package model;

import presenter.IPresenter;

public class MyModel implements IModel {
    private IPresenter presenter;
    private Resource resource;

    public MyModel() {
        this.resource = new Resource();
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void startSimulation() {
        Producer p = new Producer(resource, this);
        Consumer c = new Consumer(resource, this);

        Thread threadP = new Thread(p);
        Thread threadC = new Thread(c);

        threadP.start();
        threadC.start();

        // Hilo monitor usando una clase interna anónima (sin lambda)
        Thread monitorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    threadP.join(); // Espera a que termine el Productor
                    threadC.join(); // Espera a que termine el Consumidor

                    if (presenter != null) {
                        presenter.updateCounter(resource.getCount());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        monitorThread.start();
    }
}
