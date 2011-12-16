package de.flower.rmt.ui.common.panel;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * @author flowerrrr
 */
public class AlertMessagesPanel extends FeedbackPanel {

    public AlertMessagesPanel(String id) {
        super(id);
        setOutputMarkupId(true);
    }

    @Override
    protected Component newMessageDisplayComponent(final String id, final FeedbackMessage message) {
        if (message.getMessage() instanceof AlertMessage) {
            AlertMessage alertMessage = (AlertMessage) message.getMessage();
            return new AlertMessagePanel(id, alertMessage);
        } else {
            return super.newMessageDisplayComponent(id, message);
        }
    }
}
