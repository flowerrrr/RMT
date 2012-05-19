package de.flower.rmt.ui.panel;

import de.flower.common.model.db.entity.IEntity;
import de.flower.common.ui.markup.html.list.EntityListView;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Listview that displays listEmpty-message.
 *
 * @author flowerrrr
 */
public abstract class ListViewPanel<T extends IEntity> extends Border {

    public ListViewPanel(final String id, final IModel<List<T>> listModel, final IModel<String> noEntryModel) {
        super(id, listModel);
        addToBorder(new WebMarkupContainer("noEntry") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        }.add(new Label("noEntryLabel", noEntryModel)));
        add(new EntityListView<T>("list", listModel) {
            @Override
            public boolean isVisible() {
                return !getList().isEmpty();
            }

            @Override
            protected void populateItem(final ListItem<T> listItem) {
                ListViewPanel.this.populateItem(listItem);
            }
        });
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        getBodyContainer().setVisible(getBodyContainer().get("list").isVisible());
    }


    protected abstract void populateItem(final ListItem<T> listItem);
}
