package engine.model.ports;

import java.util.List;

import engine.actions.ActionDTO;
import engine.events.domain.ports.eventtype.DomainEvent;

public interface DomainEventProcessor {

    public void provideActions(List<DomainEvent> domainEvents, List<ActionDTO> actions);

    public void notifyNewDynamic(String entityId, String assetId);

    public void notifyNewStatic(String entityId, String assetId);

    public void notifyDynamicIsDead(String entityId);

    public void notifyPlayerIsDead(String entityId);

    public void notifyStaticIsDead(String entityId);

}
