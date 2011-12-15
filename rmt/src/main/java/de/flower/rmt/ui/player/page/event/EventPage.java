package de.flower.rmt.ui.player.page.event;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Invitee;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IInviteeManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.model.InviteeModel;
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
    private IInviteeManager inviteeManager;

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

        ResponseFormPanel responseFormPanel = new ResponseFormPanel(getResponseModel(model)) {

            @Override
            protected void onSubmit(final Invitee invitee, final AjaxRequestTarget target) {
                // save response and update responselistpanel
                inviteeManager.save(invitee);
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityAll(Invitee.class)));
            }
        };

        final InviteeListPanel responseListPanel = new InviteeListPanel(model);
        responseListPanel.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Invitee.class)));

        addMainPanel(responseListPanel);
        addSecondaryPanel(new EventDetailsPanel(model), responseFormPanel);
    }

    private IModel<Invitee> getResponseModel(final IModel<Event> model) {
        final Invitee invitee = inviteeManager.loadByEventAndUser(model.getObject(), getUserDetails().getUser());
        return new InviteeModel(invitee);
    }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }
}
