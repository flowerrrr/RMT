package de.flower.common.ui.model;

import de.flower.common.model.db.entity.IEntity;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * Model stores last value that was set. Helps to deal with detachable models when it is necessary
 * to access previously submitted values.
 *
 * @author flowerrrr
 */
public class StateSavingModel<T extends Serializable> extends AbstractChainingModel<T, T> {

    private T savedObject;

    private Long savedEntityId;

    public StateSavingModel(final IModel<T> wrappedModel) {
        super(wrappedModel);
    }

    @Override
    public T getObject() {
        T object = getChainedModelObject();
        if (savedObject == null && savedEntityId == null) {
            // init the saved values once.
            updateCachedValue(object);
        }
        return object;
    }

    private void updateCachedValue(final T object) {
        if (object != null) {
            if (object instanceof IEntity) {
                savedEntityId = ((IEntity) object).getId();
            } else {
                savedObject = object;
            }
        } else {
            savedEntityId = null;
            savedObject = null;
        }
    }

    @Override
    public void setObject(final T object) {
        getChainedModel().setObject(object);
        updateCachedValue(object);
    }

    public T getSavedObject() {
        return savedObject;
    }

    public Long getSavedEntityId() {
        return savedEntityId;
    }
}
