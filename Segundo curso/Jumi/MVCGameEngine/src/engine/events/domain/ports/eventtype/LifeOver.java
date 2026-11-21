package engine.events.domain.ports.eventtype;

import engine.events.domain.core.AbstractDomainEvent;
import engine.events.domain.ports.BodyRefDTO;
import engine.events.domain.ports.DomainEventType;
import engine.events.domain.ports.payloads.NoPayload;

public final class LifeOver extends AbstractDomainEvent<NoPayload> implements DomainEvent {

    public LifeOver(BodyRefDTO primaryBodyRef) {

        super(DomainEventType.LIFE_OVER, primaryBodyRef, null);

    }

}
