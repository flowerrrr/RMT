package de.flower.rmt.ui.page.calendar.player;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.ICalendarManager;
import de.flower.rmt.ui.model.CalItemModel;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.base.player.NavigationPanel;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class CalendarPage extends PlayerBasePage {

    private final static Logger log = LoggerFactory.getLogger(CalendarPage.class);

    @SpringBean
    private ICalendarManager calendarManager;

    public CalendarPage() {
        init(new UserModel(getUserDetails().getUser()));
    }

    public CalendarPage(IModel<User> model) {
        init(model);
    }

    private void init(final IModel<User> model) {
        setDefaultModel(model);

        setHeading("player.calendar.heading", null);

        final WebMarkupContainer placeHolderContainer = new WebMarkupContainer("calItemEditPanel") {
            {
                add(new AjaxEventListener(CalItem.class));
            }
        };

        addMainPanel(new CalendarPanel(model) {
            @Override
            protected void onAdd(final AjaxRequestTarget target, final DateTime dateTime) {
                CalItem calItem = calendarManager.newInstance();
                calItem.setStartDate(dateTime);
                getSecondaryPanel().replace(new CalItemEditPanel(new CalItemModel(calItem)) {
                    @Override
                    protected void onClose(final AjaxRequestTarget target) {
                        getSecondaryPanel().replace(placeHolderContainer);
                        target.add(getSecondaryPanel());
                    }
                });
                // might need to send event before setting model (detaching the model would erase the startDate).
                AjaxEventSender.entityEvent(this, CalItem.class);
            }
        });
        addSecondaryPanel(placeHolderContainer);
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.CALENDAR;
    }
}
