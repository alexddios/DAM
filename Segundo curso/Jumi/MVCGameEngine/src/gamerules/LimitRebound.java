package gamerules;

import java.util.List;

import engine.actions.ActionType;
import engine.actions.ActionDTO;
import engine.controller.ports.ActionsGenerator;
import engine.events.domain.ports.DomainEventType;
import engine.events.domain.ports.eventtype.CollisionEvent;
import engine.events.domain.ports.eventtype.DomainEvent;
import engine.events.domain.ports.eventtype.EmitEvent;
import engine.events.domain.ports.eventtype.LifeOver;
import engine.events.domain.ports.eventtype.LimitEvent;


public class LimitRebound implements ActionsGenerator {

    // *** INTERFACE IMPLEMENTATIONS ***

    @Override //
    public void provideActions(List<DomainEvent> domainEvents, List<ActionDTO> actions) {
        if (domainEvents != null) {
            for (DomainEvent event : domainEvents) {
                this.applyGameRules(event, actions);
            }
        }
    }

    // *** PRIVATE ***

    private void applyGameRules(DomainEvent event, List<ActionDTO> actions) {
        switch (event) {
            case LimitEvent limitEvent -> {
                ActionType action;

                switch (limitEvent.type) {
                    case REACHED_EAST_LIMIT:
                        action = ActionType.MOVE_REBOUND_IN_EAST;
                        break;
                    case REACHED_WEST_LIMIT:
                        action = ActionType.MOVE_REBOUND_IN_WEST;
                        break;
                    case REACHED_NORTH_LIMIT:
                        action = ActionType.MOVE_REBOUND_IN_NORTH;
                        break;
                    case REACHED_SOUTH_LIMIT:
                        action = ActionType.MOVE_REBOUND_IN_SOUTH;
                        break;
                    default:
                        action = ActionType.NO_MOVE;
                        break;
                }

                actions.add(new ActionDTO(
                        limitEvent.primaryBodyRef.id(), limitEvent.primaryBodyRef.type(),
                        action, event));

            }

            case LifeOver e ->
                actions.add(new ActionDTO(
                        e.primaryBodyRef.id(), e.primaryBodyRef.type(),
                        ActionType.DIE, event));

            case EmitEvent e -> {

                if (e.type == DomainEventType.EMIT_REQUESTED) {
                    actions.add(new ActionDTO(
                            e.primaryBodyRef.id(),
                            e.primaryBodyRef.type(),
                            ActionType.SPAWN_BODY,
                            event));

                } else {
                    actions.add(new ActionDTO(
                            e.primaryBodyRef.id(),
                            e.primaryBodyRef.type(),
                            ActionType.SPAWN_PROJECTILE,
                            event));
                }

            }

            case CollisionEvent e -> {

                // No action for collision events in this generator

            }
        }
    }
}
