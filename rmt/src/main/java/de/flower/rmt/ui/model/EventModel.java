package de.flower.rmt.ui.model;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.service.IEventManager;
import org.apache.commons.lang3.Validate;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventModel extends LoadableDetachableModel<Event> {

    @SpringBean
    private IEventManager manager;

    private Long id;

    private EventType type;

    public EventModel(Event entity) {
        Validate.notNull(entity);
        setObject(entity);
        this.id = entity.getId();
        this.type = EventType.from(entity);
        Injector.get().inject(this);
    }

    /**
     * If the entity was transient when the modal was created, but has been persisted
     * in the meantime, set the id of the entity so that the persisted entity will be loaded the
     * next time the entity is requested.
     */
    @Override
    protected void onDetach() {
        this.id = getObject().getId();
    }

    @Override
    protected Event load() {
        if (id == null) {
            return manager.newInstance(type);
        } else {
            return manager.findById(id);
        }
    }
}
