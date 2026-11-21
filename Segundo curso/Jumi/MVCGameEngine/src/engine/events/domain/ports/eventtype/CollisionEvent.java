package engine.events.domain.ports.eventtype;

import engine.events.domain.core.AbstractDomainEvent;
import engine.events.domain.ports.BodyRefDTO;
import engine.events.domain.ports.DomainEventType;
import engine.events.domain.ports.payloads.CollisionPayload;

public final class CollisionEvent extends AbstractDomainEvent<CollisionPayload> implements DomainEvent {

    public CollisionEvent(
            BodyRefDTO primaryBodyRef,
            BodyRefDTO secondaryBodyRef,
            CollisionPayload payload) {

        super(DomainEventType.COLLISION, primaryBodyRef, secondaryBodyRef, payload);    
        if (secondaryBodyRef == null)
            throw new IllegalArgumentException("CollisionEvent requires secondaryBody");
    }
}