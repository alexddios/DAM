package engine.model.ports;


import java.io.Serializable;


/**
 *
 * @author juanm
 */
public enum ModelState implements Serializable {
    STARTING,
    ALIVE,
    PAUSED,
    STOPPED
}
