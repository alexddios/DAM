package engine.controller.ports;

import java.util.List;

import engine.actions.ActionDTO;
import engine.events.domain.ports.eventtype.DomainEvent;

/**
 * GameRulesEngine
 * ---------------
 *
 * Contract for game-rule decision logic. Implementations translate domain
 * events into engine actions without coupling to Controller or Model.
 *
 * Responsibilities
 * ----------------
 * - Receive domain events produced by the Model
 * - Decide and append the actions to be executed by the engine
 *
 * Design goals
 * ------------
 * - Allow hot-swapping of rule sets without touching the core engine
 * - Keep rule logic modular and testable in isolation
 */
public interface ActionsGenerator {

    // *** CONSTRUCTORS ***

    // *** PUBLICS ***
    void provideActions(List<DomainEvent> domainEvents, List<ActionDTO> actions);

    // *** INTERFACE IMPLEMENTATIONS ***

    // *** PRIVATE ***
}
