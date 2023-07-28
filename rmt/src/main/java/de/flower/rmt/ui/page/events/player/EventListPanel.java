package de.flower.rmt.ui.page.events.player;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.tooltips.TooltipBehavior;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.service.EventManager;
import de.flower.rmt.service.InvitationManager;
import de.flower.rmt.service.ResponseManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.event.player.EventPage;
import de.flower.rmt.ui.page.events.EventDataProvider;
import de.flower.rmt.ui.panel.QuickResponseLabel;
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
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventListPanel extends BasePanel {

    @SpringBean
    private ResponseManager responseManager;

    @SpringBean
    private InvitationManager invitationManager;

    @SpringBean
    private EventManager eventManager;

    private static final int ITEMS_PER_PAGE = de.flower.rmt.ui.page.events.manager.EventListPanel.ITEMS_PER_PAGE;

    public EventListPanel(final UserModel userModel) {

        final EventDataProvider dataProvider = new EventDataProvider(ITEMS_PER_PAGE, userModel);
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
                final EventModel eventModel = new EventModel(item.getModel());

                if (dataProvider.isNextEvent(event)) {
                    item.add(AttributeModifier.append("class", "next-event"));
                }
                if (event.isCanceled()) {
                    item.add(AttributeModifier.append("class", "canceled-event"));
                }

                Link link = new BookmarkablePageLink("invitationsLink", EventPage.class, EventPage.getPageParams(event.getId()));
                item.add(link);
                link.add(new Label("date", Dates.formatDateShortWithWeekday(event.getDateTimeAsDate())));
                link.add(new Label("time", Dates.formatTimeShort(event.getDateTimeAsDate())));
                item.add(new Label("type", new ResourceModel(EventType.from(event).getResourceKey())));
                item.add(new Label("team", event.getTeam().getName()));
                item.add(new Label("summary", event.getSummary()));

                final Invitation invitation = getInvitation(event, userModel.getObject());
                RSVPStatus status = invitation.getStatus();
                item.add(new QuickResponseLabel("rsvpStatus", status == RSVPStatus.NORESPONSE ? null : status) {
                    @Override
                    protected void submitStatus(final RSVPStatus status) {
                        responseManager.respond(eventModel.getId(), userModel.getId(), status);
                    }

                    @Override
                    public boolean isEnabled() {
                        return !eventManager.isEventClosed(item.getModelObject());
                    }

                    @Override
                    public boolean isVisible() {
                        return !item.getModelObject().isCanceled();
                    }
                });

                Link confirmCancelationLink = new BookmarkablePageLink("confirmCancelationLink", EventPage.class, EventPage.getPageParams(event.getId()));
                confirmCancelationLink.setVisible(event.isCanceled() && status != RSVPStatus.DECLINED);
                item.add(confirmCancelationLink);
                confirmCancelationLink.add(new TooltipBehavior(new ResourceModel("player.events.tooltip.confirm.cancelation")));
            }

            private Invitation getInvitation(final Event event, final User user) {
                return invitationManager.loadByEventAndUser(event, user);
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

    /**
     * Returns true, if given event is a future event and no other event in events is closer to
     * today then given event.
     */
    public static boolean isNextEvent(Event event, List<? extends Event> events) {
        DateTime date = event.getDateTime();
        if (date.isBeforeNow()) {
            return false;
        } else {
            for (Event e : events) {
                if (e.getDateTime().isBeforeNow()) {
                    continue;
                } else {
                    if (e.getDateTime().isBefore(event.getDateTime())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
