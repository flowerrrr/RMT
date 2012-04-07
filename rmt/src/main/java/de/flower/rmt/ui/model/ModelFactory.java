package de.flower.rmt.ui.model;

import de.flower.rmt.model.event.*;

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
            if (EventType.isSoccerEvent(event)) {
                attributes.add(AbstractSoccerEvent_.surfaceList);
            }
            return new EventModel(event.getId(), attributes.toArray(new Attribute[]{}));
        }
    }
}
