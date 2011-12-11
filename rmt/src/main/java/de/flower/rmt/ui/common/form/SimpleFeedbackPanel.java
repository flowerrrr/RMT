package de.flower.rmt.ui.common.form;

import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Simple wrapper for wicket FeedbackPanel to simplify styling.
 * @author flowerrrr
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

