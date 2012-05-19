package de.flower.common.ui.markup.html.list;

import de.flower.common.model.db.entity.IEntity;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class EntityListView<T extends IEntity> extends ListView<T> {

    public EntityListView(String id, IModel<? extends List<? extends T>> iModel) {
        super(id, iModel);
    }

    @Override
     protected IModel<T> getListItemModel(final IModel<? extends List<T>> listViewModel, final int index) {
         return new ListItemEntityModel<T>(listViewModel, index);
     }
}
