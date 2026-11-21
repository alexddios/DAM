package engine.events.domain.ports.eventtype;

public sealed interface DomainEvent permits 
        CollisionEvent, EmitEvent, LimitEvent, LifeOver {

}