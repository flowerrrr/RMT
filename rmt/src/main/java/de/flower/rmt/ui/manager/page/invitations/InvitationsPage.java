package de.flower.rmt.ui.manager.page.invitations;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class InvitationsPage extends ManagerBasePage {

    @SpringBean
    private IEventManager eventManager;

     public InvitationsPage(IModel<Event> model) {
         addHeading("manager.invitations.heading", null);

         final InvitationListPanel invitationListPanel = new InvitationListPanel(model);
         addMainPanel(invitationListPanel);
         addSecondaryPanel(new InvitationPanel(model));
     }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }

}
