package de.flower.rmt.ui.page.calendar.player;

import com.google.common.collect.Lists;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.CalendarType;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.service.ICalendarManager;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.model.CalItemModel;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.base.player.NavigationPanel;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
import de.flower.rmt.ui.page.event.EventDetailsPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
public class CalendarPage extends PlayerBasePage {

    private final static Logger log = LoggerFactory.getLogger(CalendarPage.class);

    public static final String CALENDAR_SECONDARY_PANEL_ID = "calendarSecondaryPanel";

    @SpringBean
    private ICalendarManager calendarManager;

    @SpringBean
    private ISecurityService securityService;

    public CalendarPage() {
        init(new UserModel(getUserDetails().getUser()));
    }

    public CalendarPage(IModel<User> model) {
        init(model);
    }

    private void init(final IModel<User> model) {
        setDefaultModel(model);

        setHeading("player.calendar.heading");

        final Panel placeHolderContainer = new BasePanel(CALENDAR_SECONDARY_PANEL_ID) {

            @Override
            protected String getPanelMarkup() {
                return "<wicket:message key=\"player.calendar.hint\" />";
            }
        };

        final IModel<List<CalendarType>> selectedCalendarsModel = Model.of((Collection) Lists.newArrayList(CalendarType.values()));

        addMainPanel(new CalendarPanel(CALENDAR_SECONDARY_PANEL_ID, selectedCalendarsModel) {

            @Override
            protected void onEventClick(final AjaxRequestTarget target, final CalItemDto calItemDto) {
                Panel panel = new CalItemEditPanel("calendarSecondaryPanel", Model.of(calItemDto)) {
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
                Panel panel = new EventDetailsPanel(CALENDAR_SECONDARY_PANEL_ID, new EventModel(event), View.PLAYER) {
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
}
