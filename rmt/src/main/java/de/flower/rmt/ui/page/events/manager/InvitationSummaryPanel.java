package de.flower.rmt.ui.page.events.manager;

import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class InvitationSummaryPanel extends BasePanel {

    @SpringBean
    private IInvitationManager invitationManager;

    public InvitationSummaryPanel(IModel<Event> model) {

        add(new Label("accepted", createModel(model, RSVPStatus.ACCEPTED)));
        add(new Label("unsure", createModel(model, RSVPStatus.UNSURE)));
        add(new Label("declined", createModel(model, RSVPStatus.DECLINED)));
        add(new Label("noresponse", createModel(model, RSVPStatus.NORESPONSE)));
    }

    private IModel<Long> createModel(final IModel<Event> model, final RSVPStatus rsvpStatus) {
         return new LoadableDetachableModel<Long>() {
             @Override
             protected Long load() {
                 return invitationManager.numByEventAndStatus(model.getObject(), rsvpStatus);
             }
         };
     }
}
