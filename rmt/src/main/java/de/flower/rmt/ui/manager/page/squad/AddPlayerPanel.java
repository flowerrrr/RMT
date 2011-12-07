package de.flower.rmt.ui.manager.page.squad;

import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Player;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.panel.BasePanel;
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

/**
 * @author flowerrrr
 */
public class AddPlayerPanel extends BasePanel<Team> {

    @SpringBean
    private IUserManager userManager;

    @SpringBean
    private ITeamManager teamManager;

    private List<User> selectedPlayers = new ArrayList<User>();

    public AddPlayerPanel(final IModel<Team> model) {

        Form form = new Form("form");
        add(form);

        // list of not-assigned players
        WebMarkupContainer playerListContainer = new WebMarkupContainer("playerListContainer");
        form.add(playerListContainer);
        CheckGroup group = new CheckGroup("group", selectedPlayers);
        playerListContainer.add(group);
        ListView playerList = new ListView<User>("playerList", getListModel(model)) {

            @Override
            protected void populateItem(ListItem<User> item) {
                User player = item.getModelObject();
                item.add(new Check("checkbox", item.getModel()));
                item.add(new Label("name", player.getFullname()));
            }

        };
        group.add(playerList);

        // add and cancel buttons

        form.add(new MyAjaxSubmitLink("addButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                teamManager.addPlayers(model.getObject(), selectedPlayers);
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Player.class)));
                close(target);
            }
        });


        form.add(new MyAjaxLink("cancelButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                close(target);
            }
        });

        add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Player.class)));

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
                return userManager.findUnassignedPlayers(model.getObject());
            }
        };
    }

    private void close(AjaxRequestTarget target) {
        selectedPlayers.clear();
        AjaxSlideTogglePanel.hideCurrent(this, target);
    }

}
