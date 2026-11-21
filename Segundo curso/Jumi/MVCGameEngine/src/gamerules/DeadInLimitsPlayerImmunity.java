package gamerules;

import java.util.List;

import engine.actions.ActionType;
import engine.actions.ActionDTO;
import engine.controller.ports.ActionsGenerator;
import engine.events.domain.ports.BodyRefDTO;
import engine.events.domain.ports.DomainEventType;
import engine.events.domain.ports.eventtype.CollisionEvent;
import engine.events.domain.ports.eventtype.DomainEvent;
import engine.events.domain.ports.eventtype.EmitEvent;
import engine.events.domain.ports.eventtype.LifeOver;
import engine.events.domain.ports.eventtype.LimitEvent;
import engine.model.bodies.ports.BodyType;

public class DeadInLimitsPlayerImmunity implements ActionsGenerator {

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
                ActionType action = ActionType.DIE;
                if (limitEvent.primaryBodyRef.type() == BodyType.PLAYER)
                    action = ActionType.NO_MOVE;

                actions.add(new ActionDTO(
                        limitEvent.primaryBodyRef.id(), limitEvent.primaryBodyRef.type(),
                        action, event));
                break;
            }

            case LifeOver lifeOver ->
                actions.add(new ActionDTO(
                        lifeOver.primaryBodyRef.id(), lifeOver.primaryBodyRef.type(),
                        ActionType.DIE, event));

            case EmitEvent emitEvent -> {
                if (emitEvent.type == DomainEventType.EMIT_REQUESTED) {
                    actions.add(new ActionDTO(
                            emitEvent.primaryBodyRef.id(),
                            emitEvent.primaryBodyRef.type(),
                            ActionType.SPAWN_BODY,
                            event));

                } else {
                    actions.add(new ActionDTO(
                            emitEvent.primaryBodyRef.id(),
                            emitEvent.primaryBodyRef.type(),
                            ActionType.SPAWN_PROJECTILE,
                            event));
                }
            }

            case CollisionEvent collisionEvent -> {
                this.resolveCollision(collisionEvent, actions);
            }
        }
    }

    private void resolveCollision(CollisionEvent event, List<ActionDTO> actions) {
        BodyType primaryType = event.primaryBodyRef.type();
        BodyType secondaryType = event.secondaryBodyRef.type();
        BodyRefDTO player;
        boolean primaryDie, secondaryDie;

        // Check shooter immunity for PLAYER vs PROJECTILE and viceversa
        if (event.payload.haveImmunity) {
            return; // Projectile passes through its shooter during immunity period
        }

        primaryDie = primaryType != BodyType.GRAVITY && primaryType != BodyType.PLAYER;
        secondaryDie = secondaryType != BodyType.GRAVITY && secondaryType != BodyType.PLAYER;

        player = primaryType == BodyType.PLAYER ? event.primaryBodyRef
                : secondaryType == BodyType.PLAYER ? event.secondaryBodyRef : null;

        if (player != null)
            actions.add(new ActionDTO(player.id(), player.type(), ActionType.NO_MOVE, event));

        if (primaryDie)
            actions.add(new ActionDTO(
                    event.primaryBodyRef.id(), event.primaryBodyRef.type(), ActionType.DIE, event));

        if (secondaryDie)
            actions.add(new ActionDTO(
                    event.secondaryBodyRef.id(), event.secondaryBodyRef.type(), ActionType.DIE, event));
    }
}
