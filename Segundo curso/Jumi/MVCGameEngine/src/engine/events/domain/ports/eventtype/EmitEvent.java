package engine.events.domain.ports.eventtype;

import engine.events.domain.core.AbstractDomainEvent;
import engine.events.domain.ports.BodyRefDTO;
import engine.events.domain.ports.DomainEventType;
import engine.events.domain.ports.payloads.EmitPayloadDTO;

public final class EmitEvent extends AbstractDomainEvent<EmitPayloadDTO> implements DomainEvent {

    public EmitEvent(
            DomainEventType eventType,
            BodyRefDTO primaryBodyRef,
            EmitPayloadDTO payload) {

        super(eventType, primaryBodyRef, null, payload);
    }

}
