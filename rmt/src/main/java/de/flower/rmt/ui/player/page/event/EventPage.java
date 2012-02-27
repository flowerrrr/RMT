package de.flower.rmt.ui.player.page.event;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.ui.common.page.event.EventDetailsPanel;
import de.flower.rmt.ui.common.page.event.EventPagerPanel;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.model.InvitationModel;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.player.NavigationPanel;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

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
        setHeading("player.event.heading");

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
        addSecondaryPanel(createEventPagerPanel(model), new EventDetailsPanel(model), invitationFormPanel);
    }

    private EventPagerPanel createEventPagerPanel(final IModel<Event> model) {
        return new EventPagerPanel(model, getUpcomingEventList(new UserModel(getUserDetails().getUser()))) {
            @Override
            protected void onClick(IModel<Event> model) {
                setResponsePage(new EventPage(model));
            }

        };
    }

    private IModel<Invitation> getInvitationModel(final IModel<Event> model) {
        final Invitation invitation = invitationManager.loadByEventAndUser(model.getObject(), getUserDetails().getUser());
        return new InvitationModel(invitation);
    }

    private IModel<List<Event>> getUpcomingEventList(final UserModel userModel) {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAllUpcomingByUser(userModel.getObject());
            }
        };
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.EVENTS;
    }
}
