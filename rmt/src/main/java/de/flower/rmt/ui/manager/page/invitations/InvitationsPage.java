package de.flower.rmt.ui.manager.page.invitations;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class InvitationsPage extends ManagerBasePage {

    public InvitationsPage(IModel<Event> model) {
        super(model);
        setHeading("manager.invitations.heading", null);

        final InvitationListPanel invitationListPanel = new InvitationListPanel(model);
        addMainPanel(invitationListPanel);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        // makes messages back-button and reload-save. must be called after super.onBeforeRender to have this
        // message listed after messages of super-page
        // check if an invitation has been sent for this event already
        info(new NoInvitationSentMessage((IModel<Event>) getDefaultModel()));
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.EVENTS;
    }
}
