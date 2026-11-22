package engine.utils.helpers;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author juanm
 */
public class RandomArrayList<T> extends ArrayList<T> {

    // Constructor vacío
    public RandomArrayList() {
        super();
    }

    public RandomArrayList(int size) {
        super(size);
    }

    // Constructor que acepta otra colección (opcional)
    public RandomArrayList(java.util.Collection<? extends T> c) {
        super(c);
    }

    // Método que devuelve un elemento aleatorio de la lista
    public T choice() {
        if (this.isEmpty()) {
            throw new IllegalStateException("La lista está vacía");
        }
        int index = ThreadLocalRandom.current().nextInt(this.size());
        return this.get(index);
    }
}

