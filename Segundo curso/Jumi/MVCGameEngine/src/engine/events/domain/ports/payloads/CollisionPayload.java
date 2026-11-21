package engine.events.domain.ports.payloads;

public final class CollisionPayload implements DomainEventPayload {
    public final boolean haveImmunity;

    public CollisionPayload(boolean playerHaveImmunity) {
        this.haveImmunity = playerHaveImmunity;
    }
}
    