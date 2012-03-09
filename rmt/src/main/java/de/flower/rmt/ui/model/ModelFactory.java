package de.flower.rmt.ui.model;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.model.event.Match_;

import javax.persistence.metamodel.Attribute;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class ModelFactory {

    /**
     * Model returning event instance initialized with association.
     */
    public static EventModel eventModelWithAllAssociations(Event event) {
        if (event.isNew()) {
            return new EventModel(event);
        } else {
            List<Attribute> attributes = new ArrayList<Attribute>();
            attributes.add(Event_.team);
            attributes.add(Event_.venue);
            if (EventType.isMatch(event)) {
                attributes.add(Match_.opponent);
            }
            return new EventModel(event.getId(), attributes.toArray(new Attribute[]{}));
        }
    }
}
