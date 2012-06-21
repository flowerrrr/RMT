package de.flower.rmt.ui.model;

import de.flower.common.model.db.entity.IEntity;
import de.flower.common.util.Check;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public abstract class AbstractEntityModel<T extends IEntity> extends LoadableDetachableModel<T> {

    private final static Logger log = LoggerFactory.getLogger(AbstractEntityModel.class);

    private Long id;

    private IModel<T> wrappedModel;

    public AbstractEntityModel() {
        this((T) null);
    }

    public AbstractEntityModel(Long id) {
        this.id = id;
        init();
    }

    public AbstractEntityModel(T entity) {
        if (entity != null) {
            setObject(entity);
            this.id = entity.getId();
        }
        init();
    }

    /**
     * Allow overwriting for unit tests
     */
    protected void init() {
        Injector.get().inject(this);
    }

    public AbstractEntityModel(IModel<T> model) {
        this((T) null);
        Check.notNull(model);
        wrappedModel = model;
    }

    public Long getId() {
        if (id == null) {
            id = getObject().getId();
        }
        return id;
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
        // once we detach this model we discard any wrapped model. the call to getObject() will have
        // initialized the id of the entity so we can reload it next time.
        if (wrappedModel != null) {
            //noinspection AssignmentToNull
            wrappedModel = null;
        }
    }

    @Override
    protected T load() {
        // if we have a model then try to get the object from there.
        T object = null;
        try {
            if (wrappedModel != null) {
                object = wrappedModel.getObject();
            } else {
                if (id == null) {
                    object = newInstance();
                } else {
                    object = load(id);
                }
            }
        } catch (RuntimeException e) {
            log.error("load(): " + e.getMessage(), e);
            throw e;
        }
        if (!nullAllowed()) {
            Check.notNull(object);
        }
        return object;
    }

    protected boolean nullAllowed() {
        return false;
    }

    abstract protected T load(Long id);

    protected T newInstance() {
        throw new UnsupportedOperationException("Method not implemented. Model should only be used for persisted entities!");
    }
}
