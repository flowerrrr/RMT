package de.flower.rmt.ui.model;

import de.flower.common.model.IEntity;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * @author flowerrrr
 */
public abstract class AbstractEntityModel<T extends IEntity> extends LoadableDetachableModel<T> {

    private Long id;

    public AbstractEntityModel(T entity) {
        if (entity != null) {
            setObject(entity);
            this.id = entity.getId();
        }
        Injector.get().inject(this);
    }

    /**
     * If the entity was transient when the modal was created, but has been persisted
     * in the meantime, set the id of the entity so that the persisted entity will be loaded the
     * next time the entity is requested.
     */
    @Override
    protected void onDetach() {
        if (getObject() != null) {
            this.id = getObject().getId();
        }
    }

    @Override
    protected T load() {
        return load(id);
    }

    abstract protected T load(Long id);

}
