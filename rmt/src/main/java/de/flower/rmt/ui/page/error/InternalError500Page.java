package de.flower.rmt.ui.page.error;

import de.flower.rmt.ui.page.base.AbstractBaseLayoutPage;
import de.flower.rmt.ui.page.base.AnonymousNavigationPanel;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class InternalError500Page extends AbstractBaseLayoutPage {

    private final static Logger log = LoggerFactory.getLogger(InternalError500Page.class);

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
