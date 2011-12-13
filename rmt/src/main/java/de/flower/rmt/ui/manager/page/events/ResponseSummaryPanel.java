package de.flower.rmt.ui.manager.page.events;

import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IPlayerManager;
import de.flower.rmt.service.IResponseManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class ResponseSummaryPanel extends BasePanel {

    @SpringBean
    private IResponseManager responseManager;

    @SpringBean
    private IPlayerManager playerManager;

    public ResponseSummaryPanel(IModel<Event> model) {

        add(new Label("accepted", createModel(model, RSVPStatus.ACCEPTED)));
        add(new Label("unsure", createModel(model, RSVPStatus.UNSURE)));
        add(new Label("declined", createModel(model, RSVPStatus.DECLINED)));
        add(new Label("noresponse", getNotResponderList(model)));
    }

    private IModel<Long> createModel(final IModel<Event> model, final RSVPStatus rsvpStatus) {
         return new LoadableDetachableModel<Long>() {
             @Override
             protected Long load() {
                 return responseManager.numByEventAndStatus(model.getObject(), rsvpStatus);
             }
         };
     }

     private IModel<Long> getNotResponderList(final IModel<Event> model) {
         return new LoadableDetachableModel<Long>() {
             @Override
             protected Long load() {
                 return playerManager.numNotResponded(model.getObject());
             }
         };
     }

}
