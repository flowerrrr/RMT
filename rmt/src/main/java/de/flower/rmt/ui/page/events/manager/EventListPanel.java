package de.flower.rmt.ui.page.events.manager;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.tooltips.TooltipBehavior;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.page.event.manager.EventPage;
import de.flower.rmt.ui.page.event.manager.EventTabPanel;
import de.flower.rmt.ui.page.events.EventDataProvider;
import de.flower.rmt.ui.panel.DropDownMenuPanel;
import de.flower.rmt.util.Dates;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventListPanel extends BasePanel {

    public static final int ITEMS_PER_PAGE = 10;

    @SpringBean
    private IEventManager eventManager;

    public EventListPanel() {

        final IDataProvider<Event> dataProvider = new EventDataProvider(ITEMS_PER_PAGE);
        final WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        add(listContainer);
        final DataView<Event> dataView = new DataView<Event>("list", dataProvider) {
            @Override
            public boolean isVisible() {
                return getItemCount() > 0;
            }

            @Override
            protected void populateItem(final Item<Event> item) {
                final Event event = item.getModelObject();

//                if (de.flower.rmt.ui.page.events.player.EventListPanel.isNextEvent(event, getList())) {
//                    item.add(AttributeModifier.append("class", "next-event"));
//                }
                if (event.isCanceled()) {
                    item.add(AttributeModifier.append("class", "canceled-event"));
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

        };
        dataView.setItemsPerPage(ITEMS_PER_PAGE);
        listContainer.add(dataView);

        listContainer.add(new WebMarkupContainer("noEntry") {
            @Override
            public boolean isVisible() {
                return dataView.getItemCount() == 0;
            }
        });

        listContainer.add(new AjaxPagingNavigator("pager", dataView) {
            @Override
            protected void onAjaxEvent(AjaxRequestTarget target) {
                target.add(listContainer);
            }
        });

        listContainer.add(new AjaxEventListener(Event.class));

    }


    private Link createInvitationsLink(String id, final IModel<Event> model) {
        return new BookmarkablePageLink(id, EventPage.class, EventPage.getPageParams(model.getObject().getId(), EventTabPanel.INVITATIONS_PANEL_INDEX));
    }

    private Link createEditLink(String id, final IModel<Event> model) {
        return new BookmarkablePageLink(id, EventPage.class, EventPage.getPageParams(model.getObject().getId()));
    }

    private Link createNotificationLink(final IModel<Event> model) {
        Link link = new BookmarkablePageLink("notificationLink", EventPage.class, EventPage.getPageParams(model.getObject().getId())) {

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
