package de.flower.rmt.ui.page.event.manager.invitees;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.InvitationManager;
import de.flower.rmt.service.TeamManager;
import de.flower.rmt.service.UserManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author flowerrrr
 */
public class AddInviteePanel extends BasePanel<Event> {

    @SpringBean
    private InvitationManager invitationManager;

    @SpringBean
    private UserManager userManager;

    @SpringBean
    private TeamManager teamManager;

    private Set<Long> selectedUsers = new HashSet<Long>();

    public AddInviteePanel(String id, final IModel<Event> model) {
        super(id);

        final Form form = new Form("form");
        add(form);

        // list of not-assigned players

        final CheckGroup group = new CheckGroup<Long>("group", selectedUsers);
        form.add(group);
        final WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        listContainer.setOutputMarkupId(true);
        group.add(listContainer);
        ListView playerList = new EntityListView<User>("list", getListModel(model)) {

            @Override
            protected void populateItem(ListItem<User> item) {
                User player = item.getModelObject();
                item.add(new Check<Long>("checkbox", Model.of(item.getModelObject().getId())));
                item.add(new Label("name", player.getFullname()));
            }
        };
        listContainer.add(playerList);

        // displayed above checkgroup but below in code to have reference to listContainer
        ListView teamList = new ListView<Team>("teamList", getTeamListModel()) {
            @Override
            protected void populateItem(final ListItem<Team> item) {
                final IModel<Boolean> checkboxModel = Model.of(false);
                item.add(new AjaxCheckBox("team", checkboxModel) {
                    @Override
                    protected void onUpdate(final AjaxRequestTarget target) {
                        updateSelectedPlayers(model.getObject(), item.getModelObject(), checkboxModel.getObject());
                        target.add(listContainer);
                    }
                });
                item.add(new Label("name", item.getModelObject().getName()));
            }
        };
        form.add(teamList);

        // add and cancel buttons

        form.add(new AjaxSubmitLink("addButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                invitationManager.addUsers(model.getObject(), selectedUsers);
                AjaxEventSender.entityEvent(this, Invitation.class);
                close(target);
            }
        });

        add(new AjaxEventListener(Invitation.class));
    }

    /**
     * Selects all unassigned players of a club.
     *
     * @return
     */
    private IModel<List<User>> getListModel(final IModel<Event> model) {
        return new LoadableDetachableModel<List<User>>() {
            @Override
            protected List<User> load() {
                return userManager.findAllUninvitedPlayers(model.getObject());
            }
        };
    }

    private IModel<List<Team>> getTeamListModel() {
        return new LoadableDetachableModel<List<Team>>() {
            @Override
            protected List<Team> load() {
                List<Team> teams = teamManager.findAll();
                // add 'all players' fake team as first element to list
                Team allPlayersTeam = teamManager.newInstance();
                allPlayersTeam.setName(new ResourceModel("manager.event.invitee.add.all").getObject());
                teams.add(0, allPlayersTeam);
                return teams;
            }
        };
    }

    /**
     * Select/Deselect users of given team. If team is transient select/deselect all users.
     *
     * @param team
     * @param select
     */
    private void updateSelectedPlayers(Event event, Team team, boolean select) {
        List<User> users;
        if (team.isNew()) {
            users = userManager.findAllUninvitedPlayers(event);
        } else {
            users = userManager.findAllUninvitedPlayersByTeam(event, team);
        }

        for (User user : users) {
            if (select) {
                selectedUsers.add(user.getId());
            } else {
                selectedUsers.remove(user.getId());
            }
        }
    }

    private void close(AjaxRequestTarget target) {
        selectedUsers.clear();
        detach();
        onClose(target);
    }
}
