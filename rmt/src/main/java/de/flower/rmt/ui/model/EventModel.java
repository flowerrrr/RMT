package de.flower.rmt.ui.model;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.event.AbstractSoccerEvent_;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.Event_;
import de.flower.rmt.model.db.entity.event.Match_;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.service.EventManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.persistence.metamodel.Attribute;
import java.util.ArrayList;
import java.util.List;


public class EventModel<T extends Event> extends AbstractEntityModel<T> {

    @SpringBean
    private EventManager manager;

    private EventType type;

    private boolean eagerFetchAssociations = false;

    public EventModel(T entity, boolean eagerFetchAssociations) {
        super(entity.getId());
        type = entity.getEventType();
        this.eagerFetchAssociations = eagerFetchAssociations;
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
        if (eagerFetchAssociations) {
            entity = manager.loadById(id, getAttributes());
        } else {
            entity = manager.loadById(id);
        }
        return (T) entity;
    }

    @Override
    protected T newInstance() {
        return (T) manager.newInstance(type);
    }

    private Attribute[] getAttributes() {
        List<Attribute> attributes = new ArrayList<Attribute>();
        attributes.add(Event_.team);
        attributes.add(Event_.venue);
        if (type.isMatch()) {
            attributes.add(Match_.opponent);
        }
        if (type.isSoccerEvent()) {
            attributes.add(AbstractSoccerEvent_.uniform);
        }
        return attributes.toArray(new Attribute[]{});
    }
}
