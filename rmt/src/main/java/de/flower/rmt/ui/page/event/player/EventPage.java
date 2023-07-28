package de.flower.rmt.ui.page.event.player;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.service.EventManager;
import de.flower.rmt.service.InvitationManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class EventPage extends PlayerBasePage {

    private final static Logger log = LoggerFactory.getLogger(EventPage.class);

    @SpringBean
    private EventManager eventManager;

    @SpringBean
    private InvitationManager invitationManager;

    private EventInvitationModel invitationModel;

    /**
     * Deep link support
     *
     * @param params
     */
    public EventPage(PageParameters params) {
        Event event;
        try {
            Long eventId = params.get(de.flower.rmt.ui.page.event.manager.EventPage.PARAM_EVENTID).toLong();
            event = eventManager.loadById(eventId);
        } catch (Exception e) {
            log.warn("EventPage accessed with invalid parameter: " + e.toString());
            throw new AbortWithHttpErrorCodeException(404, "Invalid page parameter: " + e.getMessage());
        }
        // as associations are often needed use a fully initialized event object model.
        init(new EventModel(event, true));
    }

    public static PageParameters getPageParams(Long eventId) {
        return de.flower.rmt.ui.page.event.manager.EventPage.getPageParams(eventId);
    }

    public EventPage(final IModel<Event> model) {
        init(model);
    }

    private void init(IModel<Event> model) {
        setDefaultModel(model);
        setHeading("player.event.heading");

        addMainPanel(new EventTabPanel(model));
        invitationModel = new EventInvitationModel(model);
        addSecondaryPanel(new EventSecondaryPanel(model, invitationModel));

    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        // makes messages back-button and reload-save. must be called after super.onBeforeRender to have this
        // message listed after messages of super-page
        // check if user must confirm cancellation of event
        info(new ConfirmEventCanceledMessage(invitationModel) {
            @Override
            protected void confirm() {
                Invitation invitation = invitationModel.getObject();
                invitation.setStatus(RSVPStatus.DECLINED);
                invitationManager.save(invitation);
                AjaxEventSender.entityEvent(EventPage.this, Invitation.class);
            }
        });
    }

     @Override
    public String getActiveTopBarItem() {
        return Pages.EVENTS.name();
    }

    @Override
    protected void onDetach() {
        if (invitationModel != null) {
            invitationModel.detach();
        }
        super.onDetach();
    }
}
