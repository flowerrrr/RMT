package de.flower.common.ui.ajax.updatebehavior.events;

import java.io.Serializable;

/**
 * @author flowerrrr
 */
public abstract class AjaxEvent implements Serializable {

    private Class<?> clazz;

    protected AjaxEvent(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static AjaxEvent EntityUpdated(Class<?> clazz) {
        return new EntityUpdatedEvent(clazz);
    }

    public static AjaxEvent EntityCreated(Class<?> clazz) {
        return new EntityCreatedEvent(clazz);
    }

    public static AjaxEvent EntityDeleted(Class<?> clazz) {
        return new EntityDeletedEvent(clazz);
    }

    public static AjaxEvent EntityAll(Class<?> clazz) {
        return new EntityAllEvent(clazz);
    }

    /**
     * Check if this event matches the triggeredEvent.
     * <p/>
     * First compare clazz field. If not equal no match.
     * If same clazz then check type of Event (All, Created, Changed, Deleted).
     * ALL matches Created, Changed, Deleted.
     * Created matches Created, ...
     *
     * @param triggeredEvent
     * @return
     */
    public boolean matches(AjaxEvent triggeredEvent) {
        if (!this.clazz.equals(triggeredEvent.getClazz())) {
            return false;
        }
        if (this.getClass().isInstance(triggeredEvent)) {
            return true;
        }
        return false;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
