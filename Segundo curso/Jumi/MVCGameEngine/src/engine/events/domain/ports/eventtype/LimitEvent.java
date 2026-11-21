package engine.events.domain.ports.eventtype;

import engine.events.domain.core.AbstractDomainEvent;
import engine.events.domain.ports.BodyRefDTO;
import engine.events.domain.ports.DomainEventType;
import engine.events.domain.ports.payloads.NoPayload;

public final class LimitEvent extends AbstractDomainEvent <NoPayload> implements DomainEvent {

    public LimitEvent(
            DomainEventType type,
            BodyRefDTO primaryBodyRef) {

        super(type, primaryBodyRef, null);

        if (type != DomainEventType.REACHED_EAST_LIMIT &&
                type != DomainEventType.REACHED_WEST_LIMIT &&
                type != DomainEventType.REACHED_NORTH_LIMIT &&
                type != DomainEventType.REACHED_SOUTH_LIMIT) {

            throw new IllegalArgumentException("LimitEvent requires a limit event type");
        }
    }

}
