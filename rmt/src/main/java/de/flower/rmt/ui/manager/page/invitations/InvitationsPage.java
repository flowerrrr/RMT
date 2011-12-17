package de.flower.rmt.ui.manager.page.invitations;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class InvitationsPage extends ManagerBasePage {

    @SpringBean
    private IEventManager eventManager;

    public InvitationsPage(IModel<Event> model) {
        super(model);
        setHeading("manager.invitations.heading", null);

        final InvitationListPanel invitationListPanel = new InvitationListPanel(model);
        addMainPanel(invitationListPanel);
        addSecondaryPanel(new InvitationPanel(model));
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
