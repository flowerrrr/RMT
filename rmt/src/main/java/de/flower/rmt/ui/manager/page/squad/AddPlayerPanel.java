package de.flower.rmt.ui.manager.page.squad;

import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.Team2Player;
import de.flower.rmt.model.Users;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.service.IUserManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oblume
 */
public class AddPlayerPanel extends GenericPanel<Team> {

    @SpringBean
    private IUserManager userManager;

    @SpringBean
    private ITeamManager teamManager;

    private List<Users> selectedPlayers = new ArrayList<Users>();

    public AddPlayerPanel( IModel<Team> model) {
        super("panel", model);

        Form form = new Form("form");
        add(form);

        // list of not-assigned players
        WebMarkupContainer playerListContainer = new WebMarkupContainer("playerListContainer");
        form.add(playerListContainer);
        CheckGroup group = new CheckGroup("group", selectedPlayers);
        playerListContainer.add(group);
        ListView playerList = new ListView<Users>("playerList", getListModel(model.getObject())) {

            @Override
            protected void populateItem(ListItem<Users> item) {
                Users player = item.getModelObject();
                item.add(new Check("checkbox", item.getModel()));
                item.add(new Label("name", player.getFullname()));
            }

        };
        group.add(playerList);

        // add and cancel buttons

        form.add(new MyAjaxSubmitLink("addButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                teamManager.addPlayers(AddPlayerPanel.this.getModelObject(), selectedPlayers);
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Team2Player.class)));
                close(target);
            }
        });


        form.add(new MyAjaxLink("cancelButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                close(target);
            }
        });

        add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Team2Player.class)));

    }

    /**
     * Selects all unassigned players of a club.
     *
     * @return
     */
    private IModel<List<Users>> getListModel(final Team team) {
        return new LoadableDetachableModel<List<Users>>() {
            @Override
            protected List<Users> load() {
                return userManager.findUnassignedPlayers(team);
            }
        };
    }

    private void close(AjaxRequestTarget target) {
        selectedPlayers.clear();
        AjaxSlideTogglePanel.hideCurrent(this, target);
    }

}
