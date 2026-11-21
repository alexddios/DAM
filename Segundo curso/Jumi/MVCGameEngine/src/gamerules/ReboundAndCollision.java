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
import engine.model.bodies.ports.BodyType;

public class ReboundAndCollision implements ActionsGenerator {

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

                resolveCollision(e, actions);

            }

            default -> {
                // No action for unhandled event types
            }
        }
    }

    private void resolveCollision(CollisionEvent event, List<ActionDTO> actions) {
        BodyType primary = event.primaryBodyRef.type();
        BodyType secondary = event.secondaryBodyRef.type();

        // Ignore collisions with DECORATOR bodies
        if (primary == BodyType.DECORATOR || secondary == BodyType.DECORATOR) {
            return;
        }

        // Check shooter immunity for PLAYER vs PROJECTILE and viceversa
        if (event.payload.haveImmunity) {
            return; // Projectile passes through its shooter during immunity period
        }

        // Default: Both die
        actions.add(new ActionDTO(
                event.primaryBodyRef.id(), event.primaryBodyRef.type(), ActionType.DIE, event));

        actions.add(new ActionDTO(
                event.secondaryBodyRef.id(), event.secondaryBodyRef.type(), ActionType.DIE, event));
    }

}
