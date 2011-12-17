package de.flower.rmt.ui.model;

import de.flower.common.util.Check;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.persistence.metamodel.Attribute;

/**
 * @author flowerrrr
 */
public class EventModel extends AbstractEntityModel<Event> {

    @SpringBean
    private IEventManager manager;

    private EventType type;

    private Attribute[] attributes;

    public EventModel(Long id, Attribute... attributes) {
        super(id);
        Check.notNull(id);
        this.attributes = attributes;
    }

    public EventModel(Event entity) {
        super(entity);
        Check.notNull(entity);
        this.type = EventType.from(entity);
    }

    public EventModel(final IModel<Event> model) {
        super(model);
    }

    @Override
    protected void onDetach() {
        // if entity is transient we must save the event type in order to recreate event
        if (type == null) {
            Event entity = getObject();
            type = EventType.from(entity);
        }
        super.onDetach();
    }

    @Override
    protected Event load(Long id) {
        if (attributes != null) {
            return manager.loadById(id, attributes);
        } else {
            return manager.loadById(id);
        }
    }

    @Override
    protected Event newInstance() {
        return manager.newInstance(type);
    }
}
