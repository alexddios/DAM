package engine.events.domain.core;

import engine.events.domain.ports.BodyRefDTO;
import engine.events.domain.ports.DomainEventType;
import engine.events.domain.ports.payloads.DomainEventPayload;

public abstract class AbstractDomainEvent <P extends DomainEventPayload>{

    public final DomainEventType type;
    public final BodyRefDTO primaryBodyRef;
    public final BodyRefDTO secondaryBodyRef; // nullable
    public final P payload; // nullable

    public AbstractDomainEvent(
            DomainEventType type,
            BodyRefDTO primaryBodyRef,
            BodyRefDTO secondaryBodyRef,
            P payload) {

        if (type == null)
            throw new IllegalArgumentException("Event: type is required!");
        if (primaryBodyRef == null)
            throw new IllegalArgumentException("Event: primaryBody is required!");

        this.type = type;
        this.primaryBodyRef = primaryBodyRef;
        this.secondaryBodyRef = secondaryBodyRef;
        this.payload = payload;
    }

    protected AbstractDomainEvent(
            DomainEventType type,
            BodyRefDTO primaryBody,
            P payload) {

        this(type, primaryBody, null, payload);
    }

}

