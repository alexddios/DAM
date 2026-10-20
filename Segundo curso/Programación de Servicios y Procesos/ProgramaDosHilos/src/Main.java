import model.IModel;
import model.MyModel;
import presenter.IPresenter;
import presenter.MyPresenter;
import view.IView;
import view.MyView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Usamos Runnable explícito en lugar de lambda
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
    }
}