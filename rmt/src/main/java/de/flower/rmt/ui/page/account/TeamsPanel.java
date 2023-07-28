package de.flower.rmt.ui.page.account;

import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Player;
import de.flower.rmt.model.db.entity.Player_;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.PlayerManager;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class TeamsPanel extends BasePanel<User> {

    @SpringBean
    private PlayerManager playerManager;

    public TeamsPanel(IModel<User> model) {
        super(model);

        final IModel<? extends List<? extends Player>> listModel = getListModel(model);
        final WebMarkupContainer noTeam = new WebMarkupContainer("noTeam") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        };
        add(noTeam);

        final WebMarkupContainer container = new WebMarkupContainer("container") {
            @Override
            public boolean isVisible() {
                return !noTeam.isVisible();
            }
        };
        add(container);

        ListView<Player> list = new EntityListView<Player>("list", listModel) {

            @Override
            protected void populateItem(final ListItem<Player> item) {
                Player player = item.getModelObject();
                item.add(new Label("name", player.getTeam().getName()));
            }
        };
        container.add(list);
    }

    private IModel<? extends List<? extends Player>> getListModel(final IModel<User> model) {
        return new LoadableDetachableModel<List<? extends Player>>() {
            @Override
            protected List<? extends Player> load() {
                return playerManager.sortByTeam(playerManager.findAllByUser(model.getObject(), Player_.team));
            }
        };
    }
}
