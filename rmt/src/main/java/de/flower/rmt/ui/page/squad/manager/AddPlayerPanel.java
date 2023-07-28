package de.flower.rmt.ui.page.squad.manager;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Player;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.PlayerManager;
import de.flower.rmt.service.UserManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;


public class AddPlayerPanel extends BasePanel<Team> {

    @SpringBean
    private UserManager userManager;

    @SpringBean
    private PlayerManager playerManager;

    /**
     * Ok to store entities in field cause field is dismissed when panel is processed.
     */
    private List<User> selectedPlayers = new ArrayList<User>();

    public AddPlayerPanel(String id, final IModel<Team> model) {
        super(id);

        Form form = new Form("form");
        add(form);

        // list of not-assigned players
        WebMarkupContainer playerListContainer = new WebMarkupContainer("playerListContainer");
        form.add(playerListContainer);
        CheckGroup group = new CheckGroup<User>("group", selectedPlayers);
        playerListContainer.add(group);
        ListView playerList = new EntityListView<User>("playerList", getListModel(model)) {

            @Override
            protected void populateItem(ListItem<User> item) {
                User player = item.getModelObject();
                item.add(new Check<User>("checkbox", item.getModel()));
                item.add(new Label("name", player.getFullname()));
            }
        };
        group.add(playerList);

        // add and cancel buttons

        form.add(new AjaxSubmitLink("addButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                playerManager.addPlayers(model.getObject(), selectedPlayers);
                AjaxEventSender.entityEvent(this, Player.class);
                close(target);
            }
        });

        add(new AjaxEventListener(Player.class));
    }

    /**
     * Selects all unassigned players of a club.
     *
     * @return
     */
    private IModel<List<User>> getListModel(final IModel<Team> model) {
        return new LoadableDetachableModel<List<User>>() {
            @Override
            protected List<User> load() {
                return userManager.findAllUnassignedPlayers(model.getObject());
            }
        };
    }

    private void close(AjaxRequestTarget target) {
        selectedPlayers.clear();
        onClose(target);
    }

}
