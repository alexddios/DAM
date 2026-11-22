package engine.model.bodies.ports;


import java.io.Serializable;


/**
 *
 * @author juanm
 */
public enum BodyState implements Serializable {
    STARTING,
    ALIVE,
    PAUSED,
    COLLIDED,
    HANDS_OFF,
    DEAD
}
