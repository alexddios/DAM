package presenter;

import java.util.List;

/** Lo que la vista puede pedirle al Presenter. */
public interface IPresenter {
    /** Crea las animaciones, muestra la ventana y las pone en marcha. */
    void start();

    /** Hoja y frame actual de cada animación, en orden. La vista lo pide en cada repintado. */
    List<AnimacionDTO> getAnimaciones();

    /** Id de la imagen y posición del objeto. La vista lo pide en cada repintado. */
    DinamicObjectDTO getDinamicObject();

    /** Arranca (o reanuda) todas las animaciones y el objeto. */
    void play();

    /** Para todas las animaciones y el objeto. */
    void stop();

    /** Tecla ←: el objeto gira a la izquierda hasta que se llame a dejarGirar. La vista lo llama al pulsar la tecla. */
    void girarIzquierda();

    /** Tecla →: el objeto gira a la derecha hasta que se llame a dejarGirar. La vista lo llama al pulsar la tecla. */
    void girarDerecha();

    /** Soltar ← o →: el objeto deja de girar. La vista lo llama al soltar la tecla. */
    void dejarGirar();

    /** Tecla ↑: enciende el motor; el objeto acelera hacia donde mira hasta que se llame a apagarMotor. */
    void encenderMotor();

    /** Soltar ↑: apaga el motor; el objeto no frena, sigue con la velocidad que llevaba (inercia). */
    void apagarMotor();
}

