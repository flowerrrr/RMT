package de.flower.common.ui.alert;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * FeedbackPanel that can render AlertMessages.
 */
public class AlertMessageFeedbackPanel extends FeedbackPanel {

    public AlertMessageFeedbackPanel(String id) {
        super(id, new IFeedbackMessageFilter() {
            @Override
            public boolean accept(final FeedbackMessage message) {
                return message.getMessage() instanceof AlertMessage;
            }
        });
        setOutputMarkupId(true);
        add(new AjaxEventListener(AlertMessage.class));
    }

    @Override
    protected Component newMessageDisplayComponent(final String id, final FeedbackMessage message) {
        if (message.getMessage() instanceof AlertMessage) {
            AlertMessage alertMessage = (AlertMessage) message.getMessage();
            return new AlertMessagePanel(id, alertMessage);
        } else {
            throw new UnsupportedOperationException("Only alert messages are supported by this feedback panel!");
        }
    }
}
