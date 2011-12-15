package de.flower.rmt.ui.player.page.event;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.model.InvitationModel;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventPage extends PlayerBasePage {

    public final static String PARAM_EVENTID = "event";

    @SpringBean
    private IInvitationManager invitationManager;

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
            throw new AbortWithHttpErrorCodeException(404, "Invalid page parameter.");
        }
        init(new EventModel(event));
    }

    public EventPage(final IModel<Event> model) {
        super(model);
        init(model);
    }

    private void init(IModel<Event> model) {
        setDefaultModel(model);
        addHeading("player.event.heading");

        InvitationFormPanel invitationFormPanel = new InvitationFormPanel(getInvitationModel(model)) {

            @Override
            protected void onSubmit(final Invitation invitation, final AjaxRequestTarget target) {
                // save invitation and update invitationlistpanel
                invitationManager.save(invitation);
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityAll(Invitation.class)));
            }
        };

        final InvitationListPanel invitationListPanel = new InvitationListPanel(model);
        invitationListPanel.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Invitation.class)));

        addMainPanel(invitationListPanel);
        addSecondaryPanel(new EventDetailsPanel(model), invitationFormPanel);
    }

    private IModel<Invitation> getInvitationModel(final IModel<Event> model) {
        final Invitation invitation = invitationManager.loadByEventAndUser(model.getObject(), getUserDetails().getUser());
        return new InvitationModel(invitation);
    }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }
}
