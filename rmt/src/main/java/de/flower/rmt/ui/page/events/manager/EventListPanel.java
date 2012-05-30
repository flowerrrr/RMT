package de.flower.rmt.ui.page.events.manager;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.tooltips.TooltipBehavior;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.app.IEventListProvider;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.page.event.manager.EventPage;
import de.flower.rmt.ui.page.event.manager.EventTabPanel;
import de.flower.rmt.ui.panel.DropDownMenuPanel;
import de.flower.rmt.util.Dates;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
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
public class EventListPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

    @SpringBean
    private IEventListProvider eventListProvider;

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

                if (de.flower.rmt.ui.page.events.player.EventListPanel.isNextEvent(event, getList())) {
                    item.add(AttributeModifier.append("class", "next-event"));
                }

                Link link = createInvitationsLink("invitationsLink", item.getModel());
                link.add(new Label("date", Dates.formatDateShortWithWeekday(event.getDateTimeAsDate())));
                link.add(new Label("time", Dates.formatTimeShort(event.getDateTimeAsDate())));
                item.add(link);
                item.add(new Label("type", new ResourceModel(EventType.from(event).getResourceKey())));
                item.add(new Label("team", event.getTeam().getName()));
                item.add(new Label("summary", event.getSummary()));
                item.add(new InvitationSummaryPanel(new EventModel(event)));
                item.add(createNotificationLink(item.getModel()));
                // now the dropdown menu
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                menuPanel.addLink(createInvitationsLink("link", item.getModel()), "button.invitations");
                menuPanel.addLink(createEditLink("link", item.getModel()), "button.edit");
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
                return eventListProvider.getManagerEventListPanelList();
            }
        };
    }

    private Link createInvitationsLink(String id, final IModel<Event> model) {
        return new Link(id) {
            @Override
            public void onClick() {
                setResponsePage(EventPage.class, EventPage.getPageParams(model.getObject().getId(), EventTabPanel.INVITATIONS_PANEL_INDEX));
            }
        };
    }

    private Link createEditLink(String id, final IModel<Event> model) {
        return new Link(id) {
            @Override
            public void onClick() {
                setResponsePage(EventPage.class, EventPage.getPageParams(model.getObject().getId()));
            }
        };
    }

    private Link createNotificationLink(final IModel<Event> model) {
        Link link = new Link("notificationLink") {
            @Override
            public void onClick() {
                setResponsePage(EventPage.class, EventPage.getPageParams(model.getObject().getId(), EventTabPanel.NOTIFICATION_PANEL_INDEX));
            }

            @Override
            public boolean isVisible() {
                return !model.getObject().isInvitationSent();
            }
        };
        // RMT-426
        link.add(new TooltipBehavior(new ResourceModel("alert.message.event.noinvitationsent")));
        return link;
    }
}
