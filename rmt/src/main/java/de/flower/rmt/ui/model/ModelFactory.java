package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.event.Event;


public class ModelFactory {

    /**
     * Model returning event instance initialized with association.
     */
    public static EventModel eventModelWithAllAssociations(Event event) {
        if (event.isNew()) {
            return new EventModel(event);
        } else {
             return new EventModel(event, true);
        }
    }
}
