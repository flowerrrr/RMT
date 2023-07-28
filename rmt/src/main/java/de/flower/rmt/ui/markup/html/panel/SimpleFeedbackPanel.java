package de.flower.rmt.ui.markup.html.panel;

import de.flower.common.ui.panel.BasePanel;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Simple wrapper for wicket FeedbackPanel to simplify styling.
 */
public class SimpleFeedbackPanel extends BasePanel {

    public SimpleFeedbackPanel(final IFeedbackMessageFilter filter) {
        final FeedbackPanel feedback;
        add(feedback = new FeedbackPanel("feedback", filter));
        feedback.setOutputMarkupId(true);
        // in order to use html markup in the messages
        feedback.setEscapeModelStrings(false);
     }


}

