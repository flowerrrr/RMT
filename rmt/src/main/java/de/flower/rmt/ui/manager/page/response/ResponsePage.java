package de.flower.rmt.ui.manager.page.response;

import de.flower.common.util.Check;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.model.EventModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class ResponsePage extends ManagerBasePage {

    @SpringBean
    private IEventManager eventManager;

     public ResponsePage(Long eventId) {

         final Event event = Check.notNull(eventManager.findById(eventId, Event_.invitations));
         WebMarkupContainer noInvitationsPanel = new WebMarkupContainer("noInvitationsPanel");
         noInvitationsPanel.setVisible(event.getInvitations().isEmpty());
         noInvitationsPanel.add(new AjaxLink("sendInvitationButton") {
             @Override
             public void onClick(AjaxRequestTarget target) {
                 // setResponsePage(new InvitationPage());
                 throw new UnsupportedOperationException("Feature not implemented!");
             }
         })  ;
         add(noInvitationsPanel);

         add(new ResponseListPanel("responseListPanel", new EventModel(event)));
     }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }

}
