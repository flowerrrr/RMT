package de.flower.rmt.ui.page.calendar;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.CalendarFilter;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.security.ISecurityService;
import de.flower.rmt.service.ICalendarManager;
import de.flower.rmt.ui.app.IViewResolver;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.model.CalItemModel;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.base.AbstractCommonBasePage;
import de.flower.rmt.ui.page.base.NavigationPanel;
import de.flower.rmt.ui.page.event.EventDetailsPanel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import de.flower.rmt.ui.site.PageLinks;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * @author flowerrrr
 */
public class CalendarPage extends AbstractCommonBasePage {

    private final static Logger log = LoggerFactory.getLogger(CalendarPage.class);

    public static final String CALENDAR_SECONDARY_PANEL_ID = "calendarSecondaryPanel";

    @SpringBean
    private ICalendarManager calendarManager;

    @SpringBean
    private ISecurityService securityService;

    @SpringBean
    private IViewResolver viewResolver;

    public CalendarPage() {
        init(new UserModel(securityService.getUser()));
    }

    @VisibleForTesting
    protected CalendarPage(IModel<User> model) {
        init(model);
    }

    private void init(final IModel<User> model) {
        setDefaultModel(model);

        setHeading("player.calendar.heading");

        final Panel placeHolderContainer = new BasePanel(CALENDAR_SECONDARY_PANEL_ID) {
            {
                add(new WebMarkupContainer("hint") {
                    @Override
                    public boolean isVisible() {
                        return viewResolver.getView() == View.PLAYER;
                    }
                });
            }

            @Override
            protected String getPanelMarkup() {
                return "<span wicket:id=\"hint\"><wicket:message key=\"player.calendar.hint\" /></span>";
            }
        };

        final IModel<List<CalendarFilter>> selectedCalendarsModel = Model.of((Collection) Lists.newArrayList(CalendarFilter.USER, CalendarFilter.CLUB));
//        if (viewResolver.getView() == View.MANAGER) {
//            selectedCalendarsModel.getObject().add(CalendarFilter.OTHERS);
//        }

        addMainPanel(new CalendarPanel(CALENDAR_SECONDARY_PANEL_ID, selectedCalendarsModel) {

            @Override
            protected void onEventClick(final AjaxRequestTarget target, final CalItemDto calItemDto, User user) {
                Panel panel = new CalItemEditPanel("calendarSecondaryPanel", Model.of(calItemDto), new UserModel(user)) {
                    @Override
                    protected void onClose(final AjaxRequestTarget target) {
                        getSecondaryPanel().replace(placeHolderContainer);
                        target.add(getSecondaryPanel());
                    }
                };
                getSecondaryPanel().replace(panel);
                target.add(getSecondaryPanel());
            }

            @Override
            protected void onEventClick(final AjaxRequestTarget target, final CalItem calItem) {
                Panel panel = new CalItemDetailsPanel(CALENDAR_SECONDARY_PANEL_ID, new CalItemModel(calItem)) {
                    @Override
                    protected void onClose(final AjaxRequestTarget target) {
                        getSecondaryPanel().replace(placeHolderContainer);
                        target.add(getSecondaryPanel());
                    }
                };
                getSecondaryPanel().replace(panel);
                target.add(getSecondaryPanel());
            }

            @Override
            protected void onEventClick(final AjaxRequestTarget target, final Event event) {
                Panel panel = new EventDetailsPanelExtended(CALENDAR_SECONDARY_PANEL_ID, new EventModel(event)) {
                    @Override
                    protected void onClose(final AjaxRequestTarget target) {
                        getSecondaryPanel().replace(placeHolderContainer);
                        target.add(getSecondaryPanel());
                    }
                };
                getSecondaryPanel().replace(panel);
                target.add(getSecondaryPanel());
            }
        });

        addSecondaryPanel(new CalendarSelectPanel(selectedCalendarsModel));

        addSecondaryPanel(placeHolderContainer);
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.CALENDAR;
    }

    public static class EventDetailsPanelExtended extends RMTBasePanel {

        public EventDetailsPanelExtended(final String id, final IModel<Event> eventModel) {
            super(id);
            add(new EventDetailsPanel(eventModel));
            add(PageLinks.eventLink("link", eventModel.getObject().getId(), getView()));
        }

        @Override
        protected String getPanelMarkup() {
            return "<div wicket:id='eventDetailsPanel'/><a class='btn-link' wicket:id='link'><wicket:message key='calendar.link.event'/></a>";
        }

    }
}
