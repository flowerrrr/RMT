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
public class EventModel<T extends Event> extends AbstractEntityModel<T> {

    @SpringBean
    private IEventManager manager;

    private EventType type;

    private Attribute[] attributes;

    public EventModel(Long id, Attribute... attributes) {
        super(id);
        Check.notNull(id);
        this.attributes = attributes;
    }

    public EventModel(T entity) {
        super(entity);
        Check.notNull(entity);
        this.type = EventType.from(entity);
    }

    public EventModel(final IModel<T> model) {
        super(model);
    }

    @Override
    protected void onDetach() {
        // if entity is transient we must save the event type in order to recreate event
        if (type == null) {
            T entity = getObject();
            type = EventType.from(entity);
        }
        super.onDetach();
    }

    @Override
    protected T load(Long id) {
        Event entity;
        if (attributes != null) {
            entity = manager.loadById(id, attributes);
        } else {
            entity = manager.loadById(id);
        }
        return (T) entity;
    }

    @Override
    protected T newInstance() {
        return (T) manager.newInstance(type);
    }
}
