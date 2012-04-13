package de.flower.rmt.ui.player.page.event;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.common.page.event.EventDetailsPanel;
import de.flower.rmt.ui.common.page.event.EventSelectPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.InvitationModel;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventSecondaryPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

    @SpringBean
    private IInvitationManager invitationManager;

    public EventSecondaryPanel(IModel<Event> model) {
        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(createEventSelectPanel(model));

        IModel<Invitation> invitationModel = getInvitationModel(model);
        InvitationFormPanel invitationFormPanel = new InvitationFormPanel(AjaxSlideTogglePanel.WRAPPED_PANEL_ID, invitationModel) {

            @Override
            protected void onSubmit(final Invitation invitation, final AjaxRequestTarget target) {
                // save invitation and update invitationlistpanel
                invitationManager.save(invitation);
                AjaxEventSender.entityEvent(this, Invitation.class);
            }
        };

        add(new AjaxSlideTogglePanel("invitationFormPanel", "player.event.invitationform.heading", invitationFormPanel));
        // make form visible if user hasn't responded yet
        invitationFormPanel.setVisible(invitationModel.getObject().getStatus() == RSVPStatus.NORESPONSE);

        add(new EventDetailsPanel(model, View.PLAYER));
    }

    private Panel createEventSelectPanel(final IModel<Event> model) {
        return new EventSelectPanel(model, getUpcomingEventList()) {

            @Override
            protected void onClick(final IModel<Event> model) {
                setResponsePage(new EventPage(model));
            }
        };
    }

    private IModel<Invitation> getInvitationModel(final IModel<Event> model) {
        final Invitation invitation = invitationManager.loadByEventAndUser(model.getObject(), getUserDetails().getUser());
        return new InvitationModel(invitation);
    }

    private IModel<List<Event>> getUpcomingEventList() {
        final IModel<User> userModel = new UserModel(getUserDetails().getUser());
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAllUpcomingByUser(userModel.getObject());
            }
        };
    }
}
