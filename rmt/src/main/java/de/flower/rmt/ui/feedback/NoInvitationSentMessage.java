package de.flower.rmt.ui.feedback;

import de.flower.common.ui.feedback.AlertMessage;
import de.flower.common.ui.feedback.AlertMessagePanel;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.ui.page.event.manager.EventPage;
import de.flower.rmt.ui.page.event.manager.EventTabPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author flowerrrr
 */
public class NoInvitationSentMessage extends AlertMessage {

    private IModel<Event> model;

    public NoInvitationSentMessage(final IModel<Event> model) {
        super(new ResourceModel("alert.message.event.noinvitationsent"),
                new ResourceModel("alert.message.button.send.invitation"));
        this.model = model;
    }

    @Override
    public boolean onClick(final AlertMessagePanel alertMessagePanel) {
        // pass name of tabbed panel to display when page is rendered
        PageParameters params = EventPage.getPageParams(model.getObject().getId());
        params.set(EventTabPanel.TAB_INDEX_KEY, EventTabPanel.NOTIFICATION_PANEL_INDEX);
        alertMessagePanel.setResponsePage(EventPage.class, params);
        return false; // don't hide until user has actually sent an invitation
    }

    @Override
    public boolean isVisible(final AlertMessagePanel alertMessagePanel) {
        return !model.getObject().isInvitationSent() && !model.getObject().isNew();
    }

    @Override
    public String getSessionKey() {
        return super.getSessionKey() + model.getObject().getId();
    }
}
