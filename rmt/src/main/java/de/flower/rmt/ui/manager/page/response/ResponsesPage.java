package de.flower.rmt.ui.manager.page.response;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class ResponsesPage extends ManagerBasePage {

    @SpringBean
    private IEventManager eventManager;

     public ResponsesPage(IModel<Event> model) {
         addHeading("manager.responses.heading", null);

         final ResponseListPanel responseListPanel = new ResponseListPanel(model);
         addMainPanel(responseListPanel);
         addSecondaryPanel(new InvitationPanel(model));
     }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }

}
