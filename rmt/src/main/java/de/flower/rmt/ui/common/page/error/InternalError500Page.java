package de.flower.rmt.ui.common.page.error;

import de.flower.rmt.ui.common.page.AbstractBaseLayoutPage;
import de.flower.rmt.ui.common.page.AnonymousNavigationPanel;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;

import java.util.List;

/**
 * @author flowerrrr
 */
public class InternalError500Page extends AbstractBaseLayoutPage {

    public InternalError500Page() {
        setHeading("error.500.heading", "error.500.heading.sub");
        add(new AnonymousNavigationPanel());
        addMainPanel(new InternalError500Panel(getException()));
    }

    @Override
    protected boolean showAlertMessages() {
        // not nice to see alert messages on error pages. confuses the user.
        return false;
    }

    private Exception getException() {
        List<FeedbackMessage> messages = getSession().getFeedbackMessages().messages(new IFeedbackMessageFilter() {
            @Override
            public boolean accept(final FeedbackMessage message) {
                return message.getMessage() instanceof Exception;
            }
        });
        if (messages.isEmpty()) {
            log.warn("Could not extract exception from session");
            return null;
        } else if (messages.size() > 1) {
            log.warn("More than one exception found in sesion. Did you cleanup feedbackmessages properly?");
        }
        FeedbackMessage message = messages.get(messages.size() - 1);
        message.markRendered();
        return (Exception) message.getMessage();
    }
}
