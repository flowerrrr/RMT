package de.flower.rmt.ui.player.page.event;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.player.NavigationPanel;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventPage extends PlayerBasePage {

    public final static String PARAM_EVENTID = de.flower.rmt.ui.manager.page.event.EventPage.PARAM_EVENTID;

    @SpringBean
    private IEventManager eventManager;

    /**
     * Deep link support
     *
     * @param params
     */
    public EventPage(PageParameters params) {
        Event event = null;
        try {
            Long eventId = params.get(PARAM_EVENTID).toLong();
            event = eventManager.loadByIdAndUser(eventId, getUserDetails().getUser());
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new AbortWithHttpErrorCodeException(404, "Invalid page parameter: " + e.getMessage());
        }
        init(new EventModel(event));
    }

    public static PageParameters getPageParams(Long eventId) {
        return new PageParameters().set(PARAM_EVENTID, eventId);
    }

    public EventPage(final IModel<Event> model) {
        super(model);
        init(model);
    }

    private void init(IModel<Event> model) {
        setDefaultModel(model);
        setHeading("player.event.heading");

        final InvitationListPanel invitationListPanel = new InvitationListPanel(model);
        invitationListPanel.add(new AjaxEventListener(Invitation.class));

        addMainPanel(invitationListPanel);
        addSecondaryPanel(new EventSecondaryPanel(model));
    }

     @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.EVENTS;
    }
}
