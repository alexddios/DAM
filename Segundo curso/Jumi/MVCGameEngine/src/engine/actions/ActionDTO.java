package engine.actions;

import engine.events.domain.ports.eventtype.DomainEvent;
import engine.model.bodies.ports.BodyType;

public class ActionDTO {
    final public String bodyId;
    final public BodyType bodyType;
    final public ActionType type;
    final public DomainEvent relatedEvent;

    public ActionDTO(String bodyId, BodyType bodyType, ActionType type, DomainEvent relatedEvent) {
        this.bodyId = bodyId;
        this.bodyType = bodyType;
        this.type = type;
        this.relatedEvent = relatedEvent;
    }
}