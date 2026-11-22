package engine.events.domain.ports.payloads;

public final class NoPayload implements DomainEventPayload {
    public static final NoPayload INSTANCE = new NoPayload();
    private NoPayload() {}
}