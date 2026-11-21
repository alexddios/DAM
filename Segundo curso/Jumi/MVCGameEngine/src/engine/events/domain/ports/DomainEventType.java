package engine.events.domain.ports;

public enum DomainEventType {
    COLLISION,
    LIFE_OVER,
    FIRE_REQUESTED,
    EMIT_REQUESTED, 
    REACHED_WEST_LIMIT, 
    REACHED_EAST_LIMIT, 
    REACHED_NORTH_LIMIT, 
    REACHED_SOUTH_LIMIT
}