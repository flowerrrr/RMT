package de.flower.rmt.ui.page.calendar.player;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.service.ICalendarManager;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.base.player.NavigationPanel;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
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

        final WebMarkupContainer placeHolderContainer = new WebMarkupContainer("calItemEditPanel");

        addMainPanel(new CalendarPanel(model) {

            @Override
            protected void onEdit(final AjaxRequestTarget target, final CalItemDto calItemDto) {
                getSecondaryPanel().replace(new CalItemEditPanel(Model.of(calItemDto)) {
                    @Override
                    protected void onClose(final AjaxRequestTarget target) {
                        getSecondaryPanel().replace(placeHolderContainer);
                        target.add(getSecondaryPanel());
                    }
                });
                target.add(getSecondaryPanel());
            }
        });
        addSecondaryPanel(placeHolderContainer);
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.CALENDAR;
    }
}
