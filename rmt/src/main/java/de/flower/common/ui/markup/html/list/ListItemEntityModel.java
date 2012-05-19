package de.flower.common.ui.markup.html.list;

import de.flower.common.model.db.entity.IEntity;
import org.apache.wicket.model.IModel;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Enhanced version of ListItemModel that is stable against deleting or adding items to the list that backs the model.
 * Instead of storing index this model stores the id of the item and looks up items by id. Might be ineffictive if backing
 * list is super-long.
 *
 * @param <T> the generic type
 */
public final class ListItemEntityModel<T extends IEntity> implements IModel<T> {

    private final IModel<? extends List<? extends T>> listModel;

    private final Long id;

    /**
     * Fallback in case the id is not set. Mostly to be able to use this model in unit tests with transient objects.
     */
    private final int index;

    /**
     * Instantiates a new list item entity model.
     *
     * @param listModel the list model
     * @param index the index
     */
    public ListItemEntityModel(final IModel<? extends List<? extends T>> listModel, final int index) {
        this.listModel = listModel;
        this.id = listModel.getObject().get(index).getId();
        this.index = index;
    }

    @Override
    public void detach() {
        listModel.detach();
    }

    @Override
    public T getObject() {
        if (id != null) {
            for (final T object : listModel.getObject()) {
                if (object.getId().equals(id)) {
                    return object;
                }
            }
        } else {
            // fallback for unit tests with transient objects
            return listModel.getObject().get(index);
        }
        throw new EntityNotFoundException("Entity [" + id + "] could not be loaded from database.");
    }

    @Override
    public void setObject(final T object) {
        throw new UnsupportedOperationException("Method not implemented!");
    }

}
