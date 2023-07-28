package de.flower.rmt.ui.page.event.player;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.service.EventManager;
import de.flower.rmt.service.InvitationManager;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.page.event.EventCanceledPanel;
import de.flower.rmt.ui.page.event.EventDetailsPanel;
import de.flower.rmt.ui.panel.DownloadIcsLink;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.mail.internet.InternetAddress;
import java.util.List;


public class EventSecondaryPanel extends BasePanel {

    @SpringBean
    private InvitationManager invitationManager;

    public EventSecondaryPanel(final IModel<Event> model, final IModel<Invitation> invitationModel) {
        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(new EventCanceledPanel(model));

        add(new SlideableInvitationFormPanel(invitationModel));

        add(new EventDetailsPanel(model));

        add(Links.mailLink("allMailLink", new LoadableDetachableModel<List<InternetAddress>>() {
            @Override
            protected List<InternetAddress> load() {
                return invitationManager.getAddressesForfAllInvitees(model.getObject());
            }
        } ));

        add(Links.mailLink("managerMailLink", getManagerEmailAddress(model.getObject()), null));

        add(new DownloadIcsLink("icsLink", model, "event.ics"));
    }

    private static String getManagerEmailAddress(final Event event) {
        return event.getCreatedBy().getEmail();
    }

    public static class SlideableInvitationFormPanel extends BasePanel<Invitation> {

        @SpringBean
        private InvitationManager invitationManager;

        @SpringBean
        private EventManager eventManager;

        public SlideableInvitationFormPanel(final IModel<Invitation> invitationModel) {
            super(invitationModel);
            IModel<Boolean> eventClosedModel = new AbstractReadOnlyModel<Boolean>() {
                @Override
                public Boolean getObject() {
                    return eventManager.isEventClosed(invitationModel.getObject().getEvent());
                }
            };
            InvitationFormPanel invitationFormPanel = new InvitationFormPanel(AjaxSlideTogglePanel.WRAPPED_PANEL_ID, invitationModel, eventClosedModel) {

                @Override
                protected void onSubmit(final Invitation invitation, final AjaxRequestTarget target) {
                    // save invitation and update invitationlistpanel
                    invitationManager.save(invitation, invitation.getComment());
                    AjaxEventSender.entityEvent(this, Invitation.class);
                }
            };

            add(new AjaxSlideTogglePanel("invitationFormPanel", "player.event.invitationform.heading", invitationFormPanel));

            // make form visible if user hasn't responded yet
            // must be called after adding to AjaxSlideTogglePanel
            invitationFormPanel.setVisible(invitationModel.getObject() != null && invitationModel.getObject().getStatus() == RSVPStatus.NORESPONSE);
        }

        @Override
        public boolean isVisible() {
            // completely hide panel if user is not invitee of this event.
            return getModel().getObject() != null;
        }

        @Override
        public String getPanelMarkup() {
            return "<div wicket:id='invitationFormPanel'/>";
        }
    }
}
