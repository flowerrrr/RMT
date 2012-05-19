package de.flower.rmt.ui.page.event.player;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.model.InvitationModel;
import de.flower.rmt.ui.page.event.EventDetailsPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventSecondaryPanel extends BasePanel {

    @SpringBean
    private IInvitationManager invitationManager;

    @SpringBean
    private ISecurityService securityService;

    public EventSecondaryPanel(IModel<Event> model) {
        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        final IModel<Invitation> invitationModel = getInvitationModel(model);
        InvitationFormPanel invitationFormPanel = new InvitationFormPanel(AjaxSlideTogglePanel.WRAPPED_PANEL_ID, invitationModel) {

            @Override
            protected void onSubmit(final Invitation invitation, final AjaxRequestTarget target) {
                // save invitation and update invitationlistpanel
                invitationManager.save(invitation);
                AjaxEventSender.entityEvent(this, Invitation.class);
            }
        };

        add(new AjaxSlideTogglePanel("invitationFormPanel", "player.event.invitationform.heading", invitationFormPanel) {
            @Override
            public boolean isVisible() {
                // completely hide panel if user is not invitee of this event.
                return invitationModel.getObject() != null;
            }
        });

        // make form visible if user hasn't responded yet
        // must be called after adding to AjaxSlideTogglePanel
        invitationFormPanel.setVisible(invitationModel.getObject() != null && invitationModel.getObject().getStatus() == RSVPStatus.NORESPONSE);

        add(new EventDetailsPanel(model, View.PLAYER));

        add(Links.mailLink("allMailLink", invitationManager.getAddressesForfAllInvitees(model.getObject())));

        add(Links.mailLink("managerMailLink", getManagerEmailAddress(model.getObject()), null));
    }

    private IModel<Invitation> getInvitationModel(final IModel<Event> model) {
        final Invitation invitation = invitationManager.findByEventAndUser(model.getObject(), securityService.getUser());
        if (invitation != null) {
            return new InvitationModel(invitation);
        } else {
            //noinspection unchecked
            return new Model();
        }
    }

    private static String getManagerEmailAddress(final Event event) {
        return event.getCreatedBy().getEmail();
    }
}
