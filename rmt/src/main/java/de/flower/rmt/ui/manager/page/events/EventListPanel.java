package de.flower.rmt.ui.manager.page.events;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.common.panel.DropDownMenuPanel;
import de.flower.rmt.ui.manager.page.event.EventPage;
import de.flower.rmt.ui.manager.page.invitations.InvitationsPage;
import de.flower.rmt.ui.model.EventModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventListPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

    public EventListPanel() {

        final IModel<List<Event>> listModel = getListModel();
        WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        add(listContainer);
        listContainer.add(new WebMarkupContainer("noEntry") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        });
        listContainer.add(new EntityListView<Event>("list", listModel) {
            @Override
            public boolean isVisible() {
                return !getList().isEmpty();
            }

            @Override
            protected void populateItem(final ListItem<Event> item) {
                final Event event = item.getModelObject();
                Link link = createInvitationsLink("invitationsLink", item);
                link.add(DateLabel.forDateStyle("date", Model.of(event.getDate()), "S-"));
                link.add(DateLabel.forDateStyle("time", Model.of(event.getTime().toDateTimeToday().toDate()), "-S"));
                item.add(link);
                item.add(new Label("type", new ResourceModel(EventType.from(event).getResourceKey())));
                item.add(new Label("team", event.getTeam().getName()));
                item.add(new Label("summary", event.getSummary()));
                item.add(new InvitationSummaryPanel(new EventModel(event)));
                // now the dropdown menu
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                menuPanel.addLink(createInvitationsLink("link", item), "button.invitations");
                menuPanel.addLink(createEditLink("link", item), "button.edit");
                menuPanel.addLink(new AjaxLinkWithConfirmation("link", new ResourceModel("manager.events.delete.confirm")) {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        eventManager.delete(item.getModelObject().getId());
                        AjaxEventSender.entityEvent(this, Event.class);
                    }
                }, "button.delete");
                item.add(menuPanel);
            }
        });
        listContainer.add(new AjaxEventListener(Event.class));
    }

    private IModel<List<Event>> getListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAll(Event_.team);
            }
        };
    }

    private Link createInvitationsLink(String id, final ListItem<Event> item) {
        return new Link(id) {
            @Override
            public void onClick() {
                setResponsePage(new InvitationsPage(new EventModel(item.getModel())));
            }
        };
    }


    private Link createEditLink(String id, final ListItem<Event> item) {
        return new Link(id) {
            @Override
            public void onClick() {
                setResponsePage(EventPage.class, EventPage.getPageParams(item.getModelObject().getId()));
            }
        };
    }
}
