package engine.controller.ports;

import java.io.Serializable;

/**
 *
 * @author juanm
 */
public enum EngineState implements Serializable {
    STARTING,
    ALIVE,
    PAUSED,
    STOPPED
}
