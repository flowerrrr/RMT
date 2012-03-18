package de.flower.rmt.ui.manager.page.event;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IInvitationManager;
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
public class AddInviteePanel extends BasePanel<Event> {

    @SpringBean
    private IInvitationManager invitationManager;

    @SpringBean
    private IUserManager userManager;

    /** Ok to store entities in field cause field is dismissed when panel is processed. */
    private List<User> selectedUsers = new ArrayList<User>();

    public AddInviteePanel(final IModel<Event> model) {

        Form form = new Form("form");
        add(form);

        // list of not-assigned players
        WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        form.add(listContainer);
        CheckGroup group = new CheckGroup("group", selectedUsers);
        listContainer.add(group);
        ListView playerList = new EntityListView<User>("list", getListModel(model)) {

            @Override
            protected void populateItem(ListItem<User> item) {
                User player = item.getModelObject();
                item.add(new Check("checkbox", item.getModel()));
                item.add(new Label("name", player.getFullname()));
            }
        };
        group.add(playerList);

        // add and cancel buttons

        form.add(new AjaxSubmitLink("addButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                invitationManager.addUsers(model.getObject(), selectedUsers);
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Invitation.class)));
                close(target);
            }
        });

        form.add(new AjaxLink("cancelButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                close(target);
            }
        });

        add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Invitation.class)));
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

    private void close(AjaxRequestTarget target) {
        selectedUsers.clear();
        onClose(target);
    }

    protected void onClose(AjaxRequestTarget target) {
         ;
    }
}
