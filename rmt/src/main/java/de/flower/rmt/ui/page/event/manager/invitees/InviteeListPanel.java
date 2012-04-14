package de.flower.rmt.ui.page.event.manager.invitees;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.util.Check;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.user.manager.PlayerPage;
import de.flower.rmt.ui.panel.BasePanel;
import de.flower.rmt.ui.panel.DropDownMenuPanel;
import de.flower.rmt.ui.panel.ListViewPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class InviteeListPanel extends BasePanel {

    @SpringBean
    private IInvitationManager invitationManager;

    public InviteeListPanel(IModel<Event> model) {
        this(null, model);
    }

    public InviteeListPanel(String id, IModel<Event> model) {
        super(id);
        Check.notNull(model);
        final IModel<List<Invitation>> listModel = getListModel(model);
        ListViewPanel listView = new ListViewPanel<Invitation>("listContainer", listModel, new ResourceModel("manager.event.invitee.noentry")) {

            // @Override
            protected void populateItem(final ListItem<Invitation> item) {
                Link editLink = createEditLink("editLink", item);
                editLink.add(new Label("name", item.getModelObject().getName()));
                item.add(editLink);
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                item.add(menuPanel);
                // menuPanel.addLink(createEditLink("link", item), "button.edit");
                menuPanel.addLink(new AjaxLink("link") {
                            @Override
                            public void onClick(AjaxRequestTarget target) {
                                invitationManager.delete(item.getModelObject().getId());
                                AjaxEventSender.entityEvent(this, Invitation.class);
                            }
                        }, "button.delete");
            }
        };
        listView.add(new AjaxEventListener(Invitation.class));
        add(listView);
    }

    private Link createEditLink(String id, final ListItem<Invitation> item) {
        return new Link(id) {
            @Override
            public void onClick() {
                setResponsePage(new PlayerPage(new UserModel(item.getModel().getObject().getUser())));
            }
        };
    }

    private IModel<List<Invitation>> getListModel(final IModel<Event> model) {
        return new LoadableDetachableModel<List<Invitation>>() {
            @Override
            protected List<Invitation> load() {
                return invitationManager.findAllByEventSortedByName(model.getObject());
            }
        };
    }
}
