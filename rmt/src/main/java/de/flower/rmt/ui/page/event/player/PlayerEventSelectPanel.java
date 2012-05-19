package de.flower.rmt.ui.page.event.player;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.QEvent;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.app.IPropertyProvider;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.event.EventSelectPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
@Deprecated
public class PlayerEventSelectPanel extends EventSelectPanel {

    @SpringBean
    private IEventManager eventManager;

    @SpringBean
    private IPropertyProvider propertyProvider;

    @SpringBean
    private ISecurityService securityService;

    public PlayerEventSelectPanel(final IModel<Event> model) {
        super(model);
        setListModel(getUpcomingEventList());
    }

    @Override
    protected void onClick(final IModel<Event> model) {
        setResponsePage(new EventPage(model));
    }

    private IModel<List<Event>> getUpcomingEventList() {
        final IModel<User> userModel = new UserModel(securityService.getUser());
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAllUpcomingAndLastNByUser(userModel.getObject(), propertyProvider.getEventsNumPast(), QEvent.event.team);
            }
        };
    }
}
