package engine.events.domain.ports.payloads;

/**
 * Optional extra data attached to a DomainEvent.
 * Add new permitted payloads as you grow.
 */
public sealed interface DomainEventPayload permits
        EmitPayloadDTO, NoPayload, CollisionPayload {
}
