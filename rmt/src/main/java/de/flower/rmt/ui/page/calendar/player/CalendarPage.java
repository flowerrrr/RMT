package de.flower.rmt.ui.page.calendar.player;

import com.google.common.collect.Lists;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.type.CalendarType;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.service.ICalendarManager;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.base.player.NavigationPanel;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
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

        final Panel placeHolderContainer = new BasePanel("calItemEditPanel") {

            @Override
            protected String getPanelMarkup() {
                return "<wicket:message key=\"player.calendar.hint\" />";
            }
        };

        final IModel<List<CalendarType>> selectedCalendarsModel = Model.of((Collection) Lists.newArrayList(CalendarType.values()));

        addMainPanel(new CalendarPanel(selectedCalendarsModel) {

            @Override
            protected void onEdit(final AjaxRequestTarget target, final CalItemDto calItemDto) {
                Panel panel;
                boolean readonly = (securityService.isCurrentUser(calItemDto.getUser())) ? false : true;
                panel = new CalItemEditPanel(Model.of(calItemDto), readonly) {
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
